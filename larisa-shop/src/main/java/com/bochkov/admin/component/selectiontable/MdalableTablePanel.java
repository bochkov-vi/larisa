package com.bochkov.admin.component.selectiontable;

import com.bochkov.admin.page.DeleteModal;
import com.bochkov.admin.page.IDetailed;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.ButtonBehavior;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons;
import de.agilecoders.wicket.core.markup.html.bootstrap.dialog.ModalCloseButton;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LambdaModel;
import org.apache.wicket.model.ResourceModel;
import org.danekja.java.util.function.serializable.SerializableConsumer;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class MdalableTablePanel<T extends Serializable, S> extends GenericPanel<Collection<T>> implements IDetailed<T> {

    SelectRowDataTable<T, S> table;

    DeleteModal modal;


    public MdalableTablePanel(String id, List<? extends IColumn<T, S>> columns, ISortableDataProvider<T, S> dataProvider, long rowsPerPage) {
        super(id);
        table = new SelectRowDataTable<T, S>("table", columns, dataProvider, rowsPerPage) {
            @Override
            public void onSelect(AjaxRequestTarget target, IModel<T> entity) {
                super.onSelect(target, entity);
                MdalableTablePanel.this.onSelect(target, entity);
            }
        };
        setModel(LambdaModel.of(() -> table.getSelection().getObject(),
                (SerializableConsumer<Collection<T>>) ts -> table.getSelection().setObject(ts)));
        add(table);
    }


    @Override
    public GenericPanel<Collection<T>> setModel(IModel<Collection<T>> model) {
        return super.setModel(model);
    }

    @Override
    public IModel<Collection<T>> getModel() {
        return super.getModel();
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        modal = createDeleteDialog("modal");
        add(modal);
    }


    public DeleteModal createDeleteDialog(String id) {
        modal = new DeleteModal<T>(id,getModel()) {
            @Override

            public Component createDetails(String id, IModel<T> model) {
                return createDetailsPanel(id, model);
            }
        };
        modal.header(new ResourceModel("confirmEntityDeleting").wrapOnAssignment(MdalableTablePanel.this));
        modal.setOutputMarkupId(true);
        modal.addButton(new ModalCloseButton(new ResourceModel("cancel").wrapOnAssignment(getPage())));
        modal.addButton(new AjaxLink<String>("button", new ResourceModel("delete").wrapOnAssignment(getPage())) {
            @Override
            public void onClick(AjaxRequestTarget target) {
                onDelete(Optional.of(target), MdalableTablePanel.this.getModel());
                target.prependJavaScript(String.format("$('#%s').modal('hide');", modal.getMarkupId()));
            }

            @Override
            protected void onInitialize() {
                super.onInitialize();
                setBody(getDefaultModel());
            }
        }.add(new ButtonBehavior(Buttons.Type.Default)));
        return modal;
    }


    public void onDelete(Optional<AjaxRequestTarget> target, IModel<Collection<T>> entityModel) {

    }

    public void showModal(Optional<AjaxRequestTarget> target) {
        target.ifPresent(t -> t.add(modal));
        target.ifPresent(t -> modal.show(t));
    }

    public void onSelect(AjaxRequestTarget target, IModel<T> entity) {
        System.out.println(getModel());
    }

}
