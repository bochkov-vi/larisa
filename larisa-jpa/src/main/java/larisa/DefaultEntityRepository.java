package larisa;

import larisa.entity.DefaultEntity;
import org.entity3.repository.CustomRepository;

import java.io.Serializable;

/**
 * Created by home on 25.02.17.
 */

public interface DefaultEntityRepository<T extends DefaultEntity, ID extends Serializable> extends CustomRepository <T,ID>{
}
