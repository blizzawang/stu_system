package com.wwj.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.wwj.bean.User;
import com.wwj.dao.UserMapper;
import com.wwj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 定时任务工具类
 */
@Component
public class MyTimer {

    /**
     * 标识计时器是否已经开启
     */
    public static boolean time_flag = false;

    /**
     * 从调用此方法开始，计时30分钟后重新激活用户
     * @param user
     */
    public static void TimingTask(User user){
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {

            int count = 0;

            @Override
            public void run() {
                time_flag = true;
                System.out.println(LocalDateTime.now());
                count++;
                System.out.println(count);
                if(count == 2){
                    //锁定时间已到，重新激活用户
                    ComboPooledDataSource dataSource = (ComboPooledDataSource) new ClassPathXmlApplicationContext("applicationContext.xml").getBean("pooledDataSource");
                    try {
                        Connection connection = dataSource.getConnection();
                        String sql = "update t_user set status = 1 where id = " + user.getId();
                        PreparedStatement statement = connection.prepareStatement(sql);
                        statement.execute();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    //取消计时器
                    timer.cancel();
                    time_flag = false;
                }
            }
        };
        //每隔一分钟提醒一次
        timer.schedule(task,0,1000 * 60);
    }
}
