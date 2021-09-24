package org.openjfx.chatClient.utilities;

import java.rmi.Naming;
import java.rmi.Remote;

public class RMIManager {

	public static RMIManager rmi;
	private String ip;

	public RMIManager(String ip) {

		if (System.getSecurityManager() == null) {
			System.setProperty("java.security.policy","file:./securityPolicy.policy");
			System.setSecurityManager(new SecurityManager());
		}
	}

	public static void createRMI(String ip) {
		if(RMIManager.rmi != null) {
			rmi.setIp(ip);
		}else {
			RMIManager.rmi = new RMIManager(ip);
			RMIManager.rmi.setIp(ip);
		}
	}

	public Remote getRemoteObject(String name) {
		try {
			Remote r = Naming.lookup("rmi://"+ip+"/"+name);
			return r;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

}
