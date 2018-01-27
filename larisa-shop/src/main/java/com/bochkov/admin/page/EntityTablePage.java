package com.bochkov.admin.page;

import com.bochkov.admin.component.button.ButtonCreator;
import com.bochkov.admin.component.button.ToolbarPanel;
import com.bochkov.model.EntityDataProvider;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import de.agilecoders.wicket.core.markup.html.bootstrap.behavior.CssClassNameAppender;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.table.BootstrapDefaultDataTable;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.CollectionModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.springframework.data.domain.Persistable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public abstract class EntityTablePage<T extends Persistable> extends EntityPage<T> {
    protected Form toolbarForm = new Form("toolbar-form");

    protected ISortableDataProvider<T, String> sortableDataProvider = new EntityDataProvider<T>() {
        @Override
        public IModel<T> model(T entity) {
            return Model.of(entity);
        }

        @Override
        public PagingAndSortingRepository getRepository() {
            return EntityTablePage.this.getRepository();
        }
    };

    DataTable<T, String> table;

    boolean selectable = true;

    List<? extends IColumn<T, String>> columns = ImmutableList.of();

    IModel<Collection<T>> selection;

    public EntityTablePage() {
    }

    public EntityTablePage(IModel<T> model) {
        super(model);
    }

    public EntityTablePage(PageParameters parameters) {
        super(parameters);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        add(toolbarForm);
        toolbarForm.add(createToolbar("toolbar", toolbarForm));
        add(table = createTable("table"));
        table.setOutputMarkupId(true);
    }

    public ISortableDataProvider getSortableDataProvider() {
        return sortableDataProvider;
    }

    public EntityTablePage<T> setSortableDataProvider(ISortableDataProvider sortableDataProvider) {
        this.sortableDataProvider = sortableDataProvider;
        return this;
    }

    public BootstrapDefaultDataTable<T, String> createTable(String id) {
        BootstrapDefaultDataTable table = new BootstrapDefaultDataTable<T, String>(id, columns, sortableDataProvider, 50) {
            @Override
            protected Item<T> newRowItem(String id, int index, IModel<T> model) {
                Item<T> item = super.newRowItem(id, index, model);

                if (isSelectable()) {
                    item.setOutputMarkupId(true);
                    item.add(new AjaxEventBehavior("click") {
                        @Override
                        protected void onEvent(AjaxRequestTarget target) {
                            onSelect(target, item);
                        }
                    });
                    Optional.ofNullable(selection).ifPresent(sm -> {
                        IModel<Boolean> bm = sm.map(selected -> selected.contains(item.getModelObject()));
                        if (bm.isPresent().getObject() && bm.getObject()) {
                            item.add(new CssClassNameAppender("active"));
                        }
                    });

                }
                return item;
            }
        }.hover();


        return table;
    }

    public List<? extends IColumn<T, String>> getColumns() {
        return columns;
    }

    public EntityTablePage<T> setColumns(List<? extends IColumn<T, String>> columns) {
        this.columns = columns;
        return this;
    }

    public void onSelect(AjaxRequestTarget target, Item<T> row) {
        target.add(toolbarForm);
        if (selection == null) {
            selection = new CollectionModel<>(ImmutableList.of());
        }
        T entity = row.getModelObject();
        Collection<T> selected = selection.orElse(ImmutableList.of()).getObject();
        if (selected.contains(entity)) {
            selected.remove(entity);
            target.appendJavaScript("$('#" + row.getMarkupId() + "').removeClass('active')");
        } else {
            target.appendJavaScript("$('#" + row.getMarkupId() + "').addClass('active')");
            selection.setObject(Lists.newArrayList(Iterables.concat(selected, ImmutableList.of(entity))));
        }
    }

    public boolean isSelectable() {
        return selectable;
    }

    public EntityTablePage<T> setSelectable(boolean selectable) {
        this.selectable = selectable;
        return this;
    }

    @Override
    protected abstract PagingAndSortingRepository<T, ?> getRepository();

    @Override
    public ToolbarPanel createToolbar(String id, Form form, ButtonCreator... buttonCreators) {
        ToolbarPanel toolbarPanel = super.createToolbar(id, form, buttonCreators);
        /*toolbarPanel.add(buttonId -> new DeleteButton(buttonId, toolbarForm) {
            @Override
            protected void onSubmit(Optional<AjaxRequestTarget> target) {
                if (Optional.ofNullable(selection).isPresent() && selection.map(c -> !c.isEmpty()).getObject()) {
                    for (T entity : selection.getObject()) {
                        onDelete(Model.of(entity));
                    }
                    target.ifPresent(t -> t.add(table));
                }
            }

            @Override
            public boolean isEnabled() {
                return Optional.ofNullable(selection).isPresent() && selection.map(c -> !c.isEmpty()).getObject();
            }
        });*/
        return toolbarPanel;
    }

    @Override
    IModel<Collection<T>> getDeletedModel() {
        return selection;
    }

    @Override
    public boolean isDeleteEnabled() {
        return super.isDeleteEnabled() && getDeletedModel().map(c -> !c.isEmpty()).getObject();
    }
}
