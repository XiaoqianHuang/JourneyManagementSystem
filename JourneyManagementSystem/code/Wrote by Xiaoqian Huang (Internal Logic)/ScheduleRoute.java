package yang;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class ScheduleRoute {
	 ArrayList<Route> RouteList;
	 int RouteNo = -1;
	 String[] Stop;
	ObjectSer objectSer;
	
	//*Test start
	public static void main(String[] args) throws ParseException, ClassNotFoundException, IOException {
//		Route r1 = new Route(01,3);
//		RouteList.add(r1);
//		String[] stopname = {"guangzhou","beijing","hebei"};
//		String[] duration = {"21","30"};
		ObjectSer ob = new ObjectSer();
		ArrayList<Route> l1 = new ArrayList<>();
		Route r1 = new Route(1, 3);
		l1.add(r1);
		ob.writeRouteArrayList(l1);
		ScheduleRoute s = new ScheduleRoute();
		s.scheduleRoute(1, 0, "Kyoto", "2:12:00");
		s.scheduleRoute(1, 1, "GuangZhou", "7:45:00");
		s.scheduleRoute(1, 2, "London", "00:15:45");		//route 01 is create, routeid -> journey 
		l1 = ob.readRouteArrayList();
		
		r1 = l1.get(0);
		System.out.println("The RouteId of route is " + r1.RouteId);
		System.out.println("The stop is " + r1.StopName[0] + " " + r1.Duration[0]);
		System.out.println("The stop is " + r1.StopName[1] + " " + r1.Duration[1]);
		System.out.println("The stop is " + r1.StopName[2] + " " + r1.Duration[2]);
		System.out.println("-----------------End of schedule-----------------");
		System.out.println("");
	}
//	Test end*/
	
	public ScheduleRoute() throws ClassNotFoundException, IOException{
		objectSer = new ObjectSer();
		RouteList = new ArrayList<>();

		//read RouteList
//		Route r1 = new Route(01,3);
//		RouteList.add(r1);
//		Route r2 = new Route(02,1);
//		RouteList.add(r2);
	}
	
	public void scheduleRoute(int RouteId, int StopNo, String StopName, String DruationTime) throws ClassNotFoundException, IOException{//stop is from an array in the GUI, stopNo(from 0) is the No. of that array
		RouteList = objectSer.readRouteArrayList();
		System.out.println(RouteList.get(0).RouteId);
		System.out.println("-------Now Schedule Stop[" + StopName + "] to Route[" + RouteId + "]-------");
//		for (int i = 0; i < RouteList.size(); i++) {
//			if (RouteId == RouteList.get(i).getRouteId()) {
//				RouteNo = i;
//				break;
//			}
//		}
		
		int flag1 = 0;
		for(int i = 0;i < RouteList.size();i++){
			if(RouteList.get(i).RouteId == RouteId){
				RouteNo = i;
				flag1 = 1;
				break;
			}
		}	
		RouteList.get(RouteNo).StopName[StopNo] =  StopName;
		RouteList.get(RouteNo).Duration[StopNo] =  DruationTime;
		if(flag1 == 1 && StopNo < RouteList.get(RouteNo).StopNumber){
			RouteList.get(RouteNo).StopName[StopNo] =  StopName;
			RouteList.get(RouteNo).Duration[StopNo] =  DruationTime;
		}else if(StopNo >= RouteList.get(RouteNo).StopNumber){
			System.out.println("Error! Stop exceed!");
		}else
		{
			System.out.println("Assigned Error! Route Not exist!");
		}
		System.out.println("----------------End of shcedule-----------------");
		//write RouteList
		objectSer.writeRouteArrayList(RouteList);
		
	}
	
//	public void scheduleRoute(int RouteId, int StopNo, String StopName, String DruationTime){//stop is from an array in the GUI, stopNo(from 0) is the No. of that array
//		System.out.println("-------Now Schedule Stop[" + StopName + "] to Route[" + RouteId + "]-------");
////		for (int i = 0; i < RouteList.size(); i++) {
////			if (RouteId == RouteList.get(i).getRouteId()) {
////				RouteNo = i;
////				break;
////			}
////		}
//		
//		int flag1 = 0;
//		for(int i = 0;i < RouteList.size();i++){
//			if(RouteList.get(i).getRouteId() == RouteId){
//				RouteNo = i;
//				flag1 = 1;
//				break;
//			}
//		}	
//		RouteList.get(RouteNo).setStopName(StopNo, StopName);
//		RouteList.get(RouteNo).SetDuration(StopNo, DruationTime);
//		if(flag1 == 1 && StopNo < RouteList.get(RouteNo).getStopNumber()){
//			RouteList.get(RouteNo).setStopName(StopNo,StopName);
//			RouteList.get(RouteNo).SetDuration(StopNo,DruationTime);
//		}else if(StopNo >= RouteList.get(RouteNo).getStopNumber()){
//			System.out.println("Error! Stop exceed!");
//		}else
//		{
//			System.out.println("Assigned Error! Route Not exist!");
//		}
//		System.out.println("----------------End of shcedule-----------------");
//		//write RouteList
//	}
	
	public void ArrangeStop(int StopNo, String StopName){
		Stop[StopNo] = StopName;
	}
	
	public String GetStopName(int StopNo){//loop to get the list
		return Stop[StopNo];
	}

}
