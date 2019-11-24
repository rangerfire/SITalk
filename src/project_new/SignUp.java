package project_new;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

public class SignUp{

			public SignUp() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException
			{
				//change the UI style to the system's
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 
				
				//1. Top-level container
				JFrame jf = new JFrame("Sign Up");
				jf.setSize(400,500);
				
				//set the logo and fix it 
				ImageIcon logo = new ImageIcon("C:\\Academy\\Stevens\\JavaWorkspace\\image\\521Logo.png");
					//------------fix----------------------
					Image img = logo.getImage();
					img = img.getScaledInstance(200, 200, Image.SCALE_DEFAULT);
					logo.setImage(img);
					//------------fix----------------------
				jf.setIconImage( logo.getImage( ));
				
				jf.setLocationRelativeTo(null);													//location of the frame
				
				//2. middle container
				JPanel panel = new JPanel();
				
				//3. Components (such as button)
				
				JButton b_un  = new JButton("Username");
				b_un.setBounds(20, 130, 100, 50);
				b_un.setContentAreaFilled(false);  			//set the btn transparent
				panel.add(b_un);
				
				JTextField t_username = new JTextField();
				t_username.setBounds(130, 130, 200, 50);
				panel.add(t_username);
				
				JButton b_pw  = new JButton("Password");
				b_pw.setBounds(20, 200, 100, 50);
				b_pw.setContentAreaFilled(false);  			//set the btn transparent
				panel.add(b_pw);
				
				JTextField t_pwd = new JTextField();
				t_pwd.setBounds(130, 200, 200, 50);
				panel.add(t_pwd);
				
				JButton b_cf  = new JButton("Confirm");
				b_cf.setBounds(20, 270, 100, 50);
				b_cf.setContentAreaFilled(false);  			//set the btn transparent
				panel.add(b_cf);
				
				JTextField t_confirm = new JTextField();
				t_confirm.setBounds(130, 270, 200, 50);
				panel.add(t_confirm);
				
				JButton b_create   = new JButton("Create");
				b_create.setBounds(130, 360, 120, 50);
				panel.add(b_create);
			
				
				//set welcome logo
				JLabel jl = new JLabel();
				ImageIcon welcome = new ImageIcon("C:\\Academy\\Stevens\\JavaWorkspace\\image\\521chat.png");
				//------------fix----------------------
				img = welcome.getImage();
				img = img.getScaledInstance(180, 180, Image.SCALE_DEFAULT);
				welcome.setImage(img);
				//------------fix----------------------
				jl.setIcon(welcome);
				jl.setBounds(100, -24, welcome.getIconWidth(), welcome.getIconHeight());
				panel.add(jl);
				
				
				//4. Add the panel to the top-level container
				panel.setLayout(null);
				jf.setContentPane(panel);
//				
				//5. Show the top-level frame
				jf.setVisible(true);
				
				//6. add listener of the button and textfield
				
				jf.addWindowListener(new WindowListener() {
					
					@Override
					public void windowOpened(WindowEvent arg0) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowIconified(WindowEvent arg0) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowDeiconified(WindowEvent arg0) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowDeactivated(WindowEvent arg0) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowClosing(WindowEvent arg0) {
						try {
							new Menu();
						} catch (Exception em) {
							System.out.println("New Menu Error: " + em);
						}
						jf.dispose();
						
					}
					
					@Override
					public void windowClosed(WindowEvent arg0) {
				
						
					}
					
					@Override
					public void windowActivated(WindowEvent arg0) {
						// TODO Auto-generated method stub
						
					}
				});

				b_create.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {						
						String username = t_username.getText();
						String password = t_pwd.getText();
						String confirm = t_confirm.getText();
						//1. username and password could not be null
						if(username.length() == 0)
							JOptionPane.showMessageDialog(null, "Username can't be null!", "Alert", JOptionPane.ERROR_MESSAGE);
						else
							if(password.length() == 0)
								JOptionPane.showMessageDialog(null, "Password can't be null!", "Alert", JOptionPane.ERROR_MESSAGE);
							else
								//2. first check the two time password
								if( !password.equals(confirm) )
								{
									JOptionPane.showMessageDialog(null, "Please confirm your password!", "Alert", JOptionPane.ERROR_MESSAGE);	
								}
								//3. if password confirmed, ask the server to create an account
								else
								{
									try {
										//send request to the server
										DatagramSocket ds = new DatagramSocket();
										byte[] sendData = new byte[1024];
										byte[] receiveData = new byte[1024];
										String request = "signup;" + username + ";" + password + ";" + " ";
										sendData = request.getBytes();
										DatagramPacket send_pack = new DatagramPacket(sendData, sendData.length, new InetSocketAddress("127.0.0.1", 8080));
										ds.send(send_pack);	
										
										//receive from server
										DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
										ds.receive(receivePacket);
										String result = new String(receivePacket.getData());
										//create successful : 1
										if(result.substring(0, 1).equals("1"))
										{
											new Menu(username,password);
											jf.dispose();
										}
										else
											//user already exist : 0
											if(result.substring(0, 1).equals("0"))
											{
												JOptionPane.showMessageDialog(null, "Username already exists!", "Alert", JOptionPane.ERROR_MESSAGE);
											}
											
										
									}
									catch(Exception esu2)
									{
										System.out.println("Sign UP Error 2: " + esu2);
									}	
								}//end last else{}
						}//end void
					});
	}
}
