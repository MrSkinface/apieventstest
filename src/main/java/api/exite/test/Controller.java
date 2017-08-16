package api.exite.test;

import java.util.Base64;
import java.util.List;

import org.apache.log4j.Logger;
import org.exite.cryptex.client.CryptoClient;

import api.exite.test.http.*;
import api.exite.test.http.obj.*;
import api.exite.test.utils.Utils;

public class Controller 
{
	private static final Logger log=Logger.getLogger(Controller.class);
	
	private final String url="https://api-service.edi.su/Api/Dixy/";
	private String authToken;
	
	public Controller(String login, String pass) throws Exception 
	{
		this.authToken=authorize(login, pass);
	}
	public List<Event> getUnreadEvents()
	{
		UnreadTimeLineRequest req =new UnreadTimeLineRequest(authToken);
		UnreadTimeLineResponse resp=(UnreadTimeLineResponse)Http.post(url+"TimeLine/GetUnreadTimeLine", req, UnreadTimeLineResponse.class);
		if(resp.intCode!=200)
			try 
			{
				throw new Exception(resp.varMessage);
			} catch (Exception ex) 
			{
				log.error(ex.getMessage());
			}
		log.info("got "+resp.timeline.size()+" events");
		return resp.timeline;
	}
	public boolean confirmEvent(Event e)
	{
		if(isNoNeedToProcess(e))
			return true;
		String ticket;
		try 
		{
			ticket = generateTicket(authToken,e.document_id);
			sendTicket(authToken, e.document_id, ticket, getStringSignBody(ticket));
			log.info("event ["+e.document_id+"] processed (signed and confirmed)");
			return true;
		} catch (Exception ex) 
		{			
			log.error(ex.getMessage());
			return false;
		}		
	}
	public boolean readEvent(Event e)
	{
		MarkEventReadRequest req=new MarkEventReadRequest(authToken, e.event_id);
		MarkEventReadResponse resp=(MarkEventReadResponse)Http.post(url+"TimeLine/MarkEventRead", req, MarkEventReadResponse.class);
		if(resp.intCode!=200)
		{
			log.error(resp.varMessage);
			return false;
		}
		log.info("event ["+e.document_id+"] marked as read");
		return true;		
	}
	
	private boolean isNoNeedToProcess(Event e)
	{
		if(e.event_status.contains("SENT"))
			return true;
		else if(!e.need_reply_reciept)
			return true;
		return false;
	}
	private String authorize(String login, String pass) throws Exception
	{
		AuthorizeRequest req=new AuthorizeRequest(login, pass);
		AuthorizeResponse resp=(AuthorizeResponse)Http.post(url+"Index/Authorize", req , AuthorizeResponse.class);	
		if(resp.intCode!=200)
		{
			log.error(resp.varMessage);
			throw new Exception(resp.varMessage);			
		}
		return resp.varToken;
	}
	private String generateTicket(String varToken,String identifier) throws Exception
	{
		final String signer_fname="signer_fname";
		final String signer_sname="signer_sname";
		final String signer_position="signer_position"; 
		final String signer_inn="3313311833";
		CreateTicketRequest req=new CreateTicketRequest(varToken, identifier, signer_fname, signer_sname, signer_position, signer_inn);
		CreateTicketResponse resp=(CreateTicketResponse)Http.post(url+"Ticket/Generate", req, CreateTicketResponse.class);
		if(resp.intCode!=200)
		{
			log.info("request debug: "+Utils.toJson(req));
			log.info("response debug: "+Utils.toJson(resp));
			throw new Exception(resp.varMessage);
		}
		return resp.content;
	}
	private boolean sendTicket(String authToken, String docId, String docBody, String signBody) throws Exception
	{
		EnqueueTicketRequest req =new EnqueueTicketRequest(authToken, docId, docBody, signBody);
		EnqueueTicketResponse resp=(EnqueueTicketResponse)Http.post(url+"Ticket/Enqueue", req, EnqueueTicketResponse.class);
		if(resp.intCode!=200)
			throw new Exception(resp.varMessage);
		return resp.intCode==200;
	}
	
	private String getStringSignBody(String docBody)
	{
		byte[] doc;
		String sign=null;
		try 
		{
			doc = Base64.getDecoder().decode(docBody);
			sign=new String(CryptoClient.sign(null, "exite2016", doc));
		} catch (Exception ex) 
		{
			log.error(ex.getMessage());
		}
		return sign;
	}
}
