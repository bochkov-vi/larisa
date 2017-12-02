package com.bochkov.shop.page;

import com.bochkov.model.ProductTypePageModel;
import com.bochkov.shop.panel.FooterPanel;
import com.bochkov.shop.panel.NavigationPanel;
import com.bochkov.shop.panel.ProductTypePanel;
import larisa.entity.ProductType;
import larisa.repository.ProductTypeRepository;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import javax.inject.Inject;


public class HomePage extends WebPage {
    private static final long serialVersionUID = 1L;

    @Inject
    ProductTypeRepository productTypeRepository;

    public HomePage(final PageParameters parameters) {
        super(parameters);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        add(new Label("title",new ResourceModel("title")));
        add(new NavigationPanel("navigation"));
        add(new ListView<ProductType>("rows", new ProductTypePageModel()) {
            @Override
            protected void populateItem(ListItem<ProductType> item) {
                item.add(new ProductTypePanel("product-type",item.getModel()));
            }
        });
        add(new FooterPanel("footer"));
    }
}
