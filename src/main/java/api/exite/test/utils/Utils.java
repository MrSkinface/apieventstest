package api.exite.test.utils;

import com.google.gson.Gson;

public class Utils 
{
	public static Object fromJson(String json, Class<? extends Object> c)
	{
		Gson gson = new Gson();
		return gson.fromJson(json, c);
	}
	public static String toJson(Object o)
	{
		Gson gson = new Gson();
		return gson.toJson(o);
	}
}
