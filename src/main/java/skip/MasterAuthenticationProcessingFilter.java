package skip;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class MasterAuthenticationProcessingFilter extends UsernamePasswordAuthenticationFilter {
    
    public MasterAuthenticationProcessingFilter() {
        this.setFilterProcessesUrl("/login/master");
        
        this.setAuthenticationSuccessHandler(new DefaultAuthenticationSuccessHandler());
        this.setAuthenticationFailureHandler(new DefaultAuthenticationFailureHandler());
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        String username = obtainUsername(request);
        
        // Check whether the user's authority is USER.
        Authority authority = new AuthorityManager().getAuthorityByUsername(username);
        if(authority == null || !(AuthorityManager.MASTER).equals(authority.getAuthority())) {
            throw new BadCredentialsException("Bad credentials: " + request.getMethod());
        }
        
        String password = obtainPassword(request);

        if (username == null) {
            username = "";
        }

        if (password == null) {
            password = "";
        }

        username = username.trim();

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);

        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }
    
}
