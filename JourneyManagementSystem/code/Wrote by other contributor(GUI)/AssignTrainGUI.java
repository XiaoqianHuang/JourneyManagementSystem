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
import javax.swing.*;

public class AssignTrainGUI {
	JFrame asntFrame;
	JPanel panelContainer,left,right,rightc,rights;
	JLabel lb_journey,lb_train;
	JScrollPane jcp;
	JButton bt_AT_back;
	ArrayList<JButton> bt_journey,bt_train;
	int cj=-1;
	int train_num,journey_num;
	ObjectSer objectSer;
	ArrayList<Journey> journeyList;
	ArrayList<Train> trainList;
	ArrayList<Route> routeList;
	AssignTrain a;
	public AssignTrainGUI() throws ClassNotFoundException, IOException {
		a = new AssignTrain();
		objectSer = new ObjectSer();
		journeyList = new ArrayList<Journey>();
		trainList = new ArrayList<Train>();
		routeList = new ArrayList<>();
		routeList = objectSer.readRouteArrayList();
		journeyList = objectSer.readJourneyArrayList();
		trainList = objectSer.readTrainArrayList();
		bt_journey = new ArrayList<>();
		bt_train = new ArrayList<>();
		this.train_num = trainList.size();
		this.journey_num = journeyList.size();
//		couple = new int[journey_num][train_num];
//		for(int i = 0; i < journey_num; i++){
//			for (int j = 0; j < train_num; j++) {
//				couple[i][j] = -1;
//			}
//			
//		}
		asntFrame = new JFrame("Assign Train to Journey");
		panelContainer = new JPanel();
		panelContainer.setLayout(new GridBagLayout());

		/**
		 * 左面版
		 */
		left = new JPanel();
		left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
		jcp = new JScrollPane(left);
		lb_journey = new JLabel("Journey");
		left.add(lb_journey);
		
		for (int i = 0; i < journey_num; i++){
			JButton bt_j = new JButton("Journey"+journeyList.get(i).JourneyId);
			if (journeyList.get(i).CurrentState == false) {
//				bt_j.setForeground(Color.BLUE);//选择状态设为蓝色
				bt_j.setText("Journey"+journeyList.get(i).JourneyId+"->");
			}
			bt_j.setName(""+journeyList.get(i).JourneyId);//名字是journeyId
			bt_journey.add(bt_j);
			left.add(bt_j);
			bt_j.addActionListener(new Journeyhandler(journeyList.get(i).JourneyId,bt_j));
		}
		
		/**
		 * 右面板
		 */
		right = new JPanel();
		right.setLayout(new BorderLayout());
		lb_train = new JLabel("Train",JLabel.CENTER);
		right.add(BorderLayout.NORTH,lb_train);
		rightc = new JPanel();
		rightc.setLayout(new GridLayout(5, 2, 25, 10));
		
		for (int i = 0; i < train_num; i++){
			JButton bt_t = new JButton("Train"+trainList.get(i).TrainId);
			if (trainList.get(i).CurrentStateJ == false) {
				bt_t.setEnabled(false);
				bt_t.setText("Train"+trainList.get(i).TrainId+"->Journey"+trainList.get(i).AssignedJourneyId);
			}
			bt_t.setName(""+trainList.get(i).TrainId);//名字是id
			rightc.add(bt_t);
			bt_train.add(bt_t);
			bt_t.addActionListener(new Trainhandler(trainList.get(i).TrainId,bt_t));
		}
		right.add(BorderLayout.CENTER,rightc);
		rights = new JPanel();
		rights.setLayout(new BorderLayout());
		bt_AT_back = new JButton("back");
		bt_AT_back.addActionListener(new AT_back());
		rights.add(BorderLayout.LINE_END,bt_AT_back);
		right.add(BorderLayout.SOUTH,rights);
		
		/**
		 * 两个面板大小安排
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
		
		asntFrame.getContentPane().add(panelContainer);
		asntFrame.setSize(1000, 300);
		asntFrame.setLocationRelativeTo(null);
		asntFrame.setVisible(true);
		asntFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	}
	public static void main(String args[]) throws ClassNotFoundException, IOException{
		AssignTrainGUI at = new AssignTrainGUI();
		
	}
	
	/**
	 * Journey按钮
	 * @author dabaiji
	 *
	 */
	class Journeyhandler implements ActionListener{
		int currentJourney;
		JButton jb;
		
		public Journeyhandler(int id , JButton jb) {
			this.currentJourney = id;//journeyId
			this.jb = jb;//this button
		}
		public void actionPerformed(ActionEvent e) {
			cj = currentJourney;//分配中!
			
			int journeyNo = findJourney(currentJourney);
			
			for(int i = 0; i < bt_journey.size(); i++){
				if(bt_journey.get(i).equals(jb)){
					jb.setForeground(Color.BLUE);//选择按钮设为蓝色

				}
				else
					bt_journey.get(i).setForeground(Color.BLACK);//把未选择的按钮设为黑色
				
			}
			
			if (journeyList.get(journeyNo).CurrentState == false){//如果是分配过得,再选一次即取消
					int trainId = journeyList.get(journeyNo).AssignedTrainId;
					JButton assignedTrain = findBtTrain_AT(trainId);
					int trainNo = findTrain_AT(trainId);
					System.out.println("取消分配!");
					jb.setText("Journey"+currentJourney);//把选择的按钮的->去掉
					journeyList.get(journeyNo).CurrentState = true;
					journeyList.get(journeyNo).AssignedTrainId = -1;
					assignedTrain.setEnabled(true);
					assignedTrain.setText("Train"+trainId);
					assignedTrain.setForeground(Color.BLACK);
					trainList.get(trainNo).CurrentStateJ = true;
					trainList.get(trainNo).AssignedJourneyId = -1;
					
//				for(int i = 0; i<couple.length; i++){
//					System.out.print(i+"->"+couple[i]+"     ");
//				}
			}
			
			//如果是未分配过得
			else{
				System.out.println("选中Journey!");
				for(int i = 0; i<train_num; i++){
					System.out.print(i+"->"+journeyList.get(i).AssignedTrainId+"     ");
				}
			}
		}
	}
	
	//Train按钮
	class Trainhandler implements ActionListener{
		int assignedTrain;
		JButton jb;
		public Trainhandler(int id, JButton jb ) {
			this.assignedTrain = id;//train id
			this.jb = jb;
		}
		public void actionPerformed(ActionEvent e) {
			if(cj>=0){//有选中的journey
				int trainNo = findTrain_AT(assignedTrain);
				if(trainList.get(trainNo).CurrentStateJ == true){//火车未分配
					System.out.println("分配火车!");
					jb.setForeground(Color.BLUE);
					jb.setText("Train"+assignedTrain+"->Journey"+cj);
					try {
						//分配中...
						Object[] objects = new Object[2];
						objects = a.assignTrain(assignedTrain, cj,journeyList,trainList);
						journeyList = (ArrayList<Journey>) objects[0];
						trainList = (ArrayList<Train>) objects[1];
					} catch (ClassNotFoundException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					jb.setEnabled(false);
					JButton jButton = findBtJourney(cj);

					jButton.setText("Journey"+cj+"->");
					jButton.setForeground(Color.BLACK);
					cj = -1;
				}
				else{
					System.out.println("interesting");
					for(int i = 0; i<train_num; i++)
					System.out.print(i+"->"+trainList.get(i).TrainId+"     ");
				}
			}
			else{
				System.out.println("未选择Journey");
				for(int i = 0; i<train_num; i++)
				System.out.print(i+"->"+trainList.get(i).TrainId+"     ");
			}
			}
		}

	class AT_back implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			Object[] options ={ "Back to assign" , "Confirm and exit"};  
			int m = JOptionPane.showOptionDialog(null, "Confirm all changes？",
					"Warning",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);  
			System.out.print(m);
			if (m == 1) {//comfirm and exit
				try {
					for (int i = 0; i < journey_num; i++) {
						System.out.println("journey"+journeyList.get(i).JourneyId+"->train:"+journeyList.get(i).AssignedTrainId);
					}
					for (int i = 0; i < trainList.size(); i++) {
						trainList.get(i).speed = findSpeed(findJourney(trainList.get(i).TrainId));
						System.out.println(trainList.get(i).speed);
					}
					objectSer.writeJourneyArrayList(journeyList);
					objectSer.writeTrainArrayList(trainList);
				} catch (ClassNotFoundException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				asntFrame.dispose();
				AssignGUI a = new AssignGUI();
				
			}
		}

	}
	public int findJourney(int id){//根据journeyId找到Journey index
		int JourneyNo = -1;
		for (int i = 0; i < journey_num; i++) {
			if (journeyList.get(i).JourneyId == id) {
				JourneyNo = i;
				break;
			}
		}
		return JourneyNo;
	}
	public int findTrain_AT(int id){//根据trainId找到Train
		int TrainNo = -1;
		for (int i = 0; i < train_num; i++) {
			if (trainList.get(i).TrainId == id) {
				TrainNo = i;
				break;
			}
		}
		return TrainNo;
	}
	public int findSpeed(int jindex){//根据trainId找到Train
		int speed = -1;
		for (int i = 0; i < routeList.size(); i++) {
			if (routeList.get(i).RouteId == journeyList.get(jindex).BelongRouteId) {
				speed = routeList.get(i).defaultSpeed;
				break;
			}
		}
		return speed;
	}
	public JButton findBtTrain_AT(int id){//根据trainId找到button
		JButton btTrain = null;
		for (int i = 0; i < bt_train.size(); i++) {
			if (bt_train.get(i).getName().equals(""+id)) {
				btTrain = bt_train.get(i);
			}
		}
		return btTrain;
	}
	public JButton findBtJourney(int id){//根据journeyId找到button
		JButton btJourney = null;
		for (int i = 0; i < bt_journey.size(); i++) {
			if (bt_journey.get(i).getName().equals(""+id)) {
				btJourney = bt_journey.get(i);
			}
		}
		return btJourney;
	}
	}
