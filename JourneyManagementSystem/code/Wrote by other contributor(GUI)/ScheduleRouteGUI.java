package yang;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.dnd.DragGestureEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;

import g.NewJFrames;
import yang.MenuGUI.exit;

public class ScheduleRouteGUI {
	private JTextField new_stop_num;
	private ArrayList<JButton> bt_route;
	private ArrayList<JComboBox> stop_r;
	private ArrayList<ArrayList> stop,time;
	private ArrayList< JTextField> time_r;
	private JPanel panelContainer,left,left1,left2,right,right1,right2,cardp;
	private JFrame SR_frame,New_frame;
	private JButton add,delete,next,back;
	private JScrollPane lJScrollPane,mJScrollPane;
	private GridBagConstraints c1,c2;
	private CardLayout card;
	private int stopcount = -1;
	private ObjectSer objectSer;
	private ArrayList<Route> rArrayList;
	Random random;
	public ScheduleRouteGUI() throws ClassNotFoundException, IOException{
	random = new Random();
	objectSer = new ObjectSer();
	rArrayList = new ArrayList<>();
	
	rArrayList = objectSer.readRouteArrayList();
	
	stop = new ArrayList<>();
	time = new ArrayList<>();
	SR_frame = new JFrame("Schedule Route");
	panelContainer= new JPanel();
	panelContainer.setLayout(new GridBagLayout());
	
	card = new CardLayout();
	cardp = new JPanel();
	cardp.setLayout(card);
	
	/**
	 * 左面版
	 */
	left = new JPanel();
	left.setLayout(new BorderLayout());
	left1 = new JPanel();
	left2 = new JPanel();
	lJScrollPane = new JScrollPane(left1);
	lJScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	lJScrollPane.setPreferredSize(new Dimension(30, 130));
	BoxLayout boxLayout1 = new BoxLayout(left1, BoxLayout.Y_AXIS);
	left1.setLayout(boxLayout1);
	left1.setBorder(new TitledBorder("ROUTE"));
	bt_route = new ArrayList<JButton>();
	//init
	
	for (int i = 0; i < rArrayList.size(); i++) {
		Route r = rArrayList.get(i);
		int id = r.RouteId;
		
		
		createroute(r);
		JButton bt_r = new JButton("Route "+id);
		bt_r.addActionListener(new switchto(id));
		bt_route.add(bt_r);
		left1.add(bt_r);
		
	}
    add = new JButton("+");
    add.addActionListener(new add());
    delete = new JButton("-");
    delete.addActionListener(new delete());
    left2.setLayout(new BorderLayout());
    left2.add(add,BorderLayout.LINE_START);
    left2.add(delete,BorderLayout.LINE_END);
    left.add(lJScrollPane,BorderLayout.CENTER);
    left.add(left2,BorderLayout.SOUTH);
    

	c1 = new GridBagConstraints();
	c1.gridx = 0;
	c1.gridy = 0;
	c1.weightx = 1.0;
	c1.weighty = 1.0;

	c1.fill = GridBagConstraints.BOTH;
	panelContainer.add(left, c1);

	c2 = new GridBagConstraints();
	c2.gridx = 1;
	c2.gridy = 0;
	c2.weightx = 5.0;
	c2.weighty = 1.0;
	c2.fill = GridBagConstraints.BOTH;
	panelContainer.add(cardp, c2);

	
	SR_frame.add(panelContainer);

	SR_frame.setSize(900, 600);
	SR_frame.setLocationRelativeTo(null);
	SR_frame.setVisible(true);
	SR_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
}
	/**
	 * 创建右面板
	 * @param num stop数量,不包括Central station
	 */
	public void createroute(Route r) {
		int id = r.RouteId;
		//System.out.println(r.StopNumber);
		int num = r.StopNumber-1;
		//System.out.println("!!!"+num);
		int k = 0;
		JPanel[] p = new JPanel[4*num+2];
		JLabel[] l = new JLabel[4*num+2];
		JLabel[] min = new JLabel[2*num];
		for (int i = 0; i < 2*num; i++) {
			min[i] = new JLabel("/min");
		}
		for (int i = 0; i < 4*num+2; i++) {
			p[i] = new JPanel();
			l[i] = new JLabel();
			
		}
		stopcount = 1;
		for (int i = 1; i < 2*num+1; i++) {
			int c = i%2;
			if (c == 0) {
				l[i].setText(stopcount+"th station");
				stopcount++;
			}
			else{
				l[i].setText("duration:");
			}
		}
		for (int i = 2*num+2; i < 4*num+2; i++) {
			int c = i%2;
			if (c != 0) {
				l[i].setText(stopcount+"th station");
				stopcount++;
			}
			else{
				l[i].setText("duration:");
			}
		}
		l[0].setText("--->Outward:");
		l[2*num+1].setText("<---Return:");
		for (int i = 0; i < 4*num+2; i++) {
			System.out.println(l[i].getText());
			
		}
		stop_r = new ArrayList<>();
		time_r = new ArrayList<>();
		stop.add(stop_r);
		time.add(time_r);
//		System.out.println(num);
		JComboBox s ;
		JTextField t ;
		right1 = new JPanel();
		BoxLayout boxLayout2 = new BoxLayout(right1, BoxLayout.Y_AXIS);
		right1.setLayout(boxLayout2);
		int j = 0;
		s = new JComboBox(new String[]{"Central station"});
		p[j].add(l[j]);
		p[j].add(s);
		right1.add(p[j]);
		j++;
		stop_r.add(s);
		for (int i = 1; i <= num; i++) {
			t = new JTextField("[set duration]");
			if (r.Duration[i-1]!=null) {
				t.setText(""+r.Duration[i-1]);
			}
			t.setHorizontalAlignment(JTextField.CENTER);
			t.addMouseListener(new sa(t));
			p[j].add(l[j]);
			p[j].add(t);
			p[j].add(min[k++]);
			right1.add(p[j]);
			j++;
			time_r.add(t);
			s = new JComboBox(new String[]{"===choose a stop===","stop 1","stop 2","stop 3","stop 4","stop 5"});
			
			if (r.StopName[i-1]!=null) {
				for (int m = 1; m < s.getItemCount(); m++) {
					String item = (String) s.getItemAt(m);
					if (r.StopName[i].equals(item)) {
						s.setSelectedIndex(m);
						break;
					}
				}
			}
			
			s.addActionListener(new selectstop((stop.size()-1)));
			p[j].add(l[j]);
			p[j].add(s);
			right1.add(p[j]);
			j++;
			stop_r.add(s);
		}
		for (int i = 1; i <= num; i++) {
			s = new JComboBox(new String[]{"===choose a stop===","stop 1","stop 2","stop 3","stop 4","stop 5"});
			if (r.StopName[r.StopNumber-i]!=null) {
				for (int m = 1; m < s.getItemCount(); m++) {
					String item = (String) s.getItemAt(m);
					if (r.StopName[r.StopNumber-i].equals(item)) {
						s.setSelectedIndex(m);
						break;
					}
				}
			}
			s.addActionListener(new selectstop((stop.size()-1)));
			p[j].add(l[j]);
			p[j].add(s);
			right1.add(p[j]);
			j++;
			stop_r.add(s);
			
			t = new JTextField("[set duration]");
			if (r.Duration[r.StopNumber-i-1]!=null) {
				t.setText(""+r.Duration[r.StopNumber-i-1]);
			}
			t.setHorizontalAlignment(JTextField.CENTER);
			t.addMouseListener(new sa(t));
			p[j].add(l[j]);
			p[j].add(t);
			p[j].add(min[k++]);
			right1.add(p[j]);
			j++;
			time_r.add(t);
		}
		s = new JComboBox(new String[]{"Central station"});
		p[j].add(l[j]);
		p[j].add(s);
		right1.add(p[j]);
		stop_r.add(s);
		mJScrollPane = new JScrollPane(right1);
		mJScrollPane.setPreferredSize(new Dimension(400, 300));
		right = new JPanel();
		right.setLayout(new BorderLayout());
		right2 = new JPanel();
		right2.setLayout(new FlowLayout(FlowLayout.RIGHT));
		next = new JButton("->");
		back = new JButton("back");
		next.addActionListener(new next());
		back.addActionListener(new back());
		right2.add(next);
		right2.add(back);
		right.add(mJScrollPane,BorderLayout.CENTER);
		right.add(right2, BorderLayout.SOUTH);
		cardp.add(right,"p"+id);
		card.show(cardp, "p"+id);
		System.out.println("a"+stop_r.size());
	}
	class sa implements MouseListener{
		JTextField jt;
		public sa(JTextField jt) {
			this.jt = jt;
		}
		public void mouseClicked(MouseEvent e){
			jt.selectAll();
        }

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
	/**
	 * 添加route
	 * @author dabaiji
	 *
	 */
	class add implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			New_frame = new JFrame("new");
			JPanel nrp = new JPanel();
			JLabel sn = new JLabel("       Stop number: ");
			new_stop_num = new JTextField();
			Font font = new Font("斜体", Font.ITALIC, 10);
			Font font2 = new Font("粗体", Font.BOLD, 10);
			new_stop_num = new JTextField("(Except Central Station)",10);
			new_stop_num.setForeground(Color.GRAY);
			new_stop_num.setFont(font);
			new_stop_num.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e){
					new_stop_num.selectAll();
					new_stop_num.setText(null);
					new_stop_num.setFont(font2);
					new_stop_num.setForeground(Color.BLACK);
		        }
			});
			JButton confirm = new JButton("confirm");
			confirm.addActionListener(new confirm());
			JButton cancel = new JButton("cancel");
			cancel.addActionListener(new cancel());
			nrp.setLayout(new GridLayout(2, 2,40,40));
			nrp.add(sn);
			nrp.add(new_stop_num);
			nrp.add(confirm);
			nrp.add(cancel);
			new_stop_num.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e){
					new_stop_num.selectAll();
		        }
			});
			New_frame.add(nrp);

			New_frame.setSize(420, 120);
			New_frame.setLocationRelativeTo(null);
			New_frame.setVisible(true);
			New_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		}
		
	}
	/**
	 * 删除route
	 * @author dabaiji
	 *
	 */
	class delete implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			int dr = -1;
			for (int i = 0; i < cardp.getComponentCount(); i++) {
				Component c = cardp.getComponent(i);
				if (c.isVisible()) {
					dr = i;
				}
			}
			New_frame = new JFrame("delete");
			JPanel nrp = new JPanel();
			nrp.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 30));
			JLabel sn = new JLabel("      Are you sure you want to delete...-> route "+dr);
			
			JButton dconfirm = new JButton("confirm");
			dconfirm.addActionListener(new dconfirm(dr));
			JButton cancel = new JButton("cancel");
			cancel.addActionListener(new cancel());
