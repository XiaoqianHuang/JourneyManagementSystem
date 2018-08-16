package yang;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class ControlOnBoardScreen {
	ObjectSer objectSer;
	private ArrayList<Journey> JourneyList;
	private ArrayList<Train> TrainList;
	private int JourneyNo;
	private int StopNo =-1;
	private String Nextstop;
	private String PlanTime;
	private String[][] NextstopPlanTime = new String[1][2];
	SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
	
	public ControlOnBoardScreen(){
	}
	
	public String[][] controlOnBoardScreen(int TrainId) throws ClassNotFoundException, IOException, ParseException{
		objectSer = new ObjectSer();
		JourneyList = new ArrayList<Journey>();
		TrainList = new ArrayList<Train>();
		JourneyList = objectSer.readJourneyArrayList();
		TrainList = objectSer.readTrainArrayList();
		
		int TrainNo = -1;
		for(int i = 0;i < TrainList.size();i++){//find the train id
			if(TrainList.get(i).TrainId == TrainId){
				TrainNo = i;
				break;
			}
		}
		
		int flag1 = 0;
		int flag2 = 0;
		for(int i = 0;i < JourneyList.size();i++){//find if the driver is exist
			if(JourneyList.get(i).JourneyId == TrainList.get(TrainNo).AssignedJourneyId){
				this.JourneyNo = i;
				flag1 = 1;
				break;
			}
		}
		if(flag1 == 1){//根据时间 求最近的下一站时间，对应的站是什么
			
			String RealTime = format.format(Calendar.getInstance().getTime());
			System.out.println("##real time" + RealTime + "##");
			for(int i = 0;i < JourneyList.get(JourneyNo).StopNumber; i++){
				Long DurationTime = format.parse(JourneyList.get(JourneyNo).OutWardArrivingTime[i]).getTime() - format.parse(RealTime).getTime();
				if(DurationTime>0){
					StopNo = i;
					Nextstop = JourneyList.get(JourneyNo).route.StopName[StopNo];
					PlanTime = JourneyList.get(JourneyNo).OutWardArrivingTime[i];
					flag2 = 1;
					break;
				}
			}
			if(StopNo == -1){
				for(int i = 0;i < JourneyList.get(JourneyNo).StopNumber; i++){
					Long DurationTime = format.parse(JourneyList.get(JourneyNo).ReturnArrivingTime[i]).getTime() - format.parse(RealTime).getTime();
					if(DurationTime>0){
						StopNo = JourneyList.get(JourneyNo).StopNumber - i - 1;//??????
						Nextstop = JourneyList.get(JourneyNo).route.StopName[StopNo];
						PlanTime = JourneyList.get(JourneyNo).ReturnArrivingTime[i];
						flag2 = 2;
						break;
					}
				}
			}
			if(flag2 == 1 || flag2 == 2){//outward
				System.out.println("next stop is " + Nextstop);
				System.out.println("The time is " + PlanTime);
			}else{
				System.out.println("No next stop!!");
				TrainList.get(TrainNo).state = true;//停车
			}

		}else{
			
		}
		NextstopPlanTime[0][0] = Nextstop;
		NextstopPlanTime[0][1] = PlanTime;
		return NextstopPlanTime;
	}
	
	public String getNextstop() {
		return Nextstop;
	}

	public String getPlanTime() {
		return PlanTime;
	}
}
