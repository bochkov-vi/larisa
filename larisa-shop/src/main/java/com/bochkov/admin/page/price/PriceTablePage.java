package com.bochkov.admin.page.price;

import com.bochkov.admin.component.CurrencyLabeledLink;
import com.bochkov.admin.page.EntityTablePage;
import com.bochkov.admin.page.maker.select2.MakerSelect2Chooser;
import com.bochkov.admin.page.productType.ProductTypeEditPage;
import com.bochkov.admin.page.productType.select2.SelectProductType;
import com.google.common.collect.Lists;
import de.agilecoders.wicket.core.markup.html.bootstrap.form.FormGroup;
import larisa.entity.Maker;
import larisa.entity.Price;
import larisa.entity.ProductReceipt;
import larisa.entity.ProductType;
import larisa.repository.PriceRepository;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.LambdaColumn;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LambdaModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.wicketstuff.wicket.mount.core.annotation.MountPath;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@MountPath("products")
public class PriceTablePage extends EntityTablePage<Price> {
    @Inject
    PriceRepository repository;

    IModel<Maker> filterMakerModel = new Model<>();

    IModel<ProductType> filterProductTypeModel = Model.of();

    IModel<ProductReceipt> filterProductReceiptModel = Model.of();

    Form form;

    /**
     * Construct.
     *
     * @param parameters current backPage parameters
     */
    public PriceTablePage(PageParameters parameters) {
        super(parameters);
    }

    public PriceTablePage() {
    }


    @Override
    protected void onInitialize() {
        super.onInitialize();
        form = new Form("form");
        add(form);
        FormGroup makerGroup = new FormGroup("maker-g", new ResourceModel("productType.maker"));
        makerGroup.add(new MakerSelect2Chooser("maker", filterMakerModel).add(
                new AjaxFormComponentUpdatingBehavior("change") {

                    @Override
                    protected void onUpdate(AjaxRequestTarget target) {
                        onFilterChange(target);
                    }
                }
        ));
        form.add(makerGroup);
        form.add(new FormGroup("product-type-g", new ResourceModel("parents")).add(new SelectProductType("product-type", filterProductTypeModel).add(
                new AjaxFormComponentUpdatingBehavior("change") {

                    @Override
                    protected void onUpdate(AjaxRequestTarget target) {
                        onFilterChange(target);
                    }
                }
        )));

    }

    public void onFilterChange(AjaxRequestTarget target) {
        target.add(table);
    }

    @Override
    public List<? extends IColumn<Price, String>> createColumns() {

        List<IColumn<Price, String>> columns = Lists.newArrayList(
                ProductTypeEditPage.createProductTypeColumn("productType", getPage(), entityModel -> new ProductTypeEditPage(entityModel)),
                new LambdaColumn<Price, String>(new ResourceModel("price"), "price", Price::getPrice) {
                    @Override
                    public void populateItem(Item<ICellPopulator<Price>> item, String componentId, IModel<Price> rowModel) {
                        item.add(new CurrencyLabeledLink<Price>(componentId, rowModel, LambdaModel.of(rowModel, Price::getPrice)) {
                            @Override
                            public void onClick(Optional<AjaxRequestTarget> target) {
                                PriceTablePage.this.setResponsePage(new PriceEditPage(getModel()).setBackNavigateAction(getPage()));
                            }
                        });
                    }
                },
                new LambdaColumn<Price, String>(new ResourceModel("dateFrom"), "dateFrom", p -> p.getDateFrom()),
                new LambdaColumn<Price, String>(new ResourceModel("dateTo"), price -> price.getDateTo())
        );
        return columns;
    }

    @Override
    public PriceEditPage createEditPage(IModel<Price> entityModel) {
        return new PriceEditPage(entityModel);
    }

    @Override
    public PriceRepository getRepository() {
        return repository;
    }

    @Override
    public Specification<Price> createSpecification() {
        Specification<Price> makerSpecification = filterMakerModel.map(maker -> (Specification<Price>) (root, query, cb) -> cb.equal(root.get("maker"), maker)).getObject();
        Specification<Price> productTypeSpecification = filterProductTypeModel.map(
                productTypes -> (Specification<Price>) (root, query, cb) -> cb.equal(root.get("id"), productTypes.getId())).getObject();
        Specifications<Price> result = Specifications.where(makerSpecification);
        result = result.and(productTypeSpecification);
        return result;
    }

    public IModel<Maker> getFilterMakerModel() {
        return filterMakerModel;
    }

    public PriceTablePage setFilterMakerModel(IModel<Maker> filterMakerModel) {
        this.filterMakerModel = filterMakerModel;
        return this;
    }

    public IModel<ProductType> getFilterProductTypeModel() {
        return filterProductTypeModel;
    }

    public PriceTablePage setFilterProductTypeModel(IModel<ProductType> filterProductTypeModel) {
        this.filterProductTypeModel = filterProductTypeModel;
        return this;
    }

    @Override
    public Price createNewEntity() {
        Price price = super.createNewEntity();

        Optional.ofNullable(filterProductTypeModel)
                .ifPresent(m -> price.setProductType(
                        m.map(productType -> productType).getObject()));
        return price;
    }

    public IModel<ProductReceipt> getFilterProductReceiptModel() {
        return filterProductReceiptModel;
    }

    public PriceTablePage setFilterProductReceiptModel(IModel<ProductReceipt> filterProductReceiptModel) {
        this.filterProductReceiptModel = filterProductReceiptModel;
        return this;
    }
}
