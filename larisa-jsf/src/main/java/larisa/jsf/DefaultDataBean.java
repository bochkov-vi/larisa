package larisa.jsf;

import jsf.util3.DataManagedBean;
import larisa.EntityRepository;
import larisa.entity.AbstractEntity;
import org.entity3.repository.CustomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by home on 24.02.17.
 */

public class DefaultDataBean<T extends AbstractEntity<ID>, ID extends Serializable> extends DataManagedBean<T> {

    boolean filterHide = true;

    @Autowired
    EntityRepository<T, ID> repository;

    @Override
    public CustomRepository<T, ID> getRepository() {
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
