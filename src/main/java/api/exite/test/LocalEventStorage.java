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
	private List<EventChain>completeEventChains;
	
	private HashMap<String,EventChain>processingEvents;
	
	public LocalEventStorage(int capacity) 
	{
		this.capacity=capacity;
		completeEventChains=new ArrayList<EventChain>(capacity);
		processingEvents=new HashMap<String,EventChain>();
	}
	
	public boolean hasFreeSpace()
	{
		return completeEventChains.size()<capacity;
	}
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
class EventChain
{
	private static final Logger log=Logger.getLogger(EventChain.class);
	
	private List<Event>events;
	public EventChain(Event e) 
	{
		events=new LinkedList<Event>();
		addToChain(e);
	}
	public void addToChain(Event e)
	{
		events.add(e);
		log.info("event ["+e.document_id+"] added to chain ["+e.exchangeid+"] . Chain has ["+events.size()+"] events");		
	}
	public boolean isChainComplete()
	{
		return events.size()==6;
	}
}
