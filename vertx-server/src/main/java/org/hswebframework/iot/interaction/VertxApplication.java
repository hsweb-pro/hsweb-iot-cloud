package org.hswebframework.iot.interaction;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author zhouhao
 * @since 1.0.0
 */
@SpringCloudApplication
@EnableFeignClients
//@MapperScan(basePackages = "org.hswebframework.iot.interaction.dao", markerInterface = Dao.class)
@ComponentScan("org.hswebframework.iot")
public class VertxApplication {

    public static void main(String[] args) {
        SpringApplication.run(VertxApplication.class, args);
    }

}
