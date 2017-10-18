package larisa.jsf;

import jsf.util3.DataManagedBean;
import larisa.DefaultEntityRepository;
import larisa.entity.DefaultEntity;
import larisa.jsf.data.FilterBean;
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


    protected FilterBean filter;
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

    public FilterBean getFilter() {
        return filter;
    }

    public void setFilter(FilterBean filter) {
        this.filter = filter;
    }
}
