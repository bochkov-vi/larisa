package com.bochkov.admin.page.maker;

import com.bochkov.admin.component.LabeledLink;
import com.bochkov.admin.component.action.NavigateAction;
import com.bochkov.admin.page.EntityEditPage;
import com.bochkov.admin.page.EntityTablePage;
import com.bochkov.admin.page.productType.ProductTypeTablePage;
import com.google.common.collect.ImmutableList;
import larisa.entity.Maker;
import larisa.repository.MakerRepository;
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

@MountPath("makers")
public class MakerTablePage extends EntityTablePage<Maker> {
    @Inject
    MakerRepository repository;

    /**
     * Construct.
     *
     * @param parameters current backPage parameters
     */
    public MakerTablePage(PageParameters parameters) {
        super(parameters);
    }

    public MakerTablePage() {
    }

    public MakerTablePage(IModel model) {
        super(model);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
    }

    @Override
    public List<? extends IColumn<Maker, String>> createColumns() {
        return ImmutableList.of(
                createIdColumn(new ResourceModel("maker.id")),
                new PropertyColumn<Maker, String>(new ResourceModel("maker.name"), "name", "name") {
                    @Override
                    public void populateItem(Item<ICellPopulator<Maker>> item, String componentId, IModel<Maker> rowModel) {
                        LabeledLink link = new LabeledLink<Maker>(componentId, rowModel, getDataModel(rowModel), false) {
                            @Override
                            public void onClick(Optional optional) {
                                setResponsePage(new MakerEditPage(rowModel).setBackNavigateAction((circle, model) -> RequestCycle.get().setResponsePage(getPage())));
                            }
                        };
                        item.add(link);
                    }
                },
                createImageColumn(new ResourceModel("maker.file")),
                new PropertyColumn<Maker, String>(new ResourceModel("maker.note"), "note", "note"),
                new AbstractColumn<Maker, String>(new ResourceModel("maker.productTypes")) {
                    @Override
                    public void populateItem(Item<ICellPopulator<Maker>> item, String componentId, IModel<Maker> rowModel) {
                        item.add(LabeledLink.of(componentId, rowModel.map(maker -> maker.getProductTypes().size()).orElse(0).getObject(), target -> setResponsePage(
                                new ProductTypeTablePage()
                                        .setFilterMakerModel(rowModel)
                                        .setBackNavigateAction(NavigateAction.goBack(getPage())))).setLabelClasses("label", "label-default"));
                    }
                }
        );
    }

    @Override
    public EntityEditPage createEditPage(IModel<Maker> entityModel) {
        return new MakerEditPage(entityModel);
    }

    @Override
    public MakerRepository getRepository() {
        return repository;
    }



   /* @Override
    public Modal createDeleteDialog(String id) {
        Modal modal = super.createDeleteDialog(id);
        *//* modal.add(new AttributeAppender("class"," center-block "));*//*
        modal.size(Modal.Size.Small);
        return modal;
    }*/
}
