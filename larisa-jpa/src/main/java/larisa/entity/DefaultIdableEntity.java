package larisa.entity;

import org.springframework.data.domain.Persistable;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by home on 10.03.17.
 */
public abstract class DefaultIdableEntity<ID extends Serializable> implements Persistable<ID>, Serializable {

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DefaultIdableEntity)) {
            return false;
        }
        DefaultIdableEntity<?> that = (DefaultIdableEntity<?>) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}