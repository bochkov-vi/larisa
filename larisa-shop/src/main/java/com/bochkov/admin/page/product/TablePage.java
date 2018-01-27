package com.bochkov.admin.page.product;

import com.bochkov.admin.component.FileImage;
import com.bochkov.admin.component.LabeledLink;
import com.bochkov.admin.page.EntityEditPage;
import com.bochkov.admin.page.EntityTablePage;
import com.google.common.collect.Lists;
import larisa.entity.Product;
import larisa.repository.ProductRepository;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.wicket.mount.core.annotation.MountPath;

import javax.inject.Inject;
import java.util.Optional;

@MountPath("products")
public class TablePage extends EntityTablePage<Product> {
    @Inject
    ProductRepository repository;

    /**
     * Construct.
     *
     * @param parameters current backPage parameters
     */
    public TablePage(PageParameters parameters) {
        super(parameters);
    }

    public TablePage() {
    }

    public TablePage(IModel model) {
        super(model);
    }

    @Override
    protected void onInitialize() {

        setColumns(Lists.newArrayList(
                new PropertyColumn<Product, String>(new ResourceModel("id"), "id", "id"),
                new PropertyColumn<Product, String>(new ResourceModel("name"), "name", "name") {
                    @Override
                    public void populateItem(Item<ICellPopulator<Product>> item, String componentId, IModel<Product> rowModel) {
                        LabeledLink link = new LabeledLink<Product>(componentId, rowModel, getDataModel(rowModel), false) {
                            @Override
                            public void onClick(Optional optional) {
                                setResponsePage(new EditPage(rowModel).setBackNavigateAction((circle, model) -> RequestCycle.get().setResponsePage(getPage())));
                            }
                        };
                        item.add(link);
                    }
                },
                new PropertyColumn<Product, String>(new ResourceModel("file"), "file", "file") {
                    @Override
                    public void populateItem(Item<ICellPopulator<Product>> item, String componentId, IModel<Product> rowModel) {
                        FileImage image = new FileImage(componentId, new PropertyModel<>(rowModel, "file"));
                        item.add(image);
                    }
                },
                new PropertyColumn<Product, String>(new ResourceModel("note"), "note", "note")
        ));
        super.onInitialize();


    }

    @Override
    protected EntityEditPage createEditPage(IModel<Product> entityModel) {
        return new EditPage(entityModel);
    }

    @Override
    public ProductRepository getRepository() {
        return repository;
    }
}
