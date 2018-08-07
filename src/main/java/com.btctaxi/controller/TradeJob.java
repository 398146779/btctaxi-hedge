package com.btctaxi.controller;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.market.BookTicker;
import com.btctaxi.util.HttpUtil;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.elasticjob.lite.annotation.ElasticSimpleJob;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huobi.api.client.HuobiApiRestClient;
import com.huobi.api.client.impl.HuobiApiRestClientImpl;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

@ElasticSimpleJob(cron = "0 0/1 * * * ?", jobName = "test123", shardingTotalCount = 2, jobParameter = "测试参数", shardingItemParameters = "0=A,1=B")
@Component
public class TradeJob implements com.dangdang.ddframe.job.api.simple.SimpleJob {
    private static String apiKey = "a";
    private static String apiSecret = "s";
    private static String binance_apiKey = "a";
    private static String binance_apiSecret = "s";
    private static Double huobi_fee;
    private static Double binance_fee;
    private static Double margin;
    private HuobiApiRestClient client = new HuobiApiRestClientImpl(apiKey, apiSecret);
    private BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance(binance_apiKey, binance_apiSecret);

    static {
        InputStream is = ClassLoader.getSystemResourceAsStream("application.properties");
        Properties props = new Properties();
        try {
            props.load(is);
            apiKey = props.getProperty("accessKey");
            apiSecret = props.getProperty("secretKey");
            binance_apiKey = props.getProperty("binance_apiKey");
            binance_apiSecret = props.getProperty("binance_apiSecret");
            huobi_fee = Double.valueOf(props.getProperty("huobi_fee"));
            binance_fee = Double.valueOf(props.getProperty("binance_fee"));
            margin = Double.valueOf(props.getProperty("margin"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
    @Override
    public void execute(ShardingContext content){
        //do something
        System.out.println("JobName:" + content.getJobName());
        System.out.println("JobParameter:" + content.getJobParameter());
        System.out.println("ShardingItem:" + content.getShardingItem());
        System.out.println("ShardingParameter:" + content.getShardingParameter());
        System.out.println("ShardingTotalCount:" + content.getShardingTotalCount());
        System.out.println("TaskId:" + content.getTaskId());
        System.out.println("---------------------------------------");
        //Merged merged = client.merged("btcusdt");
        String json = HttpUtil.HttpGet("https://api.huobi.pro/market/detail/merged?symbol=btcusdt");
        ObjectMapper mapper = new ObjectMapper();
        String askPrice ="";
        String bidPrice ="";
        try{
            JsonNode rootNode = mapper.readTree(json);
            JsonNode tick = rootNode.path("tick");
            JsonNode ask = tick.path("ask");
            String askString = mapper.writeValueAsString(ask);
            askPrice = askString.split(",")[0];
            askPrice= askPrice.substring(1,askPrice.length());

            JsonNode bid = tick.path("bid");
            String bidString = mapper.writeValueAsString(bid);
            bidPrice = bidString.split(",")[0];
            bidPrice = bidPrice.substring(1, bidPrice.length());
            System.out.println(askPrice);

        }catch (Exception e) {
            e.printStackTrace();
        }


        //String json = HttpUtil.HttpGet("https://api.binance.com/api/v1/ticker/allBookTickers?symbol=btcusdt");

        BinanceApiRestClient binanceApiRestClient = factory.newRestClient();
        List<BookTicker> bookTickers = binanceApiRestClient.getBookTickers();
        for (BookTicker bookTicker : bookTickers) {
            if ("btcusdt".equals(bookTicker.getSymbol())) {
                //买价小于
                if (Double.valueOf(bookTicker.getAskPrice()) < huobi_fee + binance_fee + Double.valueOf(bidPrice) + margin) {

                }
                if (Double.valueOf(askPrice)< huobi_fee + binance_fee + Double.valueOf(bookTicker.getBidPrice()) + margin) {

                }
                break;
            }
        }

    }

}