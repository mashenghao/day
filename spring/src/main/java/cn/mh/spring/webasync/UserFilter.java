package cn.mh.spring.webasync;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * filter是注册在tomcat容器内的，是在进入servlet.service方法之前和之后执行的，如果之前没有执行完，之后方法就不会再去执行了。
 *
 * @author mash
 * @date 2024/3/13 22:55
 */
@Component
public class UserFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //todo:
        System.out.println(Thread.currentThread().getName() + "  UserFilter before");
        filterChain.doFilter(request, response);
        System.out.println(Thread.currentThread().getName() + "  UserFilter after");

    }


}
