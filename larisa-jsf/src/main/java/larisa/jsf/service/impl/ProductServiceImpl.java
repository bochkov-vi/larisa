package larisa.jsf.service.impl;

import larisa.entity.Product;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Service;

/**
 * Created by home on 24.02.17.
 */
@Service("productService")
public class ProductServiceImpl extends DefaultServiceImpl<Product, Integer> {

    @Override
    public Product createNewInstance() {
        Product product = super.createNewInstance();
        product.setDate(new LocalDate());
        return product;
    }
}
