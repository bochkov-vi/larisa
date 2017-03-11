package larisa.jsf.maker;

import larisa.entity.Maker;
import larisa.jsf.DefaultDataBean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by home on 24.02.17.
 */
@Component
@Scope("session")
public class MakerData extends DefaultDataBean<Maker, Integer> {

}
