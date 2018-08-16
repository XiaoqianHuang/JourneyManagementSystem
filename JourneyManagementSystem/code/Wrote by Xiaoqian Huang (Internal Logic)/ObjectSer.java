package yang;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.ArrayList;



import java.io.ObjectInputStream;

public class ObjectSer {

		public void writeSynMember(Object o) throws IOException,ClassNotFoundException{
	        {
	   FileOutputStream fo = new FileOutputStream("SynMember.txt");
	   ObjectOutputStream so = new ObjectOutputStream(fo);
	   try {
	       so.writeObject(o);
	       so.close();
	   } catch (IOException e)
	   {
	       System.out.println(e);
	   }
	//   o = null;
	       }
	}
	   
	public void writeRouteArrayList(Object o) throws IOException,ClassNotFoundException{
	   {
	FileOutputStream fo = new FileOutputStream("RouteArrayList.txt");
	ObjectOutputStream so = new ObjectOutputStream(fo);
	try {
	  so.writeObject(o);
	  so.close();
	} catch (IOException e)
	{
	  System.out.println(e);
	}
	//o = null;
	  }
	}
	public void writeJourneyArrayList(Object o) throws IOException,ClassNotFoundException{
	   {
	FileOutputStream fo = new FileOutputStream("JourneyArrayList.txt");
	ObjectOutputStream so = new ObjectOutputStream(fo);
	try {
	  so.writeObject(o);
	  so.close();
	} catch (IOException e)
	{
	  System.out.println(e);
	}
	//o = null;
	  }
	}
	public void writeDriverArrayList(Object o) throws IOException,ClassNotFoundException{
	   {
	FileOutputStream fo = new FileOutputStream("DriverArrayList.txt");
	ObjectOutputStream so = new ObjectOutputStream(fo);
	try {
	  so.writeObject(o);
	  so.close();
	} catch (IOException e)
	{
	  System.out.println(e);
	}
	//o = null;
	  }
	}
	
	public void writeTrainArrayList(Object o) throws IOException,ClassNotFoundException{
	   {
	FileOutputStream fo = new FileOutputStream("TrainArrayList.txt");
	ObjectOutputStream so = new ObjectOutputStream(fo);
	try {
	  so.writeObject(o);
	  so.close();
	} catch (IOException e)
	{
	  System.out.println(e);
	}
	//o = null;
	  }
	}

	 public ArrayList readSynMember() throws IOException,ClassNotFoundException{
	        
	    	ArrayList<synMember> members=new ArrayList<synMember>();
	    	FileInputStream fi = new FileInputStream("SynMember.txt");
	    	 if (fi.available()>0) {
	        ObjectInputStream si = new ObjectInputStream(fi);
	       
				
			
	        try {
	        	members = (ArrayList<synMember>) si.readObject();
	            si.close();
	        } catch (IOException e)
	        {
	        	System.out.println("null");
	            System.out.println(e);
	        }
	        return (ArrayList) members;
	    	 }
	       else {
			System.out.println("null");
			return null;
		}

	}
		public ArrayList readRouteArrayList() throws IOException,ClassNotFoundException{
		        
		    ArrayList<Route> routeArrayList=new ArrayList<Route>();
		    FileInputStream fi = new FileInputStream("RouteArrayList.txt");
		   
		    if (fi.available()>0) {
		        ObjectInputStream si = new ObjectInputStream(fi);
		        try {
		        	routeArrayList = (ArrayList<Route>) si.readObject();
		            si.close();
		        } catch (IOException e)
		        {
		            System.out.println(e);
		        }
		   	}
		    else {
		    	System.out.println("null");
		    }
		    return (ArrayList) routeArrayList;
		}

		public ArrayList readJourneyArrayList() throws IOException,ClassNotFoundException{
	        
	    	ArrayList<Journey> journeyArrayList=new ArrayList<Journey>();
	    	FileInputStream fi = new FileInputStream("JourneyArrayList.txt");
	    	if (fi.available()>0) {
	    		ObjectInputStream si = new ObjectInputStream(fi);
	    		try {
	    			journeyArrayList = (ArrayList<Journey>) si.readObject();
	    			si.close();
	    		} catch (IOException e)
	    		{
	    			System.out.println(e);
	    		}
	    	}else {
		    	System.out.println("null");
		    }
	    		return (ArrayList) journeyArrayList;
	}
		
		public ArrayList readDriverArrayList() throws IOException,ClassNotFoundException{
	        
	    	ArrayList<Driver> DriverArrayList=new ArrayList<Driver>();
	    	FileInputStream fi = new FileInputStream("DriverArrayList.txt");
	    	if (fi.available()>0) {
	    		ObjectInputStream si = new ObjectInputStream(fi);
	    		try {
	    			DriverArrayList = (ArrayList<Driver>) si.readObject();
	    			si.close();
	    		} catch (IOException e)
	    		{
	    			System.out.println(e);
	    		}
	    	}else {
	    	    	System.out.println("null");
	    	    }
	    		return (ArrayList) DriverArrayList;
	    	}
		
