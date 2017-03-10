package larisa.repository;

import larisa.EntityRepository;
import larisa.entity.ProductType;
import larisa.entity.Seller;

/**
 * Created by home on 23.02.17.
 */

public interface ProductTypeRepository extends EntityRepository<ProductType,Integer> {
    long countBySeller(Seller seller);
}
