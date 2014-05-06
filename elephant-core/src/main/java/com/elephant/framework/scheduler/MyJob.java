package com.elephant.framework.scheduler;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class MyJob implements IFJob {
	private static final Logger _log = Logger.getLogger(MyJob.class);
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		_log.info("Myjob  time :"+new Date());
	}

}
