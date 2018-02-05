package com.bochkov.admin.page.product;

import com.bochkov.admin.page.EntityEditPage;
import larisa.entity.Product;
import larisa.repository.FileRepository;
import larisa.repository.ProductRepository;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.wicket.mount.core.annotation.MountPath;

import javax.inject.Inject;

@MountPath("product")
public class ProductEditPage extends EntityEditPage<Product, Integer> {

    @Inject
    ProductRepository repository;

    @Inject
    FileRepository fileRepository;

    public ProductEditPage() {
    }

    public ProductEditPage(IModel<Product> model) {
        super(model);
    }

    public ProductEditPage(PageParameters parameters) {
        super(parameters);
    }


    @Override
    protected void onInitialize() {
        super.onInitialize();
        InputPanel input = new InputPanel("input", getModel());
        form.add(input);
   }

    @Override
    protected ProductEditPage createEditPage(IModel<Product> entityModel) {
        return new ProductEditPage(entityModel);
    }
    

    @Override
    protected ProductRepository getRepository() {
        return repository;
    }


}
