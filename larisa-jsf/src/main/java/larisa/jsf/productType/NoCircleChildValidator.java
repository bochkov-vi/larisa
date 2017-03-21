package larisa.jsf.productType;

import jsf.util3.JsfUtil;
import jsf.util3.validator.CircleHierarchicalValidator;
import org.entity3.IHierarchical;

import javax.faces.validator.FacesValidator;
import javax.faces.validator.ValidatorException;

/**
 * Created by home on 21.03.17.
 */
@FacesValidator("noCircleChildValidator")
public class NoCircleChildValidator extends CircleHierarchicalValidator {

    @Override
    protected void validateCircle(IHierarchical validated, IHierarchical value) throws ValidatorException {
        if (validated.isChildOf(validated)) {
            throw new ValidatorException(JsfUtil.createErrorMessage("Обнаружена циклическая ссылка"));
        }
    }
}
