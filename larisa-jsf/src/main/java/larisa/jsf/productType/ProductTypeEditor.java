package larisa.jsf.productType;

import com.google.common.collect.Iterables;
import jsf.util3.JsfUtil;
import larisa.entity.ProductType;
import larisa.jsf.file.FileEditor;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

/**
 * Created by home on 24.02.17.
 */
@Component
@Scope("view")
public class ProductTypeEditor extends FileEditor<ProductType, Integer> {

    public ProductTypeEditor() {

    }

    @Override
    public ProductType createNewInstance() {
        ProductType productType = super.createNewInstance();
        ProductType template = entityFromRequest("clone");
        if (template != null) {
            BeanUtils.copyProperties(template, productType, "id", "createdDate");
        }
        return productType;
    }

    public void validatePassword(ComponentSystemEvent event) {

        FacesContext fc = FacesContext.getCurrentInstance();
        UIComponent components = event.getComponent();
        UIInput childsInput = (UIInput) components.findComponent("childs");
        UIInput parentsInput = (UIInput) components.findComponent("parents");
        Iterable<ProductType> childs = (Iterable<ProductType>) childsInput.getLocalValue();
        Iterable<ProductType> parents = (Iterable<ProductType>) parentsInput.getLocalValue();
        if (childs != null && parents != null && Iterables.any(childs, c -> c.isParentOf(parents) || Iterables.contains(parents,c))) {
            JsfUtil.addErrorMessage("Обнаружена циклическая ссылка");
            fc.renderResponse();
        }

    }

}
