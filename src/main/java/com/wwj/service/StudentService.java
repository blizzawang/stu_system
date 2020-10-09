package com.wwj.service;

import com.wwj.bean.Student;
import com.wwj.bean.StudentExample;
import com.wwj.dao.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 学生管理系统服务类
 */
@Service
public class StudentService {

    @Autowired
    private StudentMapper studentMapper;

    /**
     * 查询出所有的学生信息
     * @return
     */
    public List<Student> findAll(){
        return studentMapper.selectByExample(new StudentExample());
    }

    /**
     * 删除指定ID的学生信息
     * @param id
     * @return
     */
    public int deleteStu(Integer id){
        return studentMapper.deleteByPrimaryKey(id);
    }

    /**
     * 根据指定ID查询学生信息
     * @param id
     * @return
     */
    public Student selectStu(Integer id){
        return studentMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据指定ID更新学生信息，包括姓名、性别、年龄
     * @param student
     * @return
     */
    public int updateStu(Student student){
        return studentMapper.updateByPrimaryKey(student);
    }
}
