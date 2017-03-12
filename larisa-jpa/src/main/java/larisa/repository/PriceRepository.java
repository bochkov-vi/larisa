package larisa.repository;

import larisa.EntityRepository;
import larisa.entity.Price;
import org.springframework.stereotype.Repository;

/**
 * Created by home on 23.02.17.
 */
@Repository
public interface PriceRepository extends EntityRepository<Price, Integer> {
}