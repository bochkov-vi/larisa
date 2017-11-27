package com.bochkov.shop.panel;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

public class CarouselPanel extends Panel {
    public CarouselPanel(String id) {
        super(id);
    }

    public CarouselPanel(String id, IModel<?> model) {
        super(id, model);
    }
}
