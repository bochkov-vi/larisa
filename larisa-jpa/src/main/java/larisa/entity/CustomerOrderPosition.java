package larisa.entity;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.entity3.column.ColumnPosition;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class CustomerOrderPosition implements Serializable {

    @ManyToOne
    @JoinColumn(name = "id_product_type")
    @ColumnPosition(6)
    ProductType productType;

    @Column(name = "price", precision = 4, scale = 2)
    @ColumnPosition(7)
    Double price;

    @Column(name = "volume", precision = 4, scale = 2, nullable = false)
    @ColumnPosition(8)
    Integer volume;


    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomerOrderPosition)) {
            return false;
        }
        CustomerOrderPosition that = (CustomerOrderPosition) o;
        return Objects.equal(getProductType(), that.getProductType());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getProductType());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("productType", productType)
                .add("price", price)
                .add("volume", volume)
                .toString();
    }
}
