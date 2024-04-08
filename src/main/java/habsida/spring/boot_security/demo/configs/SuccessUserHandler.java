package habsida.spring.boot_security.demo.configs;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;

@Component
public class SuccessUserHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        if (roles.contains("ROLE_ADMIN")) {
            httpServletRequest.getSession().setAttribute("isAdmin", true);
            httpServletResponse.sendRedirect("/admin");
        } else if (roles.contains("ROLE_USER")){
            httpServletRequest.getSession().setAttribute("isAdmin", false);
            httpServletResponse.sendRedirect("/user");
        } else {
            httpServletResponse.sendRedirect("/login");
        }
    }
}
