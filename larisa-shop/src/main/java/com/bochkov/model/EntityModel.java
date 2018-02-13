package com.bochkov.model;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.springframework.data.domain.Persistable;
import org.springframework.data.repository.CrudRepository;

import java.io.Serializable;
import java.util.Optional;

public abstract class EntityModel<T extends Persistable<ID>, ID extends Serializable> extends LoadableDetachableModel<T> implements IModel<T>{
    ID id;

    public EntityModel(ID id) {
        this.id = id;
    }

    public EntityModel(T object) {
        super(object);
        if (object != null) {
            this.id = object.getId();
        }
    }

    public EntityModel() {
    }

    @Override
    protected T load() {
        return Optional.ofNullable(id).map(pk->getRepostory().findById(pk).orElse(null)).orElse(null);
    }

    @Override
    public void setObject(T object) {
        super.setObject(object);
        id = object != null ? object.getId() : null;
    }

    public abstract CrudRepository<T, ID> getRepostory();
}
