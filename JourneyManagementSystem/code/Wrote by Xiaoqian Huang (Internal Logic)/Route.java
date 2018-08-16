package yang;
import java.io.Serializable;
import java.util.ArrayList;

public class Route implements Serializable{
	int StopNumber;//单程
	int RouteId;	//	private String[][] StopSerial ;//[0]stopname;[1]duration time
	String[] StopName ;
	String[] Duration ;
	ArrayList<Integer> AssignedJourney;
	int defaultSpeed;
	public Route (int RouteId, int StopNumber,String[] StopName,String[] Duration){
		
		this.StopNumber = StopNumber;
		this.RouteId = RouteId;
		this.StopName = StopName;
		this.Duration = Duration;
		this.AssignedJourney = new ArrayList<Integer>();
		this.defaultSpeed = 0;
		
		
	}
public Route (int RouteId, int StopNumber){
		
		this.StopNumber = StopNumber;
		this.RouteId = RouteId;
		this.StopName = new String[this.StopNumber];
		this.Duration = new String[this.StopNumber];
		this.AssignedJourney = new ArrayList<Integer>();
		this.defaultSpeed = 0;

		
		
	}
	
}