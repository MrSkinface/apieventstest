package api.exite.test.http;

public class Request 
{
	public String varToken;
	
	public Request() {}
	public Request(String varToken) 
	{
		this.varToken=varToken;
	}	
	@Override
	public String toString() {
		return "Request [varToken=" + varToken +"]";
	}
}
