/*
 * Copyright 2017 nghiatc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ntc.rabbit;

import com.ntc.jackson.JsonUtils;
import com.ntc.rabbit.producer.ProducerRB;
import com.ntc.rabbit.producer.ProducerUtil;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author nghiatc
 * @since May 3, 2017
 */
public class TestProducer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            testNV();
            
            System.out.println("Run test done!!!...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static class RunTask2 implements Runnable {
        public int begin = 0;

        public RunTask2(int begin) {
            try {
                this.begin = begin;
            } catch (Exception e) {
            }
        }
        
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()+" Start...");
            try {
                String routingKey = "make_thumb";
                int max = begin + 100;
                //for(int i = begin; i < max; i++){
                for(int i = begin; true; i++){
                    System.out.println("Send Message no: " + i);
                    Map<String, Object> mapData = new HashMap<>();
                    mapData.put("pathFile", "/data/files/tmp/BI" + i + ".png");
                    mapData.put("id", i);
                    String msg = JsonUtils.Instance.toJson(mapData);
                    System.out.println("msg: " + msg);

                    byte[] msgb = JsonUtils.Instance.toByteJson(mapData);
                    //System.out.println("msgb: " + msgb);

                    ProducerUtil.sendMsg(routingKey, msgb);
                    
                    Thread.sleep(2000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+" End.|||");
        }
    }
    
    public static void testNV(){
        try {
            
            ExecutorService executor = Executors.newFixedThreadPool(5);
            Thread t1 = new Thread(new RunTask2(0));
            Thread t2 = new Thread(new RunTask2(3000));
            Thread t3 = new Thread(new RunTask2(5000));
            Thread t4 = new Thread(new RunTask2(7000));
            executor.execute(t1);
            executor.execute(t2);
            executor.execute(t3);
            executor.execute(t4);
            
            executor.shutdown();
            while (!executor.isTerminated()) {
            }
            System.out.println("Finished all threads");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void testN100Util(){
        try {
            String routingKey = "make_thumb";
            
            for(int i = 0; i < 100; i++){
                System.out.println("Send Message no: " + i);
                Map<String, Object> mapData = new HashMap<>();
                mapData.put("pathFile", "/data/files/tmp/BI" + i + ".png");
                mapData.put("id", i);
                String msg = JsonUtils.Instance.toJson(mapData);
                System.out.println("msg: " + msg);
                
                byte[] msgb = JsonUtils.Instance.toByteJson(mapData);
                System.out.println("msgb: " + msgb);
                
                ProducerUtil.sendMsg(routingKey, msgb);
            }
            
            System.out.println("Run test done!!!...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void testN100(){
        try {
            String routingKey = "make_thumb";
            String amqpUrl = "amqp://username:password@localhost:5672/";
            ProducerRB prb = new ProducerRB(routingKey);

            for(int i = 0; i < 100; i++){
                System.out.println("Send Message no: " + i);
                
                Map<String, Object> mapData = new HashMap<>();
                mapData.put("pathFile", "/data/files/tmp/BI" + i + ".png");
                mapData.put("id", i);
                String msg = JsonUtils.Instance.toJson(mapData);
                System.out.println("msg: " + msg);

                byte[] msgb = JsonUtils.Instance.toByteJson(mapData);
                System.out.println("msgb: " + msgb);

                //prb.sendMessage(msg.getBytes("utf-8"));
                prb.sendMessage(msgb);
            }
            
            System.out.println("Run test done!!!...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void test1(){
        try {
            String routingKey = "make_thumb";
            String amqpUrl = "amqp://username:password@localhost:5672/";
            ProducerRB prb = new ProducerRB(routingKey);

            Map<String, Object> mapData = new HashMap<>();
            mapData.put("pathFile", "/data/files/tmp/BI0.png");
            mapData.put("id", 0);
            String msg = JsonUtils.Instance.toJson(mapData);
            System.out.println("msg: " + msg);

            byte[] msgb = JsonUtils.Instance.toByteJson(mapData);
            System.out.println("msgb: " + msgb);

            //prb.sendMessage(msg.getBytes("utf-8"));
            prb.sendMessage(msgb);

            System.out.println("Run test done!!!...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
