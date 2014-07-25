package com.trunner.init;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.trunner.bo.Function;

public class GetFuns {
	private String[] funs;
	private String path ;
	private ArrayList<Function> functions;
	private ArrayList<String> array = new ArrayList<String>(); 
	
	public GetFuns(ArrayList<Function> functions, String[] funs,String path) {
		super();
		this.functions = functions;
		this.funs = funs;
		this.path = path;
		
	}
	//从配置文件中获取测试方法列表,填充到funs中
	public void generFunction(){
		BufferedReader br = null;
		try{
			 br = new BufferedReader(new InputStreamReader(new FileInputStream(path)));  
			 for (String line = br.readLine(); line != null; line = br.readLine()) {  
				 array.add(line);				
			 }  			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		funs = new String[array.size()];
		for(int i = 0;i<array.size();i++){
			funs[i] = array.get(i);
			Function f = new Function();
			f.setClassName(array.get(i));
			functions.add(f);
		}
	}
	
	public void debug(){
		System.out.println("debug test");
				
		for(String temp : funs){
			System.out.println(temp);
			
		}
	}
}
