package larisa.entity;


import javax.persistence.*;
import java.util.Date;

/**
 * Created by home on 23.02.17.
 */
@Entity
@Table(name = "outgo")
public class Outgo extends AbstractAuditableEntity<Integer> {
    @Id
    @GeneratedValue
    @Column(name = "id_outgo")
    Integer id;

    @Temporal(TemporalType.DATE)
    @Column(name = "date", nullable = false)
    Date date;

    @Column(name = "volume", precision = 4, scale = 2, nullable = false)
    Integer volume;

    @Column(name = "price", precision = 4, scale = 2)
    Double price;

    @ManyToOne
    @JoinColumn(name = "id_product_type")
    ProductType productType;


    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
