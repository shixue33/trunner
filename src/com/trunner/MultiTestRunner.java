package com.trunner;

import java.util.ArrayList;

import com.trunner.adb.AdbRunner;

public class MultiTestRunner {
	private ArrayList<String> serials ;
	private ArrayList<TestRunner> t3 = new ArrayList<TestRunner>();
	
	public MultiTestRunner(){
		AdbRunner ar = new AdbRunner();
		serials = ar.getDeviceSerials();
		for(String temp:serials){
			System.out.println(temp);
		}
	}
	public void runtest(){
		for(String temp:serials){
			TestRunner tr3 = new TestRunner(temp); 
			t3.add(tr3);
		}
		for(TestRunner tr3:t3){
			Thread thread = new Thread(tr3);
			thread.start();
		}
	}
	
	
	
	
	public static void main(String[] args) {
		MultiTestRunner mtr = new MultiTestRunner();
		mtr.runtest();
	}

}
