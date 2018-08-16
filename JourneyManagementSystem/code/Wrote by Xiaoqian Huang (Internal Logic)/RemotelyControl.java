package yang;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.ScrollPane;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.FormatFlagsConversionMismatchException;
import java.util.Random;
import java.util.prefs.BackingStoreException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;
import javax.swing.SwingWorker;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeListener;

public class RemotelyControl {

	private JButton[] train;
	private JButton bt_outward,bt_return,bt_stop,bt_RC_back,bt_speedup,bt_slowdown;
	private JPanel panelContainer_b,panelContainer_t,left,right,displayboard;
	private JFrame RCFrame;
	private JLabel lb_state,lb_speed;
	private JScrollPane jcp,dbjcp;
	private int train_num = 0;;
	ObjectSer objectSer;
	ArrayList<Train> trainList;
	ArrayList<Journey> journeyList;
	ArrayList<Route> routeList;
	ArrayList<Integer> trainSpeed;
	ArrayList<synMember> members;
	int[][] totalLateTime ;
	int ct = -1;
	int index = -1;
	SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
	int [] lateTime;
	String[] arrivingTime;
	private ArrayList<JProgressBar> progressBar;
    //private JButton startButton;
    private ArrayList<JTextArea> taskOutput;

    
	public RemotelyControl() throws ClassNotFoundException, IOException {
		objectSer = new ObjectSer();
		trainList = new ArrayList<>();
		journeyList = new ArrayList<>();
		routeList = new ArrayList<>();
		members = new ArrayList<>();
		trainSpeed = new ArrayList<>();
		
		trainList = objectSer.readTrainArrayList();
		journeyList = objectSer.readJourneyArrayList();
		routeList = objectSer.readRouteArrayList();
		members = objectSer.readSynMember();
		
		if (trainList!=null) {
			for (int i = 0; i < trainList.size(); i++) {
				if (trainList.get(i).CurrentStateD == false && trainList.get(i).CurrentStateJ == false) {
					train_num++;
				}
			}
		}
		lateTime = new int[train_num];
		arrivingTime = new String[train_num];
		RCFrame = new JFrame("Remotely Control");
		progressBar = new ArrayList<>();
		taskOutput = new ArrayList<>();
		panelContainer_b = new JPanel();
		panelContainer_b.setLayout(new GridBagLayout());
		panelContainer_t = new JPanel();
		panelContainer_t.setLayout(new BorderLayout());
		
		/**
		 * 左面版
		 */
		left = new JPanel();
		jcp = new JScrollPane(left);
		BoxLayout boxLayout = new BoxLayout(left, BoxLayout.Y_AXIS);
		left.setLayout(boxLayout);
		left.setBorder(new TitledBorder("TRAIN"));
		train = new JButton[train_num];
		for (int i = 0; i<train_num; i++){
			train[i] = new JButton("train "+trainList.get(i).TrainId);
			train[i].setName(""+trainList.get(i).TrainId);
			train[i].addActionListener(new swtichToTrain(trainList.get(i)));
			left.add(train[i]);
			trainSpeed.add( trainList.get(i).speed);
		}
		
		/**
		 * 右面板
		 */
		right = new JPanel();
		right.setBorder(new TitledBorder(""));
		lb_state = new JLabel("state: ");
		lb_speed = new JLabel("speed: ");
		bt_outward = new JButton("Outward");
		bt_outward.setActionCommand("start");
		bt_outward.addActionListener(new start(1));
		bt_return = new JButton("Return");
		bt_return.setActionCommand("start");
		bt_return.addActionListener(new start(0));
		bt_stop = new JButton("Stop");
		bt_stop.addActionListener(new stop());
		bt_RC_back = new JButton("back");
		bt_RC_back.addActionListener(new RC_back());
		bt_speedup = new JButton("Acceleration");
		bt_speedup.addActionListener(new speedup());
		bt_slowdown = new JButton("Deceleration");
		bt_slowdown.addActionListener(new slowdown());
		SpringLayout springlayout = new SpringLayout();
		right.setLayout(springlayout);
		right.add(lb_state);
		right.add(lb_speed);
		right.add(bt_outward);
		right.add(bt_return);
		right.add(bt_stop);
		right.add(bt_speedup);
		right.add(bt_slowdown);
		right.add(bt_RC_back);
	    springlayout.putConstraint(SpringLayout.NORTH,  lb_state, 15,
	    	      SpringLayout.NORTH,  right);
	    springlayout.putConstraint(SpringLayout.WEST,  lb_state, 100,
	    	      SpringLayout.WEST,  right);
	    springlayout.putConstraint(SpringLayout.NORTH,  lb_speed, 55,
	    	      SpringLayout.NORTH,  right);
	    springlayout.putConstraint(SpringLayout.WEST,  lb_speed, 100,
	    	      SpringLayout.WEST,  right);
	    springlayout.putConstraint(SpringLayout.NORTH, bt_outward , 15,
	    	      SpringLayout.NORTH,  right);
	    springlayout.putConstraint(SpringLayout.WEST, bt_outward , 400,
	    	      SpringLayout.WEST,  right);
	    springlayout.putConstraint(SpringLayout.NORTH, bt_return , 15,
	    	      SpringLayout.NORTH,  right);
	    springlayout.putConstraint(SpringLayout.WEST, bt_return , 500,
	    	      SpringLayout.WEST,  right);
	    springlayout.putConstraint(SpringLayout.NORTH, bt_stop , 55,
	    	      SpringLayout.NORTH,  right);
	    springlayout.putConstraint(SpringLayout.WEST, bt_stop , 450,
	    	      SpringLayout.WEST,  right);
	    springlayout.putConstraint(SpringLayout.NORTH, bt_speedup , 15,
	    	      SpringLayout.NORTH,  right);
	    springlayout.putConstraint(SpringLayout.WEST, bt_speedup , 350,
	    	      SpringLayout.WEST,  bt_outward);
	    springlayout.putConstraint(SpringLayout.NORTH, bt_slowdown , 55,
	    	      SpringLayout.NORTH,  right);
	    springlayout.putConstraint(SpringLayout.WEST, bt_slowdown , 350,
	    	      SpringLayout.WEST,  bt_outward);
	    springlayout.putConstraint(SpringLayout.NORTH, bt_RC_back , 30,
	    	      SpringLayout.NORTH,  right);
	    springlayout.putConstraint(SpringLayout.WEST, bt_RC_back , 950,
	    	      SpringLayout.WEST,  right);
	    /**
	     * 火车展示面板
	     */
	    displayboard = new JPanel();
	    displayboard.setLayout(new BoxLayout(displayboard, BoxLayout.Y_AXIS));
	    dbjcp = new JScrollPane(displayboard);
	    for (int i = 0; i < train.length; i++) {
	    	 javax.swing.SwingUtilities.invokeLater(new depart(i)); 
		}
	       
	    /**
	     * 安排位置
	     */
		GridBagConstraints c1 = new GridBagConstraints();
		c1.gridx = 0;
		c1.gridy = 0;
		c1.weightx = 3.0;
		c1.weighty = 1.0;
		c1.fill = GridBagConstraints.BOTH;
		panelContainer_b.add(jcp, c1);

		GridBagConstraints c2 = new GridBagConstraints();
		c2.gridx = 3;
		c2.gridy = 0;
		c2.weightx = 10.0;
		c2.weighty = 1.0;
		c2.fill = GridBagConstraints.BOTH;
		panelContainer_b.add(right, c2);
		

		
		panelContainer_t.add(panelContainer_b,BorderLayout.NORTH);
		panelContainer_t.add(dbjcp, BorderLayout.CENTER);
		RCFrame.add(panelContainer_t);
		RCFrame.setUndecorated(false);  
	        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();  
	        RCFrame.setSize(d.width, d.height);  

		RCFrame.setVisible(true);
		RCFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	class swtichToTrain implements ActionListener{
		Train thisTrain;
		public swtichToTrain(Train train) {
			thisTrain = train;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			ct = thisTrain.TrainId;
			for (int i = 0; i < train.length; i++) {
				if (train[i].getName().equals(""+ct)) {
					train[i].setForeground(Color.BLUE);
					index = i;
				}
				else {
					train[i].setForeground(Color.BLACK);
				}
				
			}
			//切换train
			String ns = null;
			String nsp = null;
			if (thisTrain.state == true) {//停车
				ns = "stop";
				nsp = "0km/h";

			}
			else {
				ns = "start";
				nsp = thisTrain.speed+"km/h";

			}
			
			lb_state.setText("state: "+ns);
			lb_speed.setText("speed:"+nsp);
		}
		
	}
	class start implements ActionListener{
		int flag = -1;
		
		public start(int flag) {
			this.flag = flag;
		}
		public void actionPerformed(ActionEvent e) {
			System.out.println("flag"+flag);
			if (ct>=0) {
				String ns = "start";
				String nsp = null;
				for (int i = 0; i < trainList.size(); i++) {
					if (trainList.get(i).TrainId == ct) {
						trainList.get(i).state = false;
						nsp = ""+trainList.get(i).speed;
						break;
					}
				}
				
				lb_state.setText("state: "+ns);
				lb_speed.setText("speed: "+nsp+" km/h");
				//start...
				
				Task task = new Task(index,flag);//给选择start的火车分配一个task
				//taskList.add(task);
		         task.addPropertyChangeListener(new ptc(index,task));
		         task.execute();
		         
			}
			else {
				System.out.println("Haven't choose a train!");
			}
		}
		
	}
	class stop implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if (ct>=0) {
				for (int i = 0; i < trainList.size(); i++) {
					if (trainList.get(i).TrainId == ct) {
						trainList.get(i).state = true;
						trainList.get(i).speed = 0;
						trainSpeed.set(i, 0);
						break;
					}
				}
				String ns = "stop";
				lb_state.setText("state: "+ns);
				lb_speed.setText("0 km/h");
				//start...
			}
			else {
				System.out.println("Haven't choose a train!");
			}
		}
		
	}
	class speedup implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if (ct>=0) {
				String ns = null;
				for (int i = 0; i < trainList.size(); i++) {
					if (trainList.get(i).TrainId == ct) {
						trainList.get(i).speed += 20;
						ns = ""+trainList.get(i).speed;
						trainSpeed.set(i, trainList.get(i).speed);
						break;
					}
				}
				
				lb_speed.setText("speed: "+ns+" km/h");

				
			}
			else {
				System.out.println("Haven't choose a train!");
			}
		}
		
	}
	class slowdown implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if (ct>=0) {
				String ns = null;
				for (int i = 0; i < trainList.size(); i++) {
					if (trainList.get(i).TrainId == ct) {
						trainList.get(i).speed -= 20;
						trainSpeed.set(i,  trainList.get(i).speed);
						if (trainList.get(i).speed <= 0) {
							trainList.get(i).state = true;
							trainList.get(i).speed = 0;
							System.out.println("stop!");

						}
						ns = ""+trainList.get(i).speed;
						break;
					}
				}
				
				lb_speed.setText("speed: "+ns+" km/h");
				
				
			}
			else {
				System.out.println("Haven't choose a train!");
			}
		}
		
	}
	class RC_back implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			//回到主菜单
			try {
				objectSer.writeSynMember(members);
				objectSer.writeTrainArrayList(trainList);
			} catch (ClassNotFoundException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			RCFrame.dispose();
			MenuGUI m = new MenuGUI();
		}
		
	}
	class depart implements Runnable{
    	int trainId;
    	public depart(int index) {
			this.trainId = Integer.parseInt(train[index].getName());
		}
		public void run() {
			JLabel trainindex = new JLabel("Train"+trainId);
			JProgressBar jpb = new JProgressBar();
  	    	jpb = new JProgressBar(0, 100);
  	    	jpb.setValue(0);
  	    	jpb.setStringPainted(true);
  	    	jpb.setName(""+trainId);
  	        progressBar.add(jpb);
  	    	JTextArea taskop = new JTextArea();
  	    	taskop = new JTextArea(5, 20);
  	    	taskop.setName(""+trainId);
  	    	taskop.setMargin(new Insets(5,5,5,5));
  	    	taskop.setEditable(false);
  	    	taskOutput.add(taskop);
  	        JPanel panel = new JPanel();
  	        panel.add(trainindex,BorderLayout.EAST);
  	        panel.add(jpb);
  	  
  	        displayboard.add(panel);
  	        displayboard.add(new JScrollPane(taskop));
  	       
		}
	}
    public Journey findJourney(int trainId) {
    	Journey thisjourney = null;
    	for (int i = 0; i < journeyList.size(); i++) {
			if (journeyList.get(i).AssignedTrainId == trainId) {
				thisjourney = journeyList.get(i);
			}
		}
		return thisjourney;
	}
    public Route findRoute(Journey journey){
    	Route thisroute = null;
    	for (int i = 0; i < routeList.size(); i++) {
			if (routeList.get(i).RouteId == journey.BelongRouteId) {
				thisroute = routeList.get(i);
			}
		}
    	return thisroute;
    }
    class Task extends SwingWorker<Void, Void> {
    	int index;
    	int flag;
    	Journey assignedJourney;
    	Route belongRoute;
    	int defaultSpeed;
    	
        /*
         * Main task. Executed in background thread.
         */
    	public Task(int index,int flag) {
			this.index = index;
			this.flag = flag;//去程1返程0
			assignedJourney = findJourney(trainList.get(index).TrainId);
			belongRoute = findRoute(assignedJourney);
			
		}
        @Override
        public Void doInBackground() throws ParseException, InterruptedException {
        	int DurationTime = 0;
        	int[] scheduledSpend = new int[assignedJourney.StopNumber-1];//每两站之间计划花费时间(/s)
        	int[] distance = new int[assignedJourney.StopNumber-1];//每站之间距离
        	int[] realSpend = new int[assignedJourney.StopNumber-1];//实际每站经历的时间
        	int[] outDistance = new int[assignedJourney.StopNumber-1];//去程从起点到每站要经过的距离
        	int[] returnDistance = new int[assignedJourney.StopNumber-1];//返程从起点到每站要经过的距离
        	defaultSpeed = belongRoute.defaultSpeed;
        	for (int j = 0; j < assignedJourney.StopNumber-1; j++) {
        		
//        		DurationTime += format.parse(belongRoute.Duration[j]).getTime();
        		String[] info = new String[3];
    			
    			info =	belongRoute.Duration[j].split(":");
    			int hour = Integer.parseInt(info[0]);
    			int minute = Integer.parseInt(info[1]);
    			int sec = Integer.parseInt(info[2]);	
    			scheduledSpend[j] = hour*60*60+minute*60+sec;
    			distance[j] = scheduledSpend[j]*defaultSpeed;
    			
    			DurationTime += scheduledSpend[j];
			}
        	outDistance[0] = (100*distance[0])/(DurationTime*defaultSpeed);
        	for (int i = 1; i < outDistance.length; i++) {
        		
        		outDistance[i] = (100*distance[i])/(DurationTime*defaultSpeed)+outDistance[i-1];
        		System.out.println("realdistance"+i+"="+outDistance[i]);
			}
        	for (int i = 0; i < returnDistance.length-1; i++) {
				returnDistance[i] = outDistance[outDistance.length-1-i]-outDistance[outDistance.length-2-i];
			}
        	returnDistance[returnDistance.length-1] = 100;
           System.out.println("duration"+DurationTime);
           
            if (flag == 1) {//去程 && ...
            	int latetime = 1;
            	do{
            		
	            	String real = format.format(Calendar.getInstance().getTime());
	            	String schedule = assignedJourney.OutWardArrivingTime[0];
	            	String[] oldtime= new String[3];
	         		String[] newtime = new String[3];
	         		oldtime =	real.split(":");
	         		newtime = schedule.split(":");
	    			int hour = Integer.parseInt(newtime[0])-Integer.parseInt(oldtime[0]);
	    			int minute = Integer.parseInt(newtime[1])-Integer.parseInt(oldtime[1]);
	    			int sec = Integer.parseInt(newtime[2])-Integer.parseInt(oldtime[2]);
	    			latetime = hour*60*60+minute*60+sec;
	            	System.out.println("距离出发还有:"+latetime+"s");
	            	taskOutput.get(index).append("距离出发还有:"+latetime+"s\n");
	            	if (latetime>0) {
	            		 Thread.sleep(1000);
					}
	            	
            	}while(latetime>0);
            	int stopcount = 0;
                //Initialize progress property.
                int progress = 0;
                setProgress(0);
	            while (progress < 100) {

	            	String schedule = null;
	            	String real = null;
	                
	                	if ((trainList.get(index).speed/defaultSpeed)<1) {//慢了
	                		
	     	                    Thread.sleep(1000);
	     	                   taskOutput.get(index).append("warning:实际速度小于预定值!("+defaultSpeed+")可能会晚点!\n");
	     	                   schedule = format.format(Calendar.getInstance().getTime());
	     	                  while(trainList.get(index).speed==0){
									Thread.sleep(1000);
	                		 }
	                		
	                			 System.out.println("man"+format.format(Calendar.getInstance().getTime()));
	     	                    Thread.sleep(1000*trainList.get(index).speed/defaultSpeed);
	     	                  real = format.format(Calendar.getInstance().getTime());
	     	                 
	                		 progress += 100/DurationTime;
	                		 
		                	
		                	String[] newtime = new String[3];
	                 		String[] oldtime = new String[3];
	            			newtime =	real.split(":");
	            			oldtime = schedule.split(":");
	            			int hour = Integer.parseInt(newtime[0])-Integer.parseInt(oldtime[0]);
	            			int minute = Integer.parseInt(newtime[1])-Integer.parseInt(oldtime[1]);
	            			int sec = Integer.parseInt(newtime[2])-Integer.parseInt(oldtime[2]);
	            			int latetime1 = hour*60*60+minute*60+sec;
	            			System.out.println("late"+latetime1);
	            			lateTime[index] +=latetime1;
		                	System.out.println("de"+lateTime[index]);
						}
	                	else {//正常或快
	                		
	     	                    Thread.sleep(1000*trainList.get(index).speed/defaultSpeed);
	     	                
	     	                
	                		progress += 100/DurationTime;
		                	
						}
					
	               
		                if (progress>= outDistance[stopcount]) {//到站
		                	
		                    stopcount++;
							
							taskOutput.get(index).append("\n[火车"+trainList.get(index).TrainId
	                     			+"到站"+belongRoute.StopName[stopcount]+"啦!-----");
	                     	System.out.println("去程延迟"+lateTime[index]);
	                     	arrivingTime[index] = format.format(Calendar.getInstance().getTime());
	                        taskOutput.get(index).append("##到站时间" + arrivingTime[index] + "##]");
	                        if (lateTime[index]>0) {//晚点
	            				taskOutput.get(index).append("-----late!"+lateTime[index]+"s\n");
	            				for (int i = 0; i < members.size(); i++) {
									if (members.get(i).trainId == trainList.get(index).TrainId) {
										members.get(i).late_time = ""+lateTime[index]+"s";
									}
								}
							}
	                     	lateTime[index] = 0;//清空
							if (stopcount == assignedJourney.StopNumber-1) {
								progress = 100;
								
								taskOutput.get(index).append("开始返程!\n\n");
							}
							}
						
	                
       			setProgress(Math.min(progress, 100));
	                
	            }
			}
            else if (flag == 0 ) {//返程 && realTime == return[0]
            	int latetime = 1;
            	do{
            		
	            	String real = format.format(Calendar.getInstance().getTime());
	            	String schedule = assignedJourney.ReturnArrivingTime[0];
	            	String[] oldtime= new String[3];
	         		String[] newtime = new String[3];
	         		oldtime =	real.split(":");
	         		newtime = schedule.split(":");
	    			int hour = Integer.parseInt(newtime[0])-Integer.parseInt(oldtime[0]);
	    			int minute = Integer.parseInt(newtime[1])-Integer.parseInt(oldtime[1]);
	    			int sec = Integer.parseInt(newtime[2])-Integer.parseInt(oldtime[2]);
	    			latetime = hour*60*60+minute*60+sec;
	            	System.out.println("距离出发还有:"+latetime+"s");
	            	taskOutput.get(index).append("距离出发还有:"+latetime+"s\n");
//	            	taskOutput.get(index).selstart = len(taskOutput.get(index)-1);
	            	taskOutput.get(index).setSelectionStart(taskOutput.get(index).getRows()-1);
	            	if (latetime>0) {
	            		 Thread.sleep(1000);
					}
            	}while(latetime>0);
            	int stopcount = 0;
            

                 //Initialize progress property.
                 int progress = 100;
                 setProgress(100);
	            while (progress >0 ) {
	                //Sleep for up to one second.

	               // progress -= (100*trainSpeed.get(index))/(defaultSpeed*DurationTime);
	                String schedule = null;
	            	String real = null;
	            	
		               
		                	if ((trainList.get(index).speed/defaultSpeed)<1) {//慢了
		                		 
		     	                    Thread.sleep(1000);
		     	                    schedule = format.format(Calendar.getInstance().getTime());
		     	                   taskOutput.get(index).append("\nwarning:实际速度小于预计值("+defaultSpeed+")可能会晚点!");
		     	                   
		                		 while(trainList.get(index).speed==0){
										Thread.sleep(1000);
		                		 }
		                		 try {
		     	                    Thread.sleep(1000*trainList.get(index).speed/defaultSpeed);
		     	                   real = format.format(Calendar.getInstance().getTime());
		     	                
		  		                String[] newtime = new String[3];
		  	             		String[] oldtime = new String[3];
		  	        			newtime =	real.split(":");
		  	        			oldtime = schedule.split(":");
		  	        			int hour = Integer.parseInt(newtime[0])-Integer.parseInt(oldtime[0]);
		  	        			int minute = Integer.parseInt(newtime[1])-Integer.parseInt(oldtime[1]);
		  	        			int sec = Integer.parseInt(newtime[2])-Integer.parseInt(oldtime[2]);
		  	        			int latetime1 = hour*60*60+minute*60+sec;
		  	        			System.out.println("late"+latetime1);
		  	        			lateTime[index] +=latetime1;
		  	                	System.out.println("de"+lateTime[index]);
		  	                	System.out.println("!!!!!!"+(100-returnDistance[stopcount]));
		     	                  progress -= 100/DurationTime;//一秒更新一次
		     	                } catch (InterruptedException ignore) {}
		     	                
						}
		                	else {//正常或快
		                		
	     	                    Thread.sleep(1000*trainList.get(index).speed/defaultSpeed);
	     	                
	     	                
	     	                   progress -= 100/DurationTime;//一秒更新一次
		                	
						}
	                	
		               
		                
		                if (progress<= (100-returnDistance[stopcount])) {//到站
		                     stopcount++;
		                     taskOutput.get(index).append("\n[火车"+trainList.get(index).TrainId
		                     			+"到站"+belongRoute.StopName[assignedJourney.StopNumber-stopcount-1]+"啦!-----");
		                     	
		                     	arrivingTime[index] = format.format(Calendar.getInstance().getTime());
		                        taskOutput.get(index).append("##到站时间" + arrivingTime[index] + "##]");
		                     	
		                     	if (lateTime[index]>0) {//晚点
		            				taskOutput.get(index).append("-----late!"+lateTime[index]+"s\n");
		            				for (int i = 0; i < members.size(); i++) {
										if (members.get(i).trainId == trainList.get(index).TrainId) {
											members.get(i).late_time = ""+lateTime[index]+"s";
										}
									}
								}
		                     	lateTime[index] = 0;//清空

							
							if (stopcount == assignedJourney.StopNumber-1) {
								progress = 0;
								
								taskOutput.get(index).append("结束旅程!");
							}
							}
						
	                
        			setProgress(Math.max(progress, 0));
	                
	            }
            }
//            }
            return null;
        }
        /*
         * Executed in event dispatching thread
         */
        @Override
        public void done() {
            Toolkit.getDefaultToolkit().beep();

            taskOutput.get(index).append("Done!\n");
            
        }
    }
  
   
   
   
    class ptc implements PropertyChangeListener{
    	 /**
         * Invoked when task's progress property changes.
         */
    	int index;//火车按钮编号
//    	Train runningTrain = null;
//    	Journey assignedJourney = null;
//    	Route belongedRoute = null;
        Task t1;
//        int defaultSpeed;
//        int DurationTime = 0;
//    	int[] scheduledSpend ;//每站计划花费时间(/s)
//    	int[] distance ;//每站之间距离
//    	int[] realSpend ;//实际每站经历的时间
//    	int[] realDistance ;
//    	int[] returnDistance;
//    	int stopcount = -1;
//    	int lastprogress = -1;
    	
    	public ptc(int index,Task task) {
			this.index = index;

			this.t1 = task;
//			int trainId = Integer.parseInt(train[index].getName());
//			for (int i = 0; i < trainList.size(); i++) {
//				if (trainList.get(i).TrainId == trainId) {
//					runningTrain = trainList.get(i);
//				}
//			}
//			assignedJourney = findJourney(trainId);
//			belongedRoute = findRoute(assignedJourney);
//			scheduledSpend = new int[assignedJourney.StopNumber-1];//每站计划花费时间(/s)
//	    	distance = new int[assignedJourney.StopNumber-1];//每站之间距离
//	    	realSpend = new int[assignedJourney.StopNumber-1];//实际每站经历的时间
//	    	realDistance = new int[assignedJourney.StopNumber-1];
//	    	returnDistance = new int[assignedJourney.StopNumber-1];
//			
//        	defaultSpeed = belongedRoute.defaultSpeed;
//        	for (int j = 0; j < assignedJourney.StopNumber-1; j++) {
//
//        		String[] info = new String[3];
//    			
//    			info =	belongedRoute.Duration[j].split(":");
//    			int hour = Integer.parseInt(info[0]);
//    			int minute = Integer.parseInt(info[1]);
//    			int sec = Integer.parseInt(info[2]);	
//    			scheduledSpend[j] = hour*60*60+minute*60+sec;
//    			distance[j] = scheduledSpend[j]*defaultSpeed;
//    			
//    			DurationTime += scheduledSpend[j];
//			}
//        	realDistance[0] = (100*distance[0])/(DurationTime*defaultSpeed);
//        	for (int i = 1; i < realDistance.length; i++) {
//        		
//        		realDistance[i] = (100*distance[i])/(DurationTime*defaultSpeed)+realDistance[i-1];
//        		System.out.println("realdistance"+i+"="+realDistance[i]);
//			}
//        	for (int i = 0; i < returnDistance.length-1; i++) {
//				returnDistance[i] = realDistance[realDistance.length-1-i]-realDistance[realDistance.length-2-i];
//			}
//        	returnDistance[returnDistance.length-1] = 100;
//			
		}
        public void propertyChange(PropertyChangeEvent evt) {
        	
            if ("progress" == evt.getPropertyName()) {
                int progress = (Integer) evt.getNewValue();

                progressBar.get(index).setValue(progress);
                
//               if (lastprogress <0) {
//				lastprogress = progress;
//				stopcount = 0;
//			}
                
               
//                System.out.println("last"+lastprogress+"pro"+progress);
//                if (lastprogress<progress) {//去程
//                	if (progress>= realDistance[stopcount]) {//到站
//	                	
//                     	stopcount++;
////                     	
//                     	taskOutput.get(index).append("火车"+trainList.get(index).TrainId
//                     			+"到站"+belongedRoute.StopName[stopcount]+"啦!\n");
//                     	System.out.println("去程延迟"+lateTime[index]);
//                     	arrivingTime[index] = format.format(Calendar.getInstance().getTime());
//                        taskOutput.get(index).append("##real time" + arrivingTime[index] + "##");
//                     	taskOutput.get(index).append("去程晚点"+lateTime[index]+"\n");
//                     	if (lateTime[index]>0) {//晚点
//            				taskOutput.get(index).append("late!"+lateTime[index]);
//            				for (int i = 0; i < members.size(); i++) {
//								if (members.get(i).trainId == runningTrain.TrainId) {
//									members.get(i).late_time = ""+lateTime[index]+"s";
//								}
//							}
//						}
//                     	lateTime[index] = 0;//清空
//
//            			
//                	}
//
//						if (progress == 100) {
//							taskOutput.get(index).append("开始返程!\n");
//
//	                        taskOutput.get(index).append("##real time" + arrivingTime[index] + "##");
//							
//						}
//					
//				}
//                else if (lastprogress>progress) {//返程
//                	if (progress<= (100-returnDistance[stopcount])) {//到站
//	                	
//                     	stopcount++;
//
//                     	taskOutput.get(index).append("火车"+trainList.get(index).TrainId
//                     			+"到站"+belongedRoute.StopName[assignedJourney.StopNumber-stopcount-1]+"啦!\n");
//                     	System.out.println("返回延迟"+lateTime[index]);
//                     	arrivingTime[index] = format.format(Calendar.getInstance().getTime());
//                        taskOutput.get(index).append("##real time" + arrivingTime[index] + "##");
//                     	taskOutput.get(index).append("返回晚点"+lateTime[index]+"\n");
//                     	if (lateTime[index]>0) {//晚点
//            				taskOutput.get(index).append("late!"+lateTime[index]);
//            				for (int i = 0; i < members.size(); i++) {
//								if (members.get(i).trainId == runningTrain.TrainId) {
//									members.get(i).late_time = ""+lateTime[index]+"s";
//								}
//							}
//						}
//                     	lateTime[index] = 0;//清空
//
//                     	
//						if (progress == 0) {
//
//							taskOutput.get(index).append("结束旅程!\n");
//							arrivingTime[index] = format.format(Calendar.getInstance().getTime());
//	                        taskOutput.get(index).append("##real time" + arrivingTime[index] + "##");
//
//						}
//					}
//				
//
//                lastprogress = progress;
//
//            }
        }
    }
    }
	public static void main(String[] args) throws ClassNotFoundException, IOException {
		RemotelyControl r = new RemotelyControl();
	}
	

}
