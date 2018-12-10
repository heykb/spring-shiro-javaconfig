package config;

import cache.RedisCacheManager;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import session.MySessionMannager;
import session.RedisSessionDao;
import shiroFilter.RolesOrFilter;
import zhu.MyRealm;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

@Configuration
@Import(value = RedisConfig.class)
public class SpringConfig {

    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher("md5");
        hashedCredentialsMatcher.setHashIterations(2);
        return hashedCredentialsMatcher;
    }

    @Bean
    public MyRealm myRealm(){
        MyRealm myRealm = new MyRealm();
        myRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return myRealm;
    }

    @Bean
    public SessionDAO sessionDAO(){
        return new RedisSessionDao();
    }

        @Bean
    public MySessionMannager mySessionManager(){
        MySessionMannager sessionManager = new MySessionMannager();
        sessionManager.setSessionDAO(sessionDAO());
        return sessionManager;
    }

    @Bean
    public RedisCacheManager redisCacheManager(){
        return new RedisCacheManager();
    }
    @Bean
    public CookieRememberMeManager cookieRememberMeManager(){
        return new CookieRememberMeManager();
    }
    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager(){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(myRealm());
        defaultWebSecurityManager.setSessionManager(mySessionManager());
        defaultWebSecurityManager.setCacheManager(redisCacheManager());
        defaultWebSecurityManager.setRememberMeManager(cookieRememberMeManager());
        return defaultWebSecurityManager;
    }

    @Bean
    public RolesOrFilter rolesOrFilter(){
        return new RolesOrFilter();
    }
    @Bean
    public ShiroFilterFactoryBean shiroFilter(){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        Map<String,String> definitions = new HashMap<>();
        definitions.put("/login.html","anon");
        definitions.put("/login","anon");
        definitions.put("/checkRoles","rolesOr[admin,user]");
        definitions.put("/logout","logout");
        definitions.put("/*","authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(definitions);
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager());
        shiroFilterFactoryBean.setLoginUrl("/login.html");

        Map<String, Filter> flters = new HashMap<>();
        flters.put("rolesOr",rolesOrFilter());
        shiroFilterFactoryBean.setFilters(flters);
        return shiroFilterFactoryBean;
    }

}
