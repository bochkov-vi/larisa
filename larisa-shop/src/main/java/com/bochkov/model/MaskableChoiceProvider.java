package com.bochkov.model;

import larisa.DefaultMaskableEntityRepository;
import larisa.entity.DefaultEntity;
import larisa.entity.INamable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Persistable;
import org.wicketstuff.select2.ChoiceProvider;
import org.wicketstuff.select2.Response;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class MaskableChoiceProvider<T extends DefaultEntity<Integer> & INamable> extends ChoiceProvider<T> {


    @Override
    public String getDisplayValue(T object) {
        return Optional.ofNullable(object).map(T::toString).orElse("");
    }


    @Override
    public String getIdValue(T object) {
        return Optional.ofNullable(object).map(Persistable::getId).map(Object::toString).orElse("");
    }

    @Override
    public void query(String term, int page, Response<T> response) {
        org.springframework.data.domain.Pageable pageRequest = new PageRequest(page, getPageSize());
        Page pageResponse = getRepository().findByMask(Optional.ofNullable(term).map(expr->"%" + expr + "%").orElse("%"), term, pageRequest);
        response.setResults(pageResponse.getContent());
        response.setHasMore(pageResponse.hasNext());
    }

    @Override
    public Collection<T> toChoices(Collection<String> ids) {
        return getRepository().findAllById(ids.stream().map(Integer::new).collect(Collectors.toList()));
    }

    public abstract DefaultMaskableEntityRepository<T, Integer> getRepository();

    public int getPageSize() {
        return 10;
    }
}
