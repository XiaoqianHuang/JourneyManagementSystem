package yang;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;



public class ControlSynchronize {

		private String[][] NextstopPlanTime = new String[1][2];
		ObjectSer objectSer;
		ArrayList<Journey> JourneyList;
		ArrayList<synMember> sList;
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		
		public void ControlMemberTime(int trainId ) throws ClassNotFoundException, IOException, ParseException {
			
			objectSer = new ObjectSer();
			sList = objectSer.readSynMember();
			synMember member = null;
			for (int i = 0; i < sList.size(); i++) {
				if (sList.get(i).trainId == trainId) {
					member = sList.get(i);
				}
			}
		
			
			
			ControlOnBoardScreen co = new ControlOnBoardScreen();
			NextstopPlanTime = co.controlOnBoardScreen(trainId);
			
			Long DurationTime = (long) -1;
			if (NextstopPlanTime[0][1]!=null) {
				DurationTime = format.parse(NextstopPlanTime[0][1]).getTime() - format.parse(member.last_stop_time).getTime();
				member.scheduled_time = NextstopPlanTime[0][1];
			}
			else {
				member.scheduled_time = "None";
			}
			
			objectSer.writeSynMember(sList);
			
		}
		
}