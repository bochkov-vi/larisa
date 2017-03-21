package larisa.jsf.maker;

import larisa.entity.Maker;
import larisa.jsf.file.FileEditor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by home on 24.02.17.
 */
@Component
@Scope("view")
public class MakerEditor extends FileEditor<Maker, Integer> {



    public MakerEditor() {

    }

}
