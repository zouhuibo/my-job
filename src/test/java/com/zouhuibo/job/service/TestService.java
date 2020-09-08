package com.zouhuibo.job.service;

import com.zouhuibo.job.JobService;
import com.zouhuibo.job.impl.JobServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


@SpringBootTest(classes = MytechApplication.class)
@RunWith(SpringRunner.class)
public class TestService {

    @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;

    @Autowired
    private JobService jobService;

    @Test
    public void testJob() throws SchedulerException {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext("com.zouhuibo");
//        applicationContext.scan("com.zouhuibo");
//        applicationContext.register(JobServiceImpl.class);
//        applicationContext.refresh();
        JobService jobService = (JobService)applicationContext.getBean(JobServiceImpl.class);
        jobService.addJob("job1", "jobgroup1","* * 14 8 9 ?");
        System.out.println(jobService.getJobs());
    }

    @Test
    public void springJdbcTemplateTest() throws Exception{
        try{
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            String queryStr = "select * from VH_VHJOB";
            AtomicInteger i = new AtomicInteger();
            List<ResultSet> resultList = new ArrayList();
            jdbcTemplate.query(queryStr, (ResultSet resultSet)->{
                resultList.add(resultSet);
                String name = null;
                name = resultSet.getString("jobname");
                System.out.println(String.valueOf(i.getAndIncrement())+","+name);
            });
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    @Test
    public void testDb() throws SchedulerException {
        System.out.println("testDb");
        if (jobService== null) {
            System.out.println("jobService is null");
        }

        Collection jobs = jobService.getJobs();
        System.out.println(Arrays.asList(jobs));
        jobService.stopAllJob(null);
        jobService.addJob("job1", "jobgroup1","* * 15 8 9 ?");
        jobService.addJob("job2", "jobgroup1","* * 15 8 9 ?");
    }

}
