package larisa.entity;


import javax.persistence.*;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by home on 23.02.17.
 */
@Entity
@Table(name = "product")
public class Product extends AbstractAuditableEntity<Integer> implements IGetFiles {
    @Id
    @GeneratedValue(generator = "product")
    @TableGenerator(name = "product", initialValue = 100, allocationSize = 1)
    @Column(name = "id_product")
    Integer id;

    @ManyToOne
    @JoinColumn(name = "id_product_receipt", nullable = false)
    ProductReceipt productReceipt;

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
    @JoinTable(name = "product_file", joinColumns = @JoinColumn(name = "id_product"), inverseJoinColumns = @JoinColumn(name = "id_file"))
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

    public ProductReceipt getProductReceipt() {
        return productReceipt;
    }

    public void setProductReceipt(ProductReceipt productReceipt) {
        this.productReceipt = productReceipt;
    }

    @Override
    public String toString() {
        return MessageFormat.format("Поступление от {0,date}, \"{1}\" \"{2}\", {3}шт. по {4,number,currency}", productReceipt.getDate(), productType,
                Optional.ofNullable(productType).flatMap(pt -> Optional.ofNullable(pt.getMaker()).map(maker -> maker.getName())).orElse(""), volume, price);
    }
}
