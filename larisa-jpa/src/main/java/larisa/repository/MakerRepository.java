package larisa.repository;

import larisa.DefaultEntityRepository;
import larisa.entity.Maker;
import org.entity3.repository.UniqueNamedRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by home on 23.02.17.
 */
@Repository
public interface MakerRepository extends DefaultEntityRepository<Maker, Integer>, UniqueNamedRepository<Maker> {
}
