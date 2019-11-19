package com.kang.estimate.core.jmeter;

import java.io.File;
import java.io.IOException;

import org.apache.jmeter.JMeter;
import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.protocol.http.control.HeaderManager;
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerProxy;
import org.apache.jmeter.reporters.ResultCollector;
import org.apache.jmeter.reporters.Summariser;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.TestPlan;
import org.apache.jmeter.threads.ThreadGroup;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;

public class TestPlanLauncher {

    public void presureTest(){
        // jemter 引擎
        StandardJMeterEngine standardJMeterEngine = new StandardJMeterEngine();
        // 设置不适用gui的方式调用jmeter
        System.setProperty(JMeter.JMETER_NON_GUI, "true");
        // 设置jmeter.properties文件，我们将jmeter文件存放在resources中，通过classload
        String path = TestPlanLauncher.class.getClassLoader().getResource("jmeter.properties").getPath();
        String saveservice_properties=TestPlanLauncher.class.getClassLoader().getResource("saveservice.properties").getPath();
        File jmeterPropertiesFile = new File(path);
        if (jmeterPropertiesFile.exists()) {
            JMeterUtils.setJMeterHome("");
            JMeterUtils.loadJMeterProperties(jmeterPropertiesFile.getPath());
            JMeterUtils.setProperty("saveservice_properties",saveservice_properties);
            HashTree testPlanTree = new HashTree();
            // 创建测试计划
            TestPlan testPlan = new TestPlan("Create JMeter Script From Java Code");
            // 创建http请求收集器
            HTTPSamplerProxy examplecomSampler = createHTTPSamplerProxy();
            // 创建循环控制器
            LoopController loopController = createLoopController();
            // 创建线程组
            ThreadGroup threadGroup = createThreadGroup();
            // 线程组设置循环控制
            threadGroup.setSamplerController(loopController);
            // 将测试计划添加到测试配置树种
            HashTree threadGroupHashTree = testPlanTree.add(testPlan, threadGroup);
            // 将http请求采样器添加到线程组下
            threadGroupHashTree.add(examplecomSampler);

            //增加结果收集
            Summariser summer = null;
            String summariserName = JMeterUtils.getPropDefault("summariser.name", "summary");
            if (summariserName.length() > 0) {
                summer = new Summariser(summariserName);
            }

//            String csvFile = "C:\\Users\\hasee\\Desktop\\test.csv";
//            ResultCollector csvlogger = new ResultCollector(summer);
//            csvlogger.setFilename(csvFile);
//            testPlanTree.add(testPlanTree.getArray()[0], csvlogger);
            testPlanTree.add(testPlanTree.getArray()[0], new MyResultCollector(summer,"TESTPLAN1"));

            // 配置jmeter
            standardJMeterEngine.configure(testPlanTree);
            // 运行
            standardJMeterEngine.run();
        }
    }

    public static void main(String[] args) throws IOException {

    }

    /**
     * 创建线程组
     *
     * @return
     */
    public static ThreadGroup createThreadGroup() {
        ThreadGroup threadGroup = new ThreadGroup();
        threadGroup.setName("Example Thread Group");
        threadGroup.setNumThreads(10);
        threadGroup.setRampUp(0);
        threadGroup.setProperty(TestElement.TEST_CLASS, ThreadGroup.class.getName());
        threadGroup.setScheduler(true);
        threadGroup.setDuration(60);
        threadGroup.setDelay(0);
        return threadGroup;
    }

    /**
     * 创建循环控制器
     *
     * @return
     */
    public static LoopController createLoopController() {
        // Loop Controller
        LoopController loopController = new LoopController();
        //永久循环的情况下loops应设置为-1.
        loopController.setLoops(2);
        loopController.setContinueForever(true);
        loopController.setProperty(TestElement.TEST_CLASS, LoopController.class.getName());
        loopController.initialize();
        return loopController;
    }

    /**
     * 创建http采样器
     *
     * @return
     */
    public static HTTPSamplerProxy createHTTPSamplerProxy() {
        HeaderManager headerManager = new HeaderManager();
        headerManager.setProperty("Content-Type", "multipart/form-data");
        HTTPSamplerProxy httpSamplerProxy = new HTTPSamplerProxy();
        httpSamplerProxy.setDomain("www.baidu.com");
        httpSamplerProxy.setPort(80);
        httpSamplerProxy.setPath("/");
        httpSamplerProxy.setMethod("GET");
        httpSamplerProxy.setConnectTimeout("5000");
        httpSamplerProxy.setUseKeepAlive(true);
        httpSamplerProxy.setProperty(TestElement.TEST_CLASS, HTTPSamplerProxy.class.getName());
        httpSamplerProxy.setHeaderManager(headerManager);
        return httpSamplerProxy;
    }
}