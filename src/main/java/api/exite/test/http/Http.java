package api.exite.test.http;

import java.io.UnsupportedEncodingException;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import api.exite.test.utils.Utils;

public final class Http 
{
	public static Object post(String address,Object obj,Class<? extends Object> c)
	{		
		try 
		{
			HttpResponse<JsonNode> jsonResponse;
			jsonResponse=Unirest.post(address)
					.header("accept", "application/json")
					.body(Utils.toJson(obj).getBytes("UTF-8"))
					.asJson();
			return Utils.fromJson(jsonResponse.getBody().toString(), c);			
		} catch (UnsupportedEncodingException e) 
		{			
			e.printStackTrace();
		} catch (UnirestException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
}
