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
import com.ntc.rabbit.consumer.ConsumerRBProcess;
import com.ntc.rabbit.consumer.ConsumerRBQueue;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author nghiatc
 * @since May 4, 2017
 */
public class TestConsumer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ConsumerRBQueue cq = new ConsumerRBQueue();
        cq.add(new ThumbnailWorker());
        cq.add(new ThumbnailWorker());
        cq.start();
    }
    
    
    public static class ThumbnailWorker extends ConsumerRBProcess {
        private Logger logger = LoggerFactory.getLogger(ThumbnailWorker.class);
        
        private final static String routingKey = "make_thumb";
        
        public ThumbnailWorker() {
            super(routingKey);
        }
        
        @Override
        public String getRoutingKey() {
            return routingKey;
        }

        @Override
        public void execute(byte[] data) {
            try {
                //String message = new String(data, "UTF-8");
                //System.out.println(" [xxx] Received '" + routingKey + "':'" + message + "'");
                
                Map<String, Object> mapData = JsonUtils.Instance.getMapObject(data);
                System.out.println(" [xxx] Received '" + routingKey + "':'" + mapData + "'");
            } catch (Exception e) {
            }
        }
    }
    
}
