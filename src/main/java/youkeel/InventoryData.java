package youkeel;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Table(name = "inventory")
@EntityListeners(AuditingEntityListener.class)

public class InventoryData implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String product_name;

    private Double quantity;

    private Double rate;

    public String getProduct_name(){
        return product_name;
    }

    public void setProduct_name(String name){
        product_name=name;
    }

    public Double getQuantity() {
        return quantity;
    }

    public Double getRate() {
        return rate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }
}

