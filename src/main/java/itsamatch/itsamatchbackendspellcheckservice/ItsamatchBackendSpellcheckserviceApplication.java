package itsamatch.itsamatchbackendspellcheckservice;

import itsamatch.itsamatchbackendspellcheckservice.Service.SpellCheckAIModel;
import itsamatch.itsamatchbackendspellcheckservice.Service_Interface.AIModelException;
import itsamatch.itsamatchbackendspellcheckservice.Service_Interface.IAIModel;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.ProducerConfig;


@SpringBootApplication
public class ItsamatchBackendSpellcheckserviceApplication {
    public static void main(String[] args) throws AIModelException{
        SpringApplication.run(ItsamatchBackendSpellcheckserviceApplication.class, args);

        // Create an instance of SpellCheckAIModel
        IAIModel spellCheckAIModel = new SpellCheckAIModel();
//        List<String> syntaxKeywords = new ArrayList<>();

        // Add elements to the list
//        syntaxKeywords.add("Check spelling");
//        syntaxKeywords.add("spell check");
//        double maxExecutionTime = 10.00;

        var consumerProps = new Properties();
        consumerProps.put("bootstrap.servers", "tight-turkey-10793-eu1-kafka.upstash.io:9092");
        consumerProps.put("sasl.mechanism", "SCRAM-SHA-256");
        consumerProps.put("security.protocol", "SASL_SSL");
        consumerProps.put("sasl.jaas.config", "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"dGlnaHQtdHVya2V5LTEwNzkzJCfa-sl5Ouov3oLHQ4Ij_qxl0RuCOkU1Xw9G2NI\" password=\"1ce213f2a4f84205ad519f474aaed298\";");
        consumerProps.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        consumerProps.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        consumerProps.put("auto.offset.reset", "earliest");
        consumerProps.put("group.id", "$GROUP_NAME");

        var producerProps = new Properties();
        producerProps.put("bootstrap.servers", "tight-turkey-10793-eu1-kafka.upstash.io:9092");
        producerProps.put("sasl.mechanism", "SCRAM-SHA-256");
        producerProps.put("security.protocol", "SASL_SSL");
        producerProps.put("sasl.jaas.config", "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"dGlnaHQtdHVya2V5LTEwNzkzJCfa-sl5Ouov3oLHQ4Ij_qxl0RuCOkU1Xw9G2NI\" password=\"1ce213f2a4f84205ad519f474aaed298\";");
        producerProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producerProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        try (var consumer = new KafkaConsumer<String, String>(consumerProps)) {
            consumer.subscribe(Arrays.asList("SpellCheckRequest")); // Replace "your-topic-name" with the actual topic name(s)

            try (KafkaProducer<String, String> producer = new KafkaProducer<>(producerProps)) {
                while (true) {
                    ConsumerRecords<String, String> records = consumer.poll(1000); // Poll for new records (timeout: 1000ms)

                    for (ConsumerRecord<String, String> record : records) {
                        String key = record.key();
                        String value = record.value();
                        String topic = record.topic();
                        int partition = record.partition();
                        long offset = record.offset();

                        // Process the consumed message
                        System.out.printf("Received message: key = %s, value = %s, topic = %s, partition = %d, offset = %d\n",
                                key, value, topic, partition, offset);

                        String textToCheck = value.replace("Please spell check ", "");

                        String checkedText = spellCheckAIModel.execute(textToCheck);
                        System.out.printf("Checked spelling for text: " + checkedText);
                        // Produce a response message
                        ProducerRecord<String, String> responseRecord = new ProducerRecord<>("AIResponse", key, checkedText);
                        producer.send(responseRecord);
                    }
                }
            }
        }
    }
}
