package yang;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import yang.AssignTrainGUI.Journeyhandler;
import yang.AssignTrainGUI.Trainhandler;
import yang.InformationGUI.addDriver;

import javax.swing.*;

public class AssignDriverGUI {
	JFrame asndFrame;
	JPanel panelContainer,left,right,rightc,rights;
	JLabel lb_train,lb_driver;
	JScrollPane jcp;
	ArrayList<JButton> bt_train,bt_driver;
	JButton bt_AD_back;
	int cj=-1;
	int driver_num,train_num;
	ObjectSer objectSer;
	ArrayList<Driver> driverList;
	ArrayList<Train> trainList;
	AssignDriver a;
	public static void main(String args[]) throws ClassNotFoundException, IOException{
		AssignDriverGUI ad = new AssignDriverGUI();
		
	}
	public AssignDriverGUI() throws ClassNotFoundException, IOException {
		a = new AssignDriver();
		objectSer = new ObjectSer();
		driverList = new ArrayList<Driver>();
		trainList = new ArrayList<Train>();
		driverList = objectSer.readDriverArrayList();
		trainList = objectSer.readTrainArrayList();
		bt_driver = new ArrayList<>();
		bt_train = new ArrayList<>();
		this.train_num = trainList.size();
		this.driver_num = driverList.size();
		asndFrame = new JFrame("Assign Driver to Train");
		
		panelContainer = new JPanel();
		panelContainer.setLayout(new GridBagLayout());
		/**
		 * 左面版
		 */
		left = new JPanel();
		left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
		jcp = new JScrollPane(left);
		lb_train = new JLabel("Train");
		left.add(lb_train);
		for (int i = 0; i < train_num; i++){
			JButton bt_t = new JButton("Train"+trainList.get(i).TrainId);
			if (trainList.get(i).CurrentStateD == false) {
//				bt_t.setForeground(Color.BLUE);//选择状态设为蓝色
				bt_t.setText("Train"+trainList.get(i).TrainId+"->");
			}
			bt_t.setName(""+trainList.get(i).TrainId);//名字是journeyId
			bt_train.add(bt_t);
			left.add(bt_t);
			bt_t.addActionListener(new Trainhandler(trainList.get(i).TrainId,bt_t));
		}
		/**
		 * 右面板
		 */
		right = new JPanel();
		right.setLayout(new BorderLayout());
		lb_driver = new JLabel("DRIVER",JLabel.CENTER);
		right.add(BorderLayout.NORTH,lb_driver);
		rightc = new JPanel();
		rightc.setLayout(new GridLayout(5, 2, 25, 10));
		for (int i = 0; i < driver_num; i++){
			JButton bt_d = new JButton("Driver"+driverList.get(i).DriverId);
			if (driverList.get(i).CurrentState == false) {
				bt_d.setEnabled(false);
				bt_d.setText("Driver"+driverList.get(i).DriverId+"->Train"+driverList.get(i).AssignedTrainId);
			}
			bt_d.setName(""+driverList.get(i).DriverId);//名字是id
			rightc.add(bt_d);
			bt_driver.add(bt_d);
			bt_d.addActionListener(new Driverhandler(driverList.get(i).DriverId,bt_d));
		}
		right.add(BorderLayout.CENTER,rightc);
		rights = new JPanel();
		rights.setLayout(new BorderLayout());
		bt_AD_back = new JButton("back");
		bt_AD_back.addActionListener(new AD_back());
		rights.add(BorderLayout.LINE_END,bt_AD_back);
		right.add(BorderLayout.SOUTH,rights);
		
		/**
		 * 左右面板大小安排
		 */
		GridBagConstraints c1 = new GridBagConstraints();
		c1.gridx = 0;
		c1.gridy = 0;
		c1.weightx = 3.0;
		c1.weighty = 1.0;
		c1.fill = GridBagConstraints.BOTH;
		panelContainer.add(jcp, c1);

		GridBagConstraints c2 = new GridBagConstraints();
		c2.gridx = 6;
		c2.gridy = 0;
		c2.weightx = 10.0;
		c2.weighty = 1.0;
		c2.fill = GridBagConstraints.BOTH;
		panelContainer.add(right, c2);
		
		asndFrame.getContentPane().add(panelContainer);
		asndFrame.setSize(1000, 300);
		asndFrame.setLocationRelativeTo(null);
		asndFrame.setVisible(true);
		asndFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	/**
	 * Train按钮
	 * @author dabaiji
	 *
	 */
	class Trainhandler implements ActionListener{
		int currentTrain;
		JButton jb;
		
		public Trainhandler(int id , JButton jb) {
			this.currentTrain = id;
			this.jb = jb;
		}
		public void actionPerformed(ActionEvent e) {
			cj = currentTrain;
			int trainNo = findTrain_AD(currentTrain);
			for(int i = 0; i < bt_train.size(); i++){
				if(bt_train.get(i).equals(jb)){
					jb.setForeground(Color.BLUE);//选择按钮设为蓝色
				}
				else
					bt_train.get(i).setForeground(Color.BLACK);//把未选择的按钮设为黑色
				
			}
			
			if (trainList.get(trainNo).CurrentStateD == false){//如果是分配过得,再选一次即取消
				
					int driverId = trainList.get(trainNo).AssignedDriverId;
					System.out.println("jin"+driverId);
					JButton assignedDriver = findBtDriver_AD(driverId);
					System.out.println(assignedDriver.getName());
					int driverNo = findDriver_AD(driverId);
					System.out.println(driverNo);
					System.out.println("取消分配!");
					jb.setText("Train"+currentTrain);//把选择的按钮的->去掉
					trainList.get(trainNo).CurrentStateD = true;
					trainList.get(trainNo).AssignedDriverId = -1;
					assignedDriver.setEnabled(true);
					assignedDriver.setText("Driver"+driverId);
					assignedDriver.setForeground(Color.BLACK);
					driverList.get(driverNo).CurrentState = true;
					driverList.get(driverNo).AssignedTrainId = -1;

			}
			
			//选中未选中的Train
			else{
				System.out.println("选中Train!");
				
			
				for(int i = 0; i<train_num; i++){
					System.out.print(i+"->"+trainList.get(i).AssignedDriverId+"     ");
				}
			}
		}
	}
	
	/**
	 * Driver按钮
	 * @author dabaiji
	 *
	 */
	class Driverhandler implements ActionListener{
		int assignedDriver;
		JButton jb;
		public Driverhandler(int id, JButton jb ) {
			this.assignedDriver = id;
			this.jb = jb;
		}
		public void actionPerformed(ActionEvent e) {
			if(cj>=0){
				int driverNo = findDriver_AD(assignedDriver);
				if(driverList.get(driverNo).CurrentState == true){//火车未分配
					System.out.println("分配司机!");
					jb.setForeground(Color.BLUE);
					jb.setText("Driver"+assignedDriver+"->Train"+cj);
					//分配中...
					Object[] objects = new Object[2];
					objects = a.assignDriver(assignedDriver, cj, driverList, trainList);
					driverList = (ArrayList<Driver>) objects[0];
					trainList = (ArrayList<Train>) objects[1];
					jb.setEnabled(false);
					JButton jButton = findBtTrain_AD(cj);
					System.out.println(jButton.getName());
					jButton.setText("Train"+cj+"->");
					jButton.setForeground(Color.BLACK);
					cj = -1;
				}
				else{
					System.out.println("interesting");
					for(int i = 0; i<driver_num; i++)
					System.out.print(i+"->"+driverList.get(i).AssignedTrainId+"     ");
				}
			}
			else{
				System.out.println("未选择Train");
				for(int i = 0; i<driver_num; i++)
					System.out.print(i+"->"+driverList.get(i).AssignedTrainId+"     ");
			}
			}
		}

	
	class AD_back implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			Object[] options ={ "Back to assign" , "Confirm and exit"};  
			int m = JOptionPane.showOptionDialog(null, "Confirm all changes？",
					"Warning",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);  
			System.out.print(m);
			if (m == 1) {//comfirm and exit
				
				try {
					for (int i = 0; i < train_num; i++) {
						System.out.println("Train"+trainList.get(i).TrainId+"->Driver:"+trainList.get(i).AssignedDriverId);
					}
					
					objectSer.writeDriverArrayList(driverList);
					objectSer.writeTrainArrayList(trainList);
				} catch (ClassNotFoundException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				asndFrame.dispose();
				AssignGUI a = new AssignGUI();
			}
		}
	}
	public int findDriver_AD(int id){//根据journeyId找到Journey
		int DriverNo = -1;
		for (int i = 0; i < driver_num; i++) {
			if (driverList.get(i).DriverId == id) {
				DriverNo = i;
				break;
			}
		}
		return DriverNo;
	}
	public int findTrain_AD(int id){//根据trainId找到Train
		int TrainNo = -1;
		for (int i = 0; i < train_num; i++) {
			if (trainList.get(i).TrainId == id) {
				TrainNo = i;
				break;
			}
		}
		return TrainNo;
	}
	public JButton findBtTrain_AD(int id){//根据trainId找到button
		JButton btTrain = null;
		for (int i = 0; i < bt_train.size(); i++) {
			if (bt_train.get(i).getName().equals(""+id)) {
				btTrain = bt_train.get(i);
			}
		}
		return btTrain;
	}
	public JButton findBtDriver_AD(int id){//根据journeyId找到button
		JButton btDriver = null;
		for (int i = 0; i < bt_driver.size(); i++) {
			System.out.println(bt_driver.get(i).getName());
			if (bt_driver.get(i).getName().equals(""+id)) {
				btDriver = bt_driver.get(i);
			}
		}
		return btDriver;
	}
	
	
	

	}
	