package com.bochkov.model;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.LoadableDetachableModel;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public abstract class PageModel<T> extends LoadableDetachableModel<List<T>>  {

    int page = 0;

    int size = 50;

    @Override
    protected List<T> load() {
        Injector.get().inject(this);
        return getRepository().findAll(getPageable()).getContent();
    }

    public Pageable getPageable() {
        return new PageRequest(page, size);
    }

    abstract PagingAndSortingRepository<T, ?> getRepository();
}
