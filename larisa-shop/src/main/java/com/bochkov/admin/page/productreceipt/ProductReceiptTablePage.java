package com.bochkov.admin.page.productreceipt;

import com.bochkov.admin.component.LabeledLink;
import com.bochkov.admin.component.action.NavigateAction;
import com.bochkov.admin.page.EntityEditPage;
import com.bochkov.admin.page.EntityTablePage;
import com.bochkov.admin.page.product.ProductTablePage;
import com.google.common.collect.ImmutableList;
import de.agilecoders.wicket.core.markup.html.bootstrap.dialog.Modal;
import larisa.entity.Product;
import larisa.entity.ProductReceipt;
import larisa.repository.ProductReceiptRepository;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.wicket.mount.core.annotation.MountPath;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@MountPath("product-receipts")
public class ProductReceiptTablePage extends EntityTablePage<ProductReceipt> implements IProductReceiptDetailed{
    @Inject
    ProductReceiptRepository repository;

    /**
     * Construct.
     *
     * @param parameters current backPage parameters
     */
    public ProductReceiptTablePage(PageParameters parameters) {
        super(parameters);
    }

    public ProductReceiptTablePage() {
    }

    public ProductReceiptTablePage(IModel model) {
        super(model);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
    }

    @Override
    public List<? extends IColumn<ProductReceipt, String>> createColumns() {
        return ImmutableList.of(
                createIdColumn(new ResourceModel("productReceipt.id")),
                new PropertyColumn<ProductReceipt, String>(new ResourceModel("productReceipt.date"), "date", "date") {
                    @Override
                    public void populateItem(Item<ICellPopulator<ProductReceipt>> item, String componentId, IModel<ProductReceipt> rowModel) {
                        LabeledLink link = new LabeledLink<ProductReceipt>(componentId, rowModel, getDataModel(rowModel), false) {
                            @Override
                            public void onClick(Optional optional) {
                                setResponsePage(new ProductReceiptEditPage(rowModel).setBackNavigateAction((circle, model) -> RequestCycle.get().setResponsePage(getPage())));
                            }
                        };
                        item.add(link);
                    }
                },
                new PropertyColumn<ProductReceipt, String>(new ResourceModel("productReceipt.note"), "note", "note"),
                new PropertyColumn<>(new ResourceModel("productReceipt.seller"),"seller","seller"),
                new AbstractColumn<ProductReceipt, String>(new ResourceModel("productReceipt.products")) {
                    @Override
                    public void populateItem(Item<ICellPopulator<ProductReceipt>> item, String componentId, IModel<ProductReceipt> rowModel) {
                        item.add(LabeledLink.of(componentId, rowModel.map(productReceipt -> productReceipt.getProducts().size()).orElse(0).getObject(), target -> setResponsePage(
                                new ProductTablePage()
                                        .setFilterProductReceiptModel(rowModel)
                                        .setBackNavigateAction(NavigateAction.<Product>goBack(getPage())))).setLabelClasses("label", "label-default"));
                    }
                }
        );
    }

    @Override
    public EntityEditPage createEditPage(IModel<ProductReceipt> entityModel) {
        return new ProductReceiptEditPage(entityModel);
    }

    @Override
    public ProductReceiptRepository getRepository() {
        return repository;
    }


    @Override
    public Modal createDeleteDialog(String id) {
        Modal modal = super.createDeleteDialog(id);
        /* modal.add(new AttributeAppender("class"," center-block "));*/
        modal.size(Modal.Size.Small);
        return modal;
    }
}
