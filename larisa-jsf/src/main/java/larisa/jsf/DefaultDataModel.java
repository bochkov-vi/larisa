package larisa.jsf;

import jsf.util3.DataManagedBean;
import larisa.DefaultEntityRepository;
import larisa.entity.DefaultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by home on 24.02.17.
 */


@Configurable
public class DefaultDataModel<T extends DefaultEntity<ID>, ID extends Serializable> extends DataManagedBean<T> {

    boolean filterHide = true;

    @Autowired
    transient DefaultEntityRepository<T, ID> repository;

    @Override
    public DefaultEntityRepository<T, ID> getRepository() {
        return repository;
    }

    @Override
    protected Specification<T> getSpecification(Map<String, Object> filters) {
        return null;
    }

    public boolean isEmptyFilter() {
        return true;
    }

    public boolean isFilterHide() {
        if (!isEmptyFilter()) {
            filterHide = false;
        }
        return filterHide;
    }

    public void setFilterHide(boolean filterHide) {
        this.filterHide = filterHide;
    }
}