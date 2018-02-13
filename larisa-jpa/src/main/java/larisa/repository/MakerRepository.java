package larisa.repository;

import larisa.DefaultMaskableEntityRepository;
import larisa.entity.Maker;
import org.springframework.stereotype.Repository;

/**
 * Created by home on 23.02.17.
 */
@Repository
public interface MakerRepository extends DefaultMaskableEntityRepository<Maker, Integer> {
}
