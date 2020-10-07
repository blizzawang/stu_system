package com.wwj.controller;

import com.wwj.bean.User;
import com.wwj.service.UserService;
import com.wwj.util.MyTimer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/views")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public String login(@RequestParam("name") String name, @RequestParam("pwd") String pwd, Map<String,Object> map, HttpServletRequest request){

        HttpSession session = request.getSession();

        //查询数据库中是否有当前用户名
        List<User> userList = userService.ifExists(name);
        if(userList.isEmpty()){
            //用户不存在
            //将标识放入请求域
            map.put("name_flag","当前用户不存在!");
            return "login";
        }else{
            //用户若存在，则查询出当前用户的信息

            //TODO 记录一个坑，这里若是让selectByExample使用redis缓存的话，会使每次查询出来的user都是从缓存中获取的
            // 从而使每次查询到的status都为1

            User user = userList.get(0);

//            System.out.println("status---" + user.getStatus());

            //判断当前用户是否被锁定
            if(user.getStatus().equals("0")){
                //用户已被锁定，禁止登录系统
                map.put("lock_flag","0");
                System.out.println("锁定该用户");
                //激活计时器
                if(!MyTimer.time_flag){
                    MyTimer.TimingTask(user);
                }
                return "login";
            }else{
                //判断用户输入的密码是否正确
                if(!user.getPassword().equals(pwd)){
                    //若不正确，则提示用户
                    map.put("pwd_flag","密码输入错误!");
                    Integer login_count = (Integer) session.getAttribute("login_count");
                    if(login_count == null){
                        //此时说明是第一次登录
                        session.setAttribute("login_count",1);

                    }else{
                        //若不是第一次登录，则从session中获取登录次数并再增加一次
                        login_count += 1;
                        if(login_count % 3 == 0){
                            //用户输入错误密码达3次，锁定当前用户
                            userService.lockUser(user);
                        }
                        session.setAttribute("login_count",login_count);
                    }
//                    System.out.println(login_count);
                    return "login";
                }else{
                    //用户名存在，且密码正确，允许登录
                    return "emplist";
                }
            }
        }
    }
}
