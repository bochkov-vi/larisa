package larisa.jsf.productType;

import larisa.entity.ProductType;
import larisa.entity.Seller;
import larisa.jsf.ContextHolder;
import larisa.jsf.DefaultDataBean;
import larisa.jsf.seller.SellerEditor;
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

    Seller seller;

    @Override
    protected Specification<ProductType> getSpecification(Map<String, Object> filters) {
        return Specifications.where(super.getSpecification(filters)).and(getSpecification());
    }

    Specification<ProductType> getSpecification() {
        Specifications<ProductType> result = Specifications.where((root, criteriaQuery, criteriaBuilder) -> null);
        if (getSeller() != null) {
            result = result.and((root, query, cb) -> cb.equal(root.get("seller"), getSeller()));
        }
        return result;
    }

    public Seller getSeller() {
        SellerEditor sellerEditor = ContextHolder.context.getBean(SellerEditor.class);
        if (sellerEditor.entityFromRequest() != null) {
            this.seller = sellerEditor.entityFromRequest();

        }
        return seller;
    }

    public void clear(){
        this.seller=null;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }
}
