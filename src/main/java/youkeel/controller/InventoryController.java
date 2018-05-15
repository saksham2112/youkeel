package youkeel.controller;

import youkeel.config.S3Wrapper;
import youkeel.InventoryData;
import youkeel.repository.InventoryRepository;
import youkeel.ResourceNotFoundException;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class InventoryController {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private S3Wrapper s3Wrapper;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<S3ObjectSummary> list() throws IOException {
        return s3Wrapper.list();
    }
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public List<PutObjectResult> upload(@RequestParam("file") MultipartFile[] multipartFiles) {
        return s3Wrapper.upload(multipartFiles);
    }
    @RequestMapping(value="/", method=RequestMethod.GET)
    public String getFirstPage() {
        return "First Page";
    }

    @RequestMapping(value="/products", method=RequestMethod.GET)
    public List<InventoryData> getAllNotes() {
        return inventoryRepository.findAll();
    }


    @PostMapping("/products")
    public InventoryData createNote(@Valid @RequestBody InventoryData item) {

        Map map=new HashMap();
        Map collections=  Collections.synchronizedMap(map);
        return inventoryRepository.save(item);

    }


    @GetMapping("/products/{id}")
    public InventoryData getNoteById(@PathVariable(value = "id") Long productId) {
        return inventoryRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("InventoryData", "id", productId));
    }

    // Update a Note
    @PutMapping("/products/{id}")
    public InventoryData updateNote(@PathVariable(value = "id") Long productId,
                           @Valid @RequestBody InventoryData productDetails) {

        InventoryData product = inventoryRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("InventoryData", "id", productId));

        product.setProduct_name(productDetails.getProduct_name());
        product.setQuantity(productDetails.getQuantity());
        product.setRate(productDetails.getRate());

        InventoryData updatedNote = inventoryRepository.save(product);
        return updatedNote;
    }

    // Delete a Note
    @DeleteMapping("/products/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable(value = "id") Long productId) {
        InventoryData note = inventoryRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("InventoryData", "id", productId));

        inventoryRepository.delete(note);

        return ResponseEntity.ok().build();
    }
}