//			nrp.setLayout(new GridLayout(2, 2,40,40));
			nrp.add(sn);
			nrp.add(dconfirm);
			nrp.add(cancel);
			
			New_frame.add(nrp);

			New_frame.setSize(350, 150);
			New_frame.setLocationRelativeTo(null);
			New_frame.setVisible(true);
			New_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
			
		}
		
	}
	/**
	 * 确认添加route
	 * @author dabaiji
	 *
	 */
	class confirm implements ActionListener{
		
		
		@Override
		public void actionPerformed(ActionEvent e) {
			boolean isNum = new_stop_num.getText().matches("[0-9]+");//检测id是否是数字
			if (isNum==false) {
				new_stop_num.setText("Must input an integer!");
				new_stop_num.selectAll();
				New_frame.validate();
				
			}
			else {
				
			
				int sn = Integer.parseInt(new_stop_num.getText());
				
				
				
//				try {
					
//					rArrayList = objectSer.readRouteArrayList();
					int newid;
					if (rArrayList.size()==0) {
						newid = 1;
					}
					else{
						newid = (rArrayList.get(rArrayList.size()-1).RouteId)+1;
					}
					JButton r = new JButton("Route "+newid);
					r.addActionListener(new switchto(newid));
					bt_route.add(r);
					left1.add(r);
					Route newRoute = new Route(newid, sn+1);
					newRoute.defaultSpeed = 200+random.nextInt(20);
					createroute(newRoute);
					rArrayList.add(newRoute);

				New_frame.dispose();
				SR_frame.validate();
			}
			
		}
		
	}
	/**
	 * 确认删除route
	 * @author dabaiji
	 *
	 */
	class dconfirm implements ActionListener{
		int dr;
		public dconfirm(int dr) {
			this.dr = dr;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			
			
			left1.remove(bt_route.get(dr));
			bt_route.remove(dr);
			left1.repaint();
			cardp.remove(cardp.getComponent(dr));
			rArrayList.remove(dr);

			stop.remove(dr);
			time.remove(dr);
			New_frame.dispose();
			SR_frame.validate();
			
		}
		
	}
	/**
	 * 取消 添加或删除route
	 * @author dabaiji
	 *
	 */
	class cancel implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			New_frame.dispose();
		}
	}
	/**
	 * 跳转到下一页(为route安排journey)
	 * @author dabaiji
	 *
	 */
	class next implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			Object[] options ={ "Back to assign" , "Confirm and next"};  
			int m = JOptionPane.showOptionDialog(null, "Confirm all changes？",
					"Warning",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);  
			System.out.print(m);
			if (m == 1) {//comfirm and next
				int dr = -1;
				
				for (int i = 0; i < cardp.getComponentCount(); i++) {
					Component c = cardp.getComponent(i);
					if (c.isVisible()) {
						dr = i;
					}
				}
				
				for (int j = 0; j < rArrayList.size(); j++) {
					System.out.println("This is route "+rArrayList.get(j).RouteId);
					System.out.println("num"+rArrayList.get(j).StopNumber);
					String s1 = (String)((JComboBox) stop.get(j).get(1)).getSelectedItem();
					System.out.println(s1);
					for (int i = 0; i < time.get(j).size()/2; i++) {
						//设置的时间
						String t =  ((JTextField) time.get(j).get(i)).getText();
						System.out.println(t);
						rArrayList.get(j).Duration[i] = t;
						System.out.println(rArrayList.get(j).Duration[i]);
					}
					
					for (int k = 0; k < stop.get(j).size()/2; k++) {
						//选的站
						String s = (String)((JComboBox) stop.get(j).get(k)).getSelectedItem();
						System.out.println(s);
						rArrayList.get(j).StopName[k] = s;
					}
				}
				
				try {
					objectSer.writeRouteArrayList(rArrayList);
				} catch (ClassNotFoundException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				SR_frame.dispose();

				try {
					ScheduleJourneyGUI sj = new ScheduleJourneyGUI(rArrayList.get(dr));
				} catch (ClassNotFoundException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		
	}
	/**
	 * 回到主菜单
	 * @author dabaiji
	 *
	 */
	class back implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			Object[] options ={ "Back to assign" , "Confirm and exit"};  
			int m = JOptionPane.showOptionDialog(null, "Confirm all changes？",
					"Warning",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);  
			System.out.print(m);
			if (m == 1) {//comfirm and exit
				try {
					objectSer.writeRouteArrayList(rArrayList);
				} catch (ClassNotFoundException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				SR_frame.dispose();
				MenuGUI mm = new MenuGUI();

			}
		}
		
	}
	/**
	 * 下拉列表选择stop
	 * @author dabaiji
	 *
	 */
	class selectstop implements ActionListener{
		int rot = -1;
		public selectstop(int i) {
			rot = i;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			
			JComboBox s = (JComboBox)e.getSource();
			int cho = s.getSelectedIndex();
			String str = (String) s.getSelectedItem();
			//System.out.println(str);
			for (int i = 1; i <stop.get(rot).size()/2; i++) {
				JComboBox chk = (JComboBox) stop.get(rot).get(i);
				if (chk.getSelectedIndex() == cho) {
					chk.setSelectedIndex(0);
				}
			}
			
			s.setSelectedIndex(cho);
			JComboBox cmp = (JComboBox) stop.get(rot).get((stop.get(rot).size()-1-stop.get(rot).indexOf(s)));
			cmp.setSelectedIndex(cho);

		}

		
		
	}
	

	/**
	 * 查看不同route
	 * @author dabaiji
	 *
	 */
	class switchto implements ActionListener{
		int bno;
	public switchto(int i ) {
			bno = i;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		card.show(cardp, "p"+bno);
	}
	}
	
	public static void main(String[] args) throws ClassNotFoundException, IOException {
		ScheduleRouteGUI sr = new ScheduleRouteGUI();
	}
	
}
