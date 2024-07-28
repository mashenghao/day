package cn.mh.spring.webasync;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Interceptor是spring mvc的DispatcherServlet做的功能，是执行controller的mapper方法执行的，也是先去执行，
 * 如果有一个before执行失败，之后也不会去执行了
 *
 * @author mash
 * @date 2024/3/13 23:32
 */
@Component
public class LoginInter implements HandlerInterceptor, WebMvcConfigurer {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println(Thread.currentThread().getName() + "HandlerInterceptor preHandle ");
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println(Thread.currentThread().getName() + "HandlerInterceptor postHandle ");
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println(Thread.currentThread().getName() + "HandlerInterceptor afterCompletion ");
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInter()).addPathPatterns("/webAsync/**");
    }

}
