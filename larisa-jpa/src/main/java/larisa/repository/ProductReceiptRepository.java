package larisa.repository;

import larisa.DefaultEntityRepository;
import larisa.entity.ProductReceipt;
import org.springframework.stereotype.Repository;

/**
 * Created by home on 23.02.17.
 */
@Repository
public interface ProductReceiptRepository extends DefaultEntityRepository<ProductReceipt, Integer> {

}
