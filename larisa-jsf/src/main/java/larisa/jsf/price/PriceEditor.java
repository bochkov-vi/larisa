package larisa.jsf.price;

import larisa.entity.Price;
import larisa.entity.ProductType;
import larisa.jsf.ContextHolder;
import larisa.jsf.DefaultEditorBean;
import larisa.jsf.productType.ProductTypeEditor;
import org.joda.time.LocalDate;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by home on 24.02.17.
 */
@Component
@Scope("view")
public class PriceEditor extends DefaultEditorBean<Price, Integer> {


    public PriceEditor() {

    }

    @Override
    public Price createNewInstance() {
        ProductType productType = ContextHolder.context.getBean(ProductTypeEditor.class).entityFromRequest();
        Price price = super.createNewInstance();
        price.setDateFrom(new LocalDate());
        price.setProductType(productType);
        return price;
    }
}
