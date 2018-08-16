package yang;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;



public class ScheduleJourney {
	private  ArrayList<Route> RouteList;////////not used in this control class
	private  ArrayList<Journey> JourneyList;
	private int RouteNo;
	private int JourneyNo;
	SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
	ObjectSer objectSer;

	
	//*Test start
	public static void main(String[] args) throws ParseException, ClassNotFoundException, IOException {
		ObjectSer objectSer = new ObjectSer();
		ArrayList<Route> r = new ArrayList<Route>();
		r = objectSer.readRouteArrayList(); 
//		Route r1 = new Route(01,3);
//		r.add(r1);
//		Route r2 = new Route(02,1);
//		r.add(r2);
//		Route r3 = new Route(03,3);
//		r.add(r3);
		ArrayList<Journey> j = new ArrayList<Journey>();
	    j =objectSer.readJourneyArrayList(); 
//		Journey j1= new Journey(01,r1);
//		r.get(0).AssignedJourney.add(j1.JourneyId);
//		j.add(j1);
//		Journey j2= new Journey(02,r1);
//		r.get(0).AssignedJourney.add(j2.JourneyId);
//		System.out.println("!!!"+j2.route.RouteId);
//		j.add(j2);
//		Journey j3= new Journey(03,r3);
//		r.get(2).AssignedJourney.add(j3.JourneyId);// get里是routeNo，add journey
//		j.add(j3);
//		objectSer.writeRouteArrayList(r);
		//
//		ScheduleRoute sc = new ScheduleRoute();
//		sc.scheduleRoute(01, 0, "Kyoto", "01:00:00");
//		sc.scheduleRoute(01, 1, "GuangZhou", "02:00:00");
//		sc.scheduleRoute(01, 2, "Beijing", "03:00:00");
//	    sc.scheduleRoute(02, 0, "Beijing", "07：00：00");
//		sc.scheduleRoute(03, 0, "Tokyo", "04:00:00");
//		sc.scheduleRoute(03, 1, "Beijing", "05:00:00");
//		sc.scheduleRoute(03, 2, "Lijiang", "06:00:00");
//		
//	    
		
//		objectSer.writeJourneyArrayList(j);
//		objectSer.writeRouteArrayList(r);
		//
//		ScheduleJourney s = new ScheduleJourney();
//		s.scheduleJourney(01, "22:00:00","11:11:11");//id, starting time, returning time
//		s.scheduleJourney(02, "11:22:22","00:33:33");//重复分配一个route不对，重复调用scheduleJourney也不对
//		s.scheduleJourney(03, "11:44:44","22:55:55");

//		j = objectSer.readJourneyArrayList(); 
//		r = objectSer.readRouteArrayList();
		
//		for(int n=0;n<j.size();n++){
//			int RouteNo = -1;
//			System.out.println("The RouteId of Journey[" + j.get(n).JourneyId + "] is " + j.get(n).BelongRouteId) ;
//			for(int i = 0;i < r.size();i++){
//				if(r.get(i).RouteId == j.get(n).BelongRouteId){
//					RouteNo = i;
//					break;
//				}
//			}	
//			if(RouteNo != -1){
//				for(int i =0;i<j.get(n).StopNumber;i++){
//					System.out.println("The outward arriving time of stop[" + r.get(RouteNo).StopName[i] + "] "
//						+ "arriving time is " + j.get(n).OutWardArrivingTime[i]);
//				}
//				for(int i =0;i<j.get(n).StopNumber;i++){
//					System.out.println("The return arriving time of stop[" + r.get(RouteNo).StopName[j.get(n).StopNumber - i -1] + "] "
//						+ "arriving time is " + j.get(n).ReturnArrivingTime[i]);
//				}
//			}
//		}
//
//		System.out.println("-----------------End of stop-----------------");
//		System.out.println("");
		
//		ControlStopScreen c = new ControlStopScreen();//写到函数里
//		c.controlStopScreenReturn("stop 1");
		
		ArrayList<Train> rt;
		rt = new ArrayList<Train>();
		rt = objectSer.readTrainArrayList();
		
//		Train t1 = new Train(01);
//		t1.CurrentStateJ = true;//true=valid;false=invalid
//		rt.add(t1);
//		objectSer.writeTrainArrayList(rt);
//		
//		AssignTrain a = new AssignTrain();
//		a.assignTrain(01, 01,j,rt);
//		a.assignTrain(02, 01,j,rt);
		
		ControlOnBoardScreen co = new ControlOnBoardScreen();
	//	co.(rt.get(0));
		
	}
//	Test end*/
	
