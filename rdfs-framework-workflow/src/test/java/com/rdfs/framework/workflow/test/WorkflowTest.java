package com.rdfs.framework.workflow.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rdfs.framework.workflow.entity.CwProcessInfo;
import com.rdfs.framework.workflow.service.ProcessInfoService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/spring-base.xml")
public class WorkflowTest {

	@Autowired
	private ProcessInfoService processInfoService;
	
	@Test
	public void testGetProcessList(){
		List<CwProcessInfo> list = processInfoService.getProcessInfos();
		System.out.println(list);
	}
}
