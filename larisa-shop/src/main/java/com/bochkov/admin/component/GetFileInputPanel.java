package com.bochkov.admin.component;

import com.bochkov.model.EntityModel;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.fileinput.BootstrapFileInput;
import larisa.entity.File;
import larisa.repository.FileRepository;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IFormModelUpdateListener;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.util.ListModel;
import org.springframework.data.repository.CrudRepository;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class GetFileInputPanel<T extends File> extends GenericPanel<T> implements IFormModelUpdateListener {
    @Inject
    FileRepository fileRepository;

    EntityModel<File, Integer> entityModel = new EntityModel<File, Integer>() {
        @Override
        public CrudRepository<File, Integer> getRepostory() {
            return fileRepository;
        }
    };

    public GetFileInputPanel(String id, IModel<T> model) {
        super(id, model);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        Form form = new Form("form");
        add(form);
        WebMarkupContainer card = new WebMarkupContainer("card");
        entityModel.setObject(getModelObject());
        BootstrapFileInput bootstrapFileInput = new BootstrapFileInput("bootstrapFileinput", new ListModel<FileUpload>()) {
            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                super.onSubmit(target);
                List<FileUpload> fileUploads = getModelObject();
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
                        entityModel.setObject(fileRepository.save(file));
                        updateModel();
                        target.add(form);
                    }
                }
                GetFileInputPanel.this.onSubmit(target);
            }
        };
        bootstrapFileInput.getConfig().maxFileCount(1);
        setOutputMarkupId(true);
        form.add(bootstrapFileInput);




        Link link = new Link<T>("link", getModel()) {
            @Override
            public void onClick() {
                System.out.println(getModelObject());
            }
        };
        card.add(link.add(FileImage.createImage("image",getModel())));

        add(card.setOutputMarkupId(true));
    }

    public void onSubmit(AjaxRequestTarget target){
        target.add(this);
    }

    @Override
    public void updateModel() {
        getModel().setObject((T) entityModel.getObject());
    }
}
