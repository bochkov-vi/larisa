package larisa.jsf.data;

import larisa.entity.ProductType;
import larisa.jsf.DefaultDataModel;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import java.util.Map;

/**
 * Created by home on 24.02.17.
 */

public class ProductTypeData extends DefaultDataModel<ProductType, Integer> {


    @Override
    protected Specification<ProductType> getSpecification(Map<String, Object> filters) {
        return Specifications.where(super.getSpecification(filters)).and(getSpecification());
    }

    Specification<ProductType> getSpecification() {
        Specifications<ProductType> result = Specifications.where((root, criteriaQuery, criteriaBuilder) -> null);
        if (filter.getMaker() != null) {
            result = result.and((root, query, cb) -> cb.equal(root.get("maker"), filter.getMaker()));
        }
        return result;
    }
    
}
