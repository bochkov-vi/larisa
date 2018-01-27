package com.bochkov.admin.page.product;

import com.bochkov.admin.component.GetFileInputPanel;
import de.agilecoders.wicket.core.markup.html.bootstrap.form.BootstrapForm;
import de.agilecoders.wicket.core.markup.html.bootstrap.form.FormType;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.validation.TooltipValidation;
import larisa.entity.Product;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;

public class Input extends GenericPanel<Product> {

    public Input(String id, IModel model) {
        super(id, model);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        TooltipValidation validation = new TooltipValidation();
        validation.getConfig().appendToParent(true);

        BootstrapForm<Product> form = new BootstrapForm<Product>("form", new CompoundPropertyModel<Product>(getModel()));
        form.type(FormType.Default);
        form.add(new TextField("id").setLabel(new ResourceModel("id")));
        form.add(new RequiredTextField<>("name").setRequired(true).setLabel(new ResourceModel("name")));
        form.add(new TextArea<>("note").setLabel(new ResourceModel("note")));
        form.add(validation);
        form.add(new GetFileInputPanel<>("file",new PropertyModel(getModel(),"file")));
        add(form);
    }
}
