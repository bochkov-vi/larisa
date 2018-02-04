package com.bochkov.admin.page.product;

import larisa.entity.Product;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;

public class DetailsPanel extends GenericPanel<Product> {
    public DetailsPanel(String id) {
        super(id);
    }

    public DetailsPanel(String id, IModel<Product> model) {
        super(id, model);
    }
}
