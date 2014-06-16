trunner
=======

Robotium Test Tools
1 TRunner为PC端工具，用于管理测试用例，发送测试用例，统计和分析测试结果，导出截图和数值，最后输出html页面
2 BasicAction为Robotium中的Solo的扩展类，对TV类发送各类键进行封装，简化书写；对每一个动作进行自动截图，并且编号；提供自定义数据的接口，方便调试或者性能数据的展示，其中复杂数据在html页面中使用highchart折线展示，不需要额外增加标识，为自动识别。

ps：TRunner可以单独使用，但是由于约定了截图的命名方式，所以必须用BasicAction中的截图接口，最后生成的html才有正确的截图展示。
