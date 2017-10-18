package larisa.jsf.data;

import larisa.entity.Maker;
import larisa.entity.ProductType;
import org.joda.time.LocalDate;

import java.io.Serializable;

public class FilterBean implements Serializable {

    LocalDate dateFrom;
    LocalDate dateTo;
    ProductType productType;
    Maker maker;
    boolean hide = true;

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

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public Maker getMaker() {
        return maker;
    }

    public void setMaker(Maker maker) {
        this.maker = maker;
    }

    public boolean isHide() {
        return hide;
    }

    public void setHide(boolean hide) {
        this.hide = hide;
    }

    public void clear() {
        this.dateFrom = null;
        this.dateTo = null;
        hide = true;
        maker = null;
        productType = null;
    }
}
