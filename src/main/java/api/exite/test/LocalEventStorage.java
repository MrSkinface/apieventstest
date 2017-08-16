package api.exite.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import api.exite.test.http.obj.Event;

public class LocalEventStorage 
{
	private static final Logger log=Logger.getLogger(LocalEventStorage.class);
	
	private int capacity;
	/*
	 * коллекция цепочек документооборота с полным набором необходимых квитанций (т.е. завершенные цепочки документооборота)
	 * 
	 * */
	private List<EventChain>completeEventChains;
	/*
	 * коллекция цепочек с незавершенным документооборотом (т.е. документы в процессе обработки) 
	 * 
	 * */
	private HashMap<String,EventChain>processingEvents;
	
	public LocalEventStorage(int capacity) 
	{
		this.capacity=capacity;
		completeEventChains=new ArrayList<EventChain>(capacity);
		processingEvents=new HashMap<String,EventChain>();
	}
	/*
	 * флаг наличия свободного места в локальном хранилище
	 * если кол-во завершенных цепочек документооборота меньше чем ёмкость всего хранилища -> понимаем что хранилище не заполнено ожидаемым кол-вом завершенных документов
	 * 
	 * */
	public boolean hasFreeSpace()
	{
		return completeEventChains.size()<capacity;
	}
	/*
	 * каждое полученное через апи событие сохраняем в локальное хранилище (в коллекцию документов в процессе обработки)
	 * если на данный момент в хранилище отсутствует цепочка документооборота связанная с сохраняемым событием -> для события создаем новую цепочку и помещаем в хранилище
	 * после сохранения собитыя производим проверку статуса документооборота : завершена ли вся цепочка документооборота по сохраняемому событию
	 * если цепочка завершена (все связанные события с данным/сохраняемым собитием добавлены в цепочку документооборота) -> переносим цепочку документооборота в коллекцию завершенных / обработанных цепочек
	 * 
	 * */
	public void save(Event e)
	{
		EventChain chain=processingEvents.get(e.exchangeid);
		if(chain==null)
		{
			chain=new EventChain(e);
			processingEvents.put(e.exchangeid, chain);
		}
		else
			chain.addToChain(e);
		if(chain.isChainComplete())
			makeChainComplete(e);
	}
	/*
	 * перенос цепочки документооборота из документов находящихся в процессе обработки в коллекцию завершенных / обработанных цепочек
	 * 
	 * */
	private void makeChainComplete(Event e)
	{
		EventChain ch=processingEvents.remove(e.exchangeid);
		completeEventChains.add(ch);
		log.info("chain ["+e.exchangeid+"] is complete. Current load is "+completeEventChains.size()+" of "+capacity);
	}
	public List<EventChain> getCompleteChains()
	{
		return completeEventChains;
	}
	public int getCurrentCompleteChainsCount()
	{
		return completeEventChains.size();
	}
	public int getCurrentInProgressChainsCount()
	{
		return processingEvents.size();
	}
}
/*
 * модель цепочки документооборота (содержит коллекцию связанных между собой событий)
 * 
 * */
class EventChain
{
	private static final Logger log=Logger.getLogger(EventChain.class);
	
	private List<Event>events;
	public EventChain(Event e) 
	{
		events=new LinkedList<Event>();
		addToChain(e);
	}
	/*
	 * добавление заданного события в цепочку документооборота
	 * 
	 * */
	public void addToChain(Event e)
	{
		events.add(e);
		log.info("event ["+e.document_id+"] added to chain ["+e.exchangeid+"] . Chain has ["+events.size()+"] events");		
	}
	/*
	 * проверка завершена ли вся цепочка документооборота
	 * на стороне получателя УПД "СЧФ" кол-во событий, которые необходимы для завершения документооборота равно 6
	 * 
	 * */
	public boolean isChainComplete()
	{
		return events.size()==6;
	}
}
