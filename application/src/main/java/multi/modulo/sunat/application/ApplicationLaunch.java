package multi.modulo.sunat.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;


@ComponentScan("multi.modulo.*")
@EntityScan("multi.modulo.*")
@EnableFeignClients(basePackages = "multi.modulo.sunat.infrastructure.config")
@SpringBootApplication(scanBasePackages = "multi.modulo.sunat")
@EnableAsync
public class ApplicationLaunch  {

    public static void main(String[] args){
        SpringApplication.run(ApplicationLaunch.class, args);
    }

}
