package com.bochkov.admin.page.maker;

import com.bochkov.admin.page.BasePage;
import com.bochkov.model.EntityDataProvider;
import com.bochkov.model.EntityModel;
import com.google.common.collect.Lists;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.table.BootstrapDefaultDataTable;
import larisa.entity.Maker;
import larisa.repository.MakerRepository;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.HeaderlessColumn;
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

public class MakerTablePage extends BasePage {
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
                new PropertyColumn(new ResourceModel("maker.name"), "name", "name"),
                new PropertyColumn(new ResourceModel("maker.note"), "note", "note")
                ,new HeaderlessColumn<Maker,String>() {
                    @Override
                    public void populateItem(Item<ICellPopulator<Maker>> cellItem, String componentId, IModel<Maker> rowModel) {
                        cellItem.add(new EditLink(componentId,null) {
                            @Override
                            public void onClick() {
                                this.setResponsePage(new MakerEditPage(rowModel,MakerTablePage.this));
                            }
                        });
                    }

                }
        );
        BootstrapDefaultDataTable table = new BootstrapDefaultDataTable("table", columns, sortableDataProvider, 50).hover();

        add(table);
    }
}
