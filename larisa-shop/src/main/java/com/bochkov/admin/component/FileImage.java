package com.bochkov.admin.component;

import com.bochkov.admin.page.file.FileResource;
import larisa.entity.File;
import org.apache.wicket.ClassAttributeModifier;
import org.apache.wicket.StyleAttributeModifier;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.image.NonCachingImage;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;

import java.util.Map;
import java.util.Set;

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
        add(new StyleAttributeModifier() {
            @Override
            protected Map<String, String> update(Map<String, String> oldStyles) {
                oldStyles.put("max-width","10em");
                oldStyles.put("max-height","10em");

                return oldStyles;
            }
        });
        add(new ClassAttributeModifier() {
            @Override
            protected Set<String> update(Set<String> oldClasses) {
                oldClasses.add("panel-default");
                return oldClasses;
            }
        });
        add(createImage("image", getModel()).add(new ClassAttributeModifier() {
            @Override
            protected Set<String> update(Set<String> oldClasses) {
                oldClasses.add("img-responsive");
                return oldClasses;
            }
        }).add(new StyleAttributeModifier() {
            @Override
            protected Map<String, String> update(Map<String, String> oldStyles) {
                oldStyles.put("display","inline-block");
                return oldStyles;
            }
        }));
    }
}
