package com.bochkov.admin.page.productType;

import com.bochkov.admin.component.LabeledLink;
import com.bochkov.admin.page.EntityEditPage;
import com.bochkov.admin.page.IEditPageCreator;
import larisa.entity.ProductType;
import larisa.repository.FileRepository;
import larisa.repository.ProductTypeRepository;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LambdaModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.wicket.mount.core.annotation.MountPath;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.Optional;

@MountPath("product-type")
public class ProductTypeEditPage extends EntityEditPage<ProductType, Integer> {

    @Inject
    ProductTypeRepository repository;

    @Inject
    FileRepository fileRepository;

    public ProductTypeEditPage() {
    }

    public ProductTypeEditPage(IModel<ProductType> model) {
        super(model);
    }

    public ProductTypeEditPage(PageParameters parameters) {
        super(parameters);
    }

    protected static LabeledLink<ProductType> createProductTypeLink(String id, IModel<ProductType> model, Page backPage) {
        return createProductTypeLink(id, model, LambdaModel.of(model, ProductType::getName), ProductTypeEditPage::new, backPage);
    }

    protected static LabeledLink<ProductType> createProductTypeLink(String id, IModel<ProductType> model, IModel<String> linkLabelModel, IEditPageCreator editPageCreator, Page backPage) {
        return new LabeledLink<ProductType>(id, model, linkLabelModel) {
            @Override
            public void onClick(Optional<AjaxRequestTarget> target) {
                setResponsePage(editPageCreator.createEditPage(model).setBackNavigateAction((circle, model1) -> circle.setResponsePage(backPage)));
            }
        };
    }

    public static <T extends Serializable> IColumn<T, String> createProductTypeColumn(String property,
                                                                                      Page backPage,
                                                                                      IEditPageCreator editPageCreator) {
        return createProductTypeColumn(new ResourceModel("productType.name"), property, backPage, editPageCreator);
    }

    public static <T extends Serializable> IColumn<T, String> createProductTypeColumn(IModel<String> header,
                                                                                      String property,
                                                                                      Page backPage,
                                                                                      IEditPageCreator editPageCreator) {
        return new PropertyColumn<T, String>(header, property, property) {
            @Override
            public void populateItem(Item<ICellPopulator<T>> item, String componentId, IModel<T> rowModel) {
                LabeledLink link = createProductTypeLink(componentId, getDataModel(rowModel),LambdaModel.of(getDataModel(rowModel), ProductType::getName), editPageCreator, backPage);
                item.add(link);
            }

            @Override
            public IModel<ProductType> getDataModel(IModel<T> rowModel) {
                return (IModel<ProductType>) super.getDataModel(rowModel);
            }
        };
    }


    @Override
    protected void onInitialize() {
        super.onInitialize();
        InputPanel input = new InputPanel("input", getModel());
        form.add(input);
    }

    @Override
    public ProductTypeEditPage createEditPage(IModel<ProductType> entityModel) {
        return new ProductTypeEditPage(entityModel);
    }

    @Override
    protected ProductTypeRepository getRepository() {
        return repository;
    }

}
