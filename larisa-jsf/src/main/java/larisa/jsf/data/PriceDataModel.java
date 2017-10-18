package larisa.jsf.data;

import larisa.entity.Price;
import larisa.jsf.DefaultDataModel;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import java.util.Map;

/**
 * Created by home on 24.02.17.
 */

public class PriceDataModel extends DefaultDataModel<Price, Integer> {




    @Override
    protected Specification<Price> getSpecification(Map<String, Object> filters) {
        return Specifications.where(super.getSpecification(filters)).and(getSpecification());
    }

    Specification<Price> getSpecification() {
        Specifications<Price> result = Specifications.where((root, criteriaQuery, criteriaBuilder) -> null);
        if (filter.getMaker() != null) {
            result = result.and((root, query, cb) -> cb.equal(root.get("productType").get("maker"), filter.getMaker()));
        }
        if (filter.getProductType() != null) {
            result = result.and((root, query, cb) -> cb.equal(root.get("productType"),filter. getProductType()));
        }
        if (filter.getDateFrom() != null) {
            result = result.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("dateTo"), filter.getDateFrom()));
        }
        if (filter.getDateTo() != null) {
            result = result.and((root, query, cb) -> cb.lessThanOrEqualTo(root.get("dateFrom"),filter. getDateTo()));
        }
        return result;
    }




}
