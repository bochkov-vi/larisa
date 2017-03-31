package larisa.jsf.outgo;

import larisa.entity.Outgo;
import larisa.jsf.DefaultEditorBean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by home on 24.02.17.
 */
@Component
@Scope("view")
public class OutgoEditor extends DefaultEditorBean<Outgo, Integer> {


    public OutgoEditor() {

    }


}
