package com.bochkov.admin.component;

import com.bochkov.admin.page.file.FileResource;
import larisa.entity.File;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.image.NonCachingImage;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;

public class FileImage extends GenericPanel<File> {

    public FileImage(String id, IModel<File> model) {
        super(id, model);
    }

    public static Image createImage(String componentId, IModel<? extends File> fileModel) {
        return new NonCachingImage(componentId, FileResource.of(fileModel)) {
            @Override
            protected void addAntiCacheParameter(ComponentTag tag) {
                if (fileModel.isPresent().getObject()) {
                    String url = tag.getAttributes().getString("src");
                    url = url + (url.contains("?") ? "&" : "?");
                    url = url + "id=" + fileModel.getObject().getId();
                    tag.put("src", url);
                }
            }
        };
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        add(createImage("image", getModel()));
    }
}
