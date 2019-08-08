package com.wool.community.interceptor;

import com.wool.community.mapper.UserMapper;
import com.wool.community.model.User;
import com.wool.community.model.UserExample;
import com.wool.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author wool
 * 自定义拦截类
 */
@Component
public class SessionInterceptor implements HandlerInterceptor {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private NotificationService notificationService;

    /**
     * 处理请求前
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        if(cookies!=null&&cookies.length!=0){
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("token")){
                    String token = cookie.getValue();
                    UserExample userExample = new UserExample();
                    userExample.createCriteria().andTokenEqualTo(token);
                    List<User> users = userMapper.selectByExample(userExample);
                    if(users!=null&&users.size()!=0){
                        request.getSession().setAttribute("user",users.get(0));
                        Long unReadCount = notificationService.unReadCount(users.get(0).getId());
                        request.getSession().setAttribute("unReadCount",unReadCount);
                    }
                    break;
                }
            }
        }

        return true;
    }


}
