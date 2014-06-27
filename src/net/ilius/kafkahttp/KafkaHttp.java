package net.ilius.kafkahttp;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.lifecycle.Managed;
import com.codahale.metrics.health.HealthCheck;
import kafka.javaapi.producer.Producer;
import kafka.producer.ProducerConfig;
import net.ilius.kafkahttp.resources.MessageResource;

public class KafkaHttp extends Application<KafkaHttpConfiguration> implements Managed {
    public static void main(String[] args) throws Exception {
        new KafkaHttp().run(args);
    }

    private Producer<String, String> producer;

    @Override
    public String getName() {
        return "kafka-http";
    }

    @Override
    public void initialize(Bootstrap<KafkaHttpConfiguration> bootstrap) {
    }

    @Override
    public void run(KafkaHttpConfiguration configuration, Environment environment) {
        ProducerConfig config = new ProducerConfig(configuration.producer.asProperties());
        producer = new Producer<String, String>(config);

        //environment.manage(this);
        environment.jersey().register(new MessageResource(producer, configuration.consumer));
        environment.healthChecks().register("empty", new EmptyHealthCheck());
    }

    @Override
    public void start() throws Exception {

    }

    @Override
    public void stop() throws Exception {
        producer.close();
    }

    private static class EmptyHealthCheck extends HealthCheck {
        @Override
        protected Result check() throws Exception {
            return Result.healthy();
        }
    }
}
