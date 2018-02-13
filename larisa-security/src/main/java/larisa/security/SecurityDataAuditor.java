package larisa.security;

import larisa.entity.Account;
import larisa.entity.NoBodyAuditorAware;
import larisa.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SecurityDataAuditor extends NoBodyAuditorAware {

    @Autowired
    AccountRepository accountRepository;

    @Override
    public Optional<Account> getCurrentAuditor() {
        Optional<Account> account = null;
        /*if (account == null) {
            account = super.getCurrentAuditor();
        }*/
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            account = accountRepository.findById(userDetails.getUsername());
        }
        return account;
    }
}
