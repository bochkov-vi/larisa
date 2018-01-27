package com.bochkov.admin.page.maker;

import com.bochkov.admin.component.GetFileInputPanel;
import de.agilecoders.wicket.core.markup.html.bootstrap.form.BootstrapForm;
import de.agilecoders.wicket.core.markup.html.bootstrap.form.FormType;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.validation.TooltipValidation;
import larisa.entity.Maker;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;

public class MakerInput extends GenericPanel<Maker> {

    public MakerInput(String id, IModel model) {
        super(id, model);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        TooltipValidation validation = new TooltipValidation();
        validation.getConfig().appendToParent(true);

        BootstrapForm<Maker> form = new BootstrapForm<Maker>("form", new CompoundPropertyModel<Maker>(getModel()));
        form.type(FormType.Default);
        form.add(new TextField("id").setLabel(new ResourceModel("maker.id")));
        form.add(new RequiredTextField<>("name").setRequired(true).setLabel(new ResourceModel("maker.name")));
        form.add(new TextArea<>("note").setLabel(new ResourceModel("maker.note")));
        form.add(validation);
        form.add(new GetFileInputPanel<>("file",new PropertyModel(getModel(),"file")));
        add(form);
    }
}
