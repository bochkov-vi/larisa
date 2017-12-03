package com.bochkov.model;

import com.sun.xml.internal.bind.v2.model.core.ID;
import org.apache.wicket.extensions.markup.html.repeater.util.SingleSortState;
import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Persistable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Iterator;

public abstract class EntityDataProvider<T extends Persistable> extends SortableDataProvider<T, String> {

    transient Long size = null;

    @Override
    public Iterator<? extends T> iterator(long first, long count) {
        SingleSortState state = (SingleSortState) this.getSortState();
        Pageable pageRequest = new PageRequest((int) (first / count), (int) count, getSort(state));
        return getRepository().findAll(pageRequest).getContent().iterator();
    }

    @Override
    public long size() {
        if (size == null) {
            size = getRepository().count();
        }
        return size;
    }

    protected Sort getSort(SingleSortState<String> sortState) {
        if (sortState != null && sortState.getSort()!=null) {
            return new Sort(sortState.getSort().isAscending() ? Sort.Direction.ASC : Sort.Direction.DESC,sortState.getSort().getProperty());
        }
        return null;
    }

    protected Sort.Order convert(SortParam<String> sortParam) {
        return new Sort.Order(sortParam.isAscending() ? Sort.Direction.ASC : Sort.Direction.DESC, sortParam.getProperty());
    }

    public abstract PagingAndSortingRepository<T, ID> getRepository();
}
