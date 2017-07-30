package larisa.jsf.data;

import com.google.common.base.Strings;
import larisa.entity.File;
import larisa.jsf.DefaultDataModel;
import larisa.jsf.File2StreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.context.FacesContext;

/**
 * Created by home on 24.02.17.
 */
@Component
@Scope("session")
public class FileData extends DefaultDataModel<File, Integer> {

    public StreamedContent getImage() {
        FacesContext fc = FacesContext.getCurrentInstance();
        String idStr = fc.getExternalContext().getRequestParameterMap().get("File");
        if (!Strings.isNullOrEmpty(idStr)) {
            return findImage(new Integer(idStr));
        } else {
            return File2StreamedContent.get().empty();
        }
    }

    public StreamedContent findImage(Integer id) {
        return File2StreamedContent.get().apply(getRepository().findOne(id));
    }


}
