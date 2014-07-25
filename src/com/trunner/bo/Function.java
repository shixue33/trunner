package com.trunner.bo;

import java.io.File;
import java.util.ArrayList;

public class Function {
	public String className;//测试类名
	public String FunctionName = "_test";//测试方法名
	public float totalTime = 0;//总耗时
	public int executions = 0;//执行的次数	
	public int failNum;//失败次数
	public String result;//结果 success 或者fail
	public String shortMsg = "";//失败原因的简短描述
	public int pictureNum = 0;//截图的数量
	public boolean containCustomDate = false;//截图的数量
	public ArrayList<String> Log = new ArrayList<String>();//log日志
	public Test test;
	public Function(){
		
	}
	public Function(Test test){
		this.test = test;
		generFolder();
	}
	public void generFolder(){
		File file = new File("html/"+String.valueOf(test.getNo())+"/"+this.getClassName());
		file.mkdirs();
	}
	
	
	
	public boolean isContainCustomDate() {
		return containCustomDate;
	}
	public void setContainCustomDate(boolean containCustomDate) {
		this.containCustomDate = containCustomDate;
	}
	public float getTotalTime() {
		return totalTime;
	}
	public void setTotalTime(float totalTime) {
		this.totalTime = totalTime;
	}
	public ArrayList<String> getLog() {
		return Log;
	}
	public void setLog(ArrayList<String> log) {
		Log = log;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getFunctionName() {
		return FunctionName;
	}
	public void setFunctionName(String functionName) {
		FunctionName = functionName;
	}
	public int getExecutions() {
		return executions;
	}
	public void setExecutions(int executions) {
		this.executions = executions;
	}
	public int getFailNum() {
		return failNum;
	}
	public void setFailNum(int failNum) {
		this.failNum = failNum;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getShortMsg() {
		return shortMsg;
	}
	public void setShortMsg(String shortMsg) {
		this.shortMsg = shortMsg;
	}
	public int getPictureNum() {
		return pictureNum;
	}
	public void setPictureNum(int pictureNum) {
		this.pictureNum = pictureNum;
	}
	
	public static void main(String[] args){
		Test test = new Test();
		Function function = new Function(test);
		function.setClassName("TTT_meizitest");
		function.generFolder();
	}
}
