package com.bochkov.admin.page.product;

import com.bochkov.admin.page.EntityTablePage;
import com.bochkov.admin.page.maker.select2.MakerSelect2Chooser;
import com.bochkov.admin.page.productType.ProductTypeEditPage;
import com.bochkov.admin.page.productType.select2.SelectProductType;
import com.google.common.collect.ImmutableList;
import de.agilecoders.wicket.core.markup.html.bootstrap.form.FormGroup;
import larisa.entity.Maker;
import larisa.entity.Product;
import larisa.entity.ProductReceipt;
import larisa.entity.ProductType;
import larisa.repository.ProductRepository;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
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
public class ProductTablePage extends EntityTablePage<Product> {
    @Inject
    ProductRepository repository;

    IModel<Maker> filterMakerModel = new Model<>();

    IModel<ProductType> filterProductTypeModel = Model.of();

    IModel<ProductReceipt> filterProductReceiptModel = Model.of();

    Form form;

    /**
     * Construct.
     *
     * @param parameters current backPage parameters
     */
    public ProductTablePage(PageParameters parameters) {
        super(parameters);
    }

    public ProductTablePage() {
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
    public List<? extends IColumn<Product, String>> createColumns() {

        return ImmutableList.of(
                createIdColumn(new ResourceModel("id")),
                ProductTypeEditPage.createProductTypeColumn(new ResourceModel("productType.name"), "productType.name", getPage(), entityModel -> new ProductTypeEditPage(entityModel)),
                new PropertyColumn<Product, String>(new ResourceModel("volumeNote"), "volumeNote", "volumeNote")
        );
    }

    @Override
    public ProductEditPage createEditPage(IModel<Product> entityModel) {
        return new ProductEditPage(entityModel);
    }

    @Override
    public ProductRepository getRepository() {
        return repository;
    }

    @Override
    public Specification<Product> createSpecification() {
        Specification<Product> makerSpecification = filterMakerModel.map(maker -> (Specification<Product>) (root, query, cb) -> cb.equal(root.get("maker"), maker)).getObject();
        Specification<Product> productTypeSpecification = filterProductTypeModel.map(
                productTypes -> (Specification<Product>) (root, query, cb) -> cb.equal(root.get("id"), productTypes.getId())).getObject();
        Specifications<Product> result = Specifications.where(makerSpecification);
        result = result.and(productTypeSpecification);
        return result;
    }

    public IModel<Maker> getFilterMakerModel() {
        return filterMakerModel;
    }

    public ProductTablePage setFilterMakerModel(IModel<Maker> filterMakerModel) {
        this.filterMakerModel = filterMakerModel;
        return this;
    }

    public IModel<ProductType> getFilterProductTypeModel() {
        return filterProductTypeModel;
    }

    public ProductTablePage setFilterProductTypeModel(IModel<ProductType> filterProductTypeModel) {
        this.filterProductTypeModel = filterProductTypeModel;
        return this;
    }

    @Override
    public Product createNewEntity() {
        Product product = super.createNewEntity();

        Optional.ofNullable(filterProductTypeModel)
                .ifPresent(m -> product.setProductType(
                        m.map(productType -> productType).getObject()));
        return product;
    }

    public IModel<ProductReceipt> getFilterProductReceiptModel() {
        return filterProductReceiptModel;
    }

    public ProductTablePage setFilterProductReceiptModel(IModel<ProductReceipt> filterProductReceiptModel) {
        this.filterProductReceiptModel = filterProductReceiptModel;
        return this;
    }
}
