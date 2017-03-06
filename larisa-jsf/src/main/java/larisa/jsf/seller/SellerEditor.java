package larisa.jsf.seller;

import larisa.entity.Seller;
import larisa.jsf.productType.FileEditor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by home on 24.02.17.
 */
@Component
@Scope("view")
public class SellerEditor extends FileEditor<Seller,Integer> {
    public SellerEditor() {

    }

}
