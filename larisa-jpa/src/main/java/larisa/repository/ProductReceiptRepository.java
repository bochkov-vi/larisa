package larisa.repository;

import larisa.DefaultEntityRepository;
import larisa.entity.ProductReceipt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by home on 23.02.17.
 */
@Repository
public interface ProductReceiptRepository extends DefaultEntityRepository<ProductReceipt, Integer> {

    @Query(value = "SELECT DISTINCT o.receiptType FROM ProductReceipt o WHERE o.receiptType LIKE :mask ORDER BY LOCATE(:term,o.receiptType)",
    countQuery = "SELECT COUNT(DISTINCT o.receiptType) FROM ProductReceipt o WHERE o.receiptType LIKE :mask")
    Page<String> findReceiptTypeByMask(@Param("mask") String mask, @Param("term") String term, Pageable pageable);

    @Query(value = "SELECT DISTINCT o.seller FROM ProductReceipt o WHERE o.seller LIKE :mask ORDER BY LOCATE(:term,o.seller)",
            countQuery = "SELECT COUNT(DISTINCT o.seller) FROM ProductReceipt o WHERE o.seller LIKE :mask")
    Page<String> findSellerByMask(@Param("mask") String mask, @Param("term") String term, Pageable pageable);

}
