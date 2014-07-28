trunner
=======

Robotium Test Tools<br>
1 TRunner为PC端工具，用于管理测试用例，发送测试用例，统计和分析测试结果，导出截图和数值，最后输出html页面<br>
2 BasicAction为Robotium中的Solo的扩展类，对TV类发送各类键进行封装，简化书写；对每一个动作进行自动截图，并且编号；提供自定义数据的接口，方便调试或者性能数据的展示，其中复杂数据在html页面中使用highchart折线展示，不需要额外增加标识，为自动识别。<br>
<br>
ps：TRunner可以单独使用，但是由于约定了截图的命名方式，所以必须用BasicAction中的截图接口，最后生成的html才有正确的截图展示。<br>

###BasicAction用法
1 导入BasicAction.jar或者将源码直接放入工程中
2 测试类中增加变量：ActionBasic ba
3 setUp方法中增加ba = new BasicAction(solo,this.getClass().getSimpleName())
4 之后就可以再test方法中直接使用ba来进行脚本编写
###截图使用scnShot()
需要注意的是，BasicAction中的每一个动作方法（类似左、右、菜单键、enter等）中在操作后1s都会进行自动截图。
###自定义数据命令：printDate()
提供二个参数定义方法：
1 简单数据
'''ba.printDate(String key, String value);'''
html显示结果为：
cunrrent is = com.xxx.xxx
2 数组数据：
'''ba.printDate(String key,int value ,int type);'''
type=1时，为数组数据，只接受int类型 type=0时为简单数据类型，接受可以转换为String的任何类型

###Trunner用法
解压trunner.zip后运行start.cmd
必须配置的两个文件为：config/cfg,properties和其中funsPath指向的测试类方法集合
在config文件夹中已有两个示例，分别为：bvt.txt和funs2.txt 按照示例写即可。注意不要有空格或者enter或者tab制表符。
cfg,properties的配置方法如注释配置即可，选填项留空则不会执行对应模块功能
注意install path选填项，在填写两个地址后，则在每次运行trunner后，都会对对应测试程序和被测程序进行卸载和重新安装，留空则不会安装，直接开始发送测试命令。
###结果页面
结果生成在html文件夹中，index.html。执行多次的结果不会覆盖，都将记录在index.html中。
