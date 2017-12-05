package controlor;
/**
 * this is server, it is monitoring....
 * waiting one client to connect 
 */

import model.*;
import tools.ManageServerConClientThread;
import tools.ServerConClientThread;

import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.*;
import java.util.*;

import org.json.simple.JSONObject;

import db.SQLHelper;

public class ServerSetup {

	public ServerSetup() {

		try {

			// port 9999 monitoring 
			System.out.println("I am a server, I'm monitoring 9999 port....");
			ServerSocket ss = new ServerSocket(9999);
			
			SQLHelper DB = new SQLHelper();
			//List<List<String>> userData = DB.getUserData();

			while (true) {
				Socket s = ss.accept();

				ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
				JSONObject m = new JSONObject();
				ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
				JSONObject u = (JSONObject) ois.readObject();
				
				
				System.out.println(MessageType.message_createAcc);
				//User u = (User) ois.readObject();
				//Message m = new Message();
				
				if(u.get("messType").toString().equals(MessageType.message_createAcc.toString())) {
					System.out.println("Creating new account");
					
					DB.newUser(u.get("userId").toString(), u.get("passwd").toString());
					
					m.put("mesType", MessageType.message_login_fail);
					oos.writeObject(m);
					s.close();
				}
				else if(u.get("messType").toString().equals(MessageType.message_login)) {
				
					DB.checkPass(u.get("userId").toString(), u.get("passwd").toString());
					
					//if (u.getPasswd().equals("123456")) {
					//if (u.get("passwd").toString().equals("123456")) {
					if (DB.checkPass(u.get("userId").toString(), u.get("passwd").toString())) {
						//m.setMesType("1");
						m.put("mesType", MessageType.message_succeed);
						oos.writeObject(m);
	
						ServerConClientThread scct = new ServerConClientThread(s);
						
						/*
						ManageServerConClientThread.addClientThread(u.getUserId(), scct);
						*/
						
						ManageServerConClientThread.addClientThread(u.get("userId").toString(), scct);
	
	
						scct.start();
	
						//scct.notifyOther(u.getUserId());
						scct.notifyOther((String) u.get("userId"));
					} else {
						//m.setMesType("2");
						m.put("mesType", MessageType.message_login_fail);
						oos.writeObject(m);
						s.close();
					}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

	}
	
}
