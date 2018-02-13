package com.bochkov.admin.page.productType;

import com.bochkov.admin.page.EntityTablePage;
import com.bochkov.admin.page.maker.MakerEditPage;
import com.bochkov.admin.page.maker.select2.MakerSelect2Chooser;
import com.bochkov.admin.page.productType.select2.ProductTypeMultiSelect;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import de.agilecoders.wicket.core.markup.html.bootstrap.form.FormGroup;
import larisa.entity.Maker;
import larisa.entity.ProductType;
import larisa.repository.ProductTypeRepository;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.util.CollectionModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.wicketstuff.wicket.mount.core.annotation.MountPath;

import javax.inject.Inject;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@MountPath("product-types")
public class ProductTypeTablePage extends EntityTablePage<ProductType> {
    @Inject
    ProductTypeRepository repository;

    IModel<Maker> filterMakerModel = new Model<>();

    IModel<Collection<ProductType>> filterProductTypesModel = new CollectionModel<>();

    Form form;

    /**
     * Construct.
     *
     * @param parameters current backPage parameters
     */
    public ProductTypeTablePage(PageParameters parameters) {
        super(parameters);
    }

    public ProductTypeTablePage() {
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
        form.add(new FormGroup("product-type-g", new ResourceModel("parents")).add(new ProductTypeMultiSelect("product-type", filterProductTypesModel).add(
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
    public List<? extends IColumn<ProductType, String>> createColumns() {
        return ImmutableList.of(
                createIdColumn(new ResourceModel("id")),

                ProductTypeEditPage.createProductTypeColumn("", getPage(), this),
                MakerEditPage.createMakerColumn("maker", getPage(), entityModel -> new MakerEditPage(entityModel)),
               /* new PropertyColumn<ProductType, String>(new ResourceModel("productType.maker"), "maker.name", "maker.name") {
                    @Override
                    public void populateItem(Item<ICellPopulator<ProductType>> item, String componentId, IModel<ProductType> rowModel) {
                        item.add(LabeledLink.of(componentId, rowModel.map(ProductType::getMaker).map(Maker::getName).getObject(),
                                target -> setResponsePage(new MakerEditPage(rowModel.map(pt -> pt.getMaker()))
                                        .setBackNavigateAction(NavigateAction.goBack(getPage())))));
                    }
                },*/
        createImageColumn(new ResourceModel("file")),
                new PropertyColumn<ProductType, String>(new ResourceModel("volumeNote"), "volumeNote", "volumeNote")
        );
    }

    @Override
    public ProductTypeEditPage createEditPage(IModel<ProductType> entityModel) {
        return new ProductTypeEditPage(entityModel);
    }

    @Override
    public ProductTypeRepository getRepository() {
        return repository;
    }

    @Override
    public Specification<ProductType> createSpecification() {
        Specification<ProductType> makerSpecification = filterMakerModel.map(maker -> (Specification<ProductType>) (root, query, cb) -> cb.equal(root.get("maker"), maker)).getObject();
        Specification<ProductType> productTypeSpecification = filterProductTypesModel.map(
                productTypes -> (Specification<ProductType>) (root, query, cb) -> root.get("id").in(productTypes.stream().map(pt -> pt.getId()).collect(Collectors.toList()))).getObject();
        Specifications<ProductType> result = Specifications.where(makerSpecification);
        result = result.and(productTypeSpecification);
        return result;
    }

    public IModel<Maker> getFilterMakerModel() {
        return filterMakerModel;
    }

    public ProductTypeTablePage setFilterMakerModel(IModel<Maker> filterMakerModel) {
        this.filterMakerModel = filterMakerModel;
        return this;
    }

    public IModel<Collection<ProductType>> getFilterProductTypesModel() {
        return filterProductTypesModel;
    }

    public ProductTypeTablePage setFilterProductTypesModel(IModel<Collection<ProductType>> filterProductTypesModel) {
        this.filterProductTypesModel = filterProductTypesModel;
        return this;
    }

    @Override
    public ProductType createNewEntity() {
        ProductType productType = super.createNewEntity();
        Optional.ofNullable(filterMakerModel).ifPresent(makerIModel -> productType.setMaker(makerIModel.getObject()));
        Optional.ofNullable(filterProductTypesModel)
                .ifPresent(m -> productType.setParents(
                        m.map(c -> Lists.newArrayList(c)).getObject()));
        return productType;
    }

}
