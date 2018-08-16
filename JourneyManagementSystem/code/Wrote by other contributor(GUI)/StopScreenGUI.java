package yang;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.nio.file.StandardOpenOption;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;


public class StopScreenGUI {
	final int definedStopNo = 6;
	private JButton bt_SS_back,bt_outward,bt_return;
	private JButton[] bt_stop;
	private JPanel panelContainer,left,right;
	private JFrame SS_frame;
	DefaultTableModel modelT,modelI;
	private JTable timetable,infotable;
	private JScrollPane left_jsp,table_jsp,info_jsp;
	String[] stopname ;
	static String cs = "";
	ObjectSer objectSer;
	ArrayList<Route> routes;
	public StopScreenGUI() throws ClassNotFoundException, IOException {
		objectSer = new ObjectSer();
		routes = objectSer.readRouteArrayList();
		stopname = new String[definedStopNo];
		stopname[0] = "Central station";
		stopname[1] = "stop 1";
		stopname[2] = "stop 2";
		stopname[3] = "stop 3";
		stopname[4] = "stop 4";
		stopname[5] = "stop 5";
		SS_frame = new JFrame("Stop Screen");
		panelContainer = new JPanel();
		panelContainer.setLayout(new GridBagLayout());

	
		left = new JPanel();
		left_jsp = new JScrollPane(left);

		left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
		left.setBorder(new TitledBorder("STOP"));
	
		
		
	
		Object[][] cellData = null;
		String[] columnNames = {"Stop","Time"};
		//table model初始化,设置第三列已被分配的火车按钮列可点
		modelT = new DefaultTableModel(cellData, columnNames);
		//表格初始化
		timetable = new JTable(modelT);
		//设置表格排序器
//		TableRowSorter tableRowSorterT = new TableRowSorter(modelT);  
//		timetable.setRowSorter(tableRowSorterT);
		//设置单元格渲染器
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
		tcr.setHorizontalAlignment(SwingConstants.CENTER);//表格数据居中
		for(int i = 0; i < columnNames.length; i++){
			timetable.getColumn(columnNames[i]).setCellRenderer(tcr);
			
		}
		//设置表头样式
		JTableHeader tableH = timetable.getTableHeader();
		tableH.setBackground(new Color(255,182,193));
		tableH.setForeground(new Color(255,250,240));
		
		
		//设置表格属性,为按钮列添加渲染和编辑器
		timetable.setPreferredSize(null);
//		tableT.setPreferredSize(new Dimension(10, 10));
		timetable.setRowSelectionAllowed(true);
		timetable.setRowHeight(50);
		timetable.setFillsViewportHeight(false); 


		table_jsp = new JScrollPane(timetable);//为表格加入滚动条
	
		Object[][] cellDataI = {{"Next Train:", null},{"Arriving Time:", null},{"Late Time:", null}};
		String[] columnNamesI = {"  ","  ",};
		//table model初始化,设置第三列已被分配的火车按钮列可点
		modelI = new DefaultTableModel(cellDataI, columnNamesI);
		//表格初始化
		infotable = new JTable(modelI);

		for(int i = 0; i < columnNamesI.length; i++){
			infotable.getColumn(columnNamesI[i]).setCellRenderer(tcr);
		}
		//设置表头样式
		
		JTableHeader tableI = infotable.getTableHeader();

		//设置表格属性,为按钮列添加渲染和编辑器
		infotable.setPreferredSize(null);
		infotable.setRowSelectionAllowed(true);
		infotable.setRowHeight(70);
		infotable.setFillsViewportHeight(false); 

		info_jsp = new JScrollPane(infotable);//为表格加入滚动条

		
		bt_stop = new JButton[definedStopNo];
		for (int i = 0; i<definedStopNo; i++){
			bt_stop[i] = new JButton(stopname[i]);
			bt_stop[i].setName(stopname[i]);
			bt_stop[i].addActionListener(new displayInfo(bt_stop[i]));
			left.add(bt_stop[i]);
		}
		right = new JPanel();
		right.setLayout(new BorderLayout());
		bt_SS_back = new JButton("back");
		bt_SS_back.addActionListener(new SS_back());
		bt_outward = new JButton("outward");
		bt_outward.addActionListener(new outward());
		bt_return = new JButton("return");
		bt_return.addActionListener(new returned());
		right.add(bt_SS_back,BorderLayout.SOUTH);
		right.add(bt_outward, BorderLayout.NORTH);
		right.add(bt_return, BorderLayout.CENTER);
		
		GridBagConstraints c1 = new GridBagConstraints();
		c1.gridx = 0;
		c1.gridy = 0;
		c1.weightx = 4.0;
		c1.weighty = 1.0;

		c1.fill = GridBagConstraints.BOTH;
		panelContainer.add(left_jsp, c1);

		GridBagConstraints c2 = new GridBagConstraints();
		c2.gridx = 4;
		c2.gridy = 0;
		c2.weightx = 7.0;
		c2.weighty = 1.0;
		c2.fill = GridBagConstraints.BOTH;
		panelContainer.add(info_jsp, c2);
		
		GridBagConstraints c3 = new GridBagConstraints();
		c3.gridx = 11;
		c3.gridy = 0;
		c3.weightx = 7.0;
		c3.weighty = 1.0;
		c3.fill = GridBagConstraints.BOTH;
		panelContainer.add(table_jsp, c3);
		
		GridBagConstraints c4 = new GridBagConstraints();
		c4.gridx = 18;
		c4.gridy = 0;
		c4.weightx = 1.0;
		c4.weighty = 1.0;
		c4.fill = GridBagConstraints.BOTH;
		panelContainer.add(right, c4);
		SS_frame.add(panelContainer);

		SS_frame.setSize(800, 300);
		SS_frame.setLocationRelativeTo(null);
		SS_frame.setVisible(true);
		SS_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) throws ClassNotFoundException, IOException {
		new StopScreenGUI();
	}
	class displayInfo implements ActionListener{
		JButton jb;
		public displayInfo(JButton jb) {
			this.jb = jb;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			cs = jb.getName();//设置当前选择的stop
			for (int i = 0; i < bt_stop.length; i++) {
				
					bt_stop[i].setText(bt_stop[i].getName());
					bt_stop[i].setForeground(Color.BLACK);
				
			}
			jb.setText(jb.getName()+"->");
			jb.setForeground(Color.BLUE);

		}
		
	}
	class SS_back implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			//回到主菜单
			SS_frame.dispose();
			MenuGUI m = new MenuGUI();
		}
		
	}
	class outward implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			ControlStopScreen css = new ControlStopScreen();
			bt_outward.setForeground(Color.BLUE);
			bt_return.setForeground(Color.BLACK);
			try {
				Journey j =null;
				j = css.controlStopScreenOutward(cs);
				if (j != null) {
					Route route = findRoute(j);
					if (route != null) {
						
					
						System.out.println(j.JourneyId+j.StopNumber);
						modelT.setRowCount(0);
						for (int i = 0; i < j.StopNumber; i++) {
							String[] arr=new String[2];
							arr[0] = route.StopName[i];
							arr[1] = j.OutWardArrivingTime[i];
							modelT.addRow(arr);
						}
						
						timetable.invalidate();
						
	
						modelI.setValueAt(""+j.AssignedTrainId, 0, 1);
						for (int i = 0; i < route.StopNumber; i++) {
							if (route.StopName[i].equals(cs)) {
								modelI.setValueAt(j.OutWardArrivingTime[i], 1, 1);
							}
						}
						
						
						modelI.setValueAt("...late time???", 2, 1);
						infotable.invalidate();
					}
				} else {
					System.out.println("No route!");
				}
				
			} catch (ClassNotFoundException | IOException | ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	class returned implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			bt_return.setForeground(Color.BLUE);
			bt_outward.setForeground(Color.BLACK);
			ControlStopScreen css = new ControlStopScreen();
			try {
				Journey j =null;
				j = css.controlStopScreenReturn(cs);
				if (j!=null) {
					
				
					System.out.println(j.JourneyId+j.StopNumber);
					Route route = findRoute(j);
					
					
					if (route != null) {
						System.out.println(j.JourneyId+j.StopNumber);
						modelT.setRowCount(0);
						for (int i = 0; i < j.StopNumber; i++) {
							String[] arr=new String[2];
							arr[0] = route.StopName[i];
							arr[1] = j.ReturnArrivingTime[i];
							modelT.addRow(arr);
						}
						
						timetable.invalidate();
						modelI.setValueAt(""+j.AssignedTrainId, 0, 1);
						for (int i = 0; i < route.StopNumber; i++) {
							if (route.StopName[i] == cs) {
								modelI.setValueAt(j.ReturnArrivingTime[route.StopNumber-i-1], 1, 1);
							}
						}
						
						
						modelI.setValueAt("...late time???", 2, 1);
						infotable.invalidate();
					}
				} else {
					System.out.println("No route");
				}
			
			} catch (ClassNotFoundException | IOException | ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	public Route findRoute (Journey journey){
		Route thisroute = null;
		for (int i = 0; i < routes.size(); i++) {
			if (routes.get(i).RouteId == journey.BelongRouteId) {
				thisroute = routes.get(i);
				break;
			}
			
		}
		return thisroute;
	}

}
