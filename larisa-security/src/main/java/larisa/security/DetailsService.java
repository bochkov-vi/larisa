package larisa.security;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import larisa.entity.Account;
import larisa.entity.Role;
import larisa.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;

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
        Account adminAccount = repository.findOne(ADMIN);
        if (adminAccount == null) {
            adminAccount = new Account(ADMIN);
            adminAccount.setPassword("1234");
        }
        adminAccount.setRoles(Lists.newArrayList(ImmutableSet.<Role>builder().add(Role.ROLE_ADMIN).addAll(MoreObjects.firstNonNull(adminAccount.getRoles(), ImmutableSet.of())).build()));
        repository.save(adminAccount);
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Account account = repository.findOne(s);
        UserDetails userDetails = null;
        if (account != null) {
            Collection<? extends GrantedAuthority> authorities;
            authorities = Collections2.transform(account.getRoles(), r -> new SimpleGrantedAuthority(r.toString()));
            userDetails = new User(account.getId(), account.getPassword(), authorities);
        }
        return userDetails;
    }
}