		public ArrayList readTrainArrayList() throws IOException,ClassNotFoundException{
	        
	    	ArrayList<Train> TrainArrayList=new ArrayList<Train>();
	    	FileInputStream fi = new FileInputStream("TrainArrayList.txt");
	    	if (fi.available()>0) {
	    		ObjectInputStream si = new ObjectInputStream(fi);
	    		try {
	    			TrainArrayList = (ArrayList<Train>) si.readObject();
	    			si.close();
	    		} catch (IOException e)
	    		{
	    			System.out.println(e);
	    		}
	    	}else {
	    	    	System.out.println("null");
	    	    }
	    		return (ArrayList) TrainArrayList;
		}

	

    public static void main(String[] args) throws ClassNotFoundException, IOException, ParseException {
//    	ObjectSer objectSer = new ObjectSer();
//    	ArrayList<synMember> members=new ArrayList<synMember>();
//		for (int i = 0; i < 10; i++) {
//			synMember sm = new synMember(""+i, "10", "zoo", "21", "5min");
//			members.add(sm);
//		}
//		objectSer.write(members);
//		
//    	ArrayList<Route> routeArrayList=new ArrayList<Route>();
//		for (int i = 0; i < 10; i++) {
//			String[] stopname = {"guangzhou","beijing","hebei"};
//			String[] duration = {"21","30"};
//			Route route = new Route(i, 3,stopname, duration);
//			routeArrayList.add(route);
//		}
//		objectSer.writeRouteArrayList(routeArrayList);
//		ArrayList<Route> r1 = new ArrayList<Route>();
//		r1 = objectSer.readRouteArrayList();
//		for (int i = 0; i < r1.size(); i++) {
//			System.out.println("routeId"+r1.get(i).RouteId);
//			System.out.println("stopno"+r1.get(i).StopNumber);
//			for (int j = 0; j < r1.get(i).StopNumber; j++) {
//				System.out.println("stop"+j+r1.get(i).StopName[j]);
//				
//			}
//		}
//		String[] stopname = {"guangzhou","beijing","hebei"};
//		String[] duration = {"21","30"};
//		Route route = new Route(10, 3,stopname, duration);
//		routeArrayList.add(route);
//		objectSer.writeRouteArrayList(routeArrayList);
//		
//		r1 = objectSer.readRouteArrayList();
//		for (int i = 0; i < r1.size(); i++) {
//			System.out.println("routeId"+r1.get(i).RouteId);
//			System.out.println("stopno"+r1.get(i).StopNumber);
//			for (int j = 0; j < r1.get(i).StopNumber; j++) {
//				System.out.println("stop"+j+r1.get(i).StopName[j]);
//				
//			}
//		}
//		ArrayList<Journey> j = new ArrayList<Journey>();
//		for (int i = 0; i < r1.size(); i++) {
//			Journey jj = new Journey(i, r1.get(i));
//			j.add(jj);
//		}
//		objectSer.writeJourneyArrayList(j);
//		
//		j = objectSer.readJourneyArrayList();
//		for (int i = 0; i < j.size(); i++) {
//			System.out.println("journeyid:"+j.get(i).JourneyId);
//			System.out.println("belong to route:"+j.get(i).BelongRouteId);
//			System.out.println("before write,time:"+j.get(i).OutWardArrivingTime[0]);
//			j.get(i).OutWardArrivingTime[0] = "21:00:00";
//			objectSer.writeJourneyArrayList(j);
//			System.out.println("after write,time:"+j.get(i).OutWardArrivingTime[0]);
//		}
////		
////    	ObjectSer objectSer = new ObjectSer();
//    	ArrayList<Train> trainList = new ArrayList<>();
//    	trainList = objectSer.readTrainArrayList();
//    	Train t1 = new Train(1);
//    	Train t2 = new Train(2);
//    	trainList.add(t1);
//    	trainList.add(t2);
//    	objectSer.writeTrainArrayList(trainList);
//    	for (int i = 0; i < trainList.size(); i++) {
//    		System.out.println(trainList.get(i).TrainId);
//		}
////    	ObjectSer objectSer = new ObjectSer();
//    	ArrayList<Driver> driverList = new ArrayList<>();
//    	driverList = objectSer.readDriverArrayList();
//    	Driver d1 = new Driver(1);
//    	Driver d2 = new Driver(2);
//    	driverList.add(d1);
//    	driverList.add(d2);
//    	objectSer.writeDriverArrayList(driverList);
//    	for (int i = 0; i < driverList.size(); i++) {
//    		System.out.println(driverList.get(i).DriverId);
//		}
    	ObjectSer objectSer = new ObjectSer();
    	ArrayList<Route> rArrayList = new ArrayList<>();
    	rArrayList = objectSer.readRouteArrayList();
    	for (int i = 0; i < rArrayList.size(); i++) {
			System.out.println("id"+rArrayList.get(i).RouteId);
			for (int j = 0; j < rArrayList.get(i).StopNumber; j++) {
				System.out.println("stop:"+"stop"+rArrayList.get(i).StopName[j]+"time"+"stop"+rArrayList.get(i).Duration[j]);
			}
		}
	}
}