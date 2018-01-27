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
public class EditPage extends EntityEditPage<Product, Integer> {

    @Inject
    ProductRepository repository;

    @Inject
    FileRepository fileRepository;

    public EditPage() {
    }

    public EditPage(IModel<Product> model) {
        super(model);
    }

    public EditPage(PageParameters parameters) {
        super(parameters);
    }


    @Override
    protected void onInitialize() {
        super.onInitialize();
        Input input = new Input("input", getModel());
        form.add(input);
   }

    @Override
    protected EditPage createEditPage(IModel<Product> entityModel) {
        return new EditPage(entityModel);
    }


    @Override
    protected ProductRepository getRepository() {
        return repository;
    }


}
