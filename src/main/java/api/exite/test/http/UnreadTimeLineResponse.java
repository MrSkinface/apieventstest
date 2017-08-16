package api.exite.test.http;

import java.util.List;

import api.exite.test.http.obj.*;

public class UnreadTimeLineResponse extends Response
{
	public int total_events_count;
	public List<Event>timeline;
	@Override
	public String toString() {
		return "UnreadTimeLineResponse [total_events_count=" + total_events_count + ", timeline=" + timeline
				+ ", varMessage=" + varMessage + ", intCode=" + intCode + "]";
	}	
}
