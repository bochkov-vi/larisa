package larisa.jsf.productType;

import larisa.entity.ProductType;
import larisa.jsf.DefaultJsfService;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by home on 24.02.17.
 */
@Component
@Scope("view")
public class ProductTypeEditor extends DefaultJsfService<ProductType, Integer> {

    public ProductTypeEditor() {

    }

    @Override
    public ProductType createNewInstance() {
        ProductType productType = super.createNewInstance();
        ProductType template = entityFromRequest("clone");
        if (template != null) {
            BeanUtils.copyProperties(template, productType, "id", "createdDate");
        }
        return productType;
    }
}
