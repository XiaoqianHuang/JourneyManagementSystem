package yang;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Journey implements Serializable{
	int JourneyId;
	int BelongRouteId;
	int AssignedTrainId;
	boolean CurrentState;
	String[] OutWardArrivingTime;
	String[] ReturnArrivingTime;
	int StopNumber;
	Route route;
	SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
	
	public Journey(int JourneyId,Route route) throws ParseException{
		this.StopNumber = route.StopNumber;
		this.JourneyId = JourneyId;
		this.route = route;
		this.BelongRouteId = route.RouteId;
		this.AssignedTrainId = -1;
		this.CurrentState = true;
		OutWardArrivingTime = new String[StopNumber];
		ReturnArrivingTime = new String[StopNumber];
	}
}