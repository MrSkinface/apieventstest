package api.exite.test.http;

public class CreateTicketRequest extends Request
{
	public String identifier;
	public String signer_fname;
	public String signer_sname;
	public String signer_position;
	public String signer_inn;
	public String comment;
	public CreateTicketRequest() {	}
	public CreateTicketRequest(String varToken,String identifier,String signer_fname,String signer_sname,String signer_position,String signer_inn) 
	{	
		this.varToken=varToken;
		this.identifier=identifier;
		this.signer_fname=signer_fname;
		this.signer_sname=signer_sname;
		this.signer_position=signer_position;
		this.signer_inn=signer_inn;
	}
	@Override
	public String toString() {
		return "CreateTicketRequest [identifier=" + identifier + ", signer_fname=" + signer_fname + ", signer_sname="
				+ signer_sname + ", signer_position=" + signer_position + ", signer_inn=" + signer_inn + ", comment="
				+ comment + ", varToken=" + varToken + "]";
	}
}
