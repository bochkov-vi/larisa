package com.bochkov.admin.page.maker;

import com.bochkov.admin.page.EntityEditPage;
import com.bochkov.admin.page.file.FileResource;
import com.bochkov.model.EntityModel;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.BootstrapAjaxButton;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.BootstrapButton;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.Toolbar;
import de.agilecoders.wicket.core.markup.html.bootstrap.form.BootstrapForm;
import de.agilecoders.wicket.core.markup.html.bootstrap.form.FormType;
import de.agilecoders.wicket.core.markup.html.bootstrap.image.GlyphIconType;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.fileinput.BootstrapFileInput;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.validation.TooltipValidation;
import larisa.entity.File;
import larisa.entity.Maker;
import larisa.repository.FileRepository;
import larisa.repository.MakerRepository;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.*;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.springframework.data.repository.CrudRepository;
import org.wicketstuff.wicket.mount.core.annotation.MountPath;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@MountPath("maker")
public class MakerEditPage extends EntityEditPage<Maker, Integer> {

    @Inject
    MakerRepository repository;

    @Inject
    FileRepository fileRepository;


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
                super.onSubmit(target, form);
                MakerEditPage.this.onSubmit(target);
                target.add(form);
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                super.onError(target, form);
                target.add(form);
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


        WebMarkupContainer card = new WebMarkupContainer("card");
        Image image = new Image("image", FileResource.of(getModel()));


        Link link = new Link<File>("link", (IModel<File>)card.getDefaultModel()) {
            @Override
            public void onClick() {
                System.out.println(getModelObject());
            }
        };
        card.add(link.add(image));

        add(card.setOutputMarkupId(true));


        final IModel<List<FileUpload>> model = new ListModel<FileUpload>();
        BootstrapFileInput bootstrapFileInput = new BootstrapFileInput("bootstrapFileinput", model) {
            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                super.onSubmit(target);
                List<FileUpload> fileUploads = model.getObject();
                if (fileUploads != null) {
                    for (FileUpload upload : fileUploads) {
                        File file = new File();
                        file.setData(upload.getBytes());
                        file.setName(upload.getClientFileName());
                        file.setType(upload.getContentType());
                        try (InputStream in = new ByteArrayInputStream(upload.getBytes())) {
                            BufferedImage img = ImageIO.read(in);
                            file.setImageHeight(img.getHeight());
                            file.setImageWidth(img.getWidth());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        file = fileRepository.save(file);
                        MakerEditPage.this.getModelObject().setFile(file);
                        success("Uploaded: " + upload.getClientFileName());
                    }
                }

                save();
                target.add(card);
                target.add(feedback);
            }
        };
        bootstrapFileInput.getConfig().maxFileCount(1);
        form.add(bootstrapFileInput);


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
