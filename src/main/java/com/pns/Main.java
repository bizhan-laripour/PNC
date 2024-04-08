package com.pns;


import com.pns.enums.Topics;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class , args);
    }


    @Bean
    public NewTopic ErrorTopic1() {
        return TopicBuilder.name(Topics.ERROR_TOPIC_1.name()).build();
    }

    @Bean
    public NewTopic ErrorTopic2() {
        return TopicBuilder.name(Topics.ERROR_TOPIC_2.name()).build();
    }

    @Bean
    public NewTopic ErrorTopic3() {
        return TopicBuilder.name(Topics.ERROR_TOPIC_3.name()).build();
    }

}