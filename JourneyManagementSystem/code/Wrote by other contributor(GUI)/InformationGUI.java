package yang;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;
import java.util.prefs.BackingStoreException;

import javax.management.modelmbean.ModelMBean;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class InformationGUI {
	private JFrame InfoFrame;
	private JTabbedPane jtp;
	private JPanel DriverPanel,TrainPanel,JourneyPanel;
	private JPanel train_search_panel,driver_search_panel,journey_search_panel;
	private JPanel trainFunctionPanel,driverFunctionPanel,journeyFunctionPanel;
	private JButton newTrain,modifyTrain,deleteTrain;
	private JButton newDriver,modifyDriver,deleteDriver;
	private JButton train_back,driver_back,journey_back;
	private myTabelModel modelT,modelD,modelJ;
	private JTextField trainIdText,trainStatusText,trainSearchText;
	private JTextField driverIdText,driverStatusText,driverSearchText;
	private JTextField journeyIdText,journeyStatusText,journeySearchText;
	private JTable tableT,tableD,tableJ;
	private JScrollPane trainjsp,driverjsp,journeyjsp;
	private JScrollBar trainbar,driverbar,journeybar;
	private Font font ;
	private ObjectSer objectSer;
	private ArrayList<Train> trainList;
	private ArrayList<Driver> driverList;
	private ArrayList<Journey> journeyList;
	/**
	 * 标签主页面
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public InformationGUI() throws ClassNotFoundException, IOException {
	InfoFrame = new JFrame();
	font = new Font("斜体", Font.ITALIC, 10);
	train_back = new JButton("back");
	driver_back = new JButton("back");
	journey_back = new JButton("back");
	objectSer = new ObjectSer();
	trainList = new ArrayList<Train>();
	driverList = new ArrayList<Driver>();
	journeyList = new ArrayList<Journey>();
	jtp = new JTabbedPane();

	InfoFrame.setLayout(new BorderLayout());
	
	createTrainPanel();
	createDriverPanel();
	createJourneyPanel();
	InfoFrame.getContentPane().add(jtp);
	InfoFrame.setSize(800, 300);
	InfoFrame.setLocationRelativeTo(null);InfoFrame.setVisible(true);
	InfoFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	/**
	 * 火车窗口
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	private void createTrainPanel() throws ClassNotFoundException, IOException {

		/**
		 * 火车总面板
		 */
		TrainPanel = new JPanel();
		TrainPanel.setLayout(new BorderLayout());
		

			/**
			 * 搜索面板
			 */

			JComponent[] jc = new JComponent[2];
			train_search_panel = new JPanel();
			jc= createSearchPanel();
			train_search_panel = (JPanel) jc[0];
			trainSearchText = (JTextField) jc[1];
			/**
			 * 表格
			 */
			trainList = objectSer.readTrainArrayList();
			Object[][] cellData = new Object[trainList.size()][2];
			for (int i = 0; i < trainList.size(); i++) {
				cellData[i][0] = ""+trainList.get(i).TrainId;
				if (trainList.get(i).CurrentStateJ == true) {
					cellData[i][1] = "available";
				}
				else {
					cellData[i][1] = "occupied";
				}
			}
			
			String[] columnNames = {"                                                     ID",
									"                                                Status",
									"      timetable"};
			//table model初始化,设置第三列已被分配的火车按钮列可点
			modelT = new myTabelModel(cellData, columnNames) {
				
				public boolean isCellEditable(int row, int column) {
					if (column == 2 && tableT.getValueAt(row, 1).equals("occupied"))  return true;
					else  return false;
				}
				
				
			};
			//表格初始化
			tableT = new JTable(modelT);
			//设置表格排序器
			TableRowSorter tableRowSorter = new TableRowSorter(modelT);  
			tableT.setRowSorter(tableRowSorter);
			//设置单元格渲染器
			DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
			tcr.setHorizontalAlignment(SwingConstants.CENTER);//表格数据居中
			for(int i = 0; i < columnNames.length; i++){
				tableT.getColumn(columnNames[i]).setCellRenderer(tcr);
			}
			//设置表头样式
			JTableHeader tableH = tableT.getTableHeader();
			tableH.setBackground(new Color(255,182,193));
			tableH.setForeground(new Color(255,250,240));
			
			//表格按钮列比其他两列窄
			TableColumn column = null;   
			for (int i = 0; i < 3; i++) {   
				column = tableT.getColumnModel().getColumn(i);
				if (i == 2)
					column.setPreferredWidth(10);
				else
					column.setPreferredWidth(250);
			}
			//设置表格属性,为按钮列添加渲染和编辑器
			tableT.getColumnModel().getColumn(2).setCellEditor(new MyButtonEditor(tableT));
			tableT.getColumnModel().getColumn(2).setCellRenderer(new MyButtonRender(tableT));  
			tableT.setRowSelectionAllowed(false);
			tableT.setFillsViewportHeight(true); 
			tableT.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  //单选
			tableT.addMouseListener(new MouseAdapter(){    //鼠标事件
				public void mouseClicked(MouseEvent e){
					if (e.getClickCount() == 2) {
						int selectedRow = tableT.getSelectedRow(); //获得选中行索引
						
						Object oa = modelT.getValueAt(selectedRow, 0);
						Object ob = modelT.getValueAt(selectedRow, 1);
						trainIdText.setText(oa.toString());  //给文本框赋值
						trainStatusText.setText(ob.toString());
					}
		        }
		    });
			
	
			trainjsp = new JScrollPane(tableT);//为表格加入滚动条
			trainbar=trainjsp.getVerticalScrollBar();//加入滚动块

			//搜索面板
				JComponent[] jc_fp = new JComponent[7];
				jc_fp = createFunctionPanel();
				trainFunctionPanel = (JPanel) jc_fp[0];
				newTrain = (JButton) jc_fp[1];
				newTrain.addActionListener(new addTrain());
				deleteTrain = (JButton) jc_fp[2];
				deleteTrain.addActionListener(new deleteTrain());
				modifyTrain = (JButton) jc_fp[3];
				modifyTrain.addActionListener(new modifyTrain());
				train_back = (JButton) jc_fp[4];
				trainIdText = (JTextField) jc_fp[5];
				trainStatusText = (JTextField) jc_fp[6];
			
		//向火车总面板添加三个分面板
		TrainPanel.add(train_search_panel, BorderLayout.NORTH);//加入搜索面板
		TrainPanel.add(trainjsp, BorderLayout.CENTER);//加入表格
		TrainPanel.add(trainFunctionPanel, BorderLayout.SOUTH);//加入功能按钮面板

		jtp.add("Train", TrainPanel);//火车面板加入标签页
		
		//点击面板任意位置即取消过滤选项,显示全部数据
		TrainPanel.addMouseListener(new MouseAdapter() {
			 public void mouseClicked(MouseEvent e){
				 tableT.setRowSorter(tableRowSorter);
			 trainSearchText.setText("search for id");
			 trainSearchText.selectAll();
			 }
		});
		//设置整个火车面板内回车即发起搜索
		AWTEventListener al=new AWTEventListener() {
			public void eventDispatched(AWTEvent event) {
				if(event instanceof KeyEvent){
					KeyEvent e=(KeyEvent)event;
					if(e.getKeyChar()==KeyEvent.VK_ENTER&&e.getID()==KeyEvent.KEY_RELEASED){
						System.out.println("Enter");
						TableRowSorter trs = new TableRowSorter(modelT);
						trs.setRowFilter(RowFilter.regexFilter(trainSearchText.getText()));
						tableT.setRowSorter(trs);
					}
				}
			}
		};
		Toolkit.getDefaultToolkit().addAWTEventListener(al, AWTEvent.KEY_EVENT_MASK);
	}
	/**
	 * 司机窗口
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	private void createDriverPanel() throws ClassNotFoundException, IOException {
		/**
		 * 司机总面板
		 */
		DriverPanel = new JPanel();
		DriverPanel.setLayout(new BorderLayout());
		
			JComponent[] jc = new JComponent[2];
			driver_search_panel = new JPanel();
			jc= createSearchPanel();
			driver_search_panel = (JPanel) jc[0];
			driverSearchText = (JTextField) jc[1];
			/**
			 * 表格
			 */
			driverList = objectSer.readDriverArrayList();
			Object[][] cellData = new Object[driverList.size()][3];
			for (int i = 0; i < driverList.size(); i++) {
				cellData[i][0] = ""+driverList.get(i).DriverId;
				if (driverList.get(i).CurrentState == true) {
					cellData[i][1] = "available";
					cellData[i][2] = "None";
				}
				else {
					cellData[i][1] = "occupied";
					cellData[i][2] = ""+driverList.get(i).AssignedTrainId;
				}
			}
			
			String[] columnNames = {"                                        ID", 
									"                                    Status",
									"                               Assigned Train"};
			modelD = new myTabelModel(cellData, columnNames);
			tableD = new JTable(modelD);
			tableD.setRowSelectionAllowed(false);
			tableD.setFillsViewportHeight(true);  
			tableD.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  //单选
		    tableD.addMouseListener(new MouseAdapter(){    //鼠标事件
		        public void mouseClicked(MouseEvent e){
		        	if (e.getClickCount() == 2) {
			            int selectedRow = tableD.getSelectedRow(); //获得选中行索引
			            Object oa = modelD.getValueAt(selectedRow, 0);
			            Object ob = modelD.getValueAt(selectedRow, 1);
			            driverIdText.setText(oa.toString());  //给文本框赋值
			            driverStatusText.setText(ob.toString());
		        	}
		        }
		    });
			//设置表格排序器
			TableRowSorter tableRowSorter = new TableRowSorter(modelD);  
			tableD.setRowSorter(tableRowSorter);
			//设置单元格渲染器
			DefaultTableCellRenderer tcr = new DefaultTableCellRenderer() ;
			tcr.setHorizontalAlignment(SwingConstants.CENTER);//表格数据居中
			for(int i = 0; i < columnNames.length; i++){
				tableD.getColumn(columnNames[i]).setCellRenderer(tcr);
			}
			//设置表头样式
			JTableHeader tableH = tableD.getTableHeader();
			tableH.setBackground(new Color(65,105,225));
			tableH.setForeground(new Color(255,250,240));
			
			
