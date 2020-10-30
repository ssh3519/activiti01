package com.itheima.test;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

/**
 * @description
 * @author: ssh
 * @Date: 2020/10/23 9:47
 */
public class ActivitiTest {
    @Test
    public void testGenerateTable(){
        //1.创建ProcessEngineConfiguration对象  第一个参数:配置文件名称  第二个参数是processEngineConfiguration的bean的id
        ProcessEngineConfiguration configuration = ProcessEngineConfiguration
                .createProcessEngineConfigurationFromResource("activiti.cfg.xml");
        //2.创建ProcesEngine对象
        ProcessEngine processEngine = configuration.buildProcessEngine();
        //3.输出processEngine对象
        System.out.println(processEngine);

    }

    @Test
    public void testGenTable(){
        //条件：1.activiti配置文件名称：activiti.cfg.xml   2.bean的id="processEngineConfiguration"
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        System.out.println(processEngine);

        // HistoryService historyService = processEngine.getHistoryService();

    }
}
