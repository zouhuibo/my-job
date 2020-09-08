package com.zouhuibo.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class MyJob implements Job {
    public MyJob() {
//        System.out.println("construct...");
    }

    private static int i=0;
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String jobname = jobExecutionContext.getTrigger().getKey().getName();
        String groupname = jobExecutionContext.getTrigger().getKey().getGroup();
        System.out.println("任务执行: jobname:"+jobname+",group:"+groupname+","+jobExecutionContext.getFireTime());
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize");
    }


}
