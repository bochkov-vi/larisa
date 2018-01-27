package com.bochkov.admin.page.maker;

import com.bochkov.admin.component.FileImage;
import com.bochkov.admin.component.LabeledLink;
import com.bochkov.admin.page.EntityEditPage;
import com.bochkov.admin.page.EntityTablePage;
import com.google.common.collect.Lists;
import larisa.entity.Maker;
import larisa.repository.MakerRepository;
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

        setColumns(Lists.newArrayList(
                new PropertyColumn(new ResourceModel("maker.id"), "id", "id"),
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
                new PropertyColumn<Maker, String>(new ResourceModel("maker.file"), "file", "file") {
                    @Override
                    public void populateItem(Item<ICellPopulator<Maker>> item, String componentId, IModel<Maker> rowModel) {
                        FileImage image = new FileImage(componentId, new PropertyModel<>(rowModel, "file"));
                        item.add(image);
                    }
                },
                new PropertyColumn(new ResourceModel("maker.note"), "note", "note")
        ));
        super.onInitialize();


    }

    @Override
    protected EntityEditPage createEditPage(IModel<Maker> entityModel) {
        return new MakerEditPage(entityModel);
    }

    @Override
    public MakerRepository getRepository() {
        return repository;
    }
}