//			//测试数据
//			for (int i = 0; i < 40; i++) {
//				modelD.addRow(new Object[]{""+i, "available"});
//			}

			driverjsp = new JScrollPane(tableD);//为表格加入滚动条
			driverbar=driverjsp.getVerticalScrollBar();//加入滚动块
			
			JComponent[] jcfp = new JComponent[7];
			jcfp = createFunctionPanel();
			driverFunctionPanel = (JPanel) jcfp[0];
			newDriver = (JButton) jcfp[1];
			newDriver.addActionListener(new addDriver());
			deleteDriver = (JButton) jcfp[2];
			deleteDriver.addActionListener(new deleteDriver());
			modifyDriver = (JButton) jcfp[3];
			modifyDriver.addActionListener(new modifyDriver());
			driver_back = (JButton) jcfp[4];
			driverIdText = (JTextField) jcfp[5];
			driverStatusText = (JTextField) jcfp[6];
				
			
		
		
		//面板任意位置取消过滤选项
		DriverPanel.addMouseListener(new MouseAdapter() {
			 public void mouseClicked(MouseEvent e){
				 tableD.setRowSorter(tableRowSorter);
			 driverSearchText.setText("search for id");
			 driverSearchText.selectAll();
			 }
		});

		DriverPanel.add(driver_search_panel, BorderLayout.NORTH);
		DriverPanel.add(driverjsp, BorderLayout.CENTER);
		DriverPanel.add(driverFunctionPanel, BorderLayout.SOUTH);
		jtp.add("Driver", DriverPanel);
		AWTEventListener al=new AWTEventListener() {
			@Override
			public void eventDispatched(AWTEvent event) {
				if(event instanceof KeyEvent){
					KeyEvent e=(KeyEvent)event;
					if(e.getKeyChar()==KeyEvent.VK_ENTER&&e.getID()==KeyEvent.KEY_RELEASED){
						System.out.println("Enter");
						TableRowSorter trs = new TableRowSorter(modelD);
					trs.setRowFilter(RowFilter.regexFilter(driverSearchText.getText()));
					tableD.setRowSorter(trs);
					}
				}
			}
		};
		Toolkit.getDefaultToolkit().addAWTEventListener(al, AWTEvent.KEY_EVENT_MASK);
	}
	/**
	 * journey窗口
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	private void createJourneyPanel() throws ClassNotFoundException, IOException {
		/**
		 * Journey总面板
		 */
		JourneyPanel = new JPanel();
		JourneyPanel.setLayout(new BorderLayout());
		
			/**
			 * 搜索面板
			 */
			JComponent[] jc = new JComponent[2];
			journey_search_panel = new JPanel();
			jc= createSearchPanel();
			journey_search_panel = (JPanel) jc[0];
			journeySearchText = (JTextField) jc[1];
			
			/**
			 * 表格
			 */
			journeyList = objectSer.readJourneyArrayList();
			Object[][] cellData = new Object[journeyList.size()][5];
			for (int i = 0; i < journeyList.size(); i++) {
				cellData[i][0] = ""+journeyList.get(i).JourneyId;
				cellData[i][2] = ""+journeyList.get(i).BelongRouteId;
				cellData[i][4] = ""+journeyList.get(i).StopNumber;
				if (journeyList.get(i).CurrentState == true) {
					cellData[i][1] = "available";
					cellData[i][3] = "None";
				}
				else {
					cellData[i][1] = "occupied";
					cellData[i][3] = ""+journeyList.get(i).AssignedTrainId;
				}
			}
			
			String[] columnNames = {"                       ID", 
									"                     Status",
									"         Belong To Route ",
									"         Assigned Train Id",
									"              Stop Number"};
			modelJ = new myTabelModel(cellData, columnNames);
			tableJ = new JTable(modelJ);
			tableJ.setRowSelectionAllowed(false);
			tableJ.setFillsViewportHeight(true);  
			tableJ.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  //单选
			tableJ.addMouseListener(new MouseAdapter(){    //鼠标事件
				public void mouseClicked(MouseEvent e){
					if (e.getClickCount() == 2) {
						int selectedRow = tableJ.getSelectedRow(); //获得选中行索引
						Object oa = modelJ.getValueAt(selectedRow, 0);
						Object ob = modelJ.getValueAt(selectedRow, 1);
						journeyIdText.setText(oa.toString());  //给文本框赋值
						journeyStatusText.setText(ob.toString());
					}
				}
			});
			//设置表格排序器
			TableRowSorter tableRowSorter = new TableRowSorter(modelJ);  
			tableJ.setRowSorter(tableRowSorter);
			//设置单元格渲染器
			DefaultTableCellRenderer tcr = new DefaultTableCellRenderer() ;
			tcr.setHorizontalAlignment(SwingConstants.CENTER);//表格数据居中
			for(int i = 0; i < columnNames.length; i++){
				tableJ.getColumn(columnNames[i]).setCellRenderer(tcr);
			}
			//设置表头样式
			JTableHeader tableH = tableJ.getTableHeader();
			tableH.setBackground(new Color(46,139,87));
			tableH.setForeground(new Color(255,250,240));
			
			
