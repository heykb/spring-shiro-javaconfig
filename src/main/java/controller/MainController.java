package controller;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
public class MainController {


    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public String hello(@RequestParam("username") String username,@RequestParam("password") String password,
                        @RequestParam(value = "rememberMe",required = false) Boolean rememberMe){
        Subject subject = SecurityUtils.getSubject();
        System.out.println(username+": "+password);
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        token.setRememberMe(rememberMe !=null? rememberMe:false);
        try{
            subject.login(token);
            subject.checkRole("user");
            subject.checkPermission("user:update");
        }catch (Exception e){
            return e.getMessage();
        }

        return "success";
    }
    @RequestMapping(value = "/checkRoles",method = RequestMethod.GET)
    @ResponseBody
    public String checkRoles(){
        return "checkRoles success";
    }
}
