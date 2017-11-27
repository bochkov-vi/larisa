package com.bochkov.shop.panel;

import larisa.entity.ProductType;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;

public class ProductPanel extends GenericPanel<ProductType> {
    public ProductPanel(String id) {
        super(id);
    }

    public ProductPanel(String id, IModel<ProductType> model) {
        super(id, model);
    }
}
