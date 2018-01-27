package com.bochkov.admin.component;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.BootstrapLink;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons;
import de.agilecoders.wicket.core.markup.html.bootstrap.image.GlyphIconType;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

public class EditLink extends Panel {
    public EditLink(String id, IModel<?> model) {
        super(id, model);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        Link link = new BootstrapLink("link", Buttons.Type.Link) {
            @Override
            public void onClick() {
                EditLink.this.onClick();
            }
        }.setIconType(GlyphIconType.pencil);
//        link.add(new Label("label", getDefaultModel()));
        add(link);
    }

    public void onClick() {

    }
}
