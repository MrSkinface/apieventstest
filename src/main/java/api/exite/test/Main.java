package api.exite.test;

import java.io.FileInputStream;
import java.nio.file.Paths;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import api.exite.test.http.obj.Event;

public class Main 
{
	private static final Logger log=Logger.getLogger(Main.class);
	
	private Controller controller;
	
	public Main() throws Exception 
	{
		initLog();
		log.info("start");
		controller=new Controller("testSelgrosESF", "c1be0442");		
		/*
		 * 
		 * 
		 * */
		LocalEventStorage storage=new LocalEventStorage(2000);
		
		List<Event>events;
		while (storage.hasFreeSpace()) 
		{
			log.info("storage: current in progress ["+storage.getCurrentInProgressChainsCount()+"] , current completed ["+storage.getCurrentCompleteChainsCount()+"]");
			events=controller.getUnreadEvents();			
			for (Event event : events) 
			{
				if(controller.confirmEvent(event))
					if(controller.readEvent(event))
						storage.save(event);
			}
		}
		log.info("storage has no free space");
		log.info("end");
	}

	public static void main(String[] args) throws Exception 
	{
		new Main();
		
	}
	private void initLog() throws Exception
	{
		PropertyConfigurator.configure(new FileInputStream(Paths.get(System.getProperty("user.dir")).resolve("log4j").resolve("log4j.properties").toString()));
	}

}
