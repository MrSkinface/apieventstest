package api.exite.test.http.obj;

public class Event 
{
	public String document_id;
	public String event_date;
	public String event_id;
	public String event_status;
	public String exchangeid;
	public String recipient_id;
	public String sender_id;
	public boolean need_reply_reciept;	
	public Event() {	}
	@Override
	public String toString() {
		return "Event [document_id=" + document_id + ", event_date=" + event_date + ", event_id=" + event_id
				+ ", event_status=" + event_status + ", exchangeid=" + exchangeid + ", recipient_id=" + recipient_id
				+ ", sender_id=" + sender_id + ", need_reply_reciept=" + need_reply_reciept + "]";
	}
}
