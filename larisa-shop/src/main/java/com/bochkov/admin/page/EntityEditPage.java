package com.bochkov.admin.page;

import com.bochkov.model.EntityModel;
import de.agilecoders.wicket.core.markup.html.bootstrap.common.NotificationPanel;
import org.apache.wicket.Page;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.entity3.service.EntityService;
import org.springframework.data.domain.Persistable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.util.ClassTypeInformation;
import org.springframework.data.util.TypeInformation;

import java.io.Serializable;
import java.util.List;

public abstract class EntityEditPage<T extends Persistable<ID>, ID extends Serializable> extends BasePage<T> {

    protected Page backPage;

    final protected NotificationPanel feedback = new NotificationPanel("feedback");

    /**
     * Construct.
     *
     * @param parameters current backPage parameters
     */
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

    public EntityEditPage(Page backPage) {
        this.backPage = backPage;
    }

    public EntityEditPage(IModel<T> model, Page backPage) {
        super(model);
        this.backPage = backPage;
    }

    public EntityEditPage(PageParameters parameters, Page backPage) {
        super(parameters);
        this.backPage = backPage;
    }

    public EntityEditPage() {
    }

    public EntityEditPage(IModel<T> model) {
        super(model);
    }

    public static <P> Class<P> argument(EntityEditPage object, int i) {
        TypeInformation<?> information = ClassTypeInformation.from(object.getClass());
        List<TypeInformation<?>> arguments = information.getSuperTypeInformation(EntityService.class).getTypeArguments();

        if (arguments.size() < i || arguments.get(i) == null) {
            throw new IllegalArgumentException(String.format("Could not resolve id type of %s!", object.getClass()));
        }
        return (Class<P>) arguments.get(i).getType();
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        feedback.setOutputMarkupId(true);
        add(feedback);
    }


    protected T extract(PageParameters pageParameters) {
        Class<T> entityClass = argument(this, 0);
        T entity = pageParameters.get("id").toOptional(entityClass);
        if (entity == null) {
            Class<ID> idClass = argument(this, 1);
            ID id = pageParameters.get("id").toOptional(idClass);
            if (id != null) {
                entity = getRepository().findOne(id);
            }
        }
        return entity;
    }

    abstract protected CrudRepository<T, ID> getRepository();

}
