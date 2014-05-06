package com.elephant.framework.scheduler;

import java.util.Map;
import java.util.Set;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import com.elephant.framework.factory.ScheduledTaskFactory;
/**
 * 定时任务启动器
 * @author file
 * @since 2014年5月6日 下午8:32:54
 * @version 1.0
 */
public class SchedulerTask {
	
	public static SchedulerTask getInstance(){
		return SchedulerTaskHolder.SCHEDULER_TASK;
	}
	//定时任务
	private Scheduler scheduler ;
	//是否已启动
	private boolean isStart;
	/**
	 * 启动
	 */
	public void start(){
		Map<JobDetail, Set<? extends Trigger>> jobs = ScheduledTaskFactory.getJobs();
		if(jobs.isEmpty())
			return;
		try {
			 scheduler = StdSchedulerFactory.getDefaultScheduler();
			 scheduler.start();
			 scheduler.scheduleJobs(jobs,true);
			isStart= true;
		} catch (SchedulerException e) {
			e.printStackTrace();
		}		
	}
	
	/**
	 * 停止
	 */
	public void stop(){
		if(isStart && scheduler !=null){
			ScheduledTaskFactory.unregisterAll();
			try {
				  scheduler.shutdown();
			} catch (SchedulerException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	private static class SchedulerTaskHolder{ 
		
		private static final SchedulerTask SCHEDULER_TASK = new SchedulerTask();
		
	}
	/**
	public static void main(String[] args) {
		ScheduledTaskFactory.registerMilliSecondsTask(MyJob1.class,500);
		ScheduledTaskFactory.registerSecondsTask(MyJob.class);
		ScheduledTaskFactory.registerMinutesTask(MyJob1.class);
		SchedulerTask.getInstance().start();		
	}**/
	
	

}
