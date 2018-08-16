package yang;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import g.list;



public class MenuGUI {
	JFrame frame;
	JPanel panel;
	JButton[] button;
	public MenuGUI() {
		frame=new JFrame("Menu!");
		panel=new JPanel();  
		button=new JButton[8];
		button[0]=new JButton("Information");
		button[1]=new JButton("Synchronize");
		button[2]=new JButton("Assign");
		button[3]=new JButton("Shedule");
		button[4]=new JButton("On-board Screen");
		button[5]=new JButton("Stop Screen");
		button[6]=new JButton("Remotely Control");
		button[7]=new JButton("Exit");
		button[0].addActionListener(new Info());
		button[1].addActionListener(new Syn());
		button[2].addActionListener(new Ass());
		button[3].addActionListener(new Schedule());
		button[4].addActionListener(new OB());
		button[5].addActionListener(new SC());
		button[6].addActionListener(new RC());
		button[7].addActionListener(new exit());
	    GridLayout gridLayout = new GridLayout(4,2);
		panel.setLayout(gridLayout);
		gridLayout.setHgap(30);
		gridLayout.setVgap(30);
			for(int i=0;i<8;i++){
				panel.add(button[i]);
			}

		frame.getContentPane().add(panel);
//		frame.pack();  
		frame.setSize(500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	class Info implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			frame.dispose();
			try {
				InformationGUI i = new InformationGUI();
			} catch (ClassNotFoundException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
		}
	
	}
	class Syn implements ActionListener{
	
		@Override
		public void actionPerformed(ActionEvent e) {
			frame.dispose();
			try {
				SynchronizeGUI s = new SynchronizeGUI(10);
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			
		}
		
	}
	class Ass implements ActionListener{
	
		@Override
		public void actionPerformed(ActionEvent e) {
			frame.dispose();
			AssignGUI a = new AssignGUI();
			
			
			
		}
		
	}
	class OB implements ActionListener{
	
		@Override
		public void actionPerformed(ActionEvent e) {
			frame.dispose();
			
			try {
				OnBoardGUI ob = new OnBoardGUI();
			} catch (ClassNotFoundException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
		}
		
	}
	class SC implements ActionListener{
	
		@Override
		public void actionPerformed(ActionEvent e) {
			frame.dispose();
			try {
				StopScreenGUI sc = new StopScreenGUI();
			} catch (ClassNotFoundException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			
		}
		
	}
	class Schedule implements ActionListener{
	
		@Override
		public void actionPerformed(ActionEvent e) {
			frame.dispose();
			try {
				ScheduleRouteGUI sr = new ScheduleRouteGUI();
			} catch (ClassNotFoundException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
		}
		
	}
	class RC implements ActionListener{
	
		@Override
		public void actionPerformed(ActionEvent e) {
			frame.dispose();
			try {
				RemotelyControl rc = new RemotelyControl();
			} catch (ClassNotFoundException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	class exit implements ActionListener{
	
		@Override
		public void actionPerformed(ActionEvent e) {
			//回到主菜单
			frame.dispose();
		}
		
	}

	public static void main(String[] args) {
		MenuGUI m = new MenuGUI();
		
	}
}
