package shiroFilter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class RolesOrFilter extends AuthorizationFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {

        String[] roles  = (String[]) o;
        Subject subject =getSubject(servletRequest,servletResponse);
        if(roles==null || roles.length == 0){
            return true;
        }

        for(String role: roles){
            if(subject.hasRole(role)){
                return true;
            }
        }
        return false;
    }
}
