package yang;

import java.io.IOException;
import java.util.ArrayList;

public class externalSys {
	ObjectSer objectSer;
	ArrayList<Train> outTrains;
	public externalSys() {
		objectSer = new ObjectSer();
		outTrains = new ArrayList<>();
	}
	public ArrayList<synMember> track() throws ClassNotFoundException, IOException {
		ArrayList<Train> allTrain = objectSer.readTrainArrayList();
		for (int i = 0; i < allTrain.size(); i++) {
			if (allTrain.get(i).CurrentStateJ == false && allTrain.get(i).CurrentStateD == false) {
				outTrains.add(allTrain.get(i));
			}
		}
		ArrayList<synMember> members= new ArrayList<>();
		System.out.println(outTrains.size()+"+"+members.size());
		for (int i = 0; i < outTrains.size(); i++) {
			//write code here...
			
			String scheduled_time = null;
			String live_loction = "zoo";
			String last_stop_time = "11:00:00";
			String late_time = null;
			synMember member = new synMember(outTrains.get(i).TrainId, scheduled_time, live_loction, last_stop_time, late_time);
			members.add(member);
		}
		objectSer.writeSynMember(members);
		
		return members;
	}
	public static void main(String[] args) throws ClassNotFoundException, IOException {
		externalSys es = new externalSys();
		ArrayList<synMember> members= new ArrayList<>();
		members = es.track();
		for (int i = 0; i < members.size(); i++) {
			System.out.println("id:"+members.get(i).trainId+"-st:"+members.get(i).scheduled_time+"-ll:"
								+members.get(i).live_loction);
		}

	}

}
