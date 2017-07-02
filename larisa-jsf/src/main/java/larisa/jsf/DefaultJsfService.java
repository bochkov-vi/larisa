package larisa.jsf;

import jsf.util3.JsfEditService;
import larisa.EntityRepository;
import larisa.entity.AbstractEntity;
import org.entity3.repository.CustomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import java.io.Serializable;

/**
 * Created by home on 24.02.17.
 */
@Configurable
public class DefaultJsfService<T extends AbstractEntity<ID>, ID extends Serializable> extends JsfEditService<T, ID> implements Serializable {
    @Autowired
    transient EntityRepository<T, ID> repository;
    private boolean canEditId = false;

    @Override
    protected CustomRepository<T, ID> getRepository() {
        return repository;
    }

    public boolean isCanEditId() {
        return canEditId;
    }

    public void setCanEditId(boolean canEditId) {
        this.canEditId = canEditId;
    }
}
