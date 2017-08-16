package api.exite.test.http;

public class AuthorizeRequest 
{
	public String varLogin;
	public String varPassword;
	
	public AuthorizeRequest() {	}
	public AuthorizeRequest(String varLogin, String varPassword) 
	{
		this.varLogin=varLogin;
		this.varPassword=varPassword;
	}
	@Override
	public String toString() {
		return "AuthorizeRequest [varLogin=" + varLogin + ", varPassword=" + varPassword + "]";
	}
}
