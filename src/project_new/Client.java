package project_new;

import java.awt.Font;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import java.net.*;
import java.text.SimpleDateFormat;

public class Client {
	
	private int framewidth  = 1200;
	private int frameheight = 700;

	private int head_photo_width  = 150;
	private int head_photo_height = 150;
	
	private Font myFont = new Font("Arial", Font.BOLD, 20);
	private Font myFont_small = new Font("Arial", Font.ITALIC, 18);
	private Font myFont_chat = new Font("Arial", Font.PLAIN, 16);
	
	
	private JList person_list;	
	private String[] persons;
	
	public String getNowTime()
	{
		String nowTime = "";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		nowTime = df.format(date);
		return nowTime;
	}

	//constructor: log in
	public Client(String username, String password, String time) 
	{
		try{
			
			/*
			 * ------------------------------------------------------------------------------------------------------------
			ds = new DatagramSocket();
			
			//send username and pwd to the server(port:8000)
			//use ';' as the separator 
			String user_info = username + ";" + password;
			DatagramPacket send_dp = new DatagramPacket(user_info.getBytes(), user_info.length(), new InetSocketAddress("127.0.0.1", 8080));
			ds.send(send_dp);
			
			//receive message from server(store in receive_info)
			byte[] receive = new byte[1024];
			DatagramPacket receive_dp = new DatagramPacket(receive, receive.length);
			ds.receive(receive_dp);
			String receive_info = new String(receive_dp.getData());
			
			System.out.println(receive_info);
			
			* -----------------------------------------------------------------------------------------------------------------------
			*/
			
			//change the UI style to the system's
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 
		
			//1. Top-level container
			JFrame jf = new JFrame("Welcome:  " + username);
			jf.setSize(framewidth, frameheight);
			
			ImageIcon logo = new ImageIcon("C:\\Academy\\Stevens\\JavaWorkspace\\image\\521Logo.png");		//change the logo
			//------------fix----------------------
			Image img = logo.getImage();
			img = img.getScaledInstance(200, 200, Image.SCALE_DEFAULT);
			logo.setImage(img);
			//------------fix----------------------
			jf.setIconImage( logo.getImage( ));
			
			jf.setLocationRelativeTo(null);													//location of the frame
			//jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);						//click the (built-in) close button to exit
			
			//2. middle container
			JPanel panel = new JPanel();
			
			//3. Components (such as button)
			
			//head picture
			JLabel jl = new JLabel();
			ImageIcon head = new ImageIcon("C:\\Academy\\Stevens\\JavaWorkspace\\image\\521head.png");
			//------------fix----------------------
			img = head.getImage();
			img = img.getScaledInstance(head_photo_width, head_photo_height, Image.SCALE_DEFAULT);
			head.setImage(img);
			//------------fix----------------------
			jl.setIcon(head);
			jl.setBounds(20, 20, head.getIconWidth(), head.getIconHeight());
			panel.add(jl);
			
			//username button
			JButton b_name  = new JButton(username);
			b_name.setBounds(170, 40, 150, 50); 
			b_name.setContentAreaFilled(false);  			//set the btn transparent
			b_name.setFont(myFont);
			panel.add(b_name);
			
			//friends button
			JButton b_friends  = new JButton("Friends");
			b_friends.setBounds(170, 120, 150, 50); 
			b_friends.setContentAreaFilled(false);  			//set the btn transparent 
			b_friends.setFont(myFont);
			panel.add(b_friends);
			
			//group button
			JButton b_group = new JButton("Group");
			b_group.setBounds(120, 570, 100, 45);
			b_group.setFont(myFont);
			panel.add(b_group);
			
			//write area textfield
			JTextField t_write = new JTextField();
			t_write.setBounds(370, 575, 650, 40);
			t_write.setFont(myFont_chat);
			panel.add(t_write);
			
			//send button
			JButton b_send = new JButton("Send");
			b_send.setBounds(1033, 575, 90, 40);
			b_send.setFont(myFont);
			panel.add(b_send);
			
			
			//persons list
			
			try {
				//send request to server (persons)
				
				DatagramSocket ds = new DatagramSocket();
				byte[] sendData = new byte[1024];
				byte[] receiveData = new byte[1024];
				String request = "persons";
				sendData = request.getBytes();
				DatagramPacket send_pack = new DatagramPacket(sendData, sendData.length, new InetSocketAddress("127.0.0.1", 8080));
				ds.send(send_pack);	
				
				//receive from the server
				
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				ds.receive(receivePacket);
				String person = new String(receivePacket.getData());
				//get the persons with the separator ";"
				String[] persons_temp = person.split(";");
				String[] persons = new String[persons_temp.length-1];
				for(int i=0;i<persons_temp.length-1;i++)
					persons[i] = persons_temp[i];
				//delete the last one
				person_list = new JList(persons);
				person_list.setBorder(BorderFactory.createTitledBorder("Who else:"));
				person_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				person_list.setBounds(30, 200, 280, 350);
				person_list.setFont(myFont_small);
				panel.add(person_list);
				
			}
			catch(Exception ee)
			{
				System.out.println("Persons Error1: " + ee);
			}

			//chat area Jlist
			JList chat_list = new JList();
			chat_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			chat_list.setBounds(370, 40, 750, 510);
			chat_list.setFont(myFont_chat);
			panel.add(chat_list);
			
			//4. Add the panel to the top-level container
			panel.setLayout(null);
			jf.setContentPane(panel);
			
			//5. Show the top-level frame
			jf.setVisible(true);
			
			//6. add listener
			
			//Send button
			b_send.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					
					try{
						
						DatagramSocket ds = new DatagramSocket();
						byte[] receiveData = new byte[1024];
						
						//send username, message and time to the server(port:8080)
						//use ';' as the separator 
						String sendMessage = "newMessage;" + username + ";" + getNowTime() + ";" + t_write.getText() + ";";
						DatagramPacket send_dp = new DatagramPacket(sendMessage.getBytes(), sendMessage.length(), new InetSocketAddress("127.0.0.1", 8080));
						ds.send(send_dp);
							
						//receive from the server
						
						DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
						ds.receive(receivePacket);
						String record = new String( receivePacket.getData() );
						//delete the last item
						String[] records_temp = record.split(";");
						String[] records = new String[records_temp.length-1];
						for(int i=0;i<records.length;i++)
							records[i] = records_temp[i];
						
						chat_list.setListData(records);
						chat_list.repaint();
						
					} 
					catch (Exception e) 
					{
						System.out.println("Send Button Error: " + e);
						e.printStackTrace();
					} 
					
					
				}
			});
					
			//close the window ---> tell the server to quit
			jf.addWindowListener(new WindowListener() {
				
				@Override
				public void windowClosing(WindowEvent e) {
					
					try {
						
						DatagramSocket ds = new DatagramSocket();
						//send username to the server(port:8080)
						//use ';' as the separator 
						String quit = "quit" + ";" + username + "; ";
						DatagramPacket send_dp = new DatagramPacket(quit.getBytes(), quit.length(), new InetSocketAddress("127.0.0.1", 8080));
						ds.send(send_dp);
						//System.out.println("closing!");
					}
					catch(Exception eee)
					{
						System.out.println("Window closing Error: " + eee);
					}
				}
				
				
				@Override
				public void windowClosed(WindowEvent e) {
					
					try {
						
						DatagramSocket ds = new DatagramSocket();
						//send username to the server(port:8080)
						//use ';' as the separator 
						String quit = "quit" + ";" + username + "; ";
						DatagramPacket send_dp = new DatagramPacket(quit.getBytes(), quit.length(), new InetSocketAddress("127.0.0.1", 8080));
						ds.send(send_dp);
						//System.out.println("closed!");
					}
					catch(Exception eee)
					{
						System.out.println("Window closed Error: " + eee);
					}	
				}
				
				
				@Override
				public void windowOpened(WindowEvent e) {
					// TODO Auto-generated method stub
				}
				
				@Override
				public void windowIconified(WindowEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void windowDeiconified(WindowEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void windowDeactivated(WindowEvent e) {
					// TODO Auto-generated method stub
					
				}
		
				@Override
				public void windowActivated(WindowEvent e) {
					// TODO Auto-generated method stub
					
				}
			});
			
			//ADD a new thread to refresh 2 lists
			new Thread(new Runnable() {
				
				@Override
				public void run() {

					while(true)
					{
						try {
	
							//1. refresh the person list
							try {
								
								//send request to server (persons)
								
								DatagramSocket ds = new DatagramSocket();
								byte[] sendData = new byte[1024];
								byte[] receiveData = new byte[1024];
								String request = "persons";
								sendData = request.getBytes();
								DatagramPacket send_pack = new DatagramPacket(sendData, sendData.length, new InetSocketAddress("127.0.0.1", 8080));
								ds.send(send_pack);	
								
								//receive from the server
								
								DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
								ds.receive(receivePacket);
								String person = new String(receivePacket.getData());
								//get the persons with the separator ";"
								String[] persons_temp = person.split(";");
								String[] persons = new String[persons_temp.length-1];
								for(int i=0;i<persons_temp.length-1;i++)
									persons[i] = persons_temp[i];
								//delete the last one
								person_list.setListData(persons);
								person_list.repaint();
							}
							catch(Exception ee)
							{
								System.out.println("Persons Error: " + ee);
							}
							
							//2. refresh the chat list
							try {
								
								DatagramSocket ds = new DatagramSocket();
								byte[] receiveData = new byte[1024];
								//send request and  time to the server(port:8080) 
								String request = "records;" + time + "; ";
								DatagramPacket send_dp = new DatagramPacket(request.getBytes(), request.length(), new InetSocketAddress("127.0.0.1", 8080));
								ds.send(send_dp);
									
								//receive from the server
								
								DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
								ds.receive(receivePacket);
								String record = new String( receivePacket.getData() );
								//delete the last item
								String[] records_temp = record.split(";");
								String[] records = new String[records_temp.length-1];
								for(int i=0;i<records.length;i++)
									records[i] = records_temp[i];
								chat_list.setListData(records);
								chat_list.repaint();
								
							}
							catch(Exception e1)
							{
								System.out.println("Chat List Error1: " + e1);
							}
						
						Thread.sleep(1000);
						}
						catch(Exception et)
						{
							System.out.println("Thread Error: " + et);
						}
		
					}
				}//end run
			}).start();
			
		}//end try
		catch(Exception e)
		{
			System.out.println("Error: " + e);
		}
	}//end constructor
	

}//end class
