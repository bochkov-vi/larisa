package larisa.jsf.product;

import larisa.entity.Product;
import larisa.entity.ProductType;
import larisa.jsf.ContextHolder;
import larisa.jsf.file.FileEditor;
import larisa.jsf.productType.ProductTypeEditor;
import org.joda.time.LocalDate;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by home on 24.02.17.
 */
@Component
@Scope("view")
public class ProductEditor extends FileEditor<Product, Integer> {


    private boolean productTypeEdit =false;

    public ProductEditor() {

    }

    @Override
    public Product createNewInstance() {
        ProductType productType = ContextHolder.context.getBean(ProductTypeEditor.class).entityFromRequest();
        Product product = super.createNewInstance();
        product.setDate(new LocalDate());
        product.setProductType(productType);
        return product;
    }

    public boolean getProductTypeEdit() {
        return productTypeEdit;
    }

    public boolean isProductTypeEdit() {
        return productTypeEdit;
    }

    public void setProductTypeEdit(boolean productTypeEdit) {
        this.productTypeEdit = productTypeEdit;
    }
}
