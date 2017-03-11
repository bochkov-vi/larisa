package larisa.repository;

import larisa.EntityRepository;
import larisa.entity.ProductType;
import larisa.entity.Maker;
import org.entity3.repository.UniqueNamedRepository;

/**
 * Created by home on 23.02.17.
 */

public interface ProductTypeRepository extends EntityRepository<ProductType, Integer>, UniqueNamedRepository<ProductType> {
    long countBySeller(Maker maker);
}
