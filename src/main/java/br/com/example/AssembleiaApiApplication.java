package br.com.example;

import java.util.concurrent.Executor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
@EnableKafka
@EnableAsync
@EnableScheduling
public class AssembleiaApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssembleiaApiApplication.class, args);
	}

	/**
	 * Controlador do pool de execução para as tarefas assincronas. Utilizado para
	 * gerenciar as tarefas que calculam os resultados das votações.
	 * 
	 * @return Executor o controlador do pool de execuções
	 */
	@Bean(name = "taskExecutor")
	public Executor taskExecutor() {
		log.info("Creating Async Task Executor");
		final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(2);
		executor.setMaxPoolSize(10);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("AssembleiaThread-");
		executor.initialize();
		return executor;
	}

}
