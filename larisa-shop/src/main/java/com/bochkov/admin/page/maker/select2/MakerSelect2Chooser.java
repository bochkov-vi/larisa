package com.bochkov.admin.page.maker.select2;

import com.google.common.collect.Lists;
import larisa.entity.Maker;
import larisa.repository.MakerRepository;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.IModel;
import org.entity3.service.impl.EntityServiceUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.wicketstuff.select2.ChoiceProvider;
import org.wicketstuff.select2.Response;
import org.wicketstuff.select2.Select2Choice;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public class MakerSelect2Chooser extends Select2Choice<Maker> {

    int pageSize =10;

    @Inject
    transient MakerRepository repository;

    public MakerSelect2Chooser(String id) {
        super(id);
        setProvider(choiceProvider());
    }

    public MakerSelect2Chooser(String id, IModel<Maker> model) {
        super(id, model);
        setProvider(choiceProvider());
    }

    ChoiceProvider<Maker> choiceProvider(){
        return new ChoiceProvider<Maker>() {
            @Override
            public String getDisplayValue(Maker object) {
                return Optional.ofNullable(object).map(Maker::toString).orElse("");
            }

            @Override
            public String getIdValue(Maker object) {
                return Optional.ofNullable(object).map(Maker::getId).map(Object::toString).orElse("");
            }

            @Override
            public void query(String term, int page, Response<Maker> response) {
                org.springframework.data.domain.Pageable pageRequest = new PageRequest(page,pageSize);
                Page pageResponse  =getRepository().findAll(EntityServiceUtils.maskSpecification(Optional.ofNullable(term).orElse(""), "name", Lists.newArrayList()),pageRequest);
                response.setResults(pageResponse.getContent());
                response.setHasMore(pageResponse.hasNext());
            }

            @Override
            public Collection<Maker> toChoices(Collection<String> ids) {
                return getRepository().findAll(ids.stream().map(Integer::new).collect(Collectors.toList()));
            }
        };
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        getSettings().setCloseOnSelect(true);

    }

    public MakerRepository getRepository() {
        if (repository == null) {
            Injector.get().inject(this);
        }
        return repository;
    }

    public int getPageSize() {
        return pageSize;
    }

    public Select2Choice<Maker> setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }
}
