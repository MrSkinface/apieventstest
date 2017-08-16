package api.exite.test.http;

public class EnqueueTicketRequest extends Request
{
	public String identifier;
	public String xml;
	public String sign;
	public EnqueueTicketRequest() {	}
	public EnqueueTicketRequest(String varToken,String identifier,String xml,String sign) 
	{	
		this.varToken=varToken;
		this.identifier=identifier;
		this.xml=xml;
		this.sign=sign;
	}
	@Override
	public String toString() {
		return "EnqueueTicketRequest [identifier=" + identifier + ", xml=" + xml + ", sign=" + sign
				+ ", varToken=" + varToken + "]";
	}
}
