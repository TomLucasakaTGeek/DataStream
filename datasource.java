import java.time.Duration;
import org.apache.kafka.clients.consumer.*;

import java.util.Collections;
import java.util.Properties;

abstract class DataSource {
    protected String topic;
    public DataSource(String topic) {
        this.topic = topic;
    }
    abstract String fetchData();
}

class KafkaDataSource extends DataSource {
    private KafkaConsumer<String, String> consumer;
    public KafkaDataSource(String topic) {
        super(topic);
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost: 9092");
        props.put("group.id", "sustainability-group");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.serialization.StringDeserializer");

        consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(topic));
    }

    @Override
    String fetchData() {
        Consumer<String, String> records = consumer.poll(Duration.ofMillis(100));
        return records.isEmpty() ? "" : records.iterator().next().value();
    }
}