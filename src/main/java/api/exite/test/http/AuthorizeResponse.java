package api.exite.test.http;

public class AuthorizeResponse extends Response
{
public String varToken;
	
	public AuthorizeResponse() {	}	
	@Override
	public String toString() {
		return "AuthorizeResponse [varToken=" + varToken + ", varMessage=" + varMessage + ", intCode=" + intCode + "]";
	}
}
