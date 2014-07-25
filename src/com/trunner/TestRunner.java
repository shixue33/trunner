package com.trunner;

import java.util.ArrayList;

import com.trunner.adb.AdbGetResult;
import com.trunner.adb.AdbRunner;
import com.trunner.bo.Function;
import com.trunner.bo.Test;
import com.trunner.html.GenerHtml;
import com.trunner.init.Config;
import com.trunner.init.GetFuns;
import com.trunner.mail.SendMail;

public class TestRunner implements Runnable{
	private String[] funs;
	private Test test = new Test();
	private ArrayList<Function> functions = new ArrayList<Function>();
	private Config config;
	private String serialNo;	
	
	public TestRunner(String serialNo){		
		this.serialNo = serialNo;		
	}
	
	@Override
	public void run() {
		init();
		test.setDeviceSerial(serialNo);
		AdbRunner ar = new AdbRunner(test,functions,config);
		//安装apk
		ar.install();
		//获取硬件信息
		ar.getDevicesInfo();
		//删除SDCard上的旧文件
		deleteOldFile();
		//执行测试用例		
		ar.excTest();
		//导出图片文件至本地
		getPic();
		//生成html文件
		generateHtml();
		//发邮件
		sendMail();	
	}
	
	/**
	 * init环境*
	 */
	public void init(){
		//填充config
		config = new Config(test,functions);
		config.readConfig();
		//填充测试列表
		GetFuns gf = new GetFuns(functions,funs,config.funsPath);
		gf.generFunction();
	}
	
	//生成html文件
	public void generateHtml(){
		GenerHtml gener = new GenerHtml(test,functions);
		gener.generat();
	}
	public void deleteOldFile(){
		AdbGetResult agp = new AdbGetResult(funs,test,functions);
		agp.deleteOldFile();
	}
	public void getPic(){
		AdbGetResult agp = new AdbGetResult(funs,test,functions);
		agp.getSources();		
	}
	public void sendMail(){
		if(config.mailServerHost!=null&&config.mailServerHost.length()>0){
			SendMail sm = new SendMail(test,config);
			sm.send();
		}		
	}
	public static void main(String[] args){
		@SuppressWarnings("unused")
		String serialNo = args[0];
		TestRunner a = new TestRunner(serialNo);
	}
}
