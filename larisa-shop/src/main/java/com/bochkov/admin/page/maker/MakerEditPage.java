package com.bochkov.admin.page.maker;

import com.bochkov.admin.page.EntityEditPage;
import com.bochkov.model.EntityModel;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.BootstrapAjaxButton;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.BootstrapButton;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.Toolbar;
import de.agilecoders.wicket.core.markup.html.bootstrap.form.BootstrapForm;
import de.agilecoders.wicket.core.markup.html.bootstrap.form.FormType;
import de.agilecoders.wicket.core.markup.html.bootstrap.image.GlyphIconType;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.validation.TooltipValidation;
import larisa.entity.Maker;
import larisa.repository.MakerRepository;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.springframework.data.repository.CrudRepository;

import javax.inject.Inject;

public class MakerEditPage extends EntityEditPage<Maker, Integer> {

    @Inject
    MakerRepository repository;

    /**
     * Construct.
     *
     * @param parameters current backPage parameters
     */
    public MakerEditPage(PageParameters parameters) {
        super(parameters);
    }

    public MakerEditPage(Page page) {
        super(page);
    }

    public MakerEditPage(IModel<Maker> model, Page page) {
        super(model, page);
    }

    public MakerEditPage(PageParameters parameters, Page page) {
        super(parameters, page);
    }

    public MakerEditPage(Maker entity) {
        super(new EntityModel<Maker, Integer>(entity) {
            @Override
            public CrudRepository getRepostory() {
                return null;
            }
        });
    }

    public MakerEditPage() {
    }

    public MakerEditPage(IModel<Maker> model) {
        super(model);
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
        Toolbar toolbar = new Toolbar("toolbar");
        form.add(toolbar);
        toolbar.add(new BootstrapAjaxButton("save", new ResourceModel("save"), Buttons.Type.Default) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                super.onSubmit(target,form);
                MakerEditPage.this.onSubmit(target);
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                super.onError(target,form);
                target.add(feedback);
            }
        }.setIconType(GlyphIconType.edit));
        toolbar.add(new BootstrapButton("cancel", new ResourceModel("cancel"), Buttons.Type.Default) {
            @Override
            public void onSubmit() {
                setResponsePage(backPage);
            }
        }.setIconType(GlyphIconType.bancircle).setDefaultFormProcessing(true)
                .setDefaultFormProcessing(false));

        form.add(new AjaxFormSubmitBehavior("submit") {

            @Override
            protected void onError(AjaxRequestTarget target) {
                target.add(form);
            }

            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                target.add(form);
                MakerEditPage.this.onSubmit(target);
            }
        });
        add(form);
    }

    protected void onSubmit(AjaxRequestTarget target) {
        repository.save(getModelObject());
        target.add(feedback);
        info(new StringResourceModel("maker.saved", this, getModel()).getObject());
    }

    @Override
    protected MakerRepository getRepository() {
        return repository;
    }
}
