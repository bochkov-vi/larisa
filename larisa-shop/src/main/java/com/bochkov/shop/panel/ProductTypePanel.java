package com.bochkov.shop.panel;

import com.bochkov.WicketApplication;
import larisa.entity.ProductType;
import larisa.repository.PriceRepository;
import org.apache.wicket.Application;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnLoadHeaderItem;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import javax.inject.Inject;

public class ProductTypePanel extends GenericPanel<ProductType> {

    @Inject
    transient PriceRepository priceRepository;

    public ProductTypePanel(String id) {
        super(id);
    }

    public ProductTypePanel(String id, IModel<ProductType> model) {
        super(id, model);
    }

    @Override
    protected void onInitialize() {
        Injector.get().inject(this);
        super.onInitialize();
        add(new Image("img", Application.get().getSharedResources().get(WicketApplication.DB_IMAGE_KEY), new PageParameters().add("id", getModelObject().getFile().getId())));
        add(new Label("name", new PropertyModel<>(getModel(), "name")));
        add(new Label("price", new PropertyModel<Object>(priceRepository.findLast(getModelObject()), "price")) {
            @Override
            public boolean isVisible() {
                return getModelObject() != null;
            }
        });
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.render(OnLoadHeaderItem.forScript("$(document).ready(adaptHeight());"));
    }
}
