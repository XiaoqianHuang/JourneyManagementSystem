package yang;
import java.awt.BorderLayout;
import java.awt.Color;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;

public class SynchronizeGUI {
	private DefaultTableModel modelT;
	private JTable tableT;
	private JScrollPane jsp;
	private JScrollBar jsb;
	private JFrame frame;
	private int train_num;
	private JButton bt_chk,bt_syn,bt_syn_back;
	private JPanel upPanel,midPanel,downPanel;
	ObjectSer objectSer;
	ArrayList<synMember> members ;
	public SynchronizeGUI(int train_num) throws ClassNotFoundException, IOException {
		
		objectSer = new ObjectSer();
		members = new ArrayList<>();
		members = objectSer.readSynMember();
		this.train_num = train_num;
		frame=new JFrame("Synchronize!");
		upPanel=new JPanel();
		midPanel=new JPanel();
		downPanel = new JPanel();;
	 	
		bt_chk=new JButton("Check");
		bt_syn=new JButton("Synchronize");
		bt_syn_back=new JButton("Back");
		upPanel.add(bt_chk);
		upPanel.add(bt_syn);
		downPanel.add(bt_syn_back);
		bt_chk.addActionListener(new check());
		bt_syn.addActionListener(new syn());
		bt_syn_back.addActionListener(new syn_back());

		String[][] cellData = new String[members.size()][5];
		for (int i = 0; i < members.size(); i++) {
			cellData[i][0] = ""+members.get(i).trainId;
			cellData[i][1] = members.get(i).scheduled_time;
			cellData[i][2] = members.get(i).live_loction;
			cellData[i][3] = members.get(i).last_stop_time;
			cellData[i][4] = members.get(i).late_time;
		}
		String[] columnNames = {"Train",
								"Scheduled time",
								"Live loction",
								"Last stop time",
								"Late time"};
		
		modelT = new myTabelModel(null, columnNames);
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
		//设置表格属性,为按钮列添加渲染和编辑器
		
		tableT.setRowSelectionAllowed(false);
		tableT.setFillsViewportHeight(true); 
		tableT.setRowHeight(60);
		
		

		jsp = new JScrollPane(tableT);//为表格加入滚动条
		jsb=jsp.getVerticalScrollBar();//加入滚动块
		
		midPanel.add(jsp);
		frame.getContentPane().add(BorderLayout.NORTH, upPanel);
		frame.getContentPane().add(BorderLayout.CENTER,midPanel);
		frame.getContentPane().add(BorderLayout.SOUTH, downPanel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 600);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}
		class check implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				
				try {
					members = objectSer.readSynMember();
					ControlSynchronize c = new ControlSynchronize();
					
					for (int i = 0; i < members.size(); i++) {
						
						c.ControlMemberTime(members.get(i).trainId);
						
					}
					members = objectSer.readSynMember();
				} catch (ClassNotFoundException | IOException | ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				modelT.setRowCount(0);//清零
				
				for (int i = 0; i < members.size(); i++) {
					String arr[] = new String[5];
					arr[0] = "Train"+members.get(i).trainId;
					arr[1] = members.get(i).scheduled_time;
					arr[2] = members.get(i).live_loction;
					arr[3] = members.get(i).last_stop_time;
					arr[4] = members.get(i).late_time;
					modelT.addRow(arr);
				}
				tableT.invalidate();
			}
			
		}
	
		class syn implements ActionListener{

				@Override
				public void actionPerformed(ActionEvent e) {

					for (int i = 0; i < members.size(); i++) {
						System.out.println("train:"+members.get(i).trainId);
						System.out.println("scheduled time:"+members.get(i).scheduled_time);
						System.out.println("last_time:"+members.get(i).late_time);
						System.out.println("last_stop_time:"+members.get(i).last_stop_time);
						System.out.println("live_loction:"+members.get(i).live_loction);
					}
				}
				
			}
		
		class syn_back implements ActionListener{

				@Override
				public void actionPerformed(ActionEvent e) {
					//回到主菜单
					frame.dispose();
					new MenuGUI();

				}
				
			}
		
		
		 public static void main(String[] args) throws ClassNotFoundException, IOException {
			SynchronizeGUI s = new SynchronizeGUI(10);
			
		}
	 }