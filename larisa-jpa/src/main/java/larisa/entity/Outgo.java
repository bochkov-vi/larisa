package larisa.entity;

import org.entity3.column.ColumnPosition;
import org.entity3.converter.JodaLocalDateConverter;
import org.joda.time.LocalDate;

import javax.persistence.*;

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
    @Convert(converter = JodaLocalDateConverter.class)
    @Column(name = "date", nullable = false)
    LocalDate date;

    @Column(name = "volume", precision = 4, scale = 2,nullable = false)
    @ColumnPosition(4)
    Integer volume;

    @Column(name = "price", precision = 4, scale = 2)
    @ColumnPosition(4)
    Double price;

    @ManyToOne
    @JoinColumn(name = "id_product_type")
    @ColumnPosition(3)
    ProductType productType;




    @Override
    public Integer getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
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

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
