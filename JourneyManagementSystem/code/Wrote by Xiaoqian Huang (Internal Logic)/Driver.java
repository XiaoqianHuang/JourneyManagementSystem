package yang;

import java.io.Serializable;

public class Driver implements Serializable{
	int DriverId;
	boolean CurrentState;
	int AssignedTrainId;
	public Driver(int DriverId){
		this.DriverId = DriverId;
		this.CurrentState = false;
		this.AssignedTrainId = -1;
	}
}
