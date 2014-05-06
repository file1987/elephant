package com.elephant.framework.factory;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.quartz.CronExpression;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.ScheduleBuilder;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

import com.elephant.framework.scheduler.IFJob;
import com.elephant.framework.scheduler.JobDetailBuilder;
/**
 * 定时任务工厂
 * @author file
 * @since 2014年5月5日 下午5:56:12
 * @version 1.0
 */
public final class ScheduledTaskFactory {
	
	static final Map<JobDetail,Set<? extends Trigger>> jobs = new HashMap<JobDetail,Set<? extends Trigger>>();
	/**
	 * 
	 * @param job
	 */
	public static void registerNewYearTask(Class<? extends IFJob> job) {
		registerTask(job, "0 0 0 1 1 ?");
	}

	public static void registerNewMonthTask(Class<? extends IFJob> job) {
		registerTask(job, "0 0 0 1 * ?");
	}

	public static void registerNewChinaWeekTask(Class<? extends IFJob> job) {
		registerTask(job, "0 0 0 ? * MON");
	}
	
	public static void registerNewWeekTask(Class<? extends IFJob> job) {
		registerTask(job, "0 0 0 ? * SUN");
	}

	public static void registerNewDayTask(Class<? extends IFJob> job) {
		registerTask(job, "0 0 0 * * ?");
	}
	/**
	 * 注册整点重复执行任务
	 * @param job
	 */
	public static void registerNewHoursTask(Class<? extends IFJob> job) {
		registerTask(job, "0 0 * * * ?");
	}
	/**
	 * 注册每小时重复执行任务
	 * @param job 
	 */
	public static void registerHoursTask(Class<? extends IFJob> job) {
		registerHoursTask(job,1);
	}
	/**
	 * 注册小时数重复执行任务
	 * @param job 
	 * @param num
	 */
	public static void registerHoursTask(Class<? extends IFJob> job,int num) {
		JobDetail jobDetail = JobDetailBuilder.newJobDetail(job);
		Trigger trigger = TriggerBuilder.newTrigger().startNow().withSchedule(SimpleScheduleBuilder.repeatHourlyForever(num)).build();
		put(jobDetail, trigger);
	}
	/**
	 * 注册每分钟执行一次的任务
	 * @param job 任务
	 */
	public static void registerMinutesTask(Class<? extends IFJob> job) {
		registerMinutesTask(job,1);
	}
	/**
	 * 注册分钟数重复执行任务
	 * @param job 任务
	 * @param num 分钟数
	 */
	public static void registerMinutesTask(Class<? extends IFJob> job,int num) {
		JobDetail jobDetail = JobDetailBuilder.newJobDetail(job);
		Trigger trigger = TriggerBuilder.newTrigger().startNow().withSchedule(SimpleScheduleBuilder.repeatMinutelyForever(num)).build();
		put(jobDetail, trigger);
	}
	/**
	 * 注册每秒执行一次的任务
	 * @param job 任务
	 */
	public static void registerSecondsTask(Class<? extends IFJob> job) {
		registerSecondsTask(job, 1);
	}
	/**
	 * 注册秒数重复执行任务
	 * @param job  任务
	 * @param num  秒数
	 */
	public static void registerSecondsTask(Class<? extends IFJob> job,int num) {
		JobDetail jobDetail = JobDetailBuilder.newJobDetail(job);
		Trigger trigger = TriggerBuilder.newTrigger().startNow().withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(num)).build();
		put(jobDetail, trigger);
	}
	/**
	 * 注册每毫秒执行一次的任务
	 * @param job 任务
	 */
	public static void registerMilliSecondsTask(Class<? extends IFJob> job) {
		registerMilliSecondsTask(job,1l);
	}
	/**
	 * 注册毫秒数重复执行任务
	 * @param job 任务
	 * @param num 毫秒数
	 */
	public static void registerMilliSecondsTask(Class<? extends IFJob> job,long num) {
		JobDetail jobDetail = JobDetailBuilder.newJobDetail(job);
		Trigger trigger = TriggerBuilder.newTrigger().startNow().withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMilliseconds(num).repeatForever()).build();
		put(jobDetail, trigger);
	}
	/**
	 * 注册定时任务
	 * @param job 任务
	 * @param cron cron表达式
	 */
	public static void registerTask(Class<? extends IFJob> job,String cron){
		registerTask(job, CronScheduleBuilder.cronSchedule(cron));
	}
	/**
	 * 注册定时任务
	 * @param job 任务
	 * @param cronExpression cron表达式对象
	 */
	public static void registerTask(Class<? extends IFJob> job,CronExpression cronExpression){
		registerTask(job, CronScheduleBuilder.cronSchedule(cronExpression));
	}
	/**
	 * 注册定时任务
	 * @param job 任务
	 * @param startAt  启动时间
	 * @param endAt   结束时间
	 * @param cron    cron表达式
	 */
	public static void registerTask(Class<? extends IFJob> job,Date startAt,Date endAt, String cron){
		registerTask(job, startAt, endAt, CronScheduleBuilder.cronSchedule(cron));
	}
	/**
	 * 注册定时任务 
	 * @param job 任务
	 * @param startAt 启动时间
	 * @param endAt   结束时间
	 * @param schedule 定时器构造器
	 */
	public static void registerTask(Class<? extends IFJob> job,Date startAt,Date endAt,ScheduleBuilder<CronTrigger> schedule){
		JobDetail jobDetail = JobDetailBuilder.newJobDetail(job);
		Trigger trigger = TriggerBuilder.newTrigger().startAt(startAt).endAt(endAt).withSchedule(schedule).build();
		put(jobDetail, trigger);
	}
	/**
	 * 注册定时任务 
	 * @param job 任务
	 * @param schedule  定时器构造器
	 */
	public static void registerTask(Class<? extends IFJob> job,ScheduleBuilder<CronTrigger> schedule){
		JobDetail jobDetail = JobDetailBuilder.newJobDetail(job);
		Trigger trigger = TriggerBuilder.newTrigger().startNow().withSchedule(schedule).build();
		put(jobDetail, trigger);
	}
	/**
	 * 注册定时任务 
	 * @param job 任务class
	 * @param startAt 开始启动时间
	 * @param schedule 定时器构造器
	 */
	public static void registerTask(Class<? extends IFJob> job,Date startAt,ScheduleBuilder<CronTrigger> schedule){
		JobDetail jobDetail = JobDetailBuilder.newJobDetail(job);
		Trigger trigger = TriggerBuilder.newTrigger().startAt(startAt).withSchedule(schedule).build();
		put(jobDetail, trigger);
	}
	/**
	 * 注册定时任务
	 * @param job 任务  
	 * @param schedule 定时器构造器
	 * @param endAt  结束时间
	 */
	public static void registerTask(Class<? extends IFJob> job,ScheduleBuilder<CronTrigger> schedule,Date endAt){
		JobDetail jobDetail = JobDetailBuilder.newJobDetail(job);
		Trigger trigger = TriggerBuilder.newTrigger().startNow().endAt(endAt).withSchedule(schedule).build();
		put(jobDetail, trigger);
	}
	/**
	 * 注销所有定时任务
	 */
	public static void unregisterAll(){
		jobs.clear();
	}
	
	private static void put(JobDetail job,Trigger trigger){
		@SuppressWarnings("unchecked")
		Set<Trigger>  triggers =  (Set<Trigger>) jobs.get(job);
		if(triggers==null){
			triggers = new HashSet<Trigger>();
			jobs.put(job, triggers);
		}
		
		triggers.add(trigger);
	}
	/**
	 * 获取所有定时任务
	 * @return 不可修改
	 */
	public static Map<JobDetail,Set<? extends Trigger>> getJobs(){
		return Collections.unmodifiableMap(jobs);
	}
	
}