//			//测试数据
//			for (int i = 0; i < 40; i++) {
//				modelJ.addRow(new Object[]{""+i, "available"});
//			}

			journeyjsp = new JScrollPane(tableJ);//为表格加入滚动条
			journeybar=journeyjsp.getVerticalScrollBar();//加入滚动块
			
			journeyFunctionPanel= new JPanel();
			
			journey_back = new JButton("back");
			journey_back.addActionListener(new back());
		
			journeyFunctionPanel.add(journey_back,BorderLayout.CENTER);
			
		JourneyPanel.add(journey_search_panel, BorderLayout.NORTH);
		JourneyPanel.add(journeyjsp, BorderLayout.CENTER);
		JourneyPanel.add(journeyFunctionPanel, BorderLayout.SOUTH);
		jtp.add("Journey", JourneyPanel);
		
		//面板任意位置取消过滤选项
		JourneyPanel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				tableJ.setRowSorter(tableRowSorter);
			journeySearchText.setText("search for id");
			journeySearchText.selectAll();
			}
		});
		AWTEventListener al=new AWTEventListener() {
				@Override
				public void eventDispatched(AWTEvent event) {
					if(event instanceof KeyEvent){
						KeyEvent e=(KeyEvent)event;
						if(e.getKeyChar()==KeyEvent.VK_ENTER&&e.getID()==KeyEvent.KEY_RELEASED){
							System.out.println("Enter");
							TableRowSorter trs = new TableRowSorter(modelJ);
							trs.setRowFilter(RowFilter.regexFilter(journeySearchText.getText()));
							tableJ.setRowSorter(trs);
						}
					}
				}
		};
		Toolkit.getDefaultToolkit().addAWTEventListener(al, AWTEvent.KEY_EVENT_MASK);
	}
	
	/**
	 * 创建搜索面板
	 * @return 面板上组件
	 */
	private JComponent[] createSearchPanel() {
		JComponent[] jc = new JComponent[2];
		JPanel search = new JPanel(new BorderLayout());
		JTextField searchText = new JTextField("search for id",10);
		searchText.setForeground(Color.GRAY);
		searchText.setFont(font);
		searchText.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				searchText.selectAll();
	        }
		});
		search.add(searchText,BorderLayout.LINE_END);
		jc[0] = search;
		jc[1] = searchText;
		return jc;
	}
	/**
	 * 创建功能面板
	 * @return 面板上组件
	 */
	private JComponent[] createFunctionPanel() {
		
		JComponent[] jc = new JComponent[7];
		JPanel fp= new JPanel();
		JButton New = new JButton("+");
		JButton Modify = new JButton("modify");
		JButton Delete = new JButton("-");
		JTextField IdText = new JTextField("input id number",10);
		IdText.setFont(font);
		IdText.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				IdText.selectAll();
			}
		});
		JTextField StatusText = new JTextField("input status",10);
		StatusText.setFont(font);
		StatusText.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				StatusText.selectAll();
			}
		});
		JButton back = new JButton("back");
		back.addActionListener(new back());
		
		fp.add(New);
		fp.add(Delete);
		fp.add(IdText);
		fp.add(StatusText);
		fp.add(Modify);
		fp.add(back);
		jc[0] = fp;
		jc[1] = New;
		jc[2] = Delete;
		jc[3] = Modify;
		jc[4] = back;
		jc[5] = IdText;
		jc[6] = StatusText;
	return jc;
	}
	
	/**
	 * 火车添加按钮监听
	 */
	class addTrain implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			int flag = 0;//重复id的flag,不重复为0
			//检测id是否重复
			for (int i = 0; i < tableT.getRowCount(); i++) {
				if (trainIdText.getText().equals(tableT.getValueAt(i, 0))) {
					flag = 1;
					break;
				}
			}
			
			boolean isNum = trainIdText.getText().matches("[0-9]+");//检测id是否是数字
			
			if (flag == 1)
				System.out.println("already existed!");//重复
			else if(isNum){
				Object []rowValues = {trainIdText.getText(),"available"};//新火车初始状态为available
				modelT.addRow(rowValues);  //添加一行
				trainbar.setValue(trainbar.getMaximum());//下拉条滚动到最下(始终差一行)
				int id = Integer.parseInt(trainIdText.getText());
				Train t = new Train(id);
				t.CurrentStateJ = true;
				t.CurrentStateD = true;
				trainList.add(t);

				
			}
			else{
				System.out.println("error!");
			}
			
			
		}
	}
		
	/**
	 * 火车更改按钮监听
	 */
	class modifyTrain implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			int selectedRow = tableT.getSelectedRow();//获得选中行的索引
			int id = Integer.parseInt((String) modelT.getValueAt(selectedRow, 0)) ;
			if(selectedRow!= -1){ //是否存在选中行
				//修改指定的值：
				int newid = Integer.parseInt(trainIdText.getText());
				String newStatus = trainStatusText.getText();
				modelT.setValueAt(trainIdText.getText(), selectedRow, 0);
				modelT.setValueAt(trainStatusText.getText(), selectedRow, 1);
				
					for (int i = 0; i < trainList.size(); i++) {
						if (trainList.get(i).TrainId == id) {
							trainList.get(i).TrainId = newid;
							if (newStatus.equals("occupied")) {
								trainList.get(i).CurrentStateJ = false;
							}
							else {
								trainList.get(i).CurrentStateJ = true;
							}
						}
					
			}
		}
	}
	}
	
	/**
	 * 火车删除按钮监听
	 */
	class deleteTrain implements ActionListener{
		public void actionPerformed(ActionEvent e){
			int selectedRow = tableT.getSelectedRow();//获得选中行的索引
			int id = Integer.parseInt((String) modelT.getValueAt(selectedRow, 0));
			if(selectedRow!=-1)  //存在选中行
			{
				modelT.removeRow(selectedRow);  //删除行
				
					for (int i = 0; i < trainList.size(); i++) {
						if (trainList.get(i).TrainId == id) {
							trainList.remove(i);
							break;
						}
					}
			}
		}
	}
	
	/**
	 * 司机添加按钮监听
	 */
	class addDriver implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			int flag = 0;//重复id的flag,不重复为0
			//检测id是否重复
			for (int i = 0; i < tableD.getRowCount(); i++) {
				
				if (driverIdText.getText().equals(tableD.getValueAt(i, 0))) {
					flag = 1;
					break;
				}
			}
			
			boolean isNum = driverIdText.getText().matches("[0-9]+");//检测id是否是数字
			
			if (flag == 1)
				System.out.println("already existed!");//重复
			else if(isNum){
				int id = Integer.parseInt(driverIdText.getText());
				Object []rowValues = {driverIdText.getText(),"available"};//新司机初始状态为available
				modelD.addRow(rowValues);  //添加一行
				driverbar.setValue(driverbar.getMaximum());//下拉条滚动到最下(始终差一行)
				Driver newDriver = new Driver(id);
				newDriver.CurrentState = true;
				driverList.add(newDriver);
			}
			else{
				System.out.println("error!");
			}
			
			
		}
	}
		
	/**
	 * 司机更改按钮监听
	 */
	class modifyDriver implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			int selectedRow = tableD.getSelectedRow();//获得选中行的索引
			if(selectedRow!= -1)   //是否存在选中行
			{
				//修改指定的值：
				modelD.setValueAt(driverIdText.getText(), selectedRow, 0);
				modelD.setValueAt(driverStatusText.getText(), selectedRow, 1);
			}
		}
	}
	
	/**
	 * 司机删除按钮监听
	 */
	class deleteDriver implements ActionListener{
		public void actionPerformed(ActionEvent e){
			int selectedRow = tableD.getSelectedRow();//获得选中行的索引
			if(selectedRow!=-1)  //存在选中行
			{
				modelD.removeRow(selectedRow);  //删除行
			}
		}
	}
	
	/**
	 * journey添加按钮监听
	 */
	class addJourney implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			int flag = 0;//重复id的flag,不重复为0
			//检测id是否重复
			for (int i = 0; i < tableJ.getRowCount(); i++) {
				
				if (journeyIdText.getText().equals(tableJ.getValueAt(i, 0))) {
					flag = 1;
					break;
				}
			}
			
			boolean isNum = journeyIdText.getText().matches("[0-9]+");//检测id是否是数字
			
			if (flag == 1)
				System.out.println("already existed!");//重复
			else if(isNum){
				Object []rowValues = {journeyIdText.getText(),"available"};//新journey初始状态为available
				modelJ.addRow(rowValues);  //添加一行
				journeybar.setValue(journeybar.getMaximum());//下拉条滚动到最下
				int id = Integer.parseInt(journeyIdText.getText());
			}
			else{
				System.out.println("error!");
			}
			
			
		}
	}
		
	/**
	 * journey更改按钮监听
	 */
	class modifyJourney implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			int selectedRow = tableD.getSelectedRow();//获得选中行的索引
			if(selectedRow!= -1)   //是否存在选中行
			{
				//修改指定的值：
				modelJ.setValueAt(journeyIdText.getText(), selectedRow, 0);
				modelJ.setValueAt(journeyStatusText.getText(), selectedRow, 1);
			}
		}
	}
	
	/**
	 * journey删除按钮监听
	 */
	class deleteJourney implements ActionListener{
		public void actionPerformed(ActionEvent e){
			int selectedRow = tableJ.getSelectedRow();//获得选中行的索引
			if(selectedRow!=-1)  //存在选中行
			{
				modelJ.removeRow(selectedRow);  //删除行
			}
		}
	}
	/**
	 * 返回键
	 */
	class back implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			Object[] options ={ "Back" , "Save and exit"};  
			int m = JOptionPane.showOptionDialog(null, "Confirm all changes？",
					"Warning",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);  
