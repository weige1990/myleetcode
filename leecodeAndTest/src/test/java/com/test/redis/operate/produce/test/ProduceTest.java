package com.test.redis.operate.produce.test;

import com.rabbitmq.client.*;
import com.test.redis.RabbitMQTestStarter;
import com.test.redis.operate.produce.domain.QueueMessage;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

@SpringBootTest(classes = RabbitMQTestStarter.class)
public class ProduceTest extends AbstractTestNGSpringContextTests {
    @Value("${spring.rabbitmq.username}")
    private String rabbitMQUserName;
    @Value("${spring.rabbitmq.password}")
    private String rabbitMQPassword;
    @Value("${spring.rabbitmq.host}")
    private String rabbitMQHost;
    @Value("${spring.rabbitmq.port}")
    private int rabbitMQPort;
    private String ip1 = "192.168.1.1";
    private String ip2 = "192.168.1.2";
    private String exchangeName = "testExchage";
    private String queueName1 = ip1 + exchangeName;
    private String queueName2 = ip2 + exchangeName;
    private static final String CHANNEL_KEY = "channel";
    private static final String CONSUMER_KEY = "consumer";

    @Test
    public void testSendToMQ() {
        QueueMessage newQueue = new QueueMessage("newQueue", "hellowold" + LocalDateTime.now());
        //1连接到RabbitMQ 返回一个连接工厂,获取连接对象,连接里创建信道
        try {
            Connection connection;
            Channel channel;
            connection = getConnection();
            channel = connection.createChannel();
            //2.1设置两个虚拟ip,2个ip, 2个队列,一个exchange
            // 2.2连接通道发送数据
            String message1 = (LocalDateTime.now().toString() + ip1);
            String message2 = (LocalDateTime.now().toString() + ip2);
            channel.exchangeDeclare(exchangeName, ExchangeTypes.DIRECT, true);
            channel.queueDeclare(queueName1, true, false, false, null);
            channel.queueDeclare(queueName2, true, false, false, null);
            channel.queueBind(queueName1, exchangeName, ip1);
            channel.queueBind(queueName2, exchangeName, ip2);
            channel.basicPublish(exchangeName, ip1, MessageProperties.PERSISTENT_TEXT_PLAIN, message1.getBytes("utf-8"));
            channel.basicPublish(exchangeName, ip2, null, message2.getBytes("utf-8"));
            System.out.println("发送消息:" + message1);
            System.out.println("发送消息:" + message2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //测试发送和确认,这个用于自定义重发机制
    @Test
    public void testPublishConfirm() {
        //1获取连接设定为confirm模式
        Channel channel = null;
        try {
            channel = getConnection().createChannel();
            channel.confirmSelect();

        } catch (Exception e) {
            e.printStackTrace();
        }
        //2设置确认收到的操作,设置确认丢失的操作

        channel.addConfirmListener((deliveryTag, multiple) -> {


            System.out.println("成功收到:" + deliveryTag);

        }, (deliveryTag, multiple) -> {

            System.out.println("丢失:" + deliveryTag);

        });

        //3发送数据开始


    }

    private Map<String, Object> getBasicConsumerInfo() {
        return getBasicConsumerInfo(true);
    }

    private Map<String, Object> getBasicConsumerInfo(boolean ackAfterSuccess) {
        Map<String, Object> result = new HashMap();
        try {
            Connection connection;
            final Channel channel;
            connection = getConnection();
            channel = connection.createChannel();
            //channel.exchangeDeclare(exchangeName, ExchangeTypes.DIRECT);
            /* String queueName = channel.queueDeclare().getQueue();*/
            DefaultConsumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                        throws IOException {
                    String message = new String(body, "UTF-8");
                    System.out.println("routingKey为" + envelope.getRoutingKey());
                    System.out.println("message为" + message);
                    System.out.println("exchange为" + envelope.getExchange());
                    System.out.println("deliveryTag为" + envelope.getDeliveryTag());
                    if (ackAfterSuccess) {
                        channel.basicAck(envelope.getDeliveryTag(), false);//确认已经消费
                    }
                }
            };
            result.put(CONSUMER_KEY, consumer);
            result.put(CHANNEL_KEY, channel);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Test
    public void testConsumeCount() {
        Map<String, Object> basicConsumerInfo = getBasicConsumerInfo(false);
        Channel channel = (Channel) (basicConsumerInfo.get(CHANNEL_KEY));
        Consumer consumer = (Consumer) (basicConsumerInfo.get(CONSUMER_KEY));
        try {
            channel.basicQos(3);
            channel.basicConsume(queueName1, false, "youngerTag1", consumer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testConsume() {
        Map<String, Object> basicConsumerInfo = getBasicConsumerInfo(true);
        Channel channel = (Channel) (basicConsumerInfo.get(CHANNEL_KEY));
        Consumer consumer = (Consumer) (basicConsumerInfo.get(CONSUMER_KEY));
        try {
            //channel.basicQos(1);
            channel.basicConsume(queueName1, false, "youngerTag1", consumer);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Connection getConnection() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername(rabbitMQUserName);
        factory.setPassword(rabbitMQPassword);
        factory.setHost(rabbitMQHost);
        factory.setPort(rabbitMQPort);
        return factory.newConnection();
    }
}
