package com.elephant.framework.scheduler;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class MyJob1 implements IFJob {
	private static final Logger _log = Logger.getLogger(MyJob1.class);
	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		_log.info("Myjob1  time :"+System.currentTimeMillis());
		
	}

}
