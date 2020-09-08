package com.zouhuibo.job;

import org.quartz.SchedulerException;

import java.util.Collection;

public interface JobService {

    public void startJob(String jobname,String groupname);

    public void stopJob(String jobname, String groupname) throws SchedulerException;

    public void stopAllJob(String group) throws SchedulerException;

    public void updateJob(String jobname, String groupname);

    public void addJob(String jobname, String groupname, String cronexpress) throws SchedulerException;

    public Collection getJobs() throws SchedulerException;

    public String runJob2immediately(String jobname,String groupname);

}
