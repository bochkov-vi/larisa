package com.bochkov.admin.page.productType;

import com.bochkov.admin.component.GetFileInputPanel;
import com.bochkov.admin.page.maker.select2.MakerSelect2Chooser;
import de.agilecoders.wicket.core.markup.html.bootstrap.form.BootstrapForm;
import de.agilecoders.wicket.core.markup.html.bootstrap.form.FormType;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.validation.TooltipValidation;
import larisa.entity.Maker;
import larisa.entity.ProductType;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.wicketstuff.select2.Select2Choice;

public class InputPanel extends GenericPanel<ProductType> {

    public InputPanel(String id, IModel model) {
        super(id, model);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        TooltipValidation validation = new TooltipValidation();
        validation.getConfig().appendToParent(true);

        BootstrapForm<ProductType> form = new BootstrapForm<ProductType>("form", new CompoundPropertyModel<ProductType>(getModel()));
        form.type(FormType.Default);
        form.add(new TextField("id").setLabel(new ResourceModel("id")));
        form.add(new RequiredTextField<>("name").setRequired(true).setLabel(new ResourceModel("name")));
        form.add(new TextArea<>("volumeNote").setLabel(new ResourceModel("volumeNote")));
        Select2Choice<Maker> makerSelect2Choice = new MakerSelect2Chooser("maker");

        form.add(makerSelect2Choice);
        form.add(validation);
        form.add(new GetFileInputPanel<>("file", new PropertyModel(getModel(), "file")));
        add(form);
    }
}
