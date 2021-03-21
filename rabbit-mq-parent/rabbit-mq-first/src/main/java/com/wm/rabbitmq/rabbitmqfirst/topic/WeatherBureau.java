package com.wm.rabbitmq.rabbitmqfirst.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
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
        area.put("china.hubei.wuhan.20210321","中国湖北武汉20210321天气数据");
        area.put("us.cal.lsj.20210321","美国加州洛杉矶20210321天气数据");
        area.put("china.shanxi.xian.20210322","中国陕西西安20210322天气数据");

        area.put("china.hebei.shijiazhuang.20210321","中国河北石家庄20210321天气数据");
        area.put("china.hubei.wuhan.20210322","中国湖北武汉20210322天气数据");
        area.put("us.cal.lsj.20210322","美国加州洛杉矶20210322天气数据");
        area.put("china.hebei.shijiazhuang.20210322","中国河北石家庄20210321天气数据");

        try {
            Connection connection = RabbitUtil.getConnection();


            Channel channel = connection.createChannel();
            Iterator<Map.Entry<String, String >> itr = area.entrySet().iterator();
            while(itr.hasNext()){
                Map.Entry<String, String> me =  itr.next();
                // 第一个参数为交换机
                // 第二个参数Rotuing key 是消息的标记
                channel.basicPublish(RabbitConstant.EXCHANGE_WEATHER_TOPIC, me.getKey(),null, me.getValue().getBytes());
            }

            channel.close();
            connection.close();
            System.out.println("天气消息发送成功");
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
