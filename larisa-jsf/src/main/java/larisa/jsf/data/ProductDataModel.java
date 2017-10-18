package larisa.jsf.data;

import larisa.entity.Product;
import larisa.jsf.DefaultDataModel;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import java.util.Map;

/**
 * Created by home on 24.02.17.
 */

public class ProductDataModel extends DefaultDataModel<Product, Integer> {


    @Override
    protected Specification<Product> getSpecification(Map<String, Object> filters) {
        return Specifications.where(super.getSpecification(filters)).and(getSpecification());
    }

    Specification<Product> getSpecification() {
        Specifications<Product> result = Specifications.where((root, criteriaQuery, criteriaBuilder) -> null);
        if (filter.getMaker() != null) {
            result = result.and((root, query, cb) -> cb.equal(root.get("maker"), filter.getMaker()));
        }
        if(filter.getProductType()!=null){
            result = result.and((root, query, cb) -> cb.equal(root.get("productType"), filter.getProductType()));
        }
        if(filter.getDateFrom()!=null){
            result = result.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("date"), filter.getDateFrom()));
        }
        if(filter.getDateTo()!=null){
            result = result.and((root, query, cb) -> cb.lessThanOrEqualTo(root.get("date"), filter.getDateTo()));
        }
        return result;
    }

}
