package com.trunner.init;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

import com.trunner.bo.Function;
import com.trunner.bo.Test;

public class Config {
	private Test test;
	private ArrayList<Function> functions = new ArrayList<Function>();
	private String cfgPath = "config/cfg.properties";
	public String apkPath ;
	public String testApkPath;
	public String apkPackageName;
	public String testApkPackageName;
	public String testTryCount;
	public String funsPath;
	public String mailServerHost;
	public String mailUserName;
	public String mailUserPassword;
	public String mailToAddress;
	
	
	public Config(Test test, ArrayList<Function> functions) {
		super();
		this.test = test;
		this.functions = functions;
	}
	
	//读取config/cfg.properties文件，填充设置项
	public void readConfig(){
		InputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(cfgPath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Properties p = new Properties();
		try {
			p.load(in);
		} catch (IOException e) {			
			e.printStackTrace();
		}
		apkPath = p.getProperty("apkPath");
		testApkPath= p.getProperty("testApkPath");
		apkPackageName= p.getProperty("apkPackageName");
		testApkPackageName= p.getProperty("testApkPackageName");
		funsPath= p.getProperty("funsPath");
		mailServerHost= p.getProperty("mailServerHost");
		mailUserName= p.getProperty("mailUserName");
		mailUserPassword= p.getProperty("mailUserPassword");
		mailToAddress= p.getProperty("mailToAddress");
		testTryCount= p.getProperty("testTryCount");
		
		
		test.setTestAPKName(apkPath);
	}
	public void debug(){
		System.out.println(apkPath);
		System.out.println(testApkPath);
		System.out.println(apkPackageName);
		System.out.println(testApkPackageName);
	}
	public static void main(String[] args) {
		Config c = new Config(null,null);
		c.readConfig();
		c.debug();
	}
	

}
