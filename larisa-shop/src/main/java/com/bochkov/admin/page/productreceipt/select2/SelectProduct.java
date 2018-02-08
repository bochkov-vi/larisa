package com.bochkov.admin.page.productreceipt.select2;

import com.google.common.collect.Lists;
import larisa.entity.Product;
import larisa.repository.ProductRepository;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
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

public class SelectProduct extends Select2Choice<Product> {

    int pageSize =10;

    @Inject
    transient ProductRepository repository;

    public SelectProduct(String id) {
        super(id);
        setProvider(choiceProvider());
    }

    public SelectProduct(String id, IModel<Product> model) {
        super(id, model);
        setProvider(choiceProvider());
    }

    ChoiceProvider<Product> choiceProvider(){
        return new ChoiceProvider<Product>() {
            @Override
            public String getDisplayValue(Product object) {
                return Optional.ofNullable(object).map(Product::toString).orElse("");
            }

            @Override
            public String getIdValue(Product object) {
                return Optional.ofNullable(object).map(Product::getId).map(Object::toString).orElse("");
            }

            @Override
            public void query(String term, int page, Response<Product> response) {
                org.springframework.data.domain.Pageable pageRequest = new PageRequest(page,pageSize);
                Page pageResponse  =getRepository().findAll(EntityServiceUtils.maskSpecification(Optional.ofNullable(term).orElse(""), "name", Lists.newArrayList()),pageRequest);
                response.setResults(pageResponse.getContent());
                response.setHasMore(pageResponse.hasNext());
            }

            @Override
            public Collection<Product> toChoices(Collection<String> ids) {
                return getRepository().findAll(ids.stream().map(Integer::new).collect(Collectors.toList()));
            }
        };
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        getSettings().setCloseOnSelect(true).setPlaceholder(new ResourceModel("placeholder").wrapOnAssignment(this).getObject()).setAllowClear(true);

    }

    public ProductRepository getRepository() {
        if (repository == null) {
            Injector.get().inject(this);
        }
        return repository;
    }

    public int getPageSize() {
        return pageSize;
    }

    public Select2Choice<Product> setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }
}
