package larisa.repository;

import larisa.DefaultEntityRepository;
import larisa.entity.Token;
import org.springframework.stereotype.Repository;

/**
 * Created by home on 23.02.17.
 */
@Repository
public interface TokenRepository extends DefaultEntityRepository<Token, String> {
}
