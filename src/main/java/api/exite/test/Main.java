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
	
	public Main(String login, String pass, int documentChainsToBeProcessed) throws Exception 
	{
		initLog();
		log.info("start");
		controller=new Controller(login, pass);		
		/*
		 * создаем локальное хранилище документов с заданной вместимостью
		 * тест проводился на 2000 УПД, следовательно необходимая емкость хранилища == 2000 цепочек документооборота
		 * 
		 * */
		LocalEventStorage storage=new LocalEventStorage(documentChainsToBeProcessed);
		/*
		 * текущий список собитый получаемый через апи
		 * перебираем каждый полученный список в цикле
		 * 
		 * */
		List<Event>events;
		/*
		 * обработка длится до тех пор, пока локальное хранилище не заполнено (пока хранилище не содержит 2000 цепочек документооборота)		 * 
		 * 
		 * */
		while (storage.hasFreeSpace()) 
		{
			log.info("storage: current in progress ["+storage.getCurrentInProgressChainsCount()+"] , current completed ["+storage.getCurrentCompleteChainsCount()+"]");
			/*
			 * получение текущего списка событий, которые нужно перебрать в цикле
			 * 
			 * */
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
		new Main(args[0], args[1], Integer.parseInt(args[2]));
		
	}
	private void initLog() throws Exception
	{
		PropertyConfigurator.configure(new FileInputStream(Paths.get(System.getProperty("user.dir")).resolve("log4j").resolve("log4j.properties").toString()));
	}

}
