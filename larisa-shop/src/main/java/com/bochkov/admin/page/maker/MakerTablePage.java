package com.bochkov.admin.page.maker;

import com.bochkov.admin.component.LabeledLink;
import com.bochkov.admin.page.EntityTablePage;
import com.bochkov.model.EntityDataProvider;
import com.bochkov.model.EntityModel;
import com.google.common.collect.Lists;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.table.BootstrapDefaultDataTable;
import larisa.entity.Maker;
import larisa.repository.MakerRepository;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.inject.Inject;
import java.util.List;

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
        ISortableDataProvider sortableDataProvider = new EntityDataProvider<Maker>() {
            @Override
            public IModel<Maker> model(Maker entity) {
                return new EntityModel(entity) {
                    @Override
                    public CrudRepository getRepostory() {
                        return repository;
                    }
                };
            }

            @Override
            public PagingAndSortingRepository getRepository() {
                return repository;
            }
        };

        List<IColumn> columns = Lists.newArrayList(
                new PropertyColumn(new ResourceModel("maker.id"), "id", "id"),
                new PropertyColumn<Maker, String>(new ResourceModel("maker.name"), "name", "name") {
                    @Override
                    public void populateItem(Item<ICellPopulator<Maker>> item, String componentId, IModel<Maker> rowModel) {
                        LabeledLink link = new LabeledLink(componentId, rowModel,getDataModel(rowModel),false){
                            @Override
                            public void onClick(AjaxRequestTarget target) {
                                setResponsePage(new MakerEditPage(rowModel, MakerTablePage.this));
                            }
                        };
                        item.add(link);
                    }
                },
                new PropertyColumn(new ResourceModel("maker.note"), "note", "note")
        );
        BootstrapDefaultDataTable table = new BootstrapDefaultDataTable("table", columns, sortableDataProvider, 50).hover();

        add(table);
    }
}
