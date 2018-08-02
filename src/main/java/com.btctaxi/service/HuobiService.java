package com.btctaxi.service;

public interface HuobiService {
    public void onKlineTick();

    public void onDepthTick();

    public void onTradeDetailTick();

    public void onMarketDetailTick();
}
