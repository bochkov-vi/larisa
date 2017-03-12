package larisa.repository;

import larisa.EntityRepository;
import larisa.entity.Product;
import org.springframework.stereotype.Repository;

/**
 * Created by home on 23.02.17.
 */
@Repository
public interface ProductRepository extends EntityRepository<Product,Integer> {
}
