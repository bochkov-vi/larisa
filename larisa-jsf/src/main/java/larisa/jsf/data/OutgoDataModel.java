package larisa.jsf.data;

import larisa.entity.Outgo;
import larisa.jsf.DefaultDataModel;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by home on 24.02.17.
 */
@Service
@Scope("session")
public class OutgoDataModel extends DefaultDataModel<Outgo, Integer> {



    @Override
    protected Specification<Outgo> getSpecification(Map<String, Object> filters) {
        return Specifications.where(super.getSpecification(filters)).and(getSpecification());
    }

    Specification<Outgo> getSpecification() {
        Specifications<Outgo> result = Specifications.where((root, criteriaQuery, criteriaBuilder) -> null);
        if (filter.maker != null) {
            result = result.and((root, query, cb) -> cb.equal(root.get("productType").get("maker"), filter.maker));
        }
        if (filter.productType != null) {
            result = result.and((root, query, cb) -> cb.equal(root.get("productType"), filter.productType));
        }
        if (filter.getDateFrom() != null) {
            result = result.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("date"), filter.getDateFrom()));
        }
        if (filter.getDateTo() != null) {
            result = result.and((root, query, cb) -> cb.lessThanOrEqualTo(root.get("date"), filter.getDateTo()));
        }
        return result;
    }


}
