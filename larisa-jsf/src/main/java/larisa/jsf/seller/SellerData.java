package larisa.jsf.seller;

import larisa.entity.Seller;
import larisa.jsf.DefaultDataBean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by home on 24.02.17.
 */
@Component
@Scope("session")
public class SellerData extends DefaultDataBean<Seller, Integer> {

}
