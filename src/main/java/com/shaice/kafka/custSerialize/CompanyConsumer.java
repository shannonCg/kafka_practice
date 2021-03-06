package com.shaice.kafka.custSerialize;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

public class CompanyConsumer {
    public static void main(String[] args) {
        
        Properties props = initConfig();
        Consumer<String, Company> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList("test"));

        boolean flag = true;
        while (flag) {
            ConsumerRecords<String, Company> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, Company> record : records){
                System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value().toString());
            }
        }
        consumer.close();
    }
    public static Properties initConfig() {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "10.211.55.19:9092");
        props.put(ConsumerConfig.CLIENT_ID_CONFIG, "project2");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "test_consumer2");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ProtostuffDeserializer.class.getName());
        
        return props;
    }
}
