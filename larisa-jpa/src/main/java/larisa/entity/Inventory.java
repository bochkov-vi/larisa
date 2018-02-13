package larisa.entity;


import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by home on 23.02.17.
 */
@Entity
@Table(name = "inventory")
public class Inventory extends AbstractAuditableEntity<Integer> implements IGetFiles {
    @Id
    @GeneratedValue(generator = "inventory")
    @Column(name = "id_inventory")
    @TableGenerator(name = "inventory", initialValue = 100)
    Integer id;

    @Column(name = "date", nullable = true)
    @Temporal(TemporalType.DATE)
    Date date;

    @ManyToOne
    @JoinColumn(name = "id_product_type")
    ProductType productType;

    @Column(name = "volume", precision = 4, scale = 2)
    Double volume;

    @Column(name = "price", precision = 4, scale = 2)
    Double price;

    @Column(name = "expiration_date")
    @Temporal(TemporalType.DATE)
    Date expirationDate;


    @ManyToMany
    @JoinTable(name = "inventory_file", joinColumns = @JoinColumn(name = "id_inventory"), inverseJoinColumns = @JoinColumn(name = "id_file"))
    List<File> files;

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public List<File> getFiles() {
        return files;
    }

    @Override
    public void setFiles(List<File> files) {
        this.files = files;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
