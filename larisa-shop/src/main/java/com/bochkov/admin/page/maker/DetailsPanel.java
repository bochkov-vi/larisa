package com.bochkov.admin.page.maker;

import com.bochkov.admin.page.file.FileResource;
import de.agilecoders.wicket.core.util.Attributes;
import larisa.entity.Maker;
import org.apache.wicket.Component;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.resource.PackageResourceReference;

public class DetailsPanel extends GenericPanel<Maker> {
    Image image;

    Component title;

    Component note;

    public DetailsPanel(String id, IModel<Maker> model) {
        super(id, model);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        image = new Image("image", FileResource.ofProperty(getModel()));
        title = new Label("title", new PropertyModel<>(getModel(), "name"));
        note = new Label("note", new PropertyModel<>(getModel(), "note"));
        add(image, title, note);

    }

    @Override
    protected void onComponentTag(ComponentTag tag) {
        super.onComponentTag(tag);
        checkComponentTag(tag, "div");
        Attributes.addClass(tag, "img-thumbnail");
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.render(CssHeaderItem.forReference(new PackageResourceReference(DetailsPanel.class, "DetailsPanel.css")));
    }
}
