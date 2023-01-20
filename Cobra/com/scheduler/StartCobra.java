package com.scheduler;



import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import com.run.CobraRun;

public class StartCobra {
	private static Logger logger = Logger.getLogger(StartCobra.class);
	private SchedulerFactory schedFact;
	private Scheduler sched;
	
	
	public StartCobra() {
		try {
			// 스케쥴 생성후 시작
			schedFact = new StdSchedulerFactory();
			sched = schedFact.getScheduler();
			sched.start();
		   
			// Job1 생성 (Parameter : 1.Job Name, 2.Job Group Name, 3.Job Class)
			JobDetail job1 = new JobDetail("job1", "group1", CobraRun.class);

			// Trigger1 생성 (Parameter : 1.Trigger Name, 2.Trigger Group Name, 3.Cron Expression)
			CronTrigger trigger1 = new CronTrigger("trigger1", "group1", "0 0 7,11,15,19 * * ?");
			//CronTrigger trigger1 = new CronTrigger("trigger1", "group1", "0 5 9,11,21,22 * * ?");
			
			// 아래는 trigger 가 misfire 되었을 경우에 대한 처리를 설정한다.
			//trigger1.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_DO_NOTHING);
			sched.scheduleJob(job1, trigger1);

			// Job2 삭제 (30초 후)
		} catch (Exception e) {
			
		}
	}
	
	public static void main(String[] args) {
		new StartCobra();
	}
}
