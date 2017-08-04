package larisa.jsf.service;

import jsf.util3.service.JsfEntityService;
import larisa.entity.AbstractEntity;

import java.io.Serializable;
import java.util.List;

public interface DefaultService<T extends AbstractEntity<ID>, ID extends Serializable> extends JsfEntityService<T, ID> {
    T clone(T original);
    T findByName(String name);

    List<T> findByNameStartingWith(String name);
}
