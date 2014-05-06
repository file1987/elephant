package com.elephant.framework.scheduler;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
/**
 * 定时任务实例构建器
 * @author file
 * @since 2014年5月6日 下午8:47:03
 * @version 1.0
 */
public final class JobDetailBuilder {
	private static final String JOB_NAME = "defaultJobName_";
	private static final String GROUP_NAME = "defaultGroupName";
	
	public static JobDetail newJobDetail(Class<? extends IFJob> clazz){ 
		return newJobDetail(clazz,JOB_NAME+clazz.getName(),GROUP_NAME);
	}
	
	public static JobDetail newJobDetail(Class<? extends IFJob> clazz,String jobName,String groupName){ 
		return JobBuilder.newJob(clazz).withIdentity(jobName, groupName).build();
	}
	

}
