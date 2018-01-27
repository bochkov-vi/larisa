package com.bochkov.admin.page;

import com.bochkov.admin.component.button.*;
import de.agilecoders.wicket.core.markup.html.bootstrap.dialog.Modal;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.GenericWebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.springframework.data.domain.Persistable;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

import static com.bochkov.ReflectionUtils.getGenericParameterClass;

public abstract class EntityPage<T extends Persistable> extends TitledPage<T> {

    Modal<T> deleteDialog= new Modal<T>("delete-dialog"){
        @Override
        protected void onInitialize() {
            super.onInitialize();
            setOutputMarkupId(true);
        }
    };

    protected NavigateAction<T> backNavigateAction = new NavigateAction<T>() {
        @Override
        public void navigate(RequestCycle circle, IModel<T> model) {
            circle.setResponsePage(WebApplication.get().getHomePage());
        }
    };


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
    }

    protected T createNewEntity() {
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


    public ToolbarPanel createToolbar(String id, Form form, ButtonCreator... buttonCreators) {
        ToolbarPanel toolbarPanel = new ToolbarPanel(id, buttonCreators);
        toolbarPanel.add(buttonId -> new CancelButton(buttonId, form) {
            @Override
            protected void onSubmit(Optional<AjaxRequestTarget> target) {
                onCancel(target);
            }

            @Override
            public boolean isVisible() {
                return EntityPage.this.backNavigateAction != null;
            }
        });
        toolbarPanel.add(buttonId -> new CreateNewButton(id, form) {
            @Override
            protected void onSubmit(Optional<AjaxRequestTarget> target) {
                onCreateNew();
            }
        });
        toolbarPanel.add(buttonId -> new DeleteButton(id, form) {
            @Override
            protected void onSubmit(Optional<AjaxRequestTarget> target) {
                target.ifPresent(t->t.appendJavaScript(""));
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


    public void onDelete(IModel<T> entityModel) {
        if (entityModel.isPresent().getObject()) {
            getRepository().delete(entityModel.getObject());
        }
    }

    protected abstract EntityEditPage createEditPage(IModel<T> entityModel);

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


}
