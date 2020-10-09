package com.wwj.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wwj.bean.Student;
import com.wwj.service.StudentService;
import com.wwj.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/views")
public class StudentController {

    @Autowired
    private StudentService studentService;

    /**
     * 分页显示
     * @param pn 页码
     * @param map
     * @return
     */
    @RequestMapping("/stus")
    public String getStus(@RequestParam(value = "pn",defaultValue = "1") Integer pn, Map<String,Object> map){
        //设置每页仅显示4条数据
        PageHelper.startPage(pn,4);
        List<Student> studentList = studentService.findAll();
        //使用PageInfo包装查询结果
        PageInfo pageInfo = new PageInfo(studentList,5);    //传入连续显示的页数
        map.put("pageInfo",pageInfo);
        return "emplist";
    }

    /**
     * 管理系统删除学生信息功能
     * @param id
     * @return
     */
    @RequestMapping(value = "/stu/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteStu(@PathVariable("id") Integer id){
        studentService.deleteStu(id);
        return "success";
    }

    /**
     * 查询指定ID的学生信息，并回显在更新表单中
     * @param id
     * @param map
     * @return
     */
    @RequestMapping(value = "/updateStu/{id}")
    public String selectStu(@PathVariable("id") Integer id,Map<String,Object> map){
        Student student = studentService.selectStu(id);
        //将学生信息放入请求域
        map.put("student",student);
        return "updateEmp";
    }

    /**
     * 转发PUT请求
     * @return
     */
    @RequestMapping("/forwardUpdate")
    public String forwardUpdate(){
        System.out.println("forwardUpdate");
        return "updateEmp";
    }

    /**
     * 实现更新学生信息功能
     * @param id
     * @param name
     * @param sex
     * @param age
     * @param map
     * @return
     */
    @RequestMapping(value = "/stu/{id}",method = RequestMethod.PUT)
    public String updateStu(
            @PathVariable("id") Integer id,
            @RequestParam(value = "name",required = false) String name,
            @RequestParam(value = "sex",required = false) String sex,
            @RequestParam(value = "age",required = false) String age,
            Map<String,Object> map) {

        System.out.println(name + "---" + sex + "---" + age);

        Student student = studentService.selectStu(id);

        //检查用户是否空输入
        if(Utils.isEmpty(name)){
            map.put("update_name_flag","更新后的姓名不允许为空!");
        }else if(Utils.isEmpty(sex)){
            map.put("update_sex_flag","更新后的性别不允许为空!");
        }else if(Utils.isEmpty(age)){
            map.put("update_age_flag","更新后的年龄不允许为空!");
        }else if(Integer.valueOf(age) > 200 || Integer.valueOf(age) < 0){
            map.put("update_age_able_flag","这显然是一个不合法的年龄!");
        }else{
            //比较当前内容与数据表的内容是否一致
            if(name.equals(student.getName()) && sex.equals(student.getSex()) && age.equals(student.getAge().toString())){
                //如果内容一致，则提示用户
                map.put("update_flag","您还未作修改!");
                map.put("student",student);
                System.out.println("您还未做修改!");
                return "updateEmp";
            }else{
                //将用户的修改更新至数据库
                Student newStudent = new Student();
                newStudent.setId(id);
                newStudent.setName(name);
                newStudent.setAge(Integer.valueOf(age));
                newStudent.setSex(sex);
                System.out.println(newStudent);
                studentService.updateStu(newStudent);
                //因为tomcat版本的问题，若想实现REST风格的url，则必须先将请求转给控制器，再由控制器转发到页面
                return "redirect:/views/stus";
            }
        }
        map.put("student",student);
        return "updateEmp";
    }
}
