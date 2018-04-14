package com.bochkov.admin.page.productType;

import com.bochkov.admin.component.LabeledLink;
import com.bochkov.admin.page.EntityEditPage;
import com.bochkov.admin.page.IEditPageCreator;
import com.bochkov.model.EntityDataProvider;
import com.google.common.collect.Lists;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.table.BootstrapDefaultDataTable;
import larisa.entity.Price;
import larisa.entity.Product;
import larisa.entity.ProductType;
import larisa.repository.FileRepository;
import larisa.repository.PriceRepository;
import larisa.repository.ProductRepository;
import larisa.repository.ProductTypeRepository;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.LambdaColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LambdaModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.wicketstuff.wicket.mount.core.annotation.MountPath;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@MountPath("product-type")
public class ProductTypeEditPage extends EntityEditPage<ProductType, Integer> {

    @Inject
    ProductTypeRepository repository;

    @Inject
    FileRepository fileRepository;

    @Inject
    PriceRepository priceRepository;

    @Inject
    ProductRepository productRepository;

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
                LabeledLink link = createProductTypeLink(componentId, getDataModel(rowModel), LambdaModel.of(getDataModel(rowModel), ProductType::getName), editPageCreator, backPage);
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
        form.add(new LabeledLink<Price>("current-price",
                LambdaModel.of(()->priceRepository.findLast(ProductTypeEditPage.this.getModelObject())),
                LambdaModel.of(()->priceRepository.findLastOptional(ProductTypeEditPage.this.getModelObject()).map(Price::getPrice).orElse(0.0))) {


            @Override
            public void onClick(Optional<AjaxRequestTarget> target) {
//                ProductTypeEditPage.this.setResponsePage(new PriceEditPage(this.getModel()));
            }
        });
        form.add(new Label("total-product", LambdaModel.of(() -> productRepository.productTotal(ProductTypeEditPage.this.getModelObject()))));
        List<IColumn<Price, String>> priceColumns = Lists.newArrayList(new LambdaColumn<>(new ResourceModel("price.date"), Price::getDateFrom),
                new LambdaColumn<>(new ResourceModel("price.dateTo"), Price::getDateTo),
                new LambdaColumn<>(new ResourceModel("price.price"), Price::getPrice));
        DataTable<Price, String> priceDataTable = new BootstrapDefaultDataTable<>("price-table",
                priceColumns, new EntityDataProvider<Price>() {
            @Override
            public JpaSpecificationExecutor<Price> getRepository() {
                return priceRepository;
            }

            @Override
            public Specification<Price> createSpecification() {
                return (Specification<Price>) (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("productType"), ProductTypeEditPage.this.getModelObject());
            }
        }, 10);
        form.add(priceDataTable);

        DataTable<Product, String> productDataTable = new BootstrapDefaultDataTable<>("product-table",
                Lists.newArrayList(
                        new LambdaColumn<>(new ResourceModel("product.date"), p -> Optional.ofNullable(p.getProductReceipt()).map(r -> r.getDate()).get()),
                        new LambdaColumn<>(new ResourceModel("product.volume"), Product::getVolume)
                ), EntityDataProvider.create(productRepository, (Specification<Product>) (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("productType"), ProductTypeEditPage.this.getModelObject()))
                , 10);

        form.add(productDataTable);
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
