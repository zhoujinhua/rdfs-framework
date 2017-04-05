package com.rdfs.framework.quartz.utils;

import java.net.InetAddress;

public class IpUtil {

	@SuppressWarnings("static-access")
	public static String getIp(){
		InetAddress inetAddress = null;
		String ip = "";
        try {
        	inetAddress = inetAddress.getLocalHost();
             
            ip = inetAddress.getHostAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ip;
	}
	
	@SuppressWarnings("static-access")
	public static String getHostName(){
		InetAddress inetAddress = null;
		String hostName = "";
        try {
        	inetAddress = inetAddress.getLocalHost();
             
            hostName = inetAddress.getHostName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hostName;
	}
	
	public static void main(String[] args) {
		getIp();
		getHostName();
	}
}
