package larisa.jsf.service;

import jsf.util3.service.JsfEntityService;
import larisa.entity.AbstractEntity;

import java.io.Serializable;

public interface DefaultService<T extends AbstractEntity<ID>, ID extends Serializable> extends JsfEntityService<T, ID> {
    T copy(T original);
}
