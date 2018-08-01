package com.btctaxi.service.impl;

import com.btctaxi.service.BianService;
import com.btctaxi.service.HuobiService;
import com.btctaxi.util.HttpUtil;
import com.google.gson.*;

public class HuobiServiceImpl implements HuobiService {

    public String getQutotion(){
        String qutation = HttpUtil.HttpGet("https://api.binance.com/api/v1/exchangeInfo");
        Gson gson = new Gson();
        JsonObject returnData = new JsonParser().parse(qutation).getAsJsonObject();
        JsonElement jsonElement = returnData.get("symbols");
        JsonArray jsonArray = jsonElement.getAsJsonArray();

        for(JsonElement json :jsonArray){
            JsonObject symbol = json.getAsJsonObject();
            JsonElement jsonsymbol = symbol.get("symbol");
            if("ETHBTC".equals(jsonsymbol.getAsString())){
                System.out.println("结果是："+jsonsymbol);
            }
        }
        return null;
    }

    public String order(){
        String qutation = HttpUtil.HttpGet("https://api.huobi.pro/v1/order/orders?");
        Gson gson = new Gson();
        String AccessKeyId="9ef912fe-ae686b6a-2944f3e4-0ad83";
        String SignatureMethod="HmacSHA256";
        String SignatureVersion="2";
        String Timestamp="";
        JsonObject returnData = new JsonParser().parse(qutation).getAsJsonObject();
        JsonElement jsonElement = returnData.get("symbols");
        JsonArray jsonArray = jsonElement.getAsJsonArray();

        for(JsonElement json :jsonArray){
            JsonObject symbol = json.getAsJsonObject();
            JsonElement jsonsymbol = symbol.get("symbol");
            if("ETHBTC".equals(jsonsymbol.getAsString())){
                System.out.println("结果是："+jsonsymbol);
            }
        }
        return null;
    }
}
