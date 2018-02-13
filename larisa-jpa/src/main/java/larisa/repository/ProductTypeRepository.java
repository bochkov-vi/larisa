package larisa.repository;

import larisa.DefaultMaskableEntityRepository;
import larisa.entity.Maker;
import larisa.entity.ProductType;
import org.springframework.stereotype.Repository;

/**
 * Created by home on 23.02.17.
 */
@Repository
public interface ProductTypeRepository extends DefaultMaskableEntityRepository<ProductType, Integer> {
    long countByMaker(Maker maker);
}
