package com.kang.estimate.core.jmeter;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kang.estimate.core.error.BussinessException;
import com.kang.estimate.core.error.EmBussinessError;
import com.kang.estimate.core.redis.RedisUtil;
import com.kang.estimate.module.pressure.entity.PressureParams;
import org.apache.jmeter.JMeter;
import org.apache.jmeter.config.Argument;
import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.protocol.http.control.Header;
import org.apache.jmeter.protocol.http.control.HeaderManager;
import org.apache.jmeter.protocol.http.control.gui.HttpTestSampleGui;
import org.apache.jmeter.protocol.http.gui.HeaderPanel;
import org.apache.jmeter.protocol.http.sampler.HTTPSampler;
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerProxy;
import org.apache.jmeter.reporters.ResultCollector;
import org.apache.jmeter.reporters.Summariser;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.TestPlan;
import org.apache.jmeter.testelement.property.CollectionProperty;
import org.apache.jmeter.threads.ThreadGroup;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jmeter.visualizers.SummaryReport;
import org.apache.jorphan.collections.HashTree;

public class TestPlanLauncher {

    public void presureTest(RedisUtil redisUtil,PressureParams pressureParams){
        // 创建jmx脚本
//        JMeterKit.createScript(pressureParams);
        // jemter 引擎
        StandardJMeterEngine standardJMeterEngine = new StandardJMeterEngine();
        // 设置不适用gui的方式调用jmeter
        System.setProperty(JMeter.JMETER_NON_GUI, "true");
        // 设置jmeter.properties文件，我们将jmeter文件存放在resources中，通过classload
        String path = TestPlanLauncher.class.getClassLoader().getResource("jmeter.properties").getPath();
        File jmeterPropertiesFile = new File(path);
        if (jmeterPropertiesFile.exists()) {
            JMeterUtils.setJMeterHome("");
            JMeterUtils.loadJMeterProperties(jmeterPropertiesFile.getPath());
            HashTree testPlanTree = new HashTree();
            // 创建测试计划
            TestPlan testPlan = new TestPlan("Create JMeter Script From Java Code");
            HashTree requestHashTree = new HashTree();
            requestHashTree.add(JMeterKit.createHTTPSamplerProxy(pressureParams), JMeterKit.createHeaderManager(pressureParams));
            testPlanTree.add(testPlan);
            HashTree threadGroupHashTree = testPlanTree.add(testPlan, JMeterKit.createThreadGroup(pressureParams));
            threadGroupHashTree.add(requestHashTree);

            //增加结果收集
            Summariser summer = null;
            String summariserName = JMeterUtils.getPropDefault("summariser.name", "summary");
            if (summariserName.length() > 0) {
                summer = new Summariser(summariserName);
            }

            testPlanTree.add(testPlanTree.getArray()[0], new JmeterResultCollector(summer,pressureParams.getIdentifyCode(),redisUtil));
            // 配置jmeter
            standardJMeterEngine.configure(testPlanTree);
            // 运行
            standardJMeterEngine.run();
        }
    }
}