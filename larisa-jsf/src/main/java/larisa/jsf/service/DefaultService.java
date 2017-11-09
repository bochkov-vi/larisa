package larisa.jsf.service;

import jsf.util3.service.JsfEntityService;
import larisa.entity.DefaultEntity;

import java.io.Serializable;
import java.util.List;

public interface DefaultService<T extends DefaultEntity<ID>, ID extends Serializable> extends JsfEntityService<T, ID>,Serializable {

    T findByName(String name);

    List<T> findByNameStartingWith(String name);
}
