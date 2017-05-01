package org.bob.learn.zk;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ComponentScan(basePackages = {"org.bob.learn"})
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		log.info("应用启动");
		SpringApplication.run(Application.class, args);
	}

}
