import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Random;

import android.os.Environment;
import android.widget.TextView;

import com.robotium.solo.Solo;

public class BasicAction {
	private Solo solo;
	private String className;
	/**
	 * 截图计数，将用作截图的文件名
	 * */
	private int index = 1;
	/**
	 * 控制截图的开关，true时，每个动作方法结束后，将会截图，false时，每个动作方法执行，将不会截图,默认为true
	 * */
	private boolean screenShotConfig = true;
	public static final int DEBUG_VALUE = 0;
	public static final int MUILT_VALUE = 1;
	FileOutputStream fileoutputstream = null;
	PrintStream printStream =null;
	
	
	
	public BasicAction(Solo solo,String str,boolean screenShotConfig){
		this.solo = solo;
		this.className = str;
		this.screenShotConfig = screenShotConfig;
	}
	public BasicAction(Solo solo,String str){
		this.solo = solo;
		this.className = str;
	}
	/**
	 * 等待time毫秒
	 * */
	public void sleep(int time){
		solo.sleep(time);
	}
	/**
	 * 方向键向上，1s后截图
	 * */
	public void up(){
		solo.sleep(500);
		solo.sendKey(Solo.UP);
		if(screenShotConfig){
			solo.sleep(1000);
			this.scnShot();
		}		
	}
	/**
	 * 方向键向下，1s后截图
	 * */	
	public void down(){
		solo.sleep(500);
		solo.sendKey(Solo.DOWN);
		if(screenShotConfig){
			solo.sleep(1000);
			this.scnShot();
		}	
	}
	/**
	 * 方向键向左，1s后截图
	 * */
	public void left(){
		solo.sleep(500);
		solo.sendKey(Solo.LEFT);
		if(screenShotConfig){
			solo.sleep(1000);
			this.scnShot();
		}	
	}
	/**
	 * 方向键向右，1s后截图
	 * */
	public void right(){
		solo.sleep(500);
		solo.sendKey(Solo.RIGHT);
		if(screenShotConfig){
			solo.sleep(1000);
			this.scnShot();
		}	
	}
	/**
	 * enter，1s后截图
	 * */
	public void enter(){
		solo.sleep(500);
		solo.sendKey(Solo.ENTER);
		if(screenShotConfig){
			solo.sleep(1000);
			this.scnShot();
		}	
	}
	/**
	 * back，1s后截图
	 * */
	public void back(){
		solo.sleep(500);
		solo.goBack();
		if(screenShotConfig){
			solo.sleep(1000);
			this.scnShot();
		}	
	}
	/**
	 * menu，1s后截图
	 * */
	public void menu(){
		solo.sleep(500);
		solo.sendKey(Solo.MENU);
		if(screenShotConfig){
			solo.sleep(1000);
			this.scnShot();
		}	
	}
	/**
	 * 截图，存储文件名为：className+"_._"+index
	 * */
	public void scnShot(){		
		solo.takeScreenshot(className+"_._"+index,50);
		index++;
	}
	/**
	 * 等待time毫秒，如出现target界面，则返回true，没有则断言失败<br>
	 * 这个方法用来判断程序是否按照预期进入目标Activity<br>
	 * 如果未进入目标Activity，则会截图，该截图不受screenShotConfig控制
	 * */
	public boolean assertActivity(String target,int time){
		boolean result = true;		
		result = solo.waitForActivity(target,time);
		if(!result){
			scnShot();
		}
		solo.assertCurrentActivity("not arrive target "+target, target);		
		return result ;				
	}
	/**
	 * 如出现target界面，则返回true，没有则断言失败<br>
	 * 这个方法用来判断程序是否按照预期进入目标Activity<br>
	 * 如果未进入目标Activity，则会截图，该截图不受screenShotConfig控制
	 * */
	public boolean assertActivity(String target){
		boolean result = true;				
		result = solo.waitForActivity(target,10000);
		if(!result){
			scnShot();
		}
		solo.assertCurrentActivity("not arrive target "+target, target);		
		return result ;				
	}
	/**
	 * 如出现target界面，则返回true，没有则返回false<br>
	 * 这个方法用来判断程序是否按照预期进入目标Activity<br>
	 * 如果未进入目标Activity，则会截图，该截图不受screenShotConfig控制
	 * */
	public boolean isActivity(String target){
		boolean result = true;				
		result = solo.waitForActivity(target,15000);
		if(!result){
			scnShot();
		}		
		return result ;				
	}
	/**
	 * 如出现target界面，则返回true，没有则返回false<br>
	 * 这个方法用来判断程序是否按照预期进入目标Activity<br>
	 * 如果未进入目标Activity，则会截图，该截图不受screenShotConfig控制
	 * */
	public boolean isActivity(String target,int time){
		boolean result = true;				
		result = solo.waitForActivity(target,time);
		if(!result){
			scnShot();
		}		
		return result ;				
	}
	/**
	 * 通过id取得目标控件，该控件为TextView，执行getText方法<br>
	 * 与对比值value对比，如果一直则返回true，如果返回false<br>
	 * 该方法不做断言，需要在脚本直接调用assert系列方法<br>
	 * 如果结果错误，则会截图，该截图不受screenShotConfig控制
	 * */
	public boolean assertValue(String id,String value){
		boolean result = true;
		String temp = "";
		TextView view = (TextView)solo.getView(id);
		temp = (String)view.getText();
		if(temp.length()<=0){
			result = false;
		}else{
			if(temp.equals(value)){
				result = true;
			}else{
				result = false;
			}
		}
		return result ;
	}
	/**
	 * 通过id取得目标控件，点击该控件
	 * */
	public void clickOnView(String id){
		solo.clickOnView(solo.getView(id));
	}
	/**
	 * 随机选择方向命令，包括，上下左右方向
	 * */	
	public void randomArrow(){
		int r = new Random().nextInt(4)+1;
		switch(r){
		case 1 : up();break;
		case 2 : down();break;
		case 3 : left();break;
		case 4 : right();break;
		}
	}
	/**
	 * 自定义数据存储，存储文件地址为/sdcard/Robotium-Screenshots/
	 * 命名为类名_value.txt
	 * key为键值，value为值,type为类型
	 * */
	public void printDate(String key,String value,int type){
		try {
			
			fileoutputstream = new FileOutputStream(Environment.getExternalStorageDirectory()+"/Robotium-Screenshots/"+this.className+"_value.txt",true);
			//System.out.println(Environment.getExternalStorageDirectory()+"/"+this.className+"_value.txt");
			printStream = new PrintStream(fileoutputstream);
			printStream.println(type+":"+key+"="+value);
			printStream.flush();
			printStream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	}
	/**
	 * 默认为单数据
	 * 自定义数据存储，存储文件地址为/sdcard/Robotium-Screenshots/
	 * 命名为类名_value.txt
	 * key为键值，value为值,type为类型
	 * */
	public void printDate(String key,String value){
		printDate(key,value,0);    
	}
	/**
	 * 输入int，则转换为String
	 * 自定义数据存储，存储文件地址为/sdcard/Robotium-Screenshots/
	 * 命名为类名_value.txt
	 * key为键值，value为值,type为类型
	 * */
	public void printDate(String key,int value,int type){
		printDate(key,String.valueOf(value),type);    
	}		
	/**
	 * 获取一个TextView的Text内容，请确保id确实存在
	 * */
	public String getText(String id){
		String value ;
		TextView tv = (TextView)solo.getView(id);
		value = (String)tv.getText();
		return value;
	}
}
