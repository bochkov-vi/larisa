package larisa.jsf.data;

import larisa.entity.Maker;
import larisa.entity.Price;
import larisa.entity.ProductType;
import larisa.jsf.DefaultDataModel;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import java.time.LocalDate;
import java.util.Map;

/**
 * Created by home on 24.02.17.
 */

public class PriceData extends DefaultDataModel<Price, Integer> {

    Maker maker;

    ProductType productType;

    LocalDate dateFrom;

    LocalDate dateTo;


    @Override
    protected Specification<Price> getSpecification(Map<String, Object> filters) {
        return Specifications.where(super.getSpecification(filters)).and(getSpecification());
    }

    Specification<Price> getSpecification() {
        Specifications<Price> result = Specifications.where((root, criteriaQuery, criteriaBuilder) -> null);
        if (getMaker() != null) {
            result = result.and((root, query, cb) -> cb.equal(root.get("productType").get("maker"), getMaker()));
        }
        if (getProductType() != null) {
            result = result.and((root, query, cb) -> cb.equal(root.get("productType"), getProductType()));
        }
        if (getDateFrom() != null) {
            result = result.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("dateTo"), getDateFrom()));
        }
        if (getDateTo() != null) {
            result = result.and((root, query, cb) -> cb.lessThanOrEqualTo(root.get("dateFrom"), getDateTo()));
        }
        return result;
    }



    public void setMaker(Maker maker) {
        this.maker = maker;
    }

    public void clear() {
        this.maker = null;
        this.productType = null;
        dateFrom = null;
        dateTo = null;
    }



    public void setProductType(ProductType productType) {
        this.productType = productType;
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

    public Maker getMaker() {
        return maker;
    }

    public ProductType getProductType() {
        return productType;
    }
}
