package larisa.entity;

import org.entity3.column.ColumnPosition;
import org.entity3.converter.JodaLocalDateConverter;
import org.joda.time.LocalDate;

import javax.persistence.*;
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
    @ColumnPosition(1)
    @TableGenerator(name = "inventory", initialValue = 100)
    Integer id;

    @Column(name = "date", nullable = true)
    @Temporal(TemporalType.DATE)
    @Convert(converter = JodaLocalDateConverter.class)
    @ColumnPosition(2)
    LocalDate date;

    @ManyToOne
    @JoinColumn(name = "id_product_type")
    @ColumnPosition(3)
    ProductType productType;

    @Column(name = "volume", precision = 4, scale = 2)
    @ColumnPosition(4)
    Double volume;

    @Column(name = "price", precision = 4, scale = 2)
    @ColumnPosition(5)
    Double price;

    @Column(name = "expiration_date")
    @Temporal(TemporalType.DATE)
    @Convert(converter = JodaLocalDateConverter.class)
    @ColumnPosition(6)
    LocalDate expirationDate;


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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
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
