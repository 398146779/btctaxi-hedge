//package com.btctaxi.service;
//
//import com.huobi.api.client.domain.enums.MergeLevel;
//import com.huobi.api.client.domain.enums.Resolution;
//import com.huobi.api.client.impl.HuobiApiWebSocketClientImpl;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.stereotype.Service;
//
//import java.io.Closeable;
//import java.io.IOException;
//@Service
//public class HuobiServiceImpl implements HuobiService{
//    private HuobiApiWebSocketClientImpl ws = new HuobiApiWebSocketClientImpl();
//    Closeable stream;
//
//    public void onKlineTick() {
//        stream = ws.onKlineTick("BTCUSDT", Resolution.M1, data -> {
//            if (StringUtils.isNotEmpty(data.getSubbed())) {
//                System.out.println(data.getSubbed());
//            } else {
//                System.out.println(data.getTick().getClose());
//            }
//        });
//
//    }
//
//    public void onDepthTick() {
//        stream = ws.onDepthTick("BTCUSDT", MergeLevel.STEP0, data -> {
//            System.out.println(data.getRep());
//        });
//    }
//
//
//
//    public void onTradeDetailTick(){
//        stream = ws.onTradeDetailTick("BTCUSDT", data -> {
//            System.out.println(data.getTs());
//        });
//    }
//
//    public void onMarketDetailTick(){
//        stream = ws.onMarketDetailTick("BTCUSDT", data -> {
//            System.out.println(data.getTick().getClose());
//        });
//    }
//
//
//    public void after() throws InterruptedException, IOException {
//        for (int i = 0; i < 10; i++) {
//            Thread.sleep(1000L);
//        }
//        stream.close();
//    }
//}
