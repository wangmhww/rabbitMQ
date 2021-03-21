package com.wm.rabbitmq.rabbitmqfirst.comfirm;

import com.rabbitmq.client.*;
import com.wm.rabbitmq.rabbitmqfirst.utils.RabbitConstant;
import com.wm.rabbitmq.rabbitmqfirst.utils.RabbitUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * 发布者
 * @author wangm
 * @title: WeatherBureau
 * @projectName rabbit-mq-parent
 * @description: TODO
 * @date 2021/3/2115:28
 */
public class WeatherBureau {
    public static void main(String[] args) throws IOException {
        Map area = new HashMap<String, String>();
        area.put("china.shanxi.xian.20210321","中国陕西西安20210321天气数据");
        area.put("china.hubei.wuhan.20210322","中国湖北武汉20210321天气数据");
        area.put("us.cal.lsj.20210321","美国加州洛杉矶20210321天气数据");
        area.put("china.shanxi.xian.20210322","中国陕西西安20210322天气数据");

        area.put("china.hebei.shijiazhuang.20210321","中国河北石家庄20210321天气数据");
        area.put("china.hubei.wuhan.20210322","中国湖北武汉20210322天气数据");
        area.put("us.cal.lsj.20210322","美国加州洛杉矶20210322天气数据");
        area.put("china.hebei.shijiazhuang.20210322","中国河北石家庄20210321天气数据");

        Connection connection = RabbitUtil.getConnection();


        Channel channel = connection.createChannel();
        channel.confirmSelect();
        channel.addConfirmListener(new ConfirmListener() {
            @Override
            public void handleAck(long l, boolean b) throws IOException {
                // 第二个参数为是否批量接受 一般用不到
                System.out.println("消息已被接收,TAG:" + l);
            }

            @Override
            public void handleNack(long l, boolean b) throws IOException {
                System.out.println("消息已被Bocker拒收,TAG:" + l);
            }
        });
        channel.addReturnListener(returnMessage -> {
            System.out.println("==================");
            System.out.println("Return编码:"+ returnMessage.getReplyCode() + "-Return描述:"+ returnMessage.getReplyText());
            System.out.println("交换机:" + returnMessage.getExchange() + "-路由key:" + returnMessage.getRoutingKey());
            System.out.println("Return主题:" + new String(returnMessage.getBody()));
            System.out.println("==================");

        });

        Iterator itr = area.entrySet().iterator();

        while(itr.hasNext()){
            Map.Entry<String, String> me = (Map.Entry<String, String>) itr.next();
            // 第一个参数为交换机
            // 第二个参数Rotuing key 是消息的标记
            channel.basicPublish(RabbitConstant.EXCHANGE_WEATHER_TOPIC, me.getKey(),null, me.getValue().getBytes());
        }

    }
}
