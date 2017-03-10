package larisa.jsf.productType;

import larisa.entity.ProductType;
import larisa.service.ProductTypeService;
import org.entity3.repository.CustomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by home on 24.02.17.
 */
@Component("productTypeEditor")
@Scope("view")
public class ProductTypeEditor extends FileEditor<ProductType, Integer> {
    @Autowired
    ProductTypeService service;

    public ProductTypeEditor() {

    }

    @Override
    protected CustomRepository<ProductType, Integer> getRepository() {
        return service;
    }
}
