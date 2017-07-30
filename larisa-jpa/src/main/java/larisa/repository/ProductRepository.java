package larisa.repository;

import larisa.DefaultEntityRepository;
import larisa.entity.Product;
import larisa.entity.ProductType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by home on 23.02.17.
 */
@Repository
public interface ProductRepository extends DefaultEntityRepository<Product, Integer> {
    @Query("SELECT SUM(o.volume) FROM Product o WHERE o.productType=:productType")
    Long comein(@Param("productType") ProductType productType);
}
