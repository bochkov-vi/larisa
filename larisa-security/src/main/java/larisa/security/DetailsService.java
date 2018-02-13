package larisa.security;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import larisa.entity.Account;
import larisa.entity.Role;
import larisa.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Optional;

/**
 * Created by bochkov
 */
@Service
public class DetailsService implements UserDetailsService {
    public static String ADMIN = "admin";

    @Autowired
    AccountRepository repository;

    @PostConstruct
    public void initAdminAccount() {
        Account adminAccount = repository.findById(ADMIN).orElse(new Account(ADMIN).setPassword("1234"));
        adminAccount.setRoles(Lists.newArrayList(ImmutableSet.<Role>builder().add(Role.ROLE_ADMIN).addAll(MoreObjects.firstNonNull(adminAccount.getRoles(), ImmutableSet.of())).build()));
        repository.save(adminAccount);
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<Account> account = repository.findById(s);
        return account.map(a -> new User(a.getId(), a.getPassword(), Collections2.transform(a.getRoles(), role -> new SimpleGrantedAuthority(role.toString())))).orElseThrow(() -> new UsernameNotFoundException(s));
    }
}
