package com.bochkov.admin.page.product;

import com.bochkov.admin.page.EntityEditPage;
import com.bochkov.admin.page.productType.ProductTypeEditPage;
import larisa.entity.ProductType;
import larisa.repository.FileRepository;
import larisa.repository.ProductTypeRepository;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.wicket.mount.core.annotation.MountPath;

import javax.inject.Inject;

@MountPath("product")
public class ProductEditPage extends EntityEditPage<ProductType, Integer> {

    @Inject
    ProductTypeRepository repository;

    @Inject
    FileRepository fileRepository;

    public ProductEditPage() {
    }

    public ProductEditPage(IModel<ProductType> model) {
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
    protected ProductTypeEditPage createEditPage(IModel<ProductType> entityModel) {
        return new ProductTypeEditPage(entityModel);
    }
    

    @Override
    protected ProductTypeRepository getRepository() {
        return repository;
    }


}
