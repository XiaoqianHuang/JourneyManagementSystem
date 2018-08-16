package yang;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class AssignGUI {
	JFrame assignFrame;
	JPanel container,backpanel;
	JButton bt_asnD,bt_asnT;
	public AssignGUI() {
		assignFrame = new JFrame("Assign");
		container = new JPanel();
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		bt_asnD = new JButton("Assign Driver to Train");
		bt_asnD.addActionListener(new AsnD());
		bt_asnT = new JButton("Assign Train to Journey");
		bt_asnT.addActionListener(new AsnT());
		backpanel = new JPanel();
		backpanel.setLayout(new BorderLayout());
		JButton back = new JButton("back");
		back.addActionListener(new back());
		backpanel.add(BorderLayout.LINE_END,back);
		container.add(Box.createVerticalStrut(100));
		container.add(bt_asnD);
		container.add(Box.createVerticalStrut(50));
		container.add(bt_asnT);
		container.add(Box.createVerticalStrut(50));
		container.add(backpanel);
		assignFrame.getContentPane().add(container);
		assignFrame.setSize(280, 310);
		assignFrame.setLocationRelativeTo(null);
		assignFrame.setVisible(true);
		assignFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	class AsnD implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			assignFrame.dispose();
			try {
				AssignDriverGUI ad = new AssignDriverGUI();
			} catch (ClassNotFoundException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
			
		}
		
	}
	class AsnT implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			assignFrame.dispose();
			try {
				AssignTrainGUI at = new AssignTrainGUI();
			} catch (ClassNotFoundException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
		}
		
	}
	class back implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			//回到主菜单
			assignFrame.dispose();
			MenuGUI m = new MenuGUI();
			
		}
		
	}
	public static void main(String[] args) {
		AssignGUI a = new AssignGUI();
	}
}
