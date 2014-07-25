package com.trunner.adb;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;

import com.trunner.bo.Function;
import com.trunner.bo.Test;

public class AdbGetResult {
	public String[] funs;
	public Test test;
	public ArrayList<Function> functions;
	public AdbGetResult(){
		super();
	}
	public AdbGetResult(String[] funs,Test test,ArrayList<Function> functions){
		this.funs = funs;
		this.test = test;
		this.functions = functions;
	}
	public void getSources(){
		getList();		
	}
	@SuppressWarnings("deprecation")
	public void deleteOldFile(){
		Process p = null;
		try {			
			p = Runtime.getRuntime().exec("adb -s "+test.getDeviceSerial()+" shell rm /sdcard/Robotium-Screenshots/*");
			DataInputStream dis = new DataInputStream(p.getInputStream());			
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			p.waitFor();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	public void getList(){
		String temp = "";
		Process p = null;
		try {			
			p = Runtime.getRuntime().exec("adb -s "+test.getDeviceSerial()+" shell ls /sdcard/Robotium-Screenshots/");
			DataInputStream dis = new DataInputStream(p.getInputStream());
			try{
				while ((temp = dis.readLine()) != null){
					//pull图片回local
					if(temp.length()!=0){
						//如果图片名包含约定的字符
						if(temp.contains("_._")){
							//以约定的字符切分成类名+index
							String[] temps = temp.split("_._");
							for(Function function:functions){
								if(temps[0].equals(function.className)){
									function.setPictureNum(function.getPictureNum()+1);
									getAPic(temp,"html/"+test.getNo()+"/"+temps[0]+"/"+temps[1]);
								}
							}
							
						}else if(temp.contains("_value.txt")){
							//以约定的字符提取出类名
							String[] temps = temp.split("_value");
							for(Function function:functions){
								if(temps[0].equals(function.className)){
									function.setContainCustomDate(true);
									getAValueTxt(temp,"html/"+test.getNo()+"/"+temps[0]+"/"+"value"+temps[1]);
								}
							}
						}
						
					}
					
					//System.out.println(temp);				
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
	
	@SuppressWarnings("deprecation")
	public void getAPic(String picName,String path){
		Process p = null;
		try {			
			p = Runtime.getRuntime().exec("adb -s "+test.getDeviceSerial()+" pull /sdcard/Robotium-Screenshots/"+picName+" "+path);
			
			//System.out.println("adb pull /sdcard/Robotium-Screenshots/"+picName+" "+path);
			DataInputStream dis = new DataInputStream(p.getInputStream());
			try{
				while ((dis.readLine()) != null){
					//存入
					//System.out.println(temp);				
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
	
	public void getAValueTxt(String txtName,String path){
		Process p = null;
		try {			
			p = Runtime.getRuntime().exec("adb -s "+test.getDeviceSerial()+" pull /sdcard/Robotium-Screenshots/"+txtName+" "+path);
			
			//System.out.println("adb pull /sdcard/Robotium-Screenshots/"+picName+" "+path);
			DataInputStream dis = new DataInputStream(p.getInputStream());
			try{
				while ((dis.readLine()) != null){
					//存入
					//System.out.println(temp);				
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
}
