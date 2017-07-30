package larisa.repository;

import larisa.DefaultEntityRepository;
import larisa.entity.Outgo;
import larisa.entity.ProductType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by home on 23.02.17.
 */
@Repository
public interface OutgoRepository extends DefaultEntityRepository<Outgo, Integer> {

    @Query("SELECT SUM(o.volume) FROM Outgo  o WHERE o.productType=:productType")
    Long outgo(@Param("productType") ProductType productType);
}
