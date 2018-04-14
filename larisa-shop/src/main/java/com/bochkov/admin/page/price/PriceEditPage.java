package com.bochkov.admin.page.price;

import com.bochkov.admin.page.EntityEditPage;
import larisa.entity.Price;
import larisa.repository.FileRepository;
import larisa.repository.PriceRepository;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.wicket.mount.core.annotation.MountPath;

import javax.inject.Inject;

@MountPath("product")
public class PriceEditPage extends EntityEditPage<Price, Integer> {

    @Inject
    PriceRepository repository;

    @Inject
    FileRepository fileRepository;

    public PriceEditPage() {
    }

    public PriceEditPage(IModel<Price> model) {
        super(model);
    }

    public PriceEditPage(PageParameters parameters) {
        super(parameters);
    }





    @Override
    protected void onInitialize() {
        super.onInitialize();
        InputPanel input = new InputPanel("input", getModel());
        form.add(input);
    }

    @Override
    public PriceEditPage createEditPage(IModel<Price> entityModel) {
        return new PriceEditPage(entityModel);
    }

    @Override
    protected PriceRepository getRepository() {
        return repository;
    }

}
