package com.bochkov.admin.page.productreceipt;

import com.bochkov.admin.component.button.CreateNewButton;
import com.bochkov.admin.component.button.DeleteButton;
import com.bochkov.admin.component.button.ToolbarPanel;
import com.bochkov.admin.component.selectiontable.MdalableTablePanel;
import com.bochkov.admin.page.EntityEditPage;
import com.bochkov.admin.page.product.ProductEditPage;
import com.bochkov.admin.page.productType.ProductTypeEditPage;
import com.bochkov.model.EntityDataProvider;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import larisa.entity.Product;
import larisa.entity.ProductReceipt;
import larisa.repository.FileRepository;
import larisa.repository.ProductReceiptRepository;
import larisa.repository.ProductRepository;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.LambdaColumn;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.wicketstuff.wicket.mount.core.annotation.MountPath;

import javax.inject.Inject;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@MountPath("product-receipt")
public class ProductReceiptEditPage extends EntityEditPage<ProductReceipt, Integer> implements IProductReceiptDetailed {

    @Inject
    ProductReceiptRepository repository;

    @Inject
    ProductRepository productRepository;

    @Inject
    FileRepository fileRepository;

    MdalableTablePanel<Product, String> productDataTable;

    public ProductReceiptEditPage() {
    }

    public ProductReceiptEditPage(IModel<ProductReceipt> model) {
        super(model);
    }

    public ProductReceiptEditPage(PageParameters parameters) {
        super(parameters);
    }


    @Override
    protected void onInitialize() {
        super.onInitialize();
        ProductReceiptInput input = new ProductReceiptInput("input", getModel());
        form.add(input);
        List<IColumn<Product, String>> columns = Lists.newArrayList(
                ProductTypeEditPage.createProductTypeColumn("productType", getPage(), entityModel -> new ProductTypeEditPage(entityModel)),
                createPriceColumn(new ResourceModel("product.price")),
                new LambdaColumn<Product, String>(new ResourceModel("product.volume"), "volume", p -> p.getVolume()),
                new LambdaColumn<Product, String>(new ResourceModel("product.totalPrice"), product -> product.getVolume() * product.getPrice())
        );
       /* SelectRowDataTable<Product, String> productDataTable = new SelectRowDataTable<Product, String>("products", columns, new EntityDataProvider<Product>() {

            @Override
            public JpaSpecificationExecutor<Product> getRepository() {
                return productRepository;
            }

            @Override
            public Specification<Product> createSpecification() {
                return (root, query, cb) -> cb.equal(root.get("productReceipt"), ProductReceiptEditPage.this.getModelObject());
            }

        }, 50);*/
        ToolbarPanel productsToolbar = new ToolbarPanel("product-toolbar");
        productsToolbar.setOutputMarkupId(true);
        productDataTable = new MdalableTablePanel<Product, String>("products", columns, new EntityDataProvider<Product>() {

            @Override
            public JpaSpecificationExecutor<Product> getRepository() {
                return productRepository;
            }

            @Override
            public Specification<Product> createSpecification() {
                return (root, query, cb) -> cb.equal(root.get("productReceipt"), ProductReceiptEditPage.this.getModelObject());
            }

        }, 50) {
            @Override
            public void onDelete(Optional<AjaxRequestTarget> target, IModel<Collection<Product>> entityModel) {
                onDeleteProduct(target, entityModel);
            }

            @Override
            public void onSelect(AjaxRequestTarget target, IModel<Product> entity) {
                super.onSelect(target, entity);
                target.add(productsToolbar);
            }
        };

        productsToolbar.add(id -> new DeleteButton(id, form) {
            @Override
            protected void onSubmit(Optional<AjaxRequestTarget> target) {
                productDataTable.showModal(target);
            }

            @Override
            public boolean isEnabled() {
                return productDataTable.getModel().isPresent().getObject() && !productDataTable.getModelObject().isEmpty();
            }
        }, id -> new CreateNewButton(id, form) {
            @Override
            protected void onSubmit(Optional<AjaxRequestTarget> target) {
                onAddNewProduct();
            }
        });
        form.add(productsToolbar);
        form.add(productDataTable);
    }

    public void onAddNewProduct() {
        ProductEditPage page = new ProductEditPage() {
            @Override
            public void onSave(Optional<AjaxRequestTarget> target) {
                super.onSave(target);
                IModel<ProductReceipt> productReceiptModel = ProductReceiptEditPage.this.getModel();
                List<Product> products = Optional.ofNullable(productReceiptModel)
                        .map(model -> model.map(pr -> pr.getProducts()).getObject())
                        .orElse(ImmutableList.of());
                IModel<Product> productModel = getModel();
                if (productReceiptModel.isPresent().getObject() && productModel.isPresent().getObject()) {
                    productReceiptModel.getObject().setProducts(Lists.newArrayList(Iterables.concat(products, ImmutableList.of(productModel.getObject()))));
                }
                ProductReceiptEditPage.this.onSave(Optional.empty());
                setResponsePage(ProductReceiptEditPage.this);
            }
        };
        Product product = page.createNewEntity();
        product.setProductReceipt(getModelObject());
        page.setBackNavigateAction((circle, model) -> circle.setResponsePage(getPage()));
        page.onEdit(Model.of(product));
    }


    public void onDeleteProduct(Optional<AjaxRequestTarget> target, IModel<Collection<Product>> product) {
        target.ifPresent(t -> t.add(productDataTable, feedback));
        if (product.isPresent().getObject()) {
            productRepository.deleteAll(product.getObject());
        }
    }

    @Override
    public ProductReceiptEditPage createEditPage(IModel<ProductReceipt> entityModel) {
        return new ProductReceiptEditPage(entityModel);
    }

    protected void onSubmit(AjaxRequestTarget target) {
        save();
        target.add(feedback);
    }


    public void save() {
        repository.save(getModelObject());
        success(new StringResourceModel("productReceipt.saved", this, getModel()).getObject());
    }

    @Override
    protected ProductReceiptRepository getRepository() {
        return repository;
    }

}
