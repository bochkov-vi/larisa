package com.bochkov.admin.page.productreceipt;

import com.bochkov.admin.component.button.DeleteButton;
import com.bochkov.admin.component.button.DeleteButtonWithModal;
import com.bochkov.admin.component.button.ToolbarPanel;
import com.bochkov.admin.component.selectiontable.SelectRowDataTable;
import com.bochkov.admin.page.EntityEditPage;
import com.bochkov.admin.page.product.ProductEditPage;
import com.bochkov.admin.page.productType.ProductTypeEditPage;
import com.bochkov.model.EntityDataProvider;
import com.google.common.collect.Lists;
import de.agilecoders.wicket.core.markup.html.bootstrap.dialog.Modal;
import larisa.entity.Product;
import larisa.entity.ProductReceipt;
import larisa.repository.FileRepository;
import larisa.repository.ProductReceiptRepository;
import larisa.repository.ProductRepository;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.LambdaColumn;
import org.apache.wicket.model.IModel;
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
        SelectRowDataTable<Product, String> productDataTable = new SelectRowDataTable<Product, String>("products", columns, new EntityDataProvider<Product>() {

            @Override
            public JpaSpecificationExecutor<Product> getRepository() {
                return productRepository;
            }

            @Override
            public Specification<Product> createSpecification() {
                return (root, query, cb) -> cb.equal(root.get("productReceipt"), ProductReceiptEditPage.this.getModelObject());
            }
        }, 50);
        ToolbarPanel toolbarPanel = new ToolbarPanel("product-toolbar");
        DeleteButtonWithModal<Product> deleteButtonWithModal = new DeleteButtonWithModal<Product>() {
            @Override
            public Modal createDeleteDialog(String id) {
                return ProductEditPage.this.createDeleteDialog();
            }

            @Override
            public void onDelete(Optional<AjaxRequestTarget> target, IModel<Collection<Product>> entityModel) {

            }
        };
        toolbarPanel.add(id -> new DeleteButton(id, form) {
            @Override
            protected void onSubmit(Optional<AjaxRequestTarget> target) {
                super.onSubmit(target);
            }
        });
        form.add(toolbarPanel);
        form.add(productDataTable);
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
