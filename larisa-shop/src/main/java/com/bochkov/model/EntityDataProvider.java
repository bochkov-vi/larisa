package com.bochkov.model;

import larisa.entity.DefaultEntity;
import org.apache.wicket.extensions.markup.html.repeater.util.SingleSortState;
import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Iterator;
import java.util.Optional;

public abstract class EntityDataProvider<T extends DefaultEntity> extends SortableDataProvider<T, String> {

    transient Long size = null;

    public static <T extends DefaultEntity> EntityDataProvider<T> create(JpaSpecificationExecutor<T> repository) {
        return new EntityDataProvider<T>() {
            @Override
            public JpaSpecificationExecutor<T> getRepository() {
                return repository;
            }
        };
    }

    public static <T extends DefaultEntity> EntityDataProvider<T> create(JpaSpecificationExecutor<T> repository, Specification<T> specification) {
        return new EntityDataProvider<T>() {
            @Override
            public JpaSpecificationExecutor<T> getRepository() {
                return repository;
            }

            @Override
            public Specification<T> createSpecification() {
                return specification;
            }
        };

    }

    @Override
    public Iterator<? extends T> iterator(long first, long count) {
        SingleSortState state = (SingleSortState) this.getSortState();
        Pageable pageRequest = new PageRequest((int) (first / count), (int) count, getSort(state));
        return getRepository().findAll(createSpecification(), pageRequest).getContent().iterator();
    }

    @Override
    public long size() {
        if (size == null) {
            size = getRepository().count(createSpecification());
        }
        return size;
    }

    protected Sort getSort(SingleSortState<String> sortState) {
        return Optional.of(sortState).map(st -> st.getSort()).map(s -> new Sort(s.isAscending() ? Sort.Direction.ASC : Sort.Direction.DESC, s.getProperty())).orElse(Sort.unsorted());
    }

    protected Sort.Order convert(SortParam<String> sortParam) {
        return new Sort.Order(sortParam.isAscending() ? Sort.Direction.ASC : Sort.Direction.DESC, sortParam.getProperty());
    }

    @Override
    public void detach() {
        size = null;
    }

    public abstract JpaSpecificationExecutor<T> getRepository();

    public Specification<T> createSpecification() {
        return null;
    }

    @Override
    public IModel<T> model(T object) {
        return Model.of(object);
    }
}
