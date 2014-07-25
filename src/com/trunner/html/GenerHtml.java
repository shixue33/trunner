package com.trunner.html;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.trunner.bo.Function;
import com.trunner.bo.Test;
import com.trunner.html.chart.ChartJson;

public class GenerHtml {
	private Test test;
	private ArrayList<Function> functions;
	public GenerHtml(Test test,ArrayList<Function> functions) {
		this.test = test;
		this.functions = functions;
	}

	public void generat() {
		generat_htmt1();
		generat_htmt2();
		generat_htmt3s();
	}

	// 生成第一个测试总列表
	public void generat_htmt1() {
		
		// 第一个模板
		String page1 = "html/page1.html";
		
		String c = null;
		
		String a = "";
		
		String index = "html/index.html";
		File file = new File(index);
		//如果index.html已经存在，则读取index.html为模版，在<insert>标签处替代page1.html中<forInsert>标签中间的部分，并replace值
		if(file.exists()){
			try{
				FileInputStream fis = new FileInputStream(page1);
				int lenght = fis.available();
				byte bytes[] = new byte[lenght];
				fis.read(bytes);
				fis.close();
				//System.out.println(bytes);
				a = new String(bytes,"utf-8");
				//System.out.println(a);
				String[] tempA = a.split("<forInsert>");
				String b = new String(tempA[1]);
				
				b = b.replace("#test.no#", String.valueOf(test.getNo()));
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String timeStr = dateFormat.format(new Date(test.getTestFinishTime()));
				b = b.replace("#test.finishTime#", timeStr);
				b = b.replace("#test.apkName#", test.getTestAPKName());
				b = b.replace("#test.deviceName#", test.getDeviceName()+"_"+test.getDeviceSerial());
				b = b.replace("#test.totalNum#", String.valueOf(test.getTestcaseNum()));
				b = b.replace("#test.successNum#", String.valueOf(test.getSucessNum()));
				b = b.replace("#test.failNum#", String.valueOf(test.getFailNum()));
				b = b.replace("#test.totalTime#", String.valueOf((int)(test.getTotalTime()/60))+"m"+String.valueOf(((int)test.getTotalTime())%60)+"s");
				//读取index.html
				
				FileInputStream fis2 = new FileInputStream(index);
				int lenght2 = fis2.available();
				byte bytes2[] = new byte[lenght2];
				fis2.read(bytes2);
				fis2.close();
				String a2 = new String(bytes2,"utf-8");
				String[] tempA2 = a2.split("<insert>");
				c = tempA2[0];
				c = c.concat("<insert>");
				c = c.concat(b);				
				
				c = c.concat(tempA2[1]);
				
				BufferedWriter printStream = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(index), Charset.forName("UTF-8")));
				printStream.write(c);
				printStream.close();
			}catch(Exception ex){
				ex.printStackTrace();
			}			
		}else{
		//如果index.html不存在，则以page1.html为模版 创建第一个test
			try{
				FileInputStream fis = new FileInputStream(page1);
				int lenght = fis.available();
				byte bytes[] = new byte[lenght];
				fis.read(bytes);
				fis.close();
				c = new String(bytes,"utf-8");

				c = c.replace("<forInsert>", "");
				c = c.replace("#test.no#", String.valueOf(test.getNo()));
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String timeStr = dateFormat.format(new Date(test.getTestFinishTime()));
				c = c.replace("#test.finishTime#", timeStr);
				c = c.replace("#test.apkName#", test.getTestAPKName());
				c = c.replace("#test.deviceName#", test.getDeviceName());
				c = c.replace("#test.totalNum#", String.valueOf(test.getTestcaseNum()));
				c = c.replace("#test.successNum#", String.valueOf(test.getSucessNum()));
				c = c.replace("#test.failNum#", String.valueOf(test.getFailNum()));
				c = c.replace("#test.totalTime#", String.valueOf((int)(test.getTotalTime()/60))+"m"+String.valueOf(((int)test.getTotalTime())%60)+"s");
	
				
				BufferedWriter printStream = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(index), Charset.forName("UTF-8")));
				
				printStream.write(c);
				
				printStream.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		
		}
			
			
	}

	// 生成第二个测试详情列表
	public void generat_htmt2() {
		try {
			// 第2个模板
			String filePath = "html/page2.html";
			String c = "";
			String b = "";
			FileInputStream fis = new FileInputStream(filePath);
			int lenght = fis.available();
			byte bytes[] = new byte[lenght];
			fis.read(bytes);
			fis.close();
			c = new String(bytes,"utf-8");
			c = c.replace("#test.deviceName#", test.getDeviceName());
			c = c.replace("#test.deviceSerial#", test.getDeviceSerial());
			c = c.replace("#test.totalNum#", String.valueOf(test.getTestcaseNum()));
			c = c.replace("#test.successNum#", String.valueOf(test.getSucessNum()));
			c = c.replace("#test.failNum#", String.valueOf(test.getFailNum()));
			c = c.replace("#test.totalTime#", String.valueOf((int)(test.getTotalTime()/60))+"m"+String.valueOf(((int)test.getTotalTime())%60)+"s");
			
			String[] temps = c.split("#for#");
			String forStr = "";
			
			b = temps[0];
			
			for (Function function : functions) {
				forStr = new String(temps[1]);
				
				forStr = forStr.replace("#fuctionClassNamePath#",  String.valueOf(test.getNo())+"/"+function.getClassName()+".html");
				forStr = forStr.replace("#funtion.className#", function.getClassName());
				forStr = forStr.replace("#function.funcName#", function.getFunctionName());
				forStr = forStr.replace("#funtion.excutions#", String.valueOf(function.getExecutions()));
				forStr = forStr.replace("#funtion.failNum#", String.valueOf(function.getFailNum()));
				forStr = forStr.replace("#function.result#", function.getResult());
				forStr = forStr.replace("#function.shortMsg#", function.getShortMsg());
				if(function.getResult().equals("fail")){
					forStr = forStr.replace("#errorFlag#"," class=\"error\"");
				}else{
					forStr = forStr.replace("#errorFlag#","");
				}
				
				b = b.concat(forStr);
			}
			b = b.concat(temps[2]);

			// 以test的结束时间为文件名
			
			String FileName = "html/"+String.valueOf(test.getNo()) + ".html";

			
			BufferedWriter printStream = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(FileName), Charset.forName("UTF-8")));
			printStream.write(b);
			printStream.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// 生成第三个单个测试方法列表,需要循环生成
	public void generat_htmt3s() {
		for (Function function : functions) {
			generat_htmt3(function);
		}
	}

	public void generat_htmt3(Function function){
    try{
      //第3个模板
     String filePath = "html/page3.html";
      String c = "";
     
      FileInputStream fis = new FileInputStream(filePath);
      int lenght = fis.available();
      byte bytes[] = new byte[lenght];
      fis.read(bytes);
      fis.close();
      c = new String(bytes,"utf-8");
      c = c.replace("#test.no#",String.valueOf(test.getNo()));
      c = c.replace("#test.deviceName#",test.getDeviceName());
      c = c.replace("#test.deviceSerial#",test.getDeviceSerial());
      
      c = c.replace("#funtion.className#", function.getClassName());
      c = c.replace("#function.funcName#", function.getFunctionName());
      c = c.replace("#function.result#", function.getResult());
      c = c.replace("#function.totalTime#", String.valueOf((int)function.getTotalTime())+"s");
      //CustomData 资源地址为testno/classname/value.txt
      String valuePath = "html/"+test.getNo()+"/"+function.getClassName()+"/value.txt";
      File file = new File(valuePath);
      if(file.exists()){
    	  BufferedReader reader = new BufferedReader(new FileReader(valuePath));
          String line = "";
          String[] valuetemp ;
          String valueStrs = "";
          String[] multitempstr ;
          String multiKey = "";
          int multiInt ;
          ArrayList<Integer> multiarray = new ArrayList<Integer>();
          while ((line = reader.readLine()) != null) {
        	  valuetemp = line.split(":");
        	  if(valuetemp[0].equals("0")){
        		  valueStrs = valueStrs.concat(valuetemp[1]+"<BR>");
        	  }else if(valuetemp[0].equals("1")){
        		  multitempstr = valuetemp[1].split("=");
        		  multiKey = multitempstr[0];
        		  multiInt = Integer.parseInt(multitempstr[1]);
        		  multiarray.add(multiInt);
        	  }
          }
          valueStrs = valueStrs.concat("-------------------"+"<BR>");
          reader.close();     
          
          c = c.replace("#function.cunstomDate#", valueStrs);
          //CustomMultiData
          if(multiKey.length()>0){
        	  
        	  String multiStrs = "<div id=\"container\" style=\"min-width:800px;height:400px\"></div>";
        	  c = c.replace("#function.customMultiDate#", multiStrs);
        	  String temp = new ChartJson(multiKey,multiarray).toJsonString();
        	  c = c.replace("#function.customMultiDate2#", temp);
          }else{
        	  c = c.replace("#function.customMultiDate#", "");
          }            
      }else{
    	  c = c.replace("#function.customMultiDate#", "");
    	  c = c.replace("#function.cunstomDate#", "");
      }
      
      //Log
      ArrayList<String> temps = function.getLog();
      String logs = "";
      
      for(String temp : temps){
    	  temp = temp.replace("<", "&lt;");
    	  temp = temp.replace(">", "&gt;");
    	  logs = logs.concat(temp+"<BR>");
      }
      
      c = c.replace("#function.log#", logs);
      
      //图片循环的次数
      int picNum = function.getPictureNum();
      String b = "";
      String[] temps2 = c.split("#for#");
      String forStr = "";
      
      b = temps2[0];
      
      for(int i = 1;i<=picNum;i++){
    	  forStr = new String(temps2[1]);
    	  forStr = forStr.replace("#function.picPath#", function.getClassName()+"/"+i+".jpg");
    	  forStr = forStr.replace("#function.picName#",i+".jpg" );
    	  b = b.concat(forStr);
      }
      b = b.concat(temps2[2]);
      
      
      String FileName = "html/"+String.valueOf(test.getNo())+"/"+function.getClassName()+".html";
      
      
      BufferedWriter printStream = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(FileName), Charset.forName("UTF-8")));

      printStream.write(b); 
      printStream.close();
     }catch(Exception ex){
      ex.printStackTrace();
     }  
   }

}
