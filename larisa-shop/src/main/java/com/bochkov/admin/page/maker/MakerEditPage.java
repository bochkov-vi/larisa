package com.bochkov.admin.page.maker;

import com.bochkov.admin.page.EntityEditPage;
import larisa.entity.Maker;
import larisa.repository.FileRepository;
import larisa.repository.MakerRepository;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.wicket.mount.core.annotation.MountPath;

import javax.inject.Inject;

@MountPath("maker")
public class MakerEditPage extends EntityEditPage<Maker, Integer> {

    @Inject
    MakerRepository repository;

    @Inject
    FileRepository fileRepository;

    public MakerEditPage() {
    }

    public MakerEditPage(IModel<Maker> model) {
        super(model);
    }

    public MakerEditPage(PageParameters parameters) {
        super(parameters);
    }


    @Override
    protected void onInitialize() {
        super.onInitialize();
        MakerInput input = new MakerInput("input", getModel());
        form.add(input);
   }

    @Override
    protected MakerEditPage createEditPage(IModel<Maker> entityModel) {
        return new MakerEditPage(entityModel);
    }

    protected void onSubmit(AjaxRequestTarget target) {
        save();
        target.add(feedback);

    }


    public void save() {
        repository.save(getModelObject());
        success(new StringResourceModel("maker.saved", this, getModel()).getObject());
    }

    @Override
    protected MakerRepository getRepository() {
        return repository;
    }


}
