package larisa.jsf.service.impl;

import jsf.util3.service.impl.AbstractJsfEntityService;
import larisa.DefaultEntityRepository;
import larisa.entity.AbstractEntity;
import larisa.jsf.service.DefaultService;
import org.entity3.repository.CustomRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.repository.NoRepositoryBean;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * Created by home on 25.02.17.
 */
@NoRepositoryBean
@Configurable
public abstract class DefaultServiceImpl<T extends AbstractEntity<ID>, ID extends Serializable> extends AbstractJsfEntityService<T, ID> implements DefaultService<T, ID> {
    @Autowired
    DefaultEntityRepository<T, ID> repository;

    public DefaultServiceImpl() {
    }

    public DefaultServiceImpl(Class<T> entityClass) {
        super(entityClass);
    }

    public DefaultServiceImpl(String... maskedProperty) {
        super(maskedProperty);
    }

    public DefaultServiceImpl(Class<T> entityClass, String... maskedProperty) {
        super(entityClass, maskedProperty);
    }

    public DefaultServiceImpl(Class<T> entityClass, List<String> maskedPopertyList) {
        super(entityClass, maskedPopertyList);
    }

    @Override
    public CustomRepository<T, ID> getRepository() {
        return repository;
    }

    @Override
    public T copy(@NotNull T original) {
        T copy = super.createNewInstance();
        BeanUtils.copyProperties(original, copy, "id", "createdDate");
        return copy;
    }
}
