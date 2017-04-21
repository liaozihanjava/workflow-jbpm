package com.sample;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jbpm.test.JbpmJUnitBaseTestCase;
import org.junit.Test;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.task.TaskService;
import org.kie.api.task.model.TaskSummary;

/**
 * This is a sample file to test a process.
 */
public class ProcessTest extends JbpmJUnitBaseTestCase {
	
	public ProcessTest() {
		super(true, true);
	}
	
	@Test
	public void testProcess() {
		RuntimeManager manager = createRuntimeManager("sample.bpmn");
		RuntimeEngine engine = getRuntimeEngine(null);
		KieSession ksession = engine.getKieSession();
		TaskService taskService = engine.getTaskService();

		String caseID = "case_123"; // 设置案例对象ID属性
		String caseReason = "Student leave";// 设置案例对象reason属性
		String caseDetail = "no info";
		String depaOpinion = "no info";
		String student = "john";
		String teacher = "mary";
		String headmaster = "krisv";
		Object day = 3;
		Object reason = "wrong";
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("caseID", caseID); // 案例名值对映射
		params.put("caseReason", caseReason);
		params.put("caseDetail", caseDetail);
		params.put("depaOpinion", depaOpinion);
		params.put("student", student);
		params.put("teacher", teacher);
		params.put("headmaster", headmaster);
		params.put("day", day);
		params.put("reason", reason);
		
		ProcessInstance processInstance = ksession.startProcess("com.sample.bpmn.workflow",params);
		
		List<TaskSummary> list = taskService.getTasksAssignedAsPotentialOwner(student, "en-UK");
		Iterator<TaskSummary> iterator = list.iterator();
		TaskSummary task = null;
		Map<String, Object> results = new HashMap<String, Object>();
		while (iterator.hasNext()) {
			task = iterator.next();
			taskService.start(task.getId(), student);
			if (task.getName().equals("write")) {
				results.put("caseDetail", ".......");// 人员处理结果
			}
			taskService.complete(task.getId(), student, results);
			}
		
		
		
		list = taskService.getTasksAssignedAsPotentialOwner(teacher, "en-UK");
		iterator = list.iterator();
		while(iterator.hasNext()){
		 task = iterator.next();  
		taskService.start(task.getId(), teacher);
		    results = new HashMap<String, Object>();
		taskService.complete(task.getId(), teacher, results);
		}
		
		list = taskService.getTasksAssignedAsPotentialOwner(headmaster, "en-UK");
		iterator = list.iterator();
		while(iterator.hasNext()){
		 task = iterator.next();  
		taskService.start(task.getId(), headmaster);
		    results = new HashMap<String, Object>();
		taskService.complete(task.getId(), headmaster, results);
		}
		
		list = taskService.getTasksAssignedAsPotentialOwner(teacher, "en-UK");
		iterator = list.iterator();
		while(iterator.hasNext()){
		 task = iterator.next();  
		taskService.start(task.getId(), teacher);
		    results = new HashMap<String, Object>();
		taskService.complete(task.getId(), teacher, results);
		}
		
		
		list = taskService.getTasksAssignedAsPotentialOwner(headmaster, "en-UK");
		iterator = list.iterator();
		while(iterator.hasNext()){
		 task = iterator.next();  
		taskService.start(task.getId(), headmaster);
		    results = new HashMap<String, Object>();
		taskService.complete(task.getId(), headmaster, results);
		}
		
		
		manager.disposeRuntimeEngine(engine);
		manager.close();
	}

}