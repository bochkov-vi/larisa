package com.bochkov.admin.page;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import de.agilecoders.wicket.core.markup.html.bootstrap.dialog.Modal;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RefreshingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;

public class DeleteModal<T extends Serializable> extends Modal<Collection<T>> {


    public DeleteModal(String markupId) {
        super(markupId);
    }

    public DeleteModal(String id, IModel<Collection<T>> model) {
        super(id, model);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        RefreshingView<T> refreshingView = new RefreshingView<T>("content", getModel()) {
            @Override
            protected Iterator<IModel<T>> getItemModels() {
                Collection<T> collection = getModel().orElse(ImmutableList.of()).getObject();
                return Iterables.transform(collection,e-> (IModel<T>)Model.of(e)).iterator();
            }

            @Override
            protected void populateItem(Item<T> item) {
                item.add(Optional.ofNullable(createDetails("details", item.getModel())).orElse(new Label("details", item.getModel().map(Object::toString))));
            }
        };
        add(refreshingView);
    }

    public Component createDetails(String id, IModel<T> model) {
        return null;
    }
}
