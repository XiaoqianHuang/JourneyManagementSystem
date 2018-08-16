package yang;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;


public class OnBoardGUI {

	private JButton jb;
	private JButton[] train;
	private JPanel panelContainer,left,right,bp;
	private JFrame onBoardFrame;
	private JLabel latetime;
	//...

	
	private DefaultTableModel modelT;
	private JTable timetable;
	private JScrollPane jsp1,jsp2;
	private int totaltrain;//有几个火车在外面跑
	private int stopno[];//每个火车的时间表上有几个站
	SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
	ArrayList<Train> trainList;
	ArrayList<Journey> journeyList;
	ArrayList<Route> routeList;
	ObjectSer objectSer;
	
	public OnBoardGUI() throws ClassNotFoundException, IOException {//{2,3,4}->三辆火车,第一辆途径两个站
		trainList = new ArrayList<>();
		journeyList = new ArrayList<>();
		routeList = new ArrayList<>();
		objectSer = new ObjectSer();
		trainList = objectSer.readTrainArrayList();
		journeyList = objectSer.readJourneyArrayList();
		routeList = objectSer.readRouteArrayList();
		totaltrain = trainList.size();
		onBoardFrame = new JFrame("On board screen");
		panelContainer = new JPanel();
		panelContainer.setLayout(new GridBagLayout());


		/**
		 * 左面版
		 */
		left = new JPanel();
		jsp1 = new JScrollPane(left);
		
		left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
		left.setBorder(new TitledBorder("TRAIN"));
	
		train = new JButton[totaltrain];
		for (int i = 0; i<totaltrain; i++){
			train[i] = new JButton("Train "+trainList.get(i).TrainId);//默认ID = No
			train[i].addActionListener(new displayInfo(trainList.get(i).TrainId));
			left.add(train[i]);
		}
		
	

		String[] columnNames = {"Stop","Time"};
		modelT = new DefaultTableModel(null,columnNames) {
			
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		//表格初始化
		timetable = new JTable(modelT);
		//设置表格排序器
		TableRowSorter tableRowSorterT = new TableRowSorter(modelT);  
		timetable.setRowSorter(tableRowSorterT);
		
		//设置单元格渲染器
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
		tcr.setHorizontalAlignment(SwingConstants.CENTER);//表格数据居中
		for(int i = 0; i < columnNames.length; i++){
			timetable.getColumn(columnNames[i]).setCellRenderer(tcr);
		}
		//设置表头样式
		JTableHeader tableH = timetable.getTableHeader();

		
		//设置表格属性,为按钮列添加渲染和编辑器
		timetable.setPreferredSize(null);
		timetable.setRowSelectionAllowed(true);
		timetable.setRowHeight(50);
		timetable.setFillsViewportHeight(false); 


		jsp2 = new JScrollPane(timetable);//为表格加入滚动条
		jsp2.setPreferredSize(new Dimension(400, 300));
		
		/**
		 * 右面板
		 */
		right = new JPanel();
		right.setLayout(new BorderLayout());
		jb = new JButton("back");
		jb.addActionListener(new back());
		bp = new JPanel();
		bp.add(jb,BorderLayout.CENTER);
		latetime = new JLabel("Late time: ",JLabel.CENTER);
		right.add(latetime, BorderLayout.NORTH);
		right.add(bp,BorderLayout.SOUTH);
		right.add(jsp2, BorderLayout.CENTER);
		
		/**
		 * 安排大小
		 */
		GridBagConstraints c1 = new GridBagConstraints();
		c1.gridx = 0;
		c1.gridy = 0;
		c1.weightx = 2.0;
		c1.weighty = 1.0;
		c1.fill = GridBagConstraints.BOTH;
		panelContainer.add(jsp1, c1);
		
		GridBagConstraints c2 = new GridBagConstraints();
		c2.gridx = 2;
		c2.gridy = 0;
		c2.weightx = 7.0;
		c2.weighty = 1.0;
		c2.fill = GridBagConstraints.BOTH;
		panelContainer.add(right, c2);
		
		
		onBoardFrame.add(panelContainer);
		onBoardFrame.setSize(600, 300);
		onBoardFrame.setLocationRelativeTo(null);
		onBoardFrame.setVisible(true);
		onBoardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) throws ClassNotFoundException, IOException {
		
		OnBoardGUI b = new OnBoardGUI();
		
	}
	class displayInfo implements ActionListener{
		int trainId;
		String[][] NextstopPlanTime = new String[1][2];
		public displayInfo(int id) {
			trainId = id;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			modelT.setRowCount(0);
			int JourneyNo = -1;
			
			
			int flag1 = 0;
			for(int i = 0;i < journeyList.size();i++){//find if the driver is exist
				if(journeyList.get(i).AssignedTrainId == trainId){
					JourneyNo = i;
					flag1 = 1;
					break;
				}
			}
			
			ControlOnBoardScreen co = new ControlOnBoardScreen();
			try {
				NextstopPlanTime = co.controlOnBoardScreen(trainId);
			} catch (ClassNotFoundException | IOException | ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			//NextstopPlanTime[0][0] Nextstop, NextstopPlanTime[0][1]arrivingtime
			String RealTime = format.format(Calendar.getInstance().getTime());
			System.out.println("##real time" + RealTime + "##");
				Long DurationTime = (long) -1;
				try {
					DurationTime = format.parse(journeyList.get(JourneyNo).OutWardArrivingTime[journeyList.get(JourneyNo).StopNumber-1]).getTime() 
							- format.parse(RealTime).getTime();
				} catch (ParseException e1) {
					
					e1.printStackTrace();
				}
				Route route = findRoute(journeyList.get(JourneyNo).JourneyId);
			if(DurationTime > 0){
				for (int i = 0; i < journeyList.get(JourneyNo).StopNumber; i++) {//打印每一站和时间
					String arr[] = new String[2];
					arr[0] = route.StopName[i];
					arr[1] = journeyList.get(JourneyNo).OutWardArrivingTime[i];
					modelT.addRow(arr);
				}
			}else{
				for (int i = 0; i < journeyList.get(JourneyNo).StopNumber; i++) {
					String arr[] = new String[2];
					arr[0] = route.StopName[i];
					arr[1] = journeyList.get(JourneyNo).ReturnArrivingTime[i];
					modelT.addRow(arr);
				}
			}

			timetable.invalidate();
		}
		
	}
	
	class back implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			//回到主菜单
			onBoardFrame.dispose();
			MenuGUI m = new MenuGUI();
		}
		
	}
	public Journey findJourney(int trainId){
		Journey thisJourney = null;
		for (int i = 0; i < journeyList.size(); i++) {
			if (journeyList.get(i).AssignedTrainId == trainId) {
				thisJourney = journeyList.get(i);
				break;
			}
		}
		return thisJourney;
	}
	public Route findRoute(int journeyId){
		Route thisRoute = null;
		for (int i = 0; i < journeyList.size(); i++) {
			if (journeyList.get(i).JourneyId == journeyId) {
				for (int j = 0; j < routeList.size(); j++) {
					if (routeList.get(j).RouteId == journeyList.get(i).BelongRouteId) {
						thisRoute = routeList.get(j);
						break;
					}
				}
			}
		}
		return thisRoute;
	}
}
