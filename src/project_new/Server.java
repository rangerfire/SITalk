package project_new;

import java.net.*;
import java.sql.*;

public class Server {
	
	private static String usr = "postgres";   
	private static String pwd = "951119";   
	private static String url = "jdbc:postgresql://localhost:5432/cs521"; 

	//JDBC
	public static void load()
	{
		try{
			Class.forName("org.postgresql.Driver");    
			System.out.println("Successfully loaded the driver!");  
		}catch(Exception e){
			System.out.println("Failed to load the driver!");    
			e.printStackTrace();   
		}
	}
	
	public static void main(String[] args) {
		
		try 
		{
			
			//---------------------  conneted to the database  -------------------------------------------
			load();
			Connection conn = DriverManager.getConnection(url, usr, pwd);    
			System.out.println("Successfully connected to the server!");    
			// get query result to ResultSet rs
			Statement stmt = conn.createStatement();
			
			//--------------------------------------------------------------------------------------------
			
			//at first, no one in the room
			stmt.executeUpdate("truncate table persons");
			
			
			//port number: 8080
			DatagramSocket serverSocket = new DatagramSocket(8080);
			byte[] receiveData = new byte[1024];
			byte[] sendData = new byte[1024];
			byte[] temp = new byte[1024];
			int i = 1;
			while(true)
			{
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				serverSocket.receive(receivePacket);
				String request = new String(receivePacket.getData());
				
				//if it is the request for persons list!!!!!!!!!!!!!!!!!!!!!!!!!!!!
				if(request.substring(0, 7).equals("persons"))
				{
					String response = "";
					//where is the sender?
					InetAddress IPAddress = receivePacket.getAddress();
					int port = receivePacket.getPort();
					
					ResultSet rs = stmt.executeQuery("select distinct(username) from persons");		
					while(rs.next())
					{
						response = response + rs.getString("username") + ";";
					}
					//send back the message(response)(注意：多加一个无效元素，方便接收端使用)
					sendData = response.getBytes();
					DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
					serverSocket.send(sendPacket);

				}
				//if it is the request for check the password
				else
					if(request.substring(0, 5).equals("check"))
					{
						String[] user_info = request.split(";");
						String username = user_info[1];
						String password = user_info[2];
							
						String response = "";
						//where is the sender?
						InetAddress IPAddress = receivePacket.getAddress();
						int port = receivePacket.getPort();
						
						ResultSet rs = stmt.executeQuery("select * from users");
						int count = 0;
						String u_temp;
						String p_temp;
						while(rs.next())
						{
							u_temp = rs.getString("username");
							p_temp = rs.getString("pwd");
							
							if(u_temp.equals(username))
							{
								count++;
								if(p_temp.equals(password))
									response = "1";
								else
									response = "2";
							}
						}
						//no such user
						if(count == 0)
							response = "3";
						
						//send back the message(response)
						sendData = response.getBytes();
						DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
						serverSocket.send(sendPacket);
						
						//pass the check -----> a new user come into the room ---> add this user to persons list
						if(response.equals("1"))
						{
							stmt.executeUpdate("insert into persons values('" + username + "')");
						}

					}//end if(check)
					else
						if(request.substring(0, 4).equals("quit"))
						{
							String[] user_info = request.split(";");
							String username = user_info[1];
							
							stmt.executeUpdate("delete from persons where username = '" + username + "'");
							
						}//end if(quit)
						else
							if(request.substring(0, 7).equals("records"))
							{
								String[] user_info = request.split(";");
								String user_time = user_info[1];
								
								String response = "";
								//where is the sender?
								InetAddress IPAddress = receivePacket.getAddress();
								int port = receivePacket.getPort();
								
								ResultSet rs = stmt.executeQuery("select * from records where m_time >= '" + user_time + "' order by m_time asc");
								
								while(rs.next())
								{
									response = response + rs.getString("username") + ": " + rs.getString("message") + ";";
								}
								
								//send back the message(response)
								sendData = response.getBytes();
								DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
								serverSocket.send(sendPacket);
								
								
							}//end if(records)
							else
								if(request.substring(0, 10).equals("newMessage"))
								{

									String[] user_info = request.split(";");
									String username = user_info[1];
									String user_time = user_info[2];
									String user_message = user_info[3];

									//where is the sender?
									InetAddress IPAddress = receivePacket.getAddress();
									int port = receivePacket.getPort();
									
									//add the message to database
									stmt.executeUpdate("insert into records values('" + username + "','" + user_message + "','" + user_time + "')" );
									
									String response = "";
									ResultSet rs = stmt.executeQuery("select * from records where m_time >= '" + user_time + "' order by m_time asc");
									
									while(rs.next())
									{
										response = response + rs.getString("username") + ": " + rs.getString("message") + ";";
									}
	
									//send back the message(response)
									sendData = response.getBytes();
									DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
									serverSocket.send(sendPacket);

								}//end if(newMessage)
								else 
									if(request.substring(0, 6).equals("signup"))
									{
										String[] user_info = request.split(";");
										String username = user_info[1];
										String password = user_info[2];									

										//where is the sender?
										InetAddress IPAddress = receivePacket.getAddress();
										int port = receivePacket.getPort();
										
										//find if the user is existed									
										
										String response = "";
										ResultSet rs = stmt.executeQuery("select * from records where username = '" + username + "'");
										int count = 1;
										while(rs.next())
										{
											count = 0;										
										}
										//if not exist, add the user into users
										if(count == 1)
										{
											stmt.executeUpdate("insert into users values('" + username + "','" + password + "')" );										
										}
										//1 --> success
										//0 --> exist
										response = response + count;
										
										//send back the message(response)
										sendData = response.getBytes();
										DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
										serverSocket.send(sendPacket);
									}//end if(signup)
				
				
			}
			
			
		}
		catch(Exception e)
		{
			System.out.println("ServerError: " + e);
		}
		finally
		{
			
		}

	}

}
