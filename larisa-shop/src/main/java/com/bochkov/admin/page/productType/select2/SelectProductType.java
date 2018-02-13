package com.bochkov.admin.page.productType.select2;

import com.bochkov.model.MaskableChoiceProvider;
import larisa.DefaultMaskableEntityRepository;
import larisa.entity.ProductType;
import larisa.repository.ProductTypeRepository;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.wicketstuff.select2.ChoiceProvider;
import org.wicketstuff.select2.Select2Choice;

import javax.inject.Inject;

public class SelectProductType extends Select2Choice<ProductType> {

    int pageSize = 10;

    @Inject
    transient ProductTypeRepository repository;

    public SelectProductType(String id) {
        super(id);
        setProvider(choiceProvider());
    }

    public SelectProductType(String id, IModel<ProductType> model) {
        super(id, model);
        setProvider(choiceProvider());
    }

    ChoiceProvider<ProductType> choiceProvider() {
        return new MaskableChoiceProvider<ProductType>() {
            @Override
            public DefaultMaskableEntityRepository<ProductType, Integer> getRepository() {
                return repository;
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

    public Select2Choice<ProductType> setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }
}
