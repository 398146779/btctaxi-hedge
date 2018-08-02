package com.btctaxi.controller;

import com.btctaxi.service.HuobiService;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.elasticjob.lite.annotation.ElasticSimpleJob;
import com.huobi.api.client.HuobiApiRestClient;
import com.huobi.api.client.domain.Candle;
import com.huobi.api.client.impl.HuobiApiRestClientImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

@ElasticSimpleJob(cron = "* */1 * * * ?", jobName = "test123", shardingTotalCount = 2, jobParameter = "测试参数", shardingItemParameters = "0=A,1=B")
@Component
public class TradeJob implements com.dangdang.ddframe.job.api.simple.SimpleJob {
    @Autowired(required = false)
    private HuobiService huobiService;
    private static String apiKey = "a";
    private static String apiSecret = "s";
    private HuobiApiRestClient client = new HuobiApiRestClientImpl(apiKey, apiSecret);

    static {
        InputStream is = ClassLoader.getSystemResourceAsStream("config.properties");
        Properties props = new Properties();
        try {
            props.load(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
        apiKey = props.getProperty("apiKey");
        apiSecret = props.getProperty("apiSecret");
    }

    @Override
    public void execute(ShardingContext content) {
        //do something
        System.out.println("JobName:" + content.getJobName());
        System.out.println("JobParameter:" + content.getJobParameter());
        System.out.println("ShardingItem:" + content.getShardingItem());
        System.out.println("ShardingParameter:" + content.getShardingParameter());
        System.out.println("ShardingTotalCount:" + content.getShardingTotalCount());
        System.out.println("TaskId:" + content.getTaskId());
        System.out.println("---------------------------------------");
        Set<Candle> tickers = client.tickers();
        for(Candle candle: tickers){
           if("btcusdt".equals(candle.getSymbol())){

           }
        }

    }
}