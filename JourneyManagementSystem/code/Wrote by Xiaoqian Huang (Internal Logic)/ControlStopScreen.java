package yang;

import java.io.IOException;
import java.sql.Time;
import java.text.BreakIterator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class ControlStopScreen {
	private String StopName;
	ObjectSer objectSer;
	private ArrayList<Journey> JourneyList;
	private ArrayList<Route> RouteList;
	private int ArriveNo = 0;
	private ArrayList<Journey> ArrvingJourney;
	private String[] ArrivingTime;
	private int[] StopNo;
	SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
	
	public ControlStopScreen(){
//		ArrivingTime = new String[10];
//		StopNo = new int[10];
//		objectSer = new ObjectSer();
//		this.StopName = StopName;
//		JourneyList = new ArrayList<Journey>();
//		RouteList = new ArrayList<Route>();
//		JourneyList = objectSer.readJourneyArrayList();
//		RouteList = objectSer.readRouteArrayList();
//		ArrvingJourney = new ArrayList<Journey>();
//        //System.out.println("size!!"+RouteList.size());
//		int JourneyId = -1;
//		for(int i = 0;i < RouteList.size();i++){//find if the driver is exist
//			for(int j = 0;j < RouteList.get(i).StopNumber;j++){
//				if(RouteList.get(i).StopName[j].equals(StopName)){
//					for(int n=0;n<RouteList.get(i).AssignedJourney.size();n++){
//						JourneyId = RouteList.get(i).AssignedJourney.get(n);
//						ArrvingJourney.add(FindJourney(JourneyId));
//						this.StopNo[ArriveNo] = j;
//						ArriveNo++;// 应与ArrvingJourney的get(i)保持一致！！！
//						System.out.println(ArriveNo);
//					} 	
//				}
//			}
//		}	
//		
//		for(int i = 0;i < ArrvingJourney.size();i++){
//			System.out.println(this.StopNo[i]);
//		}
//		
//		for(int i = 0;i < ArrvingJourney.size();i++){
//			int no = this.StopNo[i];
//			ArrivingTime[i] = ArrvingJourney.get(i).OutWardArrivingTime[no];
//		}
//		
//		for(int i = 0;i < ArrvingJourney.size();i++){
//			System.out.println(ArrivingTime[i]);
//		}
		
		
		//找最近的arriving time
		
//		System.out.println("-------Now show information of stop[" + DriverId + "]-------");
	}
	
	public Journey controlStopScreenOutward(String StopName) throws ClassNotFoundException, IOException, ParseException{
		ArrivingTime = new String[10];
		StopNo = new int[10];
		objectSer = new ObjectSer();
		this.StopName = StopName;
		JourneyList = new ArrayList<Journey>();
		RouteList = new ArrayList<Route>();
		JourneyList = objectSer.readJourneyArrayList();
		RouteList = objectSer.readRouteArrayList();
		ArrvingJourney = new ArrayList<Journey>();

		int JourneyId = -1;
		for(int i = 0;i < RouteList.size();i++){
			for(int j = 0;j < RouteList.get(i).StopNumber;j++){
				if(RouteList.get(i).StopName[j].equals(StopName)){
					for(int n=0;n<RouteList.get(i).AssignedJourney.size();n++){
						JourneyId = RouteList.get(i).AssignedJourney.get(n);
						ArrvingJourney.add(FindJourney(JourneyId));
						this.StopNo[ArriveNo] = j;
						System.out.println("!!!!i:" + i + "j:" + j);
						System.out.println("!!!!Stopname of :" + RouteList.get(i).RouteId + "is " + RouteList.get(i).StopName[j]);
						System.out.println("!!!!arraylist size:" + RouteList.get(i).AssignedJourney.get(0));
						ArriveNo++;// 应与ArrvingJourney的get(i)保持一致！！！
					} 	
				}
			}
		}	
		
		
		
		for(int i = 0;i < ArrvingJourney.size();i++){
			int no = this.StopNo[i];
			ArrivingTime[i] = ArrvingJourney.get(i).OutWardArrivingTime[no];
		}
		String RealTime = format.format(Calendar.getInstance().getTime());
		System.out.println("##real time" + RealTime + "##");
		String FullTime = "24:00:00";
		String ZeroTime = "00:00:00";
		Long MinDurationTime = format.parse(FullTime).getTime() - format.parse(ZeroTime).getTime() ;
		Journey NextJourney = null;
		System.out.println("$$$MinDurationTime" + MinDurationTime);
		for(int i = 0;i < ArrvingJourney.size();i++){
			Long Duration = format.parse(ArrivingTime[i]).getTime() - format.parse(RealTime).getTime();
			System.out.println("$$$" + "[" + (i+1) + "]" +Duration);
			if(Duration > 0 && Duration < MinDurationTime){
				MinDurationTime = Duration;
				NextJourney = ArrvingJourney.get(i);
			}
			System.out.println(ArrivingTime[i]);
		}
		
		
		if(NextJourney != null){
			if (NextJourney.AssignedTrainId <=0) {
				NextJourney =null;
			}
			else {
				
			
				System.out.println("$$$MinDurationTime" + MinDurationTime);
				System.out.println("%%%" + NextJourney.JourneyId);
				for(int i = 0; i<NextJourney.StopNumber;i++){
					System.out.println("****" + NextJourney.OutWardArrivingTime[i]);
				}
			}
		}else{
			System.out.println("No next journey!!");
		}
		
		return NextJourney;
	}
	
	public Journey controlStopScreenReturn(String StopName) throws ClassNotFoundException, IOException, ParseException{
		ArrivingTime = new String[10];
		StopNo = new int[10];
		objectSer = new ObjectSer();
		this.StopName = StopName;
		JourneyList = new ArrayList<Journey>();
		RouteList = new ArrayList<Route>();
		JourneyList = objectSer.readJourneyArrayList();
		RouteList = objectSer.readRouteArrayList();
		ArrvingJourney = new ArrayList<Journey>();
		int JourneyId = -1;
		for(int i = 0;i < RouteList.size();i++){
			for(int j = 0;j < RouteList.get(i).StopNumber;j++){
				if(RouteList.get(i).StopName[j].equals(StopName)){
					for(int n=0;n<RouteList.get(i).AssignedJourney.size();n++){
						JourneyId = RouteList.get(i).AssignedJourney.get(n);
						ArrvingJourney.add(FindJourney(JourneyId));
						this.StopNo[ArriveNo] = j;
						System.out.println("!!!!i:" + i + "j:" + j);
						System.out.println("!!!!Stopname of :" + RouteList.get(i).RouteId + "is " + RouteList.get(i).StopName[j]);
						System.out.println("!!!!arraylist size:" + RouteList.get(i).AssignedJourney.get(0));
						ArriveNo++;// 应与ArrvingJourney的get(i)保持一致！！！
					} 	
				}
			}
		}	
		
		for(int i = 0;i < ArrvingJourney.size();i++){
			int no = this.StopNo[i];
			ArrivingTime[i] = ArrvingJourney.get(i).ReturnArrivingTime[ArrvingJourney.get(i).StopNumber - no - 1];
		}
		

		String RealTime = format.format(Calendar.getInstance().getTime());
		System.out.println("##real time" + RealTime + "##");
		String FullTime = "24:00:00";
		String ZeroTime = "00:00:00";
		Long MinDurationTime = format.parse(FullTime).getTime() - format.parse(ZeroTime).getTime() ;
		Journey NextJourney = null;
		System.out.println("$$$MinDurationTime" + MinDurationTime);
		
		for(int i =0;i<ArrvingJourney.size();i++){
			System.out.println("^^^^" + ArrivingTime[i]);
		}
		
		for(int i = 0;i < ArrvingJourney.size();i++){
			Long Duration = format.parse(ArrivingTime[i]).getTime() - format.parse(RealTime).getTime();
			System.out.println("$$$" + "[" + (i+1) + "]" +Duration);
			if(Duration > 0 && Duration < MinDurationTime){
				MinDurationTime = Duration;
				NextJourney = ArrvingJourney.get(i);
			}
			System.out.println(ArrivingTime[i]);
		}
		
		if(NextJourney != null){
			if (NextJourney.AssignedTrainId <=0) {
				NextJourney =null;
			}
			else {
				
				System.out.println("$$$MinDurationTime" + MinDurationTime);
				System.out.println("%%%" + NextJourney.JourneyId);
				for(int i = 0; i<NextJourney.StopNumber;i++){
					System.out.println("****" + NextJourney.ReturnArrivingTime[i]);
				}
			}
		}else{
			System.out.println("No next journey!!");
			
		}
		
		return NextJourney;
	}
	
	public Journey FindJourney(int JourneyId){

		for(int i = 0;i < JourneyList.size();i++){
			if(JourneyList.get(i).JourneyId == JourneyId){
				return JourneyList.get(i);
			}
		}	
		return null;
	}
}
