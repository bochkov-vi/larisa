package com.bochkov.model;

import com.google.common.collect.Lists;
import org.apache.wicket.model.LoadableDetachableModel;
import org.springframework.data.domain.Persistable;
import org.springframework.data.repository.CrudRepository;

import java.io.Serializable;
import java.util.List;

public abstract class ListEntityModel<T extends Persistable<ID>, ID extends Serializable> extends LoadableDetachableModel<List<T>> {

    List<ID> ids = null;

    @Override
    protected List<T> load() {
        List<T> result = null;
        if (ids != null && !ids.isEmpty()) {
            result = Lists.newArrayList(getRepostory().findAll(ids));
        }
        return result;
    }

    @Override
    public void setObject(List<T> object) {
        super.setObject(object);
        ids = Lists.newArrayList(Lists.transform(object,o->o.getId()));
    }

    public abstract CrudRepository<T, ID> getRepostory();

    public List getIds() {
        return ids;
    }

    public void setIds(List<ID> ids) {
        this.ids = ids;
    }
}
