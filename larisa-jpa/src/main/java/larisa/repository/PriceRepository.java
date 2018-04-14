package larisa.repository;

import larisa.DefaultEntityRepository;
import larisa.entity.Price;
import larisa.entity.ProductType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by home on 23.02.17.
 */
@Repository
public interface PriceRepository extends DefaultEntityRepository<Price, Integer> {
    @Query("SELECT o FROM Price o WHERE o.productType=:productType AND o.dateTo IS NULL")
    Price findLast(@Param("productType") ProductType productType);

    @Query("SELECT o FROM Price o WHERE o.productType=:productType AND o.dateTo IS NULL")
    Optional<Price> findLastOptional(@Param("productType") ProductType productType);
}
