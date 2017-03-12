package larisa.entity;

import org.entity3.column.ColumnPosition;
import org.entity3.converter.JodaLocalDateConverter;
import org.joda.time.LocalDate;

import javax.persistence.*;

/**
 * Created by home on 23.02.17.
 */
@Entity
@Table(name = "price")
public class Price extends AbstractAuditableEntity<Integer> {
    @Id
    @GeneratedValue
    @Column(name = "id_price")
    @ColumnPosition(1)
    Integer id;

    @ManyToOne
    @JoinColumn(name = "id_product_type")
    @ColumnPosition(2)
    ProductType productType;


    @Temporal(TemporalType.DATE)
    @Convert(converter = JodaLocalDateConverter.class)
    @Column(name = "date_from", nullable = false)
    @ColumnPosition(3)
    LocalDate dateFrom;

    @Temporal(TemporalType.DATE)
    @Convert(converter = JodaLocalDateConverter.class)
    @Column(name = "date_to", nullable = false)
    @ColumnPosition(4)
    LocalDate dateTo;

    @Column(name = "price", precision = 4, scale = 2,nullable = true)
    @ColumnPosition(5)
    Double price;

    @Override
    public Integer getId() {
        return id;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }
}
