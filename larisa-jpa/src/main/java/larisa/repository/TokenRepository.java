package larisa.repository;

import larisa.EntityRepository;
import larisa.entity.Token;
import org.springframework.stereotype.Repository;

/**
 * Created by home on 23.02.17.
 */
@Repository
public interface TokenRepository extends EntityRepository<Token, String> {
}
