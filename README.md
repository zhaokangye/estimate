# estimate
网页端服务器自动化平台
# 模块
1、服务器管理  
2、应用部署  
3、压力测试  
4、硬件监控  
# 实现原理
1、使用JSch包进行与服务器间的交互，包括执行命令到上传文件等  
2、压力测试实现原理为将Jmeter嵌入到应用中，监听ResultCollecter类将结果存入Redis  
3、硬件监控通过将记录硬件信息的日志脚本上传到被监控服务器上，在被监控服务器里按天生成监控信息日志。同步数据时先同步到Redis中，减少下次同步的数据量  
# 访问链接
www.yezhaokang.top
