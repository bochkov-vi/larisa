package com.bochkov.admin.page.price;

import com.bochkov.admin.page.productType.select2.SelectProductType;
import de.agilecoders.wicket.core.markup.html.bootstrap.form.BootstrapForm;
import de.agilecoders.wicket.core.markup.html.bootstrap.form.FormGroup;
import de.agilecoders.wicket.core.markup.html.bootstrap.form.FormType;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.DateTextField;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.DateTextFieldConfig;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.validation.TooltipValidation;
import larisa.entity.Price;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;

public class InputPanel extends GenericPanel<Price> {

    public InputPanel(String id, IModel model) {
        super(id, model);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        TooltipValidation validation = new TooltipValidation();
        validation.getConfig().appendToParent(true);
        BootstrapForm form = new BootstrapForm("form", new CompoundPropertyModel(getModel()));
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
        form.add(new FormGroup("price-g").add(new TextField("price").setRequired(true).setLabel(new ResourceModel("product.price"))));
        DateTextField dateFrom = new DateTextField("dateFrom", new ResourceModel("datePattern").wrapOnAssignment(getPage()).getObject());
        dateFrom.setRequired(false);
        dateFrom.getConfig()
                .withLanguage("ru")
                .showTodayButton(DateTextFieldConfig.TodayButton.TRUE)
                .clearButton(true).withFormat(new ResourceModel("datePattern").wrapOnAssignment(getPage()).getObject())
                .highlightToday(true)
                .autoClose(true);
        form.add(new FormGroup("date-from-g").add(dateFrom));

        DateTextField dateTo = new DateTextField("dateTo", new ResourceModel("datePattern").wrapOnAssignment(getPage()).getObject());
        dateTo.setRequired(false);
        dateTo.getConfig()
                .withLanguage("ru")
                .showTodayButton(DateTextFieldConfig.TodayButton.TRUE)
                .clearButton(true).withFormat(new ResourceModel("datePattern").wrapOnAssignment(getPage()).getObject())
                .highlightToday(true)
                .autoClose(true);
        form.add(new FormGroup("date-to-g").add(dateTo));
    }
}
