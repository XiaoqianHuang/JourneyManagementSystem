package yang;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import g.gridBagLayoutFrame;
import g.onBoard;

public class ScheduleJourneyGUI {
	JFrame sjFrame;
	JPanel panelContainer,pleft,pright;
	JTable stoptable,journeytable;
	JScrollPane sleft,sright;
	JButton add,delete,save,back;
	DefaultTableModel model;
	JTextField out,in;
	static int journeyCount = 0;
	String [] newj = {""};
	DefaultTableModel m ;
	TableColumnModel cmodel;
	DefaultTableCellRenderer tcr ;
	String endstop;
	ScheduleJourney sj;
	Route belongedRoute;
	ArrayList<Journey> journeyList;
	ArrayList<Route> routeList;
	ObjectSer objectSer;
	int routeindex;
	public ScheduleJourneyGUI(Route r) throws ClassNotFoundException, IOException {
		routeList = new ArrayList<>();
		journeyList = new ArrayList<>();
		objectSer = new ObjectSer();
		journeyList = objectSer.readJourneyArrayList();
		routeList = objectSer.readRouteArrayList();
		belongedRoute = r;
		for (int i = 0; i < routeList.size(); i++) {
			if (routeList.get(i).equals(belongedRoute)) {
				routeindex = i;
				break;
			}
		}

		
		sj = new ScheduleJourney();
		this.endstop = r.StopName[r.StopNumber-1];
		sjFrame = new JFrame("schedule journey for route");
		panelContainer = new JPanel();//总面板
		panelContainer.setLayout(new GridBagLayout());
		
		loadroute();
		/**
		 * 中面板
		 */
		//表格初始化

		m = new DefaultTableModel();
		journeytable = new JTable(m);
		journeytable.setFillsViewportHeight(true); 
		journeytable.setRowHeight(63);
		journeytable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  //单选
		journeytable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		journeytable.setRowSelectionAllowed(false);
		tcr.setHorizontalAlignment(SwingConstants.CENTER);

		int rid = belongedRoute.RouteId;
		journeyCount = journeyList.size();
		for (int i = 0; i < journeyList.size(); i++) {
			if (journeyList.get(i).BelongRouteId == rid) {
				String[] data = {journeyList.get(i).OutWardArrivingTime[0],journeyList.get(i).OutWardArrivingTime[journeyList.get(i).StopNumber-1],
						journeyList.get(i).ReturnArrivingTime[0],journeyList.get(i).ReturnArrivingTime[journeyList.get(i).StopNumber-1]};
				m.addColumn("                            JOURNEY"+ (journeyList.get(i).JourneyId), data);
				journeyCount++;
			}
			
		}
		//列渲染
		cmodel = journeytable.getColumnModel();
		for (int index = 0; index < journeytable.getColumnCount(); index++) {
		TableColumn column = cmodel.getColumn(index);
		column.setPreferredWidth(220);//看这里
		column.setCellRenderer(tcr);
		}
		sright = new JScrollPane(journeytable);
		sright.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

		
		/**
		 * 右面板
		 */
		pright = new JPanel();
		pright.setLayout(new BoxLayout(pright, BoxLayout.Y_AXIS));
		
		
		JPanel blank = new JPanel();//搭载输入框的panel
		Font font = new Font("斜体", Font.ITALIC, 10);
		//出发时间框初始化
		out = new JTextField("input outward time",10);
		out.setForeground(Color.GRAY);
		out.setFont(font);
		out.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				out.selectAll();//点击即全选
	        }
		});
		//回程时间初始化
		in = new JTextField("input return time",10);
		in.setForeground(Color.GRAY);
		in.setFont(font);
		in.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				in.selectAll();//点击即全选
	        }
		});
		blank.setLayout(new BoxLayout(blank, BoxLayout.Y_AXIS));
		blank.add(out);
		blank.add(in);
		
		add = new JButton("+");
		add.addActionListener(new add());
		delete = new JButton("-");
		delete.addActionListener(new delete());

		JPanel up = new JPanel();//搭载按钮的panel
		up.setLayout(new BorderLayout());
		up.add(add,BorderLayout.CENTER);
		up.add(delete, BorderLayout.LINE_END);


		save = new JButton("save");
		save.addActionListener(new save());
		back = new JButton("back");
		back.addActionListener(new back());
		JPanel down = new JPanel();
		down.setLayout(new BorderLayout());
		down.add(save,BorderLayout.LINE_START);
		down.add(back, BorderLayout.LINE_END);
		pright.add(blank);
		pright.add(up);
		pright.add(Box.createVerticalStrut(130));
		pright.add(down);


		
		//三个面板位置参数设置
		GridBagConstraints c1 = new GridBagConstraints();
		c1.gridx = 0;
		c1.gridy = 0;
		c1.weightx = 3.0;
		c1.weighty = 1.0;
		c1.fill = GridBagConstraints.BOTH;
		panelContainer.add(sleft, c1);

		GridBagConstraints c2 = new GridBagConstraints();
		c2.gridx = 6;
		c2.gridy = 0;
		c2.weightx = 10.0;
		c2.weighty = 1.0;
		c2.fill = GridBagConstraints.BOTH;
		panelContainer.add(sright, c2);
		
		GridBagConstraints c3 = new GridBagConstraints();
		c2.gridx = 16;
		c2.gridy = 0;
		c2.weightx = 3.0;
		c2.weighty = 1.0;
		c2.fill = GridBagConstraints.BOTH;
		panelContainer.add(pright, c3);
		
		sjFrame.getContentPane().add(panelContainer);
		sjFrame.setSize(1000, 300);
		sjFrame.setLocationRelativeTo(null);
		sjFrame.setVisible(true);
		sjFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
	public void loadroute() {
		/**
		 * 左面版
		 */
		pleft = new JPanel();
		String[][] cellData = {{"Central Station"},{endstop}
								,{endstop},{"Central Station"}};
		String[] columnNames = {"                          STATION"};
		//table model初始化
		model = new DefaultTableModel(cellData, columnNames) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		stoptable = new JTable(model);
		tcr = new DefaultTableCellRenderer();
		tcr.setHorizontalAlignment(SwingConstants.CENTER);
		for(int i = 0; i < columnNames.length; i++){
			stoptable.getColumn(columnNames[i]).setCellRenderer(tcr);
		}
		//表格初始化

		JTableHeader tableH = stoptable.getTableHeader();
		tableH.setBackground(new Color(255,182,193));
		tableH.setForeground(new Color(255,250,240));
		stoptable.setRowSelectionAllowed(false);
		stoptable.setFillsViewportHeight(true); 
		stoptable.setRowHeight(63);
		sleft = new JScrollPane(stoptable);
		
	}
	
	class add implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			journeyCount++;
			
			try {
				Journey journey = new Journey(journeyCount, belongedRoute);
				
				journeyList.add(journey);
				//objectSer.writeJourneyArrayList(journeyList);
				System.out.println(out.getText()+"in:  "+in.getText());
				journeyList = sj.scheduleJourney(journeyList,journeyCount, out.getText(), in.getText());
				journey = journeyList.get(journeyList.size()-1);
				System.out.println("out:"+journey.OutWardArrivingTime[journey.StopNumber-1]);
				System.out.println("in:"+journey.ReturnArrivingTime[journey.StopNumber-1]);
				String[] data = {out.getText(),journey.OutWardArrivingTime[journey.StopNumber-1],
						in.getText(),journey.ReturnArrivingTime[journey.StopNumber-1]};
				m.addColumn("                            JOURNEY"+ (journeyCount), data);
			} catch (ClassNotFoundException | ParseException | IOException e1) {
				
				e1.printStackTrace();
			}
			
			
			for (int index = 0; index < journeytable.getColumnCount(); index++) {
			TableColumn column = cmodel.getColumn(index);
			column.setPreferredWidth(220);
			column.setCellRenderer(tcr);
			} 
		
			
		}
		
	}
	
	class delete implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			TableColumn column = cmodel.getColumn(journeytable.getSelectedColumn());
			cmodel.removeColumn(column);
		}
		
	}
	class JTableTestRenderer extends JPanel implements TableCellRenderer {
        JButton edit_btn;
        JLabel edit_lb;
        public JTableTestRenderer(){ 
              super();
             setLayout(new BorderLayout());
             edit_btn = new JButton("x");
             edit_btn.addActionListener(new delete());
             edit_lb = new JLabel("journey");
             add(edit_lb);
             add(edit_btn,BorderLayout.EAST);
             edit_btn.setBackground(Color.white);
             edit_btn.setPreferredSize(new Dimension(20,getHeight()));
       } 
 
     public Component getTableCellRendererComponent(JTable table, Object value, 
          boolean isSelected, boolean hasFocus, int row, int column) { 
          if(isSelected){ 
              setForeground(table.getForeground()); 
               super.setBackground(table.getBackground()); 
          }else{ 
               setForeground(table.getForeground()); 
               setBackground(table.getBackground()); 
         } 
       if(value != null)
             edit_lb.setText(value.toString());
             return this; 
        } 
}
	class back implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			Object[] options ={ "Back to assign" , "Confirm and back"};  
			int m = JOptionPane.showOptionDialog(null, "Confirm all changes？",
					"Warning",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);  
			System.out.print(m);
			if (m == 1) {//comfirm and exit
				
				try {
					sjFrame.dispose();
					ScheduleRouteGUI sr = new ScheduleRouteGUI();
				} catch (ClassNotFoundException | IOException e1) {

					e1.printStackTrace();
				}

			}
		}
	}
	
	class save implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			Object[] options ={ "Back to assign" , "Confirm and save"};  
			int m = JOptionPane.showOptionDialog(null, "Confirm all changes？",
					"Warning",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);  
			System.out.print(m);
			if (m == 1) {//comfirm and exit
				try {
					for (int i = 0; i < journeyList.size(); i++) {
						if (routeList.get(routeindex).AssignedJourney.contains(journeyList.get(i).JourneyId)) {
							System.out.println("already exist!");
						}
						else {
							routeList.get(routeindex).AssignedJourney.add(journeyList.get(i).JourneyId);
						}
					}
					objectSer.writeRouteArrayList(routeList);
					objectSer.writeJourneyArrayList(journeyList);
					System.out.println("successfully save!");
				} catch (ClassNotFoundException | IOException e1) {

					e1.printStackTrace();
				}
			}
				
			
		}
		
	}

}
