package com.bochkov.admin.component.details;

import com.bochkov.admin.page.file.FileResource;
import de.agilecoders.wicket.core.util.Attributes;
import larisa.entity.IGetFile;
import larisa.entity.INamable;
import larisa.entity.INotable;
import org.apache.wicket.Component;
import org.apache.wicket.IGenericComponent;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.IObjectClassAwareModel;
import org.apache.wicket.model.LambdaModel;

public class Details<T> extends Border implements IGenericComponent<T, Details<T>> {
    Image image;

    Component title;

    Component note;

    public Details(String id, IModel<T> model) {
        super(id, model);
        image = createImage(model);
        title = createTitleLabel(model);
        note = createNoteLabel(model);
        addToBorder(image, title, note);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();


    }

    @Override
    protected void onComponentTag(ComponentTag tag) {
        super.onComponentTag(tag);
        checkComponentTag(tag, "div");
        Attributes.addClass(tag, "img-thumbnail");
    }

    public Image createImage(IModel<T> model) {
        Image image = null;
        if (model instanceof IObjectClassAwareModel && IGetFile.class.isAssignableFrom(((IObjectClassAwareModel) model).getObjectClass())) {
            IObjectClassAwareModel<? extends IGetFile> filableModel = (IObjectClassAwareModel<? extends IGetFile>) model;
            image = new Image("detailed-image", FileResource.ofGetFile(filableModel));
        } else {
            image = new Image("detailed-image", "image") {
                @Override
                public boolean isVisible() {
                    return false;
                }
            };
        }
        return image;
    }

    public Component createTitleLabel(IModel<T> model) {
        if (model instanceof IObjectClassAwareModel && INamable.class.isAssignableFrom(((IObjectClassAwareModel) model).getObjectClass())) {
            IObjectClassAwareModel<? extends INamable> namableModel = (IObjectClassAwareModel<? extends INamable>) model;
            return new Label("title", LambdaModel.of(namableModel, t -> t.getName()));
        } else {
            return new Label("title");
        }
    }

    public Component createNoteLabel(IModel<T> model) {
        if (model instanceof IObjectClassAwareModel && INotable.class.isAssignableFrom(((IObjectClassAwareModel) model).getObjectClass())) {
            IObjectClassAwareModel<? extends INotable> notableModel = (IObjectClassAwareModel<? extends INotable>) model;
            return new Label("note", LambdaModel.of(notableModel, t -> t.getNote()));
        } else {
            return new Label("note");
        }
    }

}
