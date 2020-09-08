### Quartz 常用的表

```
select * from QRTZ_JOB_DETAILS where job_name like '%job%';
select * from QRTZ_TRIGGERS where TRIGGER_NAME like'%job%';
select * from QRTZ_LOCKS;
select * from QRTZ_CALENDARS;
select * from QRTZ_CRON_TRIGGERS;
select * from QRTZ_SCHEDULER_STATE;
select * from QRTZ_SIMPLE_TRIGGERS;
select * from QRTZ_FIRED_TRIGGERS;
select * from QRTZ_SIMPROP_TRIGGERS;
select * from QRTZ_PAUSED_TRIGGER_GRPS;
```
删除
```
delete from QRTZ_JOB_DETAILS where job_name like '%job%';
delete from QRTZ_TRIGGERS where TRIGGER_NAME like'%job%';
delete from QRTZ_CRON_TRIGGERS where TRIGGER_NAME like'%job%';
```
