package larisa.security;

import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * Created by home on 28.03.17.
 */
@Component
@Scope("session")
public class SecuritySupport {
    public String getRemoteUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = String.valueOf(principal);
        if (principal instanceof UserDetails) {
            name = ((UserDetails) principal).getUsername(); //get logged in username
        }
        return name;
    }

    public boolean isAuthorized(){
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails;
    }
}
