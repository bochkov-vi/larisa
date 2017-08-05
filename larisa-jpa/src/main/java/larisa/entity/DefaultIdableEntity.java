package larisa.entity;

import com.google.common.base.Objects;
import org.entity3.IIdable;

import java.io.Serializable;

/**
 * Created by home on 10.03.17.
 */
public abstract class DefaultIdableEntity<ID extends Serializable> implements IIdable<ID> ,Serializable{

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DefaultIdableEntity)) {
            return false;
        }
        DefaultIdableEntity<?> that = (DefaultIdableEntity<?>) o;
        return Objects.equal(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}