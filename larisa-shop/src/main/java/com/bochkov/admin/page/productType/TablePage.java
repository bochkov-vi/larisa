package com.bochkov.admin.page.productType;

import com.bochkov.admin.component.FileImage;
import com.bochkov.admin.component.LabeledLink;
import com.bochkov.admin.page.EntityEditPage;
import com.bochkov.admin.page.EntityTablePage;
import com.google.common.collect.Lists;
import larisa.entity.ProductType;
import larisa.repository.ProductTypeRepository;
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

@MountPath("product-types")
public class TablePage extends EntityTablePage<ProductType> {
    @Inject
    ProductTypeRepository repository;

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
                new PropertyColumn<ProductType, String>(new ResourceModel("id"), "id", "id"),
                new PropertyColumn<ProductType, String>(new ResourceModel("name"), "name", "name") {
                    @Override
                    public void populateItem(Item<ICellPopulator<ProductType>> item, String componentId, IModel<ProductType> rowModel) {
                        LabeledLink link = new LabeledLink<ProductType>(componentId, rowModel, getDataModel(rowModel), false) {
                            @Override
                            public void onClick(Optional optional) {
                                setResponsePage(new EditPage(rowModel).setBackNavigateAction((circle, model) -> RequestCycle.get().setResponsePage(getPage())));
                            }
                        };
                        item.add(link);
                    }
                },
                new PropertyColumn<ProductType, String>(new ResourceModel("file"), "file", "file") {
                    @Override
                    public void populateItem(Item<ICellPopulator<ProductType>> item, String componentId, IModel<ProductType> rowModel) {
                        FileImage image = new FileImage(componentId, new PropertyModel<>(rowModel, "file"));
                        item.add(image);
                    }
                },
                new PropertyColumn<ProductType, String>(new ResourceModel("volumeNote"), "volumeNote", "volumeNote")
        ));
        super.onInitialize();


    }

    @Override
    protected EntityEditPage createEditPage(IModel<ProductType> entityModel) {
        return new EditPage(entityModel);
    }

    @Override
    public ProductTypeRepository getRepository() {
        return repository;
    }
}
