package by.bntu.diplomainformationproject.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static by.bntu.diplomainformationproject.util.Constants.AUTHORIZATION;
import static by.bntu.diplomainformationproject.util.Constants.TOKEN_PREFIX;
import static org.springframework.util.StringUtils.hasText;

@Slf4j
@Component
public class JwtFilter extends GenericFilterBean {
    @Autowired
    private JwtProvider jwtProvider;

    @Qualifier("studentServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String token = getTokenFromRequest((HttpServletRequest) servletRequest);
        if (token != null && jwtProvider.validateToken(token)) {
            String userEmail = jwtProvider.getLoginFromToken(token);
            User user = (User) userDetailsService.loadUserByUsername(userEmail);
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
            log.info("User = {} logged in", user);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearer = request.getHeader(AUTHORIZATION);
        if (hasText(bearer) && bearer.startsWith(TOKEN_PREFIX)) {
            bearer = bearer.replace(TOKEN_PREFIX, "");
            return bearer;
        }

        return null;
    }
}
