package com.test.redis.operate.produce.domain;

/**
 * description:发送到队列的数据封装模型
 * 该模块下执行controller层方法产生日志,1产生本模块日志,2把本地模块日志发送到处理日志的队列中去(一般是队列那边进行汇总);
 * date:2020/08/12
 * author:weiwenchen
 * company:weiwenco
 */
public class QueueMessage {

    private String queueName;//目标队列名称

    private Object content;//发送到队列的内容

    public QueueMessage(String queueName, Object content) {
        this.queueName = queueName;
        this.content = content;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }



}
