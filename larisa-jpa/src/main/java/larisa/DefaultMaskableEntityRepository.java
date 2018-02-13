package larisa;

import larisa.entity.DefaultEntity;
import larisa.entity.INamable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.io.Serializable;
import java.util.List;

/**
 * Created by home on 25.02.17.
 */

public interface DefaultMaskableEntityRepository<T extends DefaultEntity & INamable, ID extends Serializable> extends DefaultEntityRepository<T, ID> {
    @Query("SELECT o FROM #{#entityName} o WHERE o.name LIKE :like ORDER BY LOCATE(:orderPattern,o.name)")
    List<T> findByMask(@Param("like") String like, @Param("orderPattern") String orderPattern);

    @Query(value = "SELECT o FROM #{#entityName} o WHERE o.name LIKE :like ORDER BY LOCATE(:orderPattern,o.name)"
//            , countQuery = "SELECT COUNT(o) FROM #{#entityName} o WHERE o.name LIKE :like"
    )
    Page<T> findByMask(@Param("like") String like, @Param("orderPattern") String orderPattern, Pageable pageRequest);
}
