package larisa.jsf.service.impl;

import jsf.util3.service.impl.JsfEntityServiceImpl;
import larisa.DefaultEntityRepository;
import larisa.entity.DefaultEntity;
import larisa.jsf.service.DefaultService;
import org.entity3.repository.CustomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * Created by home on 25.02.17.
 */
@NoRepositoryBean
@Configurable
public abstract class DefaultServiceImpl<T extends DefaultEntity<ID>, ID extends Serializable> extends JsfEntityServiceImpl<T, ID> implements DefaultService<T, ID> {
    @Autowired
    DefaultEntityRepository<T, ID> repository;

    public DefaultServiceImpl() {
    }

    public DefaultServiceImpl(String... maskedProperty) {
        super(maskedProperty);
    }

    public DefaultServiceImpl(String idParameterName, Iterable<String> maskedProperty) {
        super(idParameterName, maskedProperty);
    }

    @Override
    public CustomRepository<T, ID> getRepository() {
        return repository;
    }




}
