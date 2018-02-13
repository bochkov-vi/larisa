package com.bochkov.admin.page.maker.select2;

import com.bochkov.model.MaskableChoiceProvider;
import larisa.DefaultMaskableEntityRepository;
import larisa.entity.Maker;
import larisa.repository.MakerRepository;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.wicketstuff.select2.ChoiceProvider;
import org.wicketstuff.select2.Select2Choice;

import javax.inject.Inject;

public class MakerSelect2Chooser extends Select2Choice<Maker> {

    int pageSize = 10;

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

    ChoiceProvider<Maker> choiceProvider() {
        return new MaskableChoiceProvider<Maker>() {
            @Override
            public DefaultMaskableEntityRepository<Maker, Integer> getRepository() {
                return repository;
            }
        };
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        getSettings().setCloseOnSelect(true).setPlaceholder(new ResourceModel("placeholder").wrapOnAssignment(this).getObject()).setAllowClear(true);

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
