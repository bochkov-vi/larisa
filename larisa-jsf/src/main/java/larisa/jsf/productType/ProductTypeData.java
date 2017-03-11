package larisa.jsf.productType;

import larisa.entity.Maker;
import larisa.entity.ProductType;
import larisa.jsf.ContextHolder;
import larisa.jsf.DefaultDataBean;
import larisa.jsf.maker.MakerEditor;
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
public class ProductTypeData extends DefaultDataBean<ProductType, Integer> {

    Maker maker;

    @Override
    protected Specification<ProductType> getSpecification(Map<String, Object> filters) {
        return Specifications.where(super.getSpecification(filters)).and(getSpecification());
    }

    Specification<ProductType> getSpecification() {
        Specifications<ProductType> result = Specifications.where((root, criteriaQuery, criteriaBuilder) -> null);
        if (getMaker() != null) {
            result = result.and((root, query, cb) -> cb.equal(root.get("maker"), getMaker()));
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

    public void clear(){
        this.maker =null;
    }

    public void setMaker(Maker maker) {
        this.maker = maker;
    }
}
