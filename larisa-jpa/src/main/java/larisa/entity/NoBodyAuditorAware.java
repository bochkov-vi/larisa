package larisa.entity;

import larisa.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

/**
 * Created by home on 23.02.17.
 */
public class NoBodyAuditorAware implements AuditorAware<Account> {
    public static String NOBODY = "nobody";

    @Autowired
    AccountRepository repository;

    @Override
    public Optional<Account> getCurrentAuditor() {
        return repository.findById(NOBODY);
    }
}
