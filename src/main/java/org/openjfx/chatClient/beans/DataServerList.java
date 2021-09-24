package org.openjfx.chatClient.beans;

import java.io.Serializable;

public class DataServerList implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String ip;
    private String name;
    private String port;
    
	public DataServerList(String ip, String port, String name) {
		this.ip =ip;
		this.port = port;
		this.name = name;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
    public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}
}
