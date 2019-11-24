package project_new;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

public class Menu {

	public static String getTime()
	{
		String nowTime = "";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		nowTime = df.format(date);
		return nowTime;
	}
	
	
	public Menu() throws Exception
	{
		//change the UI style to the system's
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 
		
		//1. Top-level container
		JFrame jf = new JFrame("SITalk");
		jf.setSize(300,400);
		
		//set the logo and fix it 
		ImageIcon logo = new ImageIcon("C:\\Academy\\Stevens\\JavaWorkspace\\image\\521Logo.png");
			//------------fix----------------------
			Image img = logo.getImage();
			img = img.getScaledInstance(200, 200, Image.SCALE_DEFAULT);
			logo.setImage(img);
			//------------fix----------------------
		jf.setIconImage( logo.getImage( ));
		
		jf.setLocationRelativeTo(null);													//location of the frame
		jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);						//click the (built-in) close button to exit
		
		//2. middle container
		JPanel panel = new JPanel();
		
		//3. Components (such as button)
		
		JTextField t_username = new JTextField("Username");
		t_username.setBounds(30, 110, 160, 50);
		panel.add(t_username);
		
		JTextField t_pwd = new JTextField("Password");
		t_pwd.setBounds(30, 180, 160, 50);
		panel.add(t_pwd);
		
		
		JButton b_signup  = new JButton("Sign Up");
		b_signup.setBounds(185, 110, 80, 50);
//		b_signup.setBorderPainted(false); 
		b_signup.setContentAreaFilled(false);  			//set the btn transparent
//		b_signup.setIcon(new ImageIcon(getClass().getResource("qq.png"))); 
		panel.add(b_signup);
		
		JButton b_findback  = new JButton("Find Back");
		b_findback.setBounds(178, 180, 100, 50);
		b_findback.setContentAreaFilled(false);  			//set the btn transparent
		panel.add(b_findback);
		
		JButton b_login   = new JButton("Log In");
		b_login.setBounds(30, 260, 100, 50);
		panel.add(b_login);
		
		JButton b_signout = new JButton("Sign out");
		b_signout.setBounds(160, 260, 100, 50);
		panel.add(b_signout);
		
		//set welcome logo
		JLabel jl = new JLabel();
		ImageIcon welcome = new ImageIcon("C:\\Academy\\Stevens\\JavaWorkspace\\image\\521chat.png");
		//------------fix----------------------
		img = welcome.getImage();
		img = img.getScaledInstance(150, 150, Image.SCALE_DEFAULT);
		welcome.setImage(img);
		//------------fix----------------------
		jl.setIcon(welcome);
		jl.setBounds(62, -15, welcome.getIconWidth(), welcome.getIconHeight());
		panel.add(jl);
		
		
		//4. Add the panel to the top-level container
		panel.setLayout(null);
		jf.setContentPane(panel);
//		
		//5. Show the top-level frame
		jf.setVisible(true);
		
		//6. add listener of the button and textfield
		
		//sign up
		b_signup.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					SignUp su = new SignUp();
					jf.dispose();
				}
				catch(Exception eSU)
				{
					System.out.println("Sign Up Error: " + eSU);
				}
				
			}
		});
		
		
		//log in
		b_login.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				try{
					String username = t_username.getText();
					String password = t_pwd.getText();
					Check c = new Check(username, password, getTime());
					if(c.sign == 1)
						jf.dispose();
				} 
				catch (Exception e) 
				{
					System.out.println("Error: " + e);
					e.printStackTrace();
				} 
				
				
			}
		});
	}
	
	//another constructor!!!
	public Menu(String username, String password) throws Exception
	{
		//change the UI style to the system's
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 
		
		//1. Top-level container
		JFrame jf = new JFrame("SITalk");
		jf.setSize(300,400);
		
		//set the logo and fix it 
		ImageIcon logo = new ImageIcon("C:\\Academy\\Stevens\\JavaWorkspace\\image\\521Logo.png");
			//------------fix----------------------
			Image img = logo.getImage();
			img = img.getScaledInstance(200, 200, Image.SCALE_DEFAULT);
			logo.setImage(img);
			//------------fix----------------------
		jf.setIconImage( logo.getImage( ));
		
		jf.setLocationRelativeTo(null);													//location of the frame
		jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);						//click the (built-in) close button to exit
		
		//2. middle container
		JPanel panel = new JPanel();
		
		//3. Components (such as button)
		
		JTextField t_username = new JTextField(username);
		t_username.setBounds(30, 110, 160, 50);
		panel.add(t_username);
		
		JTextField t_pwd = new JTextField(password);
		t_pwd.setBounds(30, 180, 160, 50);
		panel.add(t_pwd);
		
		
		JButton b_signup  = new JButton("Sign Up");
		b_signup.setBounds(185, 110, 80, 50);
//		b_signup.setBorderPainted(false); 
		b_signup.setContentAreaFilled(false);  			//set the btn transparent
//		b_signup.setIcon(new ImageIcon(getClass().getResource("qq.png"))); 
		panel.add(b_signup);
		
		JButton b_findback  = new JButton("Find Back");
		b_findback.setBounds(178, 180, 100, 50);
		b_findback.setContentAreaFilled(false);  			//set the btn transparent
		panel.add(b_findback);
		
		JButton b_login   = new JButton("Log In");
		b_login.setBounds(30, 260, 100, 50);
		panel.add(b_login);
		
		JButton b_signout = new JButton("Sign out");
		b_signout.setBounds(160, 260, 100, 50);
		panel.add(b_signout);
		
		//set welcome logo
		JLabel jl = new JLabel();
		ImageIcon welcome = new ImageIcon("C:\\Academy\\Stevens\\JavaWorkspace\\image\\521chat.png");
		//------------fix----------------------
		img = welcome.getImage();
		img = img.getScaledInstance(150, 150, Image.SCALE_DEFAULT);
		welcome.setImage(img);
		//------------fix----------------------
		jl.setIcon(welcome);
		jl.setBounds(62, -15, welcome.getIconWidth(), welcome.getIconHeight());
		panel.add(jl);
		
		
		//4. Add the panel to the top-level container
		panel.setLayout(null);
		jf.setContentPane(panel);
//		
		//5. Show the top-level frame
		jf.setVisible(true);
		
		//6. add listener of the button and textfield
		
		//sign up
		b_signup.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					SignUp su = new SignUp();
					jf.dispose();
				}
				catch(Exception eSU)
				{
					System.out.println("Sign Up Error: " + eSU);
				}
				
			}
		});
		
		
		//log in
		b_login.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				try{
					Check c = new Check(username, password, getTime());
					if(c.sign == 1)
						jf.dispose();
				} 
				catch (Exception e) 
				{
					System.out.println("Error: " + e);
					e.printStackTrace();
				} 
				
				
			}
		});
	}
	
	
	public static void main(String[] args) throws Exception
	{
		
		new Menu();
		
	}
	
}
