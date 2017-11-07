package larisa.security.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.List;

public class SavedAnonymousFilter extends AnonymousAuthenticationFilter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        super.doFilter(req, res, chain);

    }

    public SavedAnonymousFilter(String key) {
        super(key);
    }

    public SavedAnonymousFilter(String key, Object principal, List<GrantedAuthority> authorities) {
        super(key, principal, authorities);
    }
}
