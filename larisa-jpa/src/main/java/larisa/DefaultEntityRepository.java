package larisa;

import larisa.entity.DefaultEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.io.Serializable;

/**
 * Created by home on 25.02.17.
 */

public interface DefaultEntityRepository<T extends DefaultEntity, ID extends Serializable> extends JpaRepository<T,ID> ,JpaSpecificationExecutor<T> {
}
