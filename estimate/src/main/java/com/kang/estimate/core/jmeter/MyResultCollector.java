package com.kang.estimate.core.jmeter;

import org.apache.jmeter.reporters.ResultCollector;
import org.apache.jmeter.reporters.Summariser;
import org.apache.jmeter.samplers.SampleEvent;
import org.apache.jmeter.samplers.SampleResult;

public class MyResultCollector extends ResultCollector {

    private String identifyCode;

    public MyResultCollector(Summariser summer,String identifyCode) {
        super(summer);
        this.identifyCode=identifyCode;
    }

    @Override
    public void sampleOccurred(SampleEvent e) {
        super.sampleOccurred(e);
        SampleResult r = e.getResult();
        Sample sample=new Sample();
        sample.setIdentifyCode(identifyCode);
        sample.setThreadName(r.getThreadName());
        sample.setResponseCode(r.getResponseCode());
        sample.setResponseMessage(r.getResponseMessage());
        sample.setSuccess(r.isSuccessful()?"true":"false");
        sample.setElapsedTime(r.getTime());
    }
}