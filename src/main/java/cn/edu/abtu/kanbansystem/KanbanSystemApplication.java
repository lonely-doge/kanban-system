package cn.edu.abtu.kanbansystem;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

/**
 * @author Richard
 */
@Slf4j
@SpringBootApplication
@MapperScan("cn.edu.abtu.kanbansystem.mapper")
public class KanbanSystemApplication {

    static {
        System.setProperty("druid.mysql.usePingMethod", "false");
//        System.setProperty("spring.security.strategy", "MODE_GLOBAL");
    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(KanbanSystemApplication.class);
        Environment env = app.run(args).getEnvironment();
        log.info("Server URL: http://localhost:{}{}",
                env.getProperty("server.port"),
                env.getProperty("server.servlet.context-path"));
    }

}
