package com.trunner.bo;

import java.io.File;
import java.util.Date;


public class Test {
	private long no;//测试编号,new该用例时，需要生产该编号
	public long testFinishTime;//测试结束时间点
	public String testAPKName ;//被测包包名
	public int testcaseNum = 0;//总用例数
	public int SucessNum = 0;//成功数
	public int FailNum = 0;//失败数
	public float totalTime;//总耗时
	public String deviceName ="default";//设备名称
	public String deviceSerial ;//设备序列号
	//构造函数生成测试编号，编号为当前时间的毫秒数
	public Test(){
		Date date = new Date();		
		this.setNo(date.getTime());
		//本地建一个no命名的文件夹
		generFolder();
	}
	public void generFolder(){
		File file = new File("html/"+String.valueOf(this.getNo()));
		file.mkdirs();
	}
	
	public long getTestFinishTime() {
		return testFinishTime;
	}
	public void setTestFinishTime(long testFinishTime) {
		this.testFinishTime = testFinishTime;
	}
	public String getTestAPKName() {
		return testAPKName;
	}
	public void setTestAPKName(String testAPKName) {
		this.testAPKName = testAPKName;
	}
	public int getTestcaseNum() {
		return testcaseNum;
	}
	public void setTestcaseNum(int testcaseNum) {
		this.testcaseNum = testcaseNum;
	}
	public int getSucessNum() {
		return SucessNum;
	}
	public void setSucessNum(int sucessNum) {
		SucessNum = sucessNum;
	}
	public int getFailNum() {
		return FailNum;
	}
	public void setFailNum(int failNum) {
		FailNum = failNum;
	}
	public float getTotalTime() {
		return totalTime;
	}
	public void setTotalTime(float totalTime) {
		this.totalTime = totalTime;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getDeviceSerial() {
		return deviceSerial;
	}
	public void setDeviceSerial(String deviceSerial) {
		this.deviceSerial = deviceSerial;
	}
	public long getNo() {
		return no;
	}
	public void setNo(long no) {
		this.no = no;
	}
	
	public static void main(String[] args){
		new Test();
	}
	
}
