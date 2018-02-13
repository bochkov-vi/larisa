package com.bochkov.admin.component.selectiontable;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import de.agilecoders.wicket.core.markup.html.bootstrap.behavior.CssClassNameAppender;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.table.BootstrapDefaultDataTable;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.CollectionModel;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class SelectRowDataTable<T, S> extends BootstrapDefaultDataTable<T, S> {

    final IModel<Collection<T>> selection = new CollectionModel<T>();

    boolean selectable = true;


    public SelectRowDataTable(String id, List<? extends IColumn<T, S>> columns, ISortableDataProvider<T, S> dataProvider, long rowsPerPage) {
        super(id, columns, dataProvider, rowsPerPage);
    }

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
            IModel<Collection<T>> selectedEntitiesModel = Optional.ofNullable(this.selection).orElseGet(() -> new Model(Lists.<T>newArrayList())).orElse(Lists.<T>newArrayList());
            Collection<T> selectedEntities = selectedEntitiesModel.getObject();
            if (selectedEntities.contains(item.getModelObject())) {
                item.add(new CssClassNameAppender("active"));
            }

        }
        return item;
    }


    final void onSelect(AjaxRequestTarget target, Item<T> row) {
        T entity = row.getModelObject();
        Collection<T> selectedEntities = selection.orElse(Lists.<T>newArrayList()).getObject();
        if (selectedEntities.contains(entity)) {
            selectedEntities.remove(entity);
            target.appendJavaScript("$('#" + row.getMarkupId() + "').removeClass('active')");
        } else {
            target.appendJavaScript("$('#" + row.getMarkupId() + "').addClass('active')");
            selectedEntities = Lists.newArrayList(Iterables.concat(selectedEntities, ImmutableList.of(row.getModelObject())));
        }
        selection.setObject(selectedEntities);
        onSelect(target, row.getModel());
    }

    public void onSelect(AjaxRequestTarget target, IModel<T> entity) {
        System.out.println(selection);
    }

    public boolean isSelectable() {
        return selectable;
    }

    public SelectRowDataTable<T, S> setSelectable(boolean selectable) {
        this.selectable = selectable;
        return this;
    }

    public IModel<Collection<T>> getSelection() {
        return selection;
    }

}
