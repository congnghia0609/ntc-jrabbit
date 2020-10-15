# ntc-jrabbit
ntc-jrabbit is a module rabbitmq java client.  

## Maven
```Xml
<dependency>
    <groupId>com.streetcodevn</groupId>
    <artifactId>ntc-jrabbit</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Usage
### Producer
```java
String routingKey = "test_queue";
String msg = "this is message";
byte[] msgb = JsonUtils.Instance.toByteJson(mapData);
ProducerUtil.sendMsg(routingKey, msgb);
```

### Consumer
```java
public static void main(String[] args) {
    ConsumerRBQueue cq = new ConsumerRBQueue();
    cq.add(new SampleWorker());
    cq.add(new SampleWorker());
    cq.start();
}


public static class SampleWorker extends ConsumerRBProcess {
    private Logger logger = LoggerFactory.getLogger(SampleWorker.class);

    private final static String routingKey = "test_queue";
    private final static String amqpUrl = "amqp://username:password@localhost:5672/";

    public ThumbnailWorker() {
        super(routingKey, amqpUrl);
    }

    @Override
    public String getRoutingKey() {
        return routingKey;
    }

    public static String getAmqpUrl() {
        return amqpUrl;
    }

    @Override
    public void execute(byte[] data) {
        try {
            String message = new String(data, "UTF-8");
            System.out.println(" [xxx] Received '" + routingKey + "':'" + message + "'");
        } catch (Exception e) {
        }
    }
}
```

## License
This code is under the [Apache License v2](https://www.apache.org/licenses/LICENSE-2.0).  
