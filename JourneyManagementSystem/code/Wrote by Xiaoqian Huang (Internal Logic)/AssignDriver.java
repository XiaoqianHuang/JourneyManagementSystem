package yang;
import java.util.ArrayList;

public class AssignDriver {
	private ArrayList<Driver> DriverList;
	private ArrayList<Train> TrainList ;
	private int DriverNo;
	private int TrainNo;
	
	
	/*Test start
	public static void main(String[] args) {
		
		Driver d1 = new Driver(01);
		d1.CurrentState =true;//true=valid;false=invalid
		DriverList.add(d1);
		Driver d2 = new Driver(02);
		d2.CurrentState =true;
		DriverList.add(d2);
		Driver d3 = new Driver(03);
		d3.CurrentState =false;
		DriverList.add(d3);
		Train t1 = new Train(01);
		t1.CurrentStateD =true;
		TrainList.add(t1);
		Train t2 = new Train(02);//d3 assigned to t2
		t2.CurrentStateD =false;
		t2.AssignedDriverId =03;
		TrainList.add(t2);
		
		AssignDriver a = new AssignDriver();
		a.assignDriver(01, 01);
		a.assignDriver(02, 01);
		a.assignDriver(03, 02);
		int driver1 = a.GetDriverId(01);
		System.out.println("The DriverId of Train[" + "01] is " + driver1);
		int driver2 = a.GetDriverId(02);
		System.out.println("The DriverId of Train[" + "02] is " + driver2);
		int driver3 = a.GetDriverId(03);
		System.out.println("The DriverId of Train[" + "03] is " + driver3);
		System.out.println("-----------------End of getid-----------------");
		System.out.println("");
	}
	/*Test end*/
	
	public AssignDriver(){

		TrainList = new ArrayList<>();
		DriverList = new ArrayList<>();
		//read DriverList and TrainList
		//write DriverList and TrainList
	}
	
	public Object[] assignDriver(int DriverId,int TrainId,ArrayList<Driver> dArrayList, ArrayList<Train> tArrayList){
		TrainList = tArrayList;
		DriverList = dArrayList;
		
		System.out.println("-------Now Assign Driver[" + DriverId + "] to Train[" + TrainId + "]-------");
		
		int flag1 = 0;
		for(int i = 0;i < DriverList.size();i++){//find if the driver is exist
			if(DriverList.get(i).DriverId  == DriverId){
				this.DriverNo = i;
				flag1 = 1;
				break;
			}
		}		
		
		int flag2 = 0;
		for(int i = 0;i < TrainList.size();i++){//find the train id
			if(TrainList.get(i).TrainId  == TrainId){
				this.TrainNo = i;
				flag2 = 1;
				break;
			}
		}	
		
		if(flag1 == 1 && flag2 == 1){
			boolean Valid;
			Valid = this.CheckValid();
			if(Valid == true){
				TrainList.get(TrainNo).AssignedDriverId =DriverId;//assign driver to train
				TrainList.get(TrainNo).CurrentStateD =false;
				DriverList.get(DriverNo).CurrentState =false;
				DriverList.get(DriverNo).AssignedTrainId = TrainId;
				System.out.println("Assignment Driver[" + DriverId + "] to Train[" + TrainId + "]Success!");
			}else{
				System.out.println("Assignment Driver[" + DriverId + "] to Train[" + TrainId + "]Failed.");
			}
		}else if(flag1 == 0){
			System.out.println("Assigned Error! Driver Not exist!");//is -1, driver not exist
		}else if(flag2 == 0){
			System.out.println("Assigned Error! Train Not exist!");//is -1, train not exist
		}
		System.out.println("----------------End of assign-----------------");
		System.out.println("");
		Object[] couple = new Object[2];
		couple[0] = DriverList;
		couple[1] = TrainList;
		return couple;
		
	}
	
	public boolean CheckValid(){
		if((DriverList.get(DriverNo).CurrentState == true)&&(TrainList.get(TrainNo).CurrentStateD == true)){//if both is valid, assign
			return true;
		}
		else{
			if(DriverList.get(DriverNo).CurrentState == false){
				System.out.println("Driver is not available");
			}else if(TrainList.get(TrainNo).CurrentStateD == false){
				System.out.println("Train is not available");
			}
			return false;
		}
	}
	
	public int GetDriverId(int TrainId){//if returned value != -1, we can know train not assigned; if contain the driver id, we can know the driver is assigned; and we can know this train is assigned which driver
		System.out.println("-----------------get driver id----------------");
		int flag = 0;
		for(int i = 0;i < TrainList.size();i++){//find the train id
			if(TrainList.get(i).TrainId  == TrainId){
				this.TrainNo = i;
				flag = 1;
				break;
			}
		}

		if(flag == 1){
			return TrainList.get(TrainNo).AssignedDriverId ;
		}else{
			System.out.println("This train not exist!");//is -1, train not exist
			return -2;
		}

	}
}
