package yang;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;



public class synMember implements Serializable{
		protected int trainId;
		protected String scheduled_time;
		protected String live_loction;
		protected String last_stop_time;
		protected String late_time;
		
		public synMember(int trainId, String scheduled_time, String live_location, String last_stop_time, String late_time) {
			this.trainId = trainId;
			this.scheduled_time = scheduled_time;
			this.live_loction = live_location;
			this.last_stop_time = last_stop_time;
			this.late_time = late_time;
		}

}