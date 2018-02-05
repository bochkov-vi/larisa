package com.bochkov.admin.page.productreceipt;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import de.agilecoders.wicket.core.markup.html.bootstrap.form.BootstrapForm;
import de.agilecoders.wicket.core.markup.html.bootstrap.form.FormGroup;
import de.agilecoders.wicket.core.markup.html.bootstrap.form.FormType;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.DateTextField;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.DateTextFieldConfig;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.validation.TooltipValidation;
import larisa.entity.ProductReceipt;
import larisa.repository.ProductReceiptRepository;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.wicketstuff.select2.Response;
import org.wicketstuff.select2.Select2Choice;
import org.wicketstuff.select2.StringTextChoiceProvider;

import javax.inject.Inject;

public class ProductReceiptInput extends GenericPanel<ProductReceipt> {

    @Inject
    ProductReceiptRepository repository;

    public ProductReceiptInput(String id, IModel model) {
        super(id, model);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        TooltipValidation validation = new TooltipValidation();
        validation.getConfig().appendToParent(true);

        BootstrapForm<ProductReceipt> form = new BootstrapForm<ProductReceipt>("form", new CompoundPropertyModel<ProductReceipt>(getModel()));
        form.type(FormType.Default);
        form.add(new FormGroup("id-g").add(new TextField("id").setLabel(new ResourceModel("productReceipt.id"))));
        DateTextField date = new DateTextField("date",new ResourceModel("datePattern").wrapOnAssignment(getPage()).getObject());
        date.getConfig()
                .withLanguage("ru")
                .showTodayButton(DateTextFieldConfig.TodayButton.TRUE)
                .clearButton(true).withFormat(new ResourceModel("datePattern").wrapOnAssignment(getPage()).getObject())
                .highlightToday(true)
                .autoClose(true);
        form.add(new FormGroup("date-g").add(date.setLabel(new ResourceModel("productReceipt.date"))));
        form.add(new FormGroup("receiptType-g").add(new Select2Choice<String>("receiptType", new StringTextChoiceProvider() {
            @Override
            public void query(String term, int pageNumber, Response<String> response) {
                Pageable pageRequest = new PageRequest(pageNumber, 10);
                Page page = repository.findReceiptTypeByMask("%" + term + "%", term, pageRequest);
                response.setHasMore(page.hasNext());
                response.setResults(Lists.newArrayList(Iterables.concat(ImmutableList.of(term),page.getContent())));
            }
        }).setLabel(new ResourceModel("productReceipt.receiptType"))));
        form.add(new FormGroup("note-g").add(new TextArea<String>("note").setLabel(new ResourceModel("productReceipt.note"))));

        add(form);

        //SelectRowDataTable<Product,String> table = new SelectRowDataTable<>("product-table",)
    }


    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.render(OnDomReadyHeaderItem.forScript("$.fn.datepicker.dates['ru']['clear']='Очистить'"));
    }

    public void queryReceiptType(String term, int page, Response<String> response) {

    }

}
