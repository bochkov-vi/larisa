package com.bochkov.admin.page;

import com.bochkov.admin.component.button.ComponentCreator;
import com.bochkov.admin.component.button.SaveButton;
import com.bochkov.admin.component.button.ToolbarPanel;
import com.bochkov.model.EntityModel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.springframework.data.domain.Persistable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.util.ClassTypeInformation;
import org.springframework.data.util.TypeInformation;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public abstract class EntityEditPage<T extends Persistable<ID>, ID extends Serializable> extends EntityPage<T> {


    protected Form<T> form = new Form<>("form");


    public EntityEditPage() {
    }

    public EntityEditPage(IModel<T> model) {
        super(model);
    }

    public EntityEditPage(PageParameters parameters) {
        super(parameters);
        setModel(new EntityModel<T, ID>() {
            @Override
            public CrudRepository<T, ID> getRepostory() {
                return EntityEditPage.this.getRepository();
            }
        });
        T entity = extract(parameters);
        getModel().setObject(entity);
    }


    public static <T extends Persistable> PageParameters pageParameters(T entity) {
        return new PageParameters().add(entity.getClass().getSimpleName(), entity.getId());
    }

    public static <T extends Persistable> PageParameters pageParameters(IModel<T> model) {
        return pageParameters(model.getObject());
    }

    public static String parameterName(Class c) {
        return c.getSimpleName();
    }

    public static <P> Class<P> argument(EntityEditPage object, int i) {
        TypeInformation<?> information = ClassTypeInformation.from(object.getClass());
        List<TypeInformation<?>> arguments = information.getSuperTypeInformation(EntityEditPage.class).getTypeArguments();

        if (arguments.size() < i || arguments.get(i) == null) {
            throw new IllegalArgumentException(String.format("Could not resolve id type of %s!", object.getClass()));
        }
        return (Class<P>) arguments.get(i).getType();
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        form.setModel(new CompoundPropertyModel<>(getModel()));
        add(form);
        form.add(createToolbar("toolbar", form));
    }

    protected T extract(PageParameters pageParameters) {
        Class<T> entityClass = argument(this, 0);
        T entity = null;
        try {
            entity = pageParameters.get(parameterName(entityClass)).toOptional(entityClass);
        } catch (Exception e) {
            Class<ID> idClass = argument(this, 1);
            ID id = null;
            try {
                id = pageParameters.get(parameterName(entityClass)).toOptional(idClass);
            } catch (Exception e1) {

            }
            if (id != null) {
                entity = getRepository().findOne(id);
            }
        }
        return entity;
    }

    @Override
    public ToolbarPanel createToolbar(String id, Form form, ComponentCreator... componentCreators) {
        ToolbarPanel toolbarPanel = super.createToolbar(id, form, componentCreators);
        toolbarPanel.add(bid -> new SaveButton(bid, form) {
            @Override
            protected void onSubmit(Optional<AjaxRequestTarget> target) {
                onSave(target);
            }

            @Override
            protected void onError(Optional<AjaxRequestTarget> target) {
                target.ifPresent(t -> t.add(feedback, form));
            }
        });
        return toolbarPanel;
    }

    abstract protected CrudRepository<T, ID> getRepository();


    public void onSave(Optional<AjaxRequestTarget> target) {
        getRepository().save(EntityEditPage.this.getModelObject());
        success(new StringResourceModel("entitySavedMessage.${new}", this, getModel()).getObject());
        target.ifPresent(t -> t.add(feedback, form));
    }

    public void onCancel(Optional<AjaxRequestTarget> target) {
        Optional.of(backNavigateAction).ifPresent(backNavigateAction -> backNavigateAction.navigate(RequestCycle.get(), getModel()));
    }

    public void onClone(Optional<AjaxRequestTarget> target) throws InvocationTargetException, IllegalAccessException {
        T clone = createNewEntity();
        org.springframework.beans.BeanUtils.copyProperties(clone, getModelObject(), "id", "createdDate", "lastModifiedBy", "createdBy", "lastModifiedDate");
        setModelObject(createNewEntity());
    }

    public void onDelete(Optional<AjaxRequestTarget> target) {

    }

    public void onCreateNew(Optional<AjaxRequestTarget> target) {
        setModelObject(createNewEntity());
        target.ifPresent(t -> t.add(form, feedback));
    }

    @Override
    public void onDelete(Optional<AjaxRequestTarget> target, IModel<Collection<T>> entityModel) {
        super.onDelete(target, entityModel);
        onCancel(target);
    }
}
