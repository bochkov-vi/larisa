package larisa.repository;

import larisa.EntityRepository;
import larisa.entity.Outgo;
import org.springframework.stereotype.Repository;

/**
 * Created by home on 23.02.17.
 */
@Repository
public interface OutgoRepository extends EntityRepository<Outgo, Integer> {
}
