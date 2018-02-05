package com.bochkov.admin.page.productreceipt;

import com.bochkov.admin.page.EntityEditPage;
import larisa.entity.ProductReceipt;
import larisa.repository.FileRepository;
import larisa.repository.ProductReceiptRepository;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.wicket.mount.core.annotation.MountPath;

import javax.inject.Inject;

@MountPath("product-receipt")
public class ProductReceiptEditPage extends EntityEditPage<ProductReceipt, Integer> implements IProductReceiptDetailed{

    @Inject
    ProductReceiptRepository repository;

    @Inject
    FileRepository fileRepository;

    public ProductReceiptEditPage() {
    }

    public ProductReceiptEditPage(IModel<ProductReceipt> model) {
        super(model);
    }

    public ProductReceiptEditPage(PageParameters parameters) {
        super(parameters);
    }


    @Override
    protected void onInitialize() {
        super.onInitialize();
        ProductReceiptInput input = new ProductReceiptInput("input", getModel());
        form.add(input);
   }

    @Override
    protected ProductReceiptEditPage createEditPage(IModel<ProductReceipt> entityModel) {
        return new ProductReceiptEditPage(entityModel);
    }

    protected void onSubmit(AjaxRequestTarget target) {
        save();
        target.add(feedback);

    }


    public void save() {
        repository.save(getModelObject());
        success(new StringResourceModel("productReceipt.saved", this, getModel()).getObject());
    }

    @Override
    protected ProductReceiptRepository getRepository() {
        return repository;
    }

}
