package com.epam.hibernate;


import com.epam.hibernate.repository.TrainingTypeRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableFeignClients
public class GymServiceApplication {

    public static void main(String[] args) {
        ApplicationContext run = SpringApplication.run(GymServiceApplication.class, args);

        TrainingTypeRepository trainingTypeRepository = run.getBean(TrainingTypeRepository.class);
        trainingTypeRepository.addTrainingTypes();
    }

}
