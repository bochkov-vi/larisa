package com.bochkov.admin.page.productType;

import larisa.entity.ProductType;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;

public class DetailsPanel extends GenericPanel<ProductType> {
    public DetailsPanel(String id) {
        super(id);
    }

    public DetailsPanel(String id, IModel<ProductType> model) {
        super(id, model);
    }
}
