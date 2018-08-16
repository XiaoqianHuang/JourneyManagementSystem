package yang;

import java.io.Serializable;

public class Train implements Serializable{
	int TrainId;
	boolean CurrentStateJ ;//if it is assigned to a journey
	boolean CurrentStateD;
	int AssignedDriverId;//not assigned
	int AssignedJourneyId;
	boolean state;
	int speed;
	
	public Train(int TrainId){
		this.TrainId = TrainId;
		this.CurrentStateJ = true;//youokong
		this.CurrentStateD = true;
		this.AssignedDriverId = -1;
		this.AssignedJourneyId = -1;
		this.state = true;//stop
		this.speed = 0;
	}
	

}
