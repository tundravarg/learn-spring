package tuman.learnspring.server;

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.runApplication;
import org.springframework.boot.SpringApplication
import org.springframework.boot.WebApplicationType


@SpringBootApplication
@AutoConfigurationPackage
open class App

fun main(args: Array<String>) {
	System.out.println("Hello Kotlin!");

	// 1
	// var application = runApplication<App>(*args);

	// // 2
	// val application = SpringApplication(App::class.java);
	// application.setWebApplicationType(WebApplicationType.NONE);
	// application.run(*args);

	// 3
	SApp().app(args);
}

@SpringBootApplication
@AutoConfigurationPackage
open class SApp {

	fun app(args: Array<String>) {
		val application = SpringApplication(SApp::class.java);
		application.setWebApplicationType(WebApplicationType.NONE);
		application.run(*args);
	}

}
