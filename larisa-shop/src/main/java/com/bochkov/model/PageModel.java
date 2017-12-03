package com.bochkov.model;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.LoadableDetachableModel;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public abstract class PageModel<T> extends LoadableDetachableModel<List<T>> {

    private int page = 0;

    private int size = 50;

    @Override
    protected List<T> load() {
        Injector.get().inject(this);
        return getRepository().findAll(createPageableRequest()).getContent();
    }

    protected Pageable createPageableRequest() {
        return new PageRequest(page, size);
    }

    protected abstract PagingAndSortingRepository<T, ?> getRepository();

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
