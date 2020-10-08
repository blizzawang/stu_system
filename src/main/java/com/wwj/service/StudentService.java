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
}
