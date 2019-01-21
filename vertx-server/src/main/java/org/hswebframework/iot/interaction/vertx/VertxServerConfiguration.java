package org.hswebframework.iot.interaction.vertx;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.core.net.PfxOptions;
import io.vertx.core.spi.VerticleFactory;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.mqtt.MqttServerOptions;
import io.vertx.spi.cluster.zookeeper.ZookeeperClusterManager;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.hswebframework.iot.interaction.vertx.cluster.RedisClusterManager;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author zhouhao
 * @since 1.0.0
 */

@Slf4j
@Configuration
public class VertxServerConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "vertx")
    public VertxOptions vertxOptions() {
        return new VertxOptions();
    }

    @Bean
    @ConfigurationProperties(prefix = "vertx.mqtt")
    public MqttServerOptions mqttServerOptions() {
        return new MqttServerOptions();
    }

    @Bean
    @ConfigurationProperties(prefix = "vertx.mqtt.pfx")
    public MqttSslProperties mqttSslProperties() {
        return new MqttSslProperties();
    }

    @Bean
    public RedisClusterManager redisClusterManager(RedissonClient redissonClient, Environment environment) {

        String nodeId = environment.getProperty("vertx.cluster.node-id", UUID.randomUUID().toString());
        return new RedisClusterManager(redissonClient, nodeId);
    }

    @Bean
    @ConfigurationProperties(prefix = "vertx.mqtt")
    public MqttServerOptions mqttServerOptions(MqttSslProperties sslProperties) {
        MqttServerOptions serverOptions = new MqttServerOptions();
        PfxOptions pfxOptions = new PfxOptions();
        pfxOptions.setPassword(sslProperties.getPassword());
        if (StringUtils.hasText(sslProperties.valueBase64)) {
            pfxOptions.setValue(Buffer.buffer(Base64.decodeBase64(sslProperties.valueBase64)));
        } else {
            pfxOptions.setPath(sslProperties.path);
        }
        serverOptions.setSsl(true)
                .setPfxKeyCertOptions(pfxOptions);
        return serverOptions;
    }

    @Bean
    @SneakyThrows
    public Vertx vertx(RedisClusterManager redisClusterManager) {
        VertxOptions vertxOptions = vertxOptions();

        log.debug("init vertx : \n{}", vertxOptions);
        Vertx vertx;
        if (vertxOptions.isClustered()) {
            vertxOptions.setClusterManager(redisClusterManager);
            CountDownLatch clusterLatch = new CountDownLatch(1);
            AtomicReference<Throwable> errorReference = new AtomicReference<>();
            AtomicReference<Vertx> vertxAtomicReference = new AtomicReference<>();
            Vertx.clusteredVertx(vertxOptions, e -> {
                try {
                    if (e.succeeded()) {
                        log.debug("init clustered vertx success");
                        vertxAtomicReference.set(e.result());
                    } else {
                        errorReference.set(e.cause());
                    }
                } finally {
                    clusterLatch.countDown();
                }
            });
            boolean success = clusterLatch.await(1, TimeUnit.MINUTES);
            if (!success) {
                log.warn("wait vertx init timeout!");
            }
            if (errorReference.get() != null) {
                throw errorReference.get();
            }
            vertx = vertxAtomicReference.get();
        } else {
            vertx = Vertx.vertx(vertxOptions);
        }
        return vertx;
    }

    @Bean
    public VerticleRegisterProcessor startMqttServerProcessor() {
        return new VerticleRegisterProcessor();
    }

    public static class VerticleRegisterProcessor implements CommandLineRunner {

        @Autowired
        private VerticleFactory verticleFactory;

        @Autowired
        private List<VerticleSupplier> verticles;

        @Autowired
        private Vertx vertx;

        @Override
        public void run(String... args) throws Exception {
            vertx.registerVerticleFactory(verticleFactory);
            for (VerticleSupplier suplier : verticles) {
                DeploymentOptions options = new DeploymentOptions();
                options.setHa(true);
                options.setInstances(suplier.getInstances());
                vertx.deployVerticle(suplier, options, e -> {
                    if (!e.succeeded()) {
                        log.error("deploy verticle :{} error", suplier, e.succeeded(), e.cause());
                    } else {
                        log.debug("deploy verticle :{} success",suplier);
                    }
                });
            }
        }
    }

    @Getter
    @Setter
    public static class MqttSslProperties {

        private String path = "mqtt.pfx";

        private String valueBase64;

        private String password = "";

    }
}
