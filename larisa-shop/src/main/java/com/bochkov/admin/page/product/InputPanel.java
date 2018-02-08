package com.bochkov.admin.page.product;

import com.bochkov.admin.page.productType.select2.SelectProductType;
import de.agilecoders.wicket.core.markup.html.bootstrap.form.BootstrapForm;
import de.agilecoders.wicket.core.markup.html.bootstrap.form.FormGroup;
import de.agilecoders.wicket.core.markup.html.bootstrap.form.FormType;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.DateTextField;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.DateTextFieldConfig;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.validation.TooltipValidation;
import larisa.entity.Product;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;

public class InputPanel extends GenericPanel<Product> {

    public InputPanel(String id, IModel model) {
        super(id, model);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        TooltipValidation validation = new TooltipValidation();
        validation.getConfig().appendToParent(true);
        BootstrapForm<Product> form = new BootstrapForm<Product>("form", new CompoundPropertyModel<Product>(getModel()));
        form.add(validation);
        form.type(FormType.Default);
        form.add(validation);
        add(form);


        //===============================================================

        /*productReceipt
                productType
        volume
                price
        expirationDate*/
        //===============================================================
        form.add(new FormGroup("id-g").add(new TextField("id").setLabel(new ResourceModel("product.id"))));
        form.add(new FormGroup("productType-g").add(new SelectProductType("productType").setRequired(true).setLabel(new ResourceModel("productType.name"))));
        form.add(new FormGroup("volume-g").add(new TextField("volume").setRequired(true).setLabel(new ResourceModel("product.volume"))));
        form.add(new FormGroup("price-g").add(new TextField("price").setRequired(true).setLabel(new ResourceModel("product.price"))));
        DateTextField expirationDate = new DateTextField("expirationDate", new ResourceModel("datePattern").wrapOnAssignment(getPage()).getObject());
        expirationDate.setRequired(false);
        expirationDate.getConfig()
                .withLanguage("ru")
                .showTodayButton(DateTextFieldConfig.TodayButton.TRUE)
                .clearButton(true).withFormat(new ResourceModel("datePattern").wrapOnAssignment(getPage()).getObject())
                .highlightToday(true)
                .autoClose(true);
        form.add(new FormGroup("expirationDate-g").add(expirationDate));
    }
}