//			System.out.print(m);
			if (m == 1) {//comfirm and exit
				try {
					objectSer.writeTrainArrayList(trainList);
					objectSer.writeDriverArrayList(driverList);
					objectSer.writeJourneyArrayList(journeyList);
				} catch (ClassNotFoundException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				InfoFrame.dispose();
				MenuGUI mm = new MenuGUI();

			}
		}
		
	}

	 class MyButtonEditor extends DefaultCellEditor  
	{  
	  
	    /** 
	     * serialVersionUID 
	     */  
	    private static final long serialVersionUID = -6546334664166791132L;  
	  
	    private JPanel panel;  
	  
	    private JButton button;  
	  
	    public MyButtonEditor(JTable table)  
	    {  
	        super(new JTextField());  
	        this.initPanel();  
	        // 设置点击几次激活编辑。   
	        this.setClickCountToStart(0);  
	       this.initButton();
	        this.panel.add(BorderLayout.CENTER,this.button);
	    }  
	  
	    private void initButton()  
	    {  

	        this.button = new JButton("check");
   
	       
	  
	           
	        this.button.addActionListener(new ActionListener()  
	        {  
	            public void actionPerformed(ActionEvent e)  
	            {  
	                
	                MyButtonEditor.this.fireEditingCanceled();  
	                System.out.println("111");
	                try {
						check(tableT);
					} catch (ClassNotFoundException | IOException e1) {

						e1.printStackTrace();
					}

	            }  
	        });  
	  
	    }  
	   
	    private void initPanel()  
	    {  
	        this.panel = new JPanel(new BorderLayout());  
	    }  
	  
	  
	    /** 
	     * 这里重写父类的编辑方法，返回一个JPanel对象即可（也可以直接返回一个Button对象，但是那样会填充满整个单元格） 
	     */  
	    @Override  
	    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)  
	    {  
	    	this.button.setText("check");
	    	
	    		
	        return this.panel;  
	    }  
	  
	    /** 
	     * 重写编辑单元格时获取的值。如果不重写，这里可能会为按钮设置错误的值。 
	     */  
	    @Override  
	    public Object getCellEditorValue()  
	    {  
	        return this.button.getText();  
	    }
	}
	   
	    

	     


	  public void check(JTable table) throws ClassNotFoundException, IOException {
		JTable trainT = table;
		int row = trainT.getSelectedRow();
		System.out.println(row);
		int trainId = Integer.parseInt((String) trainT.getValueAt(row, 0));
		System.out.println(trainId);
		ArrayList<Journey> journeys = new ArrayList<>();
		ArrayList<Route> routes = new ArrayList<>();
		journeys = objectSer.readJourneyArrayList();
		routes = objectSer.readRouteArrayList();
		Journey thisjourney = null;
		for (int i = 0; i < journeys.size(); i++) {
			if (journeys.get(i).AssignedTrainId == trainId) {
				thisjourney = journeys.get(i);
				break;
			}
		}
		Route thisroute = null;
		for (int i = 0; i < routes.size(); i++) {
			if (routes.get(i).RouteId == thisjourney.BelongRouteId) {
				thisroute = routes.get(i);
				break;
			}
		}
		JFrame timeFrame = new JFrame("TIMETABLE");
		JTable timetable;
		JScrollPane jsp;
		String[][] cellData = new String[thisroute.StopNumber][2];
		for (int i = 0; i < thisroute.StopNumber; i++) {
			cellData[i][0] = thisroute.StopName[i];
		}
		for (int i = 0; i < thisjourney.StopNumber/2; i++) {
			cellData[i][1] = thisjourney.OutWardArrivingTime[i];
		}
		for (int i = thisjourney.StopNumber/2; i < thisjourney.StopNumber; i++) {
			cellData[i][1] = thisjourney.ReturnArrivingTime[i-thisjourney.StopNumber/2];
		}
		
		String[] columnNames = {"STOP", "TIME"};
		DefaultTableModel model;
		model = new DefaultTableModel(cellData, columnNames);
		
		timetable = new JTable(model);
		timetable.setRowHeight(50);
		jsp = new JScrollPane(timetable);
		timeFrame.getContentPane().add(jsp);
		timeFrame.setSize(300, 400);
		timeFrame.setLocationRelativeTo(null);
		timeFrame.setVisible(true);
//		timeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	 


	 class MyButtonRender implements TableCellRenderer
	    {
	        private JPanel panel;

	        private JButton button;

	        public MyButtonRender(JTable table)
	        {
	        	 this.initPanel();
	            // 添加按钮。
	        	this.initButton();
	            this.panel.add(BorderLayout.CENTER,this.button);
	        }

	        private void initButton()
	        {
	            this.button = new JButton("check");
	        }



	        private void initPanel()
	        {
	            this.panel = new JPanel(new BorderLayout());
	        }

	        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row,
	                int column)
	        {

	        	this.button.setText("check");
	            return this.panel;
	        }

	    }
	
	public static void main(String[] args) throws ClassNotFoundException, IOException {
		InformationGUI i = new InformationGUI();
		
	}
}