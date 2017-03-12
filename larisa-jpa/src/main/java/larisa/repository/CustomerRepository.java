package larisa.repository;

import larisa.EntityRepository;
import larisa.entity.Customer;
import org.springframework.stereotype.Repository;

/**
 * Created by home on 23.02.17.
 */
@Repository
public interface CustomerRepository extends EntityRepository<Customer,Integer> {
}
