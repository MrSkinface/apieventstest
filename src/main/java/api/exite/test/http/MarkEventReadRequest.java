package api.exite.test.http;

public class MarkEventReadRequest extends Request
{
	public String event_id;
	public MarkEventReadRequest(String varToken, String event_id) 
	{	
		this.varToken=varToken;
		this.event_id=event_id;
	}
	@Override
	public String toString() {
		return "MarkEventReadRequest [event_id=" + event_id + ", varToken=" + varToken + "]";
	}	
}
