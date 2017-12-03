package com.bochkov.model;

import org.apache.wicket.model.LoadableDetachableModel;
import org.springframework.data.domain.Persistable;
import org.springframework.data.repository.CrudRepository;

import java.io.Serializable;

public abstract class EntityModel<T extends Persistable<ID>, ID extends Serializable> extends LoadableDetachableModel<T> {
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
        if (id != null) {
            return getRepostory().findOne(id);
        } else {
            return null;
        }
    }

    @Override
    public void setObject(T object) {
        super.setObject(object);
        id = object != null ? object.getId() : null;
    }

    public abstract CrudRepository<T, ID> getRepostory();
}
