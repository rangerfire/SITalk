package project_new;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

import javax.swing.JOptionPane;

public class Check {
	
	public static int sign;
	//check the username and password
	public Check(String username, String password, String time)
	{
		try
		{
			
			//send request to the server
			DatagramSocket ds = new DatagramSocket();
			byte[] sendData = new byte[1024];
			byte[] receiveData = new byte[1024];
			String request = "check;" + username + ";" + password + ";" + " ";
			sendData = request.getBytes();
			DatagramPacket send_pack = new DatagramPacket(sendData, sendData.length, new InetSocketAddress("127.0.0.1", 8080));
			ds.send(send_pack);	
			
			//receive from server
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			ds.receive(receivePacket);
			String result = new String(receivePacket.getData());
			//right password : 1
			if(result.substring(0, 1).equals("1"))
			{
				sign = 1;
				Client cl = new Client(username, password, time);
			}
			else
				//wrong password : 2
				if(result.substring(0, 1).equals("2"))
				{
					sign = 2;
					JOptionPane.showMessageDialog(null, "Wrong Password!", "Alert", JOptionPane.ERROR_MESSAGE);
				}
				//user not exist: 3
				else
				{
					sign = 3;
					JOptionPane.showMessageDialog(null, "User Not exist!", "Alert", JOptionPane.ERROR_MESSAGE);
				}
			
			
		}
		catch(Exception e)
		{
			System.out.println("Check Error: " + e);
		}
		
	}


}
