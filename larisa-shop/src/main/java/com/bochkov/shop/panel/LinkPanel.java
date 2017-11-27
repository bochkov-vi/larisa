package com.bochkov.shop.panel;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

public class LinkPanel extends Panel {
    public LinkPanel(String id) {
        super(id);
    }

    public LinkPanel(String id, IModel<?> model) {
        super(id, model);
    }
}
