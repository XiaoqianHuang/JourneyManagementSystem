package yang;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class AssignTrain {
	private ArrayList<Journey> JourneyList ;
	private ArrayList<Train> TrainList ;
	private int JourneyNo;
	private int TrainNo;
	ObjectSer objectSer;
	
	
	/*Test start
	public static void main(String[] args) throws ParseException {
		Train t1 = new Train(01);
		t1.CurrentStateJ =true;//true=valid;false=invalid
		TrainList.add(t1);
		Train t2 = new Train(02);
		t2.CurrentStateJ =true;
		TrainList.add(t2);
		Train t3 = new Train(03);
		t3.CurrentStateJ =false;
		TrainList.add(t3);
		Route r = new Route(1, 3);
		String s1 = "00:00:00";
		Journey j1 = new Journey(1,r);
		j1.CurrentState =true;
		j1.OutWardArrivingTime[0] = s1;
		JourneyList.add(j1);
		String s2 = "13:22:55";
		Journey j2 = new Journey(02,r);//d3 assigned to t2
		j2.OutWardArrivingTime[0] = s2;
		j2.CurrentState =true;
		j2.AssignedTrainId =03;
		JourneyList.add(j2);
		
		AssignTrain a = new AssignTrain();
		a.assignTrain(01, 01);
		a.assignTrain(02, 01);
		a.assignTrain(03, 02);
//		int train1 = a.GetTrainId(01);
//		System.out.println("The TrainId of Journey[" + "01] is " + train1);
//		int train2 = a.GetTrainId(02);
//		System.out.println("The TrainId of Journey[" + "02] is " + train2);
//		int train3 = a.GetTrainId(03);
//		System.out.println("The TrainId of Journey[" + "03] is " + train3);
		System.out.println("-----------------End of getid-----------------");
		System.out.println("");
	}
//	Test end*/
	
	public AssignTrain(){
		objectSer = new ObjectSer();
		JourneyList = new ArrayList<>();
		TrainList = new ArrayList<>();
		//read JourneyList and TrainList
		//write JourneyList and TrainList
	}
	
	public Object[] assignTrain(int TrainId,int JourneyId,ArrayList<Journey> jArrayList, ArrayList<Train> tArrayList) throws ClassNotFoundException, IOException{
//		JourneyList = objectSer.readJourneyArrayList();
//		TrainList = objectSer.readTrainArrayList();
		JourneyList = jArrayList;
		TrainList = tArrayList;
		System.out.println("-------Now Assign Train[" + TrainId + "] to Jouney[" + JourneyId + "]-------");
		
		int flag1 = 0;
		for(int i = 0;i < JourneyList.size();i++){//find if the driver is exist
			if(JourneyList.get(i).JourneyId == JourneyId){
				this.JourneyNo = i;
				flag1 = 1;
				break;
			}
		}		
		
		int flag2 = 0;
		for(int i = 0;i < TrainList.size();i++){//find the train id
			if(TrainList.get(i).TrainId == TrainId){
				this.TrainNo = i;
				flag2 = 1;
				break;
			}
		}	
		
		if(flag1 == 1 && flag2 == 1){
			boolean Valid;
			Valid = this.CheckValid();
			if(Valid == true){
				JourneyList.get(JourneyNo).AssignedTrainId =TrainId;//assign driver to train
				JourneyList.get(JourneyNo).CurrentState =false;
				TrainList.get(TrainNo).CurrentStateJ =false;
				TrainList.get(TrainNo).AssignedJourneyId = JourneyId;
				System.out.println("Assignment Train[" + TrainId + "] to Journey[" + JourneyId + "]Success!");
			}else{
				System.out.println("Assignment Train[" + TrainId + "] to Journey[" + JourneyId + "]Failed.");
			}
		}else if(flag1 == 0){
			System.out.println("Assigned Error! Journey Not exist!");//is -1, driver not exist
		}else if(flag2 == 0){
			System.out.println("Assigned Error! Train Not exist!");//is -1, train not exist
		}
		System.out.println("----------------End of assign-----------------");
		System.out.println("");
//		objectSer.writeJourneyArrayList(JourneyList);
//		objectSer.writeTrainArrayList(TrainList);
		Object[] couple = new Object[2];
		couple[0] = JourneyList;
		couple[1] = TrainList;
		return couple;
	}
	
	public boolean CheckValid(){
		if((JourneyList.get(JourneyNo).CurrentState == true)&&(TrainList.get(TrainNo).CurrentStateJ == true)){//if both is valid, assign
			return true;
		}
		else{
			if(JourneyList.get(JourneyNo).CurrentState == false){
				System.out.println("Journey is not available");
			}else if(TrainList.get(TrainNo).CurrentStateJ == false){
				System.out.println("Train is not available");
			}
			return false;
		}
	}
	
//	public int GetTrainId(int JourneyId){//if returned value != -1, we can know train not assigned; if contain the driver id, we can know the driver is assigned; and we can know this train is assigned which driver
//		System.out.println("-----------------get train id----------------");
//		int flag = 0;
//		for(int i = 0;i < JourneyList.size();i++){//find the train id
//			if(JourneyList.get(i).AssignedTrainId == JourneyId){
//				this.JourneyNo = i;
//				flag = 1;
//				break;
//			}
//		}
//
//		if(flag == 1){
//			return TrainList.get(TrainNo).TrainId;
//		}else{
//			System.out.println("This journey not exist!");//is -1, train not exist
//			return -2;
//		}
//	}
}