	public ScheduleJourney() throws ClassNotFoundException, IOException{
		RouteList = new ArrayList<Route>();
		JourneyList = new ArrayList<Journey>();
		objectSer = new ObjectSer();

	}
	
	public ArrayList<Journey> scheduleJourney(ArrayList<Journey> jArrayList,int JourneyId, String StartingTime, String ReturningTime) throws ParseException, ClassNotFoundException, IOException{
		RouteList = objectSer.readRouteArrayList();
		JourneyList = jArrayList;
		
		System.out.println(RouteList.get(0).RouteId);
		System.out.println("-------Now schedule Journey[" + JourneyId + "]-------");
		
		int flag1 = 0;
		for(int i = 0;i < JourneyList.size();i++){
			if(JourneyList.get(i).JourneyId == JourneyId){
				this.JourneyNo = i;
				flag1 = 1;
				break;
			}
		}		
		
		int flag2 = 0;
		for(int i = 0;i < RouteList.size();i++){
			if(RouteList.get(i).RouteId == JourneyList.get(JourneyNo).BelongRouteId){
				this.RouteNo = i;
				flag2 = 1;
				break;
			}
		}	
		
		if(flag1 == 1 && flag2 == 1){
			//calculate time 
			JourneyList.get(JourneyNo).OutWardArrivingTime[0] = StartingTime;
			JourneyList.get(JourneyNo).ReturnArrivingTime[0] = ReturningTime;
			
			for(int i = 0;i < (RouteList.get(RouteNo).StopNumber -1);i++){//从1开始
				if (RouteList.get(RouteNo).Duration[i]!=null) {
					
				
					String[] info = new String[3];
					
					info =	RouteList.get(RouteNo).Duration[i].split(":");
					int hour = Integer.parseInt(info[0]);
					System.out.println(hour);
					int minute = Integer.parseInt(info[1]);
					int sec = Integer.parseInt(info[2]);			
					Date LastAT = format.parse(JourneyList.get(JourneyNo).OutWardArrivingTime[i]);
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(LastAT);
					calendar.add(Calendar.HOUR,hour);
					calendar.add(Calendar.MINUTE,minute);
					calendar.add(Calendar.SECOND,sec);
					String finalTime = format.format(calendar.getTime());
					JourneyList.get(JourneyNo).OutWardArrivingTime[i+1] = finalTime;
				}
			}
			
			int j =1;
			for(int i = (RouteList.get(RouteNo).StopNumber -2);i >= 0; i--){//从1开始
				if (RouteList.get(RouteNo).Duration[i]!=null) {
					
				
					String[] info = RouteList.get(RouteNo).Duration[i].split(":");
					int hour = Integer.parseInt(info[0]);
					int minute = Integer.parseInt(info[1]);
					int sec = Integer.parseInt(info[2]);			
					Date LastAT = format.parse(JourneyList.get(JourneyNo).ReturnArrivingTime[j-1]);
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(LastAT);
					calendar.add(Calendar.HOUR,hour);
					calendar.add(Calendar.MINUTE,minute);
					calendar.add(Calendar.SECOND,sec);
					String finalTime = format.format(calendar.getTime());
					JourneyList.get(JourneyNo).ReturnArrivingTime[j] = finalTime;
					j++;
				}
			}
			
			System.out.println("Assignment Route[" + RouteList.get(RouteNo).RouteId + "] to Journey[" + JourneyId + "]Success!");
		}else if(flag2 == 0){
			System.out.println("Assigned Error! Route Not exist!");//is -1, driver not exist
		}else if(flag1 == 0){
			System.out.println("Assigned Error! Journey Not exist!");//is -1, train not exist
		}
		
		objectSer.writeJourneyArrayList(JourneyList);
		return JourneyList;
	}

}
