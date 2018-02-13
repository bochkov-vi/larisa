package com.bochkov.admin.page;

import com.bochkov.admin.component.CurrencyLabel;
import com.bochkov.admin.component.FileImage;
import com.bochkov.admin.component.action.NavigateAction;
import com.bochkov.admin.component.button.*;
import com.google.common.collect.ImmutableList;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.ButtonBehavior;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons;
import de.agilecoders.wicket.core.markup.html.bootstrap.dialog.Modal;
import de.agilecoders.wicket.core.markup.html.bootstrap.dialog.ModalCloseButton;
import larisa.entity.IGetFile;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.GenericWebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.*;
import org.apache.wicket.model.util.CollectionModel;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.springframework.data.domain.Persistable;
import org.springframework.data.repository.CrudRepository;

import java.io.Serializable;
import java.util.Collection;
import java.util.Optional;

import static com.bochkov.ReflectionUtils.getGenericParameterClass;

public abstract class EntityPage<T extends Persistable<? extends Serializable>> extends TitledPage<T> implements IDetailed<T>, IEditPageCreator<T> {

    protected NavigateAction<T> backNavigateAction = new NavigateAction<T>() {
        @Override
        public void navigate(RequestCycle circle, IModel<T> model) {
            circle.setResponsePage(WebApplication.get().getHomePage());
        }
    };


    Modal<String> deleteDialog;

    public EntityPage() {
    }

    public EntityPage(IModel<T> model) {
        super(model);
    }

    public EntityPage(PageParameters parameters) {
        super(parameters);
    }


    @Override
    protected void onInitialize() {
        super.onInitialize();
        deleteDialog =createDeleteDialog("delete-dialog");
        add(deleteDialog);
    }

    public T createNewEntity() {
        Class<T> cl = getEntityClass();
        T entity = null;
        try {
            entity = cl.getConstructor().newInstance();
        } catch (Exception e) {
        }
        return entity;
    }

    protected Class<T> getEntityClass() {
        return getGenericParameterClass(getClass(), GenericWebPage.class, 0);
    }


    public ToolbarPanel createToolbar(String toolbarId, Form form, ComponentCreator... componentCreators) {
        ToolbarPanel toolbarPanel = new ToolbarPanel(toolbarId, componentCreators);
        toolbarPanel.add(id -> new CancelButton(id, form) {
            @Override
            protected void onSubmit(Optional<AjaxRequestTarget> target) {
                onCancel(target);
            }

            @Override
            public boolean isVisible() {
                return EntityPage.this.backNavigateAction != null;
            }
        });
        toolbarPanel.add(id -> new CreateNewButton(id, form) {
            @Override
            protected void onSubmit(Optional<AjaxRequestTarget> target) {
                onCreateNew();
            }
        });
        toolbarPanel.add(id -> new DeleteButton(id, form) {
            @Override
            protected void onSubmit(Optional<AjaxRequestTarget> target) {
                target.ifPresent(t -> {
                    t.add(EntityPage.this.deleteDialog);
                    deleteDialog.show(t);
                });
                // target.ifPresent(t -> t.appendJavaScript(String.format("$('#%s').modal('show');", deleteDialog.getMarkupId())));
            }

            @Override
            public boolean isEnabled() {
                return isDeleteEnabled();
            }
        });
        return toolbarPanel;
    }

    public void onCreateNew() {
        onEdit(Model.of(createNewEntity()));
    }

    public void onEdit(IModel<T> entityModel) {
        EntityEditPage entityEditPage = createEditPage(entityModel);
        entityEditPage.setBackNavigateAction((circle, model) -> RequestCycle.get().setResponsePage(getPage()));
        RequestCycle.get().setResponsePage(entityEditPage);
    }


    public void onDelete(Optional<AjaxRequestTarget> target, IModel<Collection<T>> entityModel) {
        target.ifPresent(t -> t.add(feedback));
        if (entityModel.isPresent().getObject()) {
            try {
                if (entityModel instanceof CollectionModel) {
                    getRepository().delete((Collection) entityModel.getObject());
                } else {
                    getRepository().delete(entityModel.getObject());
                }
                success(new StringResourceModel(""));
            } catch (Exception e) {
                error(e.getLocalizedMessage());
            }
        }
    }



    protected abstract CrudRepository<T, ?> getRepository();

    public NavigateAction<T> getBackNavigateAction() {
        return backNavigateAction;
    }

    public EntityPage<T> setBackNavigateAction(NavigateAction<T> backNavigateAction) {
        this.backNavigateAction = backNavigateAction;
        return this;
    }

    public void onCancel(Optional<AjaxRequestTarget> target) {
        backNavigateAction.navigate(RequestCycle.get(), getModel());
    }

    public IModel<Collection<T>> getDeletedModel() {
        return LambdaModel.of(() -> ImmutableList.of(getModelObject()));
    }

    public boolean isDeleteEnabled() {
        return getDeletedModel() != null && (Boolean) getDeletedModel().isPresent().getObject();
    }

    public Modal createDeleteDialog(String id) {
        DeleteModal<T> modal = new DeleteModal<T>(id, getDeletedModel()) {
            @Override
            public Component createDetails(String id, IModel<T> model) {
                return createDetailsPanel(id, model);
            }
        };
        modal.header(new ResourceModel("confirmEntityDeleting").wrapOnAssignment(EntityPage.this));
        modal.setOutputMarkupId(true);
        modal.addButton(new ModalCloseButton(new ResourceModel("cancel").wrapOnAssignment(getPage())));
        modal.addButton(new AjaxLink<String>("button", new ResourceModel("delete").wrapOnAssignment(getPage())) {
            @Override
            public void onClick(AjaxRequestTarget target) {
                onDelete(Optional.of(target), getDeletedModel());
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


    protected static <E extends IGetFile> IColumn<E, String> createImageColumn(IModel<String> header) {
        return new PropertyColumn<E, String>(header, "file", "file") {
            @Override
            public void populateItem(Item<ICellPopulator<E>> item, String componentId, IModel<E> rowModel) {
                FileImage image = new FileImage(componentId, new PropertyModel<>(rowModel, "file"));
                item.add(image);
            }

            @Override
            public String getCssClass() {
                return "visible-lg visible-sm visible-md";
            }
        };

    }

    protected static <E extends Persistable> IColumn<E, String> createIdColumn(IModel<String> header) {
        return new PropertyColumn<E, String>(header, "id", "id") {
            @Override
            public String getCssClass() {
                return "visible-lg visible-sm visible-md";
            }
        };
    }
    protected static <E extends Persistable> IColumn<E, String> createPriceColumn(IModel<String> header) {
        return new PropertyColumn<E, String>(header, "price", "price") {
            @Override
            public void populateItem(Item<ICellPopulator<E>> item, String componentId, IModel<E> rowModel) {
                item.add(new CurrencyLabel(componentId,getDataModel(rowModel)));
            }
        };
    }



}
