package com.bochkov.admin.page.productType.select2;

import larisa.entity.ProductType;
import larisa.repository.ProductTypeRepository;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.wicketstuff.select2.ChoiceProvider;
import org.wicketstuff.select2.Response;
import org.wicketstuff.select2.Select2MultiChoice;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProductTypeMultiSelect extends Select2MultiChoice<ProductType> {

    int pageSize = 10;

    @Inject
    transient ProductTypeRepository repository;

    public ProductTypeMultiSelect(String id) {
        super(id);
        setProvider(choiceProvider());
    }

    public ProductTypeMultiSelect(String id, IModel<Collection<ProductType>> model) {
        super(id, model);
        setProvider(choiceProvider());
    }

    ChoiceProvider<ProductType> choiceProvider() {
        return new ChoiceProvider<ProductType>() {
            @Override
            public String getDisplayValue(ProductType object) {
                return Optional.ofNullable(object).map(ProductType::toString).orElse("");
            }

            @Override
            public String getIdValue(ProductType object) {
                return Optional.ofNullable(object).map(ProductType::getId).map(Object::toString).orElse("");
            }

            @Override
            public void query(String term, int page, Response<ProductType> response) {
                org.springframework.data.domain.Pageable pageRequest = PageRequest.of(page, pageSize);
                Page pageResponse = getRepository().findByMask("%" + term + "%", term, pageRequest);
                response.setResults(pageResponse.getContent());
                response.setHasMore(pageResponse.hasNext());
            }

            @Override
            public Collection<ProductType> toChoices(Collection<String> ids) {
                return getRepository().findAllById(ids.stream().map(Integer::new).collect(Collectors.toList()));
            }
        };
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        getSettings().setCloseOnSelect(true).setPlaceholder(new ResourceModel("placeholder").wrapOnAssignment(this).getObject()).setAllowClear(true);

    }

    public ProductTypeRepository getRepository() {
        if (repository == null) {
            Injector.get().inject(this);
        }
        return repository;
    }

    public int getPageSize() {
        return pageSize;
    }

    public ProductTypeMultiSelect setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }
}
