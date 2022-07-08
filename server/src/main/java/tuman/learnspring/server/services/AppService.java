package tuman.learnspring.server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


@Component
public class AppService {

	@Autowired
	private PropertiesTestService propertiesTestService;
	@Autowired
	private DataTestService dataTestService;


	@EventListener(ApplicationReadyEvent.class)
	public void onAppReady() {
		System.out.println("----------- APP is ready");
//		propertiesTestService.testProperties();
//		dataTestService.testDataAccess();
	}

}
