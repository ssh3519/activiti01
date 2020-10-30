package com.itheima.activiti.day05;

import com.itheima.activiti.day04.Holiday;
import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import java.util.HashMap;
import java.util.Map;

/**
 * 测试排他网关
 *     已经加入了排他网关来进行测试
 *     如果从网关出去的线所有条件都不满足则系统抛出异常。经过排他网关必须要有一条且只有一条分支走
 * @description
 * @author: ssh
 * @Date: 2020/10/29 14:33
 */
public class ExclusiveGateWayTest02 {
    //1.部署流程定义
    public static void main1(String[] args) {
        //1.创建ProcessEngine对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        //2.得到RepositoryService实例
        RepositoryService repositoryService = processEngine.getRepositoryService();

        //3.进行部署
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("diagram/day05/holiday5.bpmn")  //添加bpmn资源
                //.addClasspathResource("diagram/day05/holiday5.png")
                .name("请假申请单流程")
                .deploy();

        //4.输出部署的一些信息
        System.out.println(deployment.getName());//请假申请单流程
        System.out.println(deployment.getId());//10001
    }

    //2.启动流程实例
   public static void main2(String[] args) {
        //1.得到ProcessEngine对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        //2.得到RunService对象
        RuntimeService runtimeService = processEngine.getRuntimeService();

        Holiday holiday = new Holiday();
        holiday.setNum(5F);
        Map<String,Object> map = new HashMap<>();
        map.put("holiday",holiday);//流程变量赋值

        //3.创建流程实例  流程定义的key需要知道 holiday
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("holidayExclusive",map);

        //4.输出实例的相关信息
        System.out.println("流程定义ID"+processInstance.getProcessDefinitionId());//holidayExclusive:1:10003
        System.out.println("流程实例ID"+processInstance.getId());//12501
    }

    //3.填写请假单的任务要执行完成
    public static void main(String[] args) {
        //1.得到ProcessEngine对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        //2.得到TaskService对象
        TaskService taskService = processEngine.getTaskService();

        //3.查询当前用户的任务
        Task task = taskService.createTaskQuery()
                .processDefinitionKey("holidayExclusive")
                .taskAssignee("lisi")
                .singleResult();

        //4.处理任务,结合当前用户任务列表的查询操作的话,任务ID:task.getId()
        if(task!=null){
            taskService.complete(task.getId());
            System.out.println("用户任务执行完毕...");
        }

        //5.输出任务的id
        System.out.println(task.getId());
    }
}
