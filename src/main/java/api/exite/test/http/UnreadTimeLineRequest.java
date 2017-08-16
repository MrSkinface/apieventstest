package api.exite.test.http;

public class UnreadTimeLineRequest extends Request
{
	public final String mode="UPD";
	public UnreadTimeLineRequest(String varToken) 
	{
		this.varToken=varToken;
	}
	@Override
	public String toString() {
		return "UnreadTimeLineRequest [mode=" + mode + ", varToken=" + varToken + "]";
	}	
}
