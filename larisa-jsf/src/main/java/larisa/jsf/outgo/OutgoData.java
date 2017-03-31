package larisa.jsf.outgo;

import larisa.entity.Maker;
import larisa.entity.Outgo;
import larisa.entity.ProductType;
import larisa.jsf.ContextHolder;
import larisa.jsf.DefaultDataBean;
import larisa.jsf.maker.MakerEditor;
import larisa.jsf.productType.ProductTypeEditor;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;

/**
 * Created by home on 24.02.17.
 */
@Service
@Scope("session")
public class OutgoData extends DefaultDataBean<Outgo, Integer> {

    Maker maker;

    ProductType productType;

    LocalDate dateFrom;

    LocalDate dateTo;


    @Override
    protected Specification<Outgo> getSpecification(Map<String, Object> filters) {
        return Specifications.where(super.getSpecification(filters)).and(getSpecification());
    }

    Specification<Outgo> getSpecification() {
        Specifications<Outgo> result = Specifications.where((root, criteriaQuery, criteriaBuilder) -> null);
        if (getMaker() != null) {
            result = result.and((root, query, cb) -> cb.equal(root.get("productType").get("maker"), getMaker()));
        }
        if (getProductType() != null) {
            result = result.and((root, query, cb) -> cb.equal(root.get("productType"), getProductType()));
        }
        if (getDateFrom() != null) {
            result = result.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("date"), getDateFrom()));
        }
        if (getDateTo() != null) {
            result = result.and((root, query, cb) -> cb.lessThanOrEqualTo(root.get("date"), getDateTo()));
        }
        return result;
    }

    public Maker getMaker() {
        MakerEditor makerEditor = ContextHolder.context.getBean(MakerEditor.class);
        if (makerEditor.entityFromRequest() != null) {
            this.maker = makerEditor.entityFromRequest();

        }
        return maker;
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

    public ProductType getProductType() {
        ProductTypeEditor productTypeEditor = ContextHolder.context.getBean(ProductTypeEditor.class);
        if (productTypeEditor.entityFromRequest() != null) {
            this.productType = productTypeEditor.entityFromRequest();
        }
        return this.productType;
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
}
