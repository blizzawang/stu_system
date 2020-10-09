package com.wwj.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wwj.bean.Student;
import com.wwj.bean.User;
import com.wwj.service.StudentService;
import com.wwj.service.UserService;
import com.wwj.util.MyTimer;
import com.wwj.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 登录注册控制器
 */
@Controller
@RequestMapping("/views")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 实现登录功能
     * @param name
     * @param pwd
     * @param map
     * @param request
     * @return
     */
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
                    //在查询数据之前，调用分页插件
                    //将数据表中的所有学生信息查询出来，并放入请求域
//                    List<Student> studentList = studentService.findAll();
//                    map.put("stu_list",studentList);
                    //将用户名放入请求域
                    map.put("username",user.getUsername());
                    return "forward:/views/stus";
                }
            }
        }
    }

    /**
     * 实现注册功能
     * @param user
     * @param map
     * @return
     */
    @RequestMapping("/register")
    public String register(User user,Map<String,Object> map){
        //判断输入数据是否为空
        if(Utils.isEmpty(user.getUsername())){
            System.out.println(user.getUsername());
            map.put("name_flag","用户名不允许为空!");
        }else if(Utils.isEmpty(user.getRealname())){
            System.out.println(user.getRealname());
            map.put("rename_flag","真实姓名不允许为空!");
        }else if(Utils.isEmpty(user.getPassword())){
            System.out.println(user.getUsername());
            map.put("pwd_flag","密码不允许为空!");
        }else{
            //判断该用户在数据表中是否已经存在
            List<User> userList = userService.ifExists(user.getUsername());
            if(!userList.isEmpty()){
                //若集合不为空，则说明该用户名已经被注册，提示用户
                map.put("regist_flag","该用户名已经被注册!");
            }else{
                //若不存在，则将注册信息进行保存
                //改造从表单传递过来的User对象
                user.setStatus("1");    //新创建的用户处于激活状态
                user.setRegistertime(new Date()); //注册时间
                userService.saveUser(user);
            }
        }
        return "regist";
    }
}
