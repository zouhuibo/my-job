package com.zouhuibo.job.impl;

import com.zouhuibo.job.JobService;
import com.zouhuibo.job.MyJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Set;

@Service
public class JobServiceImpl implements JobService {
    @Resource(name="scheduler")
    private Scheduler scheduler;

    public void startJob(String jobname,String groupname) {

    }

    public void stopJob(String jobname, String groupname) throws SchedulerException {
//        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        JobKey jobKey = JobKey.jobKey(jobname,groupname);
//        TriggerKey triggerKey = TriggerKey.triggerKey(jobname, groupname);
//        scheduler.pauseTrigger(triggerKey);
//        scheduler.unscheduleJob(triggerKey);
        scheduler.pauseJob(jobKey);
        scheduler.deleteJob(jobKey);
//        scheduler.start();
    }

    @Override
    public void stopAllJob(String group) throws SchedulerException {
        Set<JobKey> alljob = scheduler.getJobKeys(GroupMatcher.anyGroup());
        for (JobKey jobKey : alljob)
        {
            System.out.println("stopAllJob:jobname="+jobKey.getName());
            scheduler.deleteJob(jobKey);
        }
    }

    public void updateJob(String jobname, String groupname) {

    }

    public void addJob(String jobname,String groupname, String cronexpress) throws SchedulerException {
//        scheduler = StdSchedulerFactory.getDefaultScheduler();
        if (scheduler==null)
            throw new RuntimeException("scheduler 初始化失败!");
        try {
            JobKey jobKey = new JobKey(jobname, groupname);
            boolean existsjob = scheduler.checkExists(jobKey);
            if (existsjob) {
                throw new RuntimeException(jobname+","+groupname+"任务已经存在!不能重启动.");
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        CronTrigger crontrigger = TriggerBuilder.newTrigger().withIdentity(jobname, groupname)
                .withSchedule(CronScheduleBuilder.cronSchedule(cronexpress)).build();
        JobDetail jobDetail = JobBuilder.newJob(MyJob.class).withIdentity(jobname, groupname).build();
        scheduler.scheduleJob(jobDetail, crontrigger);
        System.out.println("加入任务: jobname:"+jobname+",groupname:"+groupname);
        scheduler.start();
    }

    public Collection getJobs() throws SchedulerException {
        Set<JobKey> jobKeys = scheduler.getJobKeys(GroupMatcher.<JobKey>anyGroup());
        return jobKeys;
    }

    public String runJob2immediately(String jobname,String groupname) {
        return null;
    }

    public void simpletrigger(String jobname) throws SchedulerException {
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        SimpleTrigger simpleTrigger = TriggerBuilder.newTrigger().withIdentity("myjob1", "mygroup1")
//                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(2).withRepeatCount(5))
                .endAt(new GregorianCalendar(2020,9,3,15,47,0).getTime())
                .build();

        JobDetail jobDetail = JobBuilder.newJob(MyJob.class).withIdentity("job1", "group1").build();
        scheduler.scheduleJob(jobDetail, simpleTrigger);
        scheduler.start();
    }

    public void crontrigger(String jobgroup,String jobname, String cronpress) throws SchedulerException {
        scheduler = StdSchedulerFactory.getDefaultScheduler();
        try {
            JobKey jobKey = new JobKey(jobname, jobgroup);
            boolean existsjob = scheduler.checkExists(jobKey);
            if (existsjob) {
                throw new RuntimeException(jobname+","+jobgroup+"任务已经存在!不能重启动.");
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        CronTrigger crontrigger = TriggerBuilder.newTrigger().withIdentity(jobname, jobgroup)
//                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule(cronpress)).build();
//                .endAt(new GregorianCalendar(2020,9,3,15,47,0).getTime())
//                .build();

        JobDetail jobDetail = JobBuilder.newJob(MyJob.class).withIdentity(jobname, jobgroup).build();
        scheduler.scheduleJob(jobDetail, crontrigger);
        scheduler.start();
    }

    public static void main(String[] args) throws SchedulerException {
        JobServiceImpl jobService = new JobServiceImpl();
        jobService.crontrigger("myjob","job1","*/3 35 11 7 9 ?");
        jobService.crontrigger("myjob","job1","*/3 35 11 7 9 ?");
//        jobService.crontrigger("myjob","job2","*/3 39 9 7 9 ?");
//        jobService.crontrigger("myjob2","job2","*/3 39 9 7 9 ?");
        jobService.stopJob("job1","myjob");
        jobService.stopJob("job1","myjob");

        jobService.crontrigger("myjob","job1","*/3 46 11 7 9 ?");
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        Scheduler scheduler2 = StdSchedulerFactory.getDefaultScheduler();
        Scheduler scheduler3 = StdSchedulerFactory.getDefaultScheduler();
        System.out.println(scheduler==scheduler2);
        System.out.println(scheduler==scheduler3);
        System.out.println(scheduler2==scheduler3);

        JobDetail jobDetail = scheduler.getJobDetail(new JobKey("job1","myjob"));
        System.out.println(scheduler.checkExists(new JobKey("job","myjob")));
        System.out.println(scheduler.checkExists(new JobKey("job2","myjob")));
        System.out.println(jobDetail);
    }


}
