package larisa.repository;

import larisa.EntityRepository;
import larisa.entity.Account;
import org.springframework.stereotype.Repository;

/**
 * Created by home on 23.02.17.
 */
@Repository
public interface AccountRepository extends EntityRepository<Account, String> {
}
