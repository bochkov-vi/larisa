package larisa.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by home on 23.02.17.
 */
@Entity
@Table(name = "price", uniqueConstraints = {@UniqueConstraint(name = "price_unique", columnNames = {"id_product_type", "date_from"})})
public class Price extends AbstractAuditableEntity<Integer> {
    @Id
    @GeneratedValue(generator = "price")
    @Column(name = "id_price")
    @TableGenerator(name = "price", initialValue = 100, allocationSize = 1)
    Integer id;

    @ManyToOne
    @JoinColumn(name = "id_product_type")
    ProductType productType;


    @Temporal(TemporalType.DATE)
    @Column(name = "date_from", nullable = false)
    Date dateFrom;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_to", nullable = true)
    Date dateTo;

    @Column(name = "price", precision = 4, scale = 2, nullable = true)
    Double price;

    @Override
    public Integer getId() {
        return id;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
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
