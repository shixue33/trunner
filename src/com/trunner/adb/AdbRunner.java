package com.trunner.adb;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;

import com.trunner.bo.Function;
import com.trunner.bo.Test;
import com.trunner.init.Config;

public class AdbRunner {
	private Test test;
	private ArrayList<Function> functions;
	private Config config;
	private int tryCount = 1;
	private Process p = null;
	private ArrayList<String> outPutInfo = new ArrayList<String>();
	
	
	public AdbRunner() {
		super();
	}

	public AdbRunner(Test test,ArrayList<Function> functions,Config config){
		this.test = test;
		this.functions = functions;
		this.config = config;		
	}
	
	public void install(){		
		if(config.apkPath!=null&&config.apkPath.length()>0){
			this.adbExcute("adb -s "+test.getDeviceSerial()+" uninstall "+config.apkPackageName);
			this.adbExcute("adb -s "+test.getDeviceSerial()+" uninstall "+config.testApkPackageName);
			this.adbExcute("adb -s "+test.getDeviceSerial()+" install "+config.apkPath);
			this.adbExcute("adb -s "+test.getDeviceSerial()+" install "+config.testApkPath);	
		}		
	}
	
	
	public void getDevicesInfo(){		
		this.adbExcute("adb -s "+test.getDeviceSerial()+" shell cat /system/build.prop | grep \"product\"");
		for(String temp:outPutInfo){
			if(temp.startsWith("ro.usb.product.string")){
				test.setDeviceName((temp.split("="))[1]);
			}
		}
	}
	public ArrayList<String> getDeviceSerials(){		
		ArrayList<String> serials = new ArrayList<String>();
		this.adbExcute("adb devices");
		if(outPutInfo.size()>1){
			for(int i = 1;i<outPutInfo.size();i++){
				String temp = outPutInfo.get(i);				
				String[] strs = temp.split("\t");
				serials.add(strs[0].trim());
			}
		}
		
		return serials;
	}
	
	/**
	 * 发送adb命令，将接收到的数据返回到ArrayList中
	 * */
	public void excTest(){
		boolean success ;
		boolean fail;
		boolean crashError;
		if(config.testTryCount!=null&&config.testTryCount.length()>0){
			tryCount = Integer.valueOf(config.testTryCount);
		}
		for(Function function:functions){
			success = false;
			fail = false;
			crashError = false;
			//若没有成功，则重复执行3次
			//测试用例总数+1
			
			for(int i = 1;i<=tryCount;i++){
				success = false;
				fail = false;
				crashError = false;
				//adbExcute("adb -s "+test.getDeviceSerial()+" shell am instrument -w -e class "+config.testApkPackageName+"."+
				//		function.getClassName()+" "+config.testApkPackageName+"/android.test.InstrumentationTestRunner");
				if(function.getClassName().contains(".")){
					adbExcute("adb -s "+test.getDeviceSerial()+" shell am instrument -w -e class "+config.testApkPackageName+"."+
							function.getClassName().replace(".", "#")+" "+config.testApkPackageName+"/android.test.InstrumentationTestRunner");
				}else{
					adbExcute("adb -s "+test.getDeviceSerial()+" shell am instrument -w -e class "+config.testApkPackageName+"."+
							function.getClassName()+" "+config.testApkPackageName+"/android.test.InstrumentationTestRunner");
				}
				
				
				
				for(String s : outPutInfo){
					if(s.startsWith("INSTRUMENTATION_CODE")){
						crashError = true;
					}else if(s.startsWith("FAILURES!!!")){
						fail = true;
					}else if(s.startsWith("OK (")){
						success = true;
					}
					//插入耗时
					if(s.startsWith("Time")){
						String num =  (s.split(":")[1]).trim();
						function.setTotalTime(function.getTotalTime()+Float.parseFloat(num));
						
					}
					
				}
				if(success){
					if(function.getResult()==null){
						function.setResult("success");
					}
					function.setExecutions(function.getExecutions()+1);
				}				
				if(fail){
					function.setExecutions(function.getExecutions()+1);
					function.setResult("fail");
					function.setFailNum(function.getFailNum()+1);
				}				
				if(crashError){
					function.setExecutions(function.getExecutions()+1);
					function.setResult("fail");
					function.setFailNum(function.getFailNum()+1);
					function.setShortMsg("ProcessError!");
				}
				function.getLog().addAll(outPutInfo);
				function.getLog().add("-------------------");
				//test耗时增加本次耗时
				test.setTotalTime(test.getTotalTime()+function.getTotalTime());
				//如果第一次测试成功，则跳出循环
				if(success){
					break;
				}
				//System.out.println("Executions:"+function.getExecutions());
				//System.out.println("success:"+success);
				//System.out.println("fail:"+fail);
				//System.out.println("crashError:"+crashError);
			}
			
			if(function.getResult().equals("success")){
				test.setTestcaseNum(test.getTestcaseNum()+1);
				test.setSucessNum(test.getSucessNum()+1);
			}else{
				test.setFailNum(test.getFailNum()+1);
				test.setTestcaseNum(test.getTestcaseNum()+1);
			}
			
		}
		Date date = new Date();		
		test.setTestFinishTime(date.getTime());
	}
	public void adbExcute(String cmd){
		String temp = "";
		//清空用于接收数据的ArrayList
		outPutInfo.clear();
		DataInputStream dis = null;
		try {
			
			p = Runtime.getRuntime().exec(cmd);
			dis = new DataInputStream(p.getInputStream());
			
			try{
				while ((temp = dis.readLine()) != null){
					if(temp.length()>0){
						outPutInfo.add(temp);	
						System.out.println(temp);
					}
				}				
			}catch(Exception e){
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}		
		try {
			
			p.waitFor();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	public void adbLogcat(String cmd){
		String temp = "";
		//清空用于接收数据的ArrayList
		DataInputStream dis = null;
		String filePath = "log.txt"; 
		try {
			
			p = Runtime.getRuntime().exec(cmd);
			dis = new DataInputStream(p.getInputStream());
			
			FileOutputStream fops = new FileOutputStream(filePath);
			PrintStream ps = new PrintStream(fops);
			try{
				while ((temp = dis.readLine()) != null){
					if(temp.length()>0){
						ps.println(temp);
					}
				}				
			}catch(Exception e){
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}		
		try {
			
			p.waitFor();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	public void debug(){
		System.out.println(outPutInfo.size());
		for(String s :outPutInfo){
			System.out.println(s);
		}
	}
	
	public static void main(String[] args) {
		AdbRunner ar = new AdbRunner(null,null,null);
		//ar.adbExcute("adb shell cat /system/build.prop");

		ar.adbExcute("adb logcat -c");
		ar.adbExcute("adb logcat");
		
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ar.adbExcute("for /f \"tokens=2\" %%i in ('adb shell ps ^|findstr \"logcat\"') do adb shell kill %%i");
		ar.debug();
		
	}

}
