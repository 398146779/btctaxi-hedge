package com.btctaxi.service.impl;

import com.btctaxi.service.BianService;
import com.btctaxi.util.HttpUtil;
import com.google.gson.*;

public class BianServiceImpl implements BianService{
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

}
