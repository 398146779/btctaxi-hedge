package com.btctaxi.controller;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.elasticjob.lite.annotation.ElasticSimpleJob;
import org.springframework.stereotype.Component;

@ElasticSimpleJob(cron="* */5 * * * ?",jobName="test456",shardingTotalCount=2,jobParameter="测试参数",shardingItemParameters="0=C,1=D")
@Component
public class MyJob2 implements com.dangdang.ddframe.job.api.simple.SimpleJob {

    @Override
    public void execute(ShardingContext content) {
        //do something
        System.out.println("JobName:"+content.getJobName());
        System.out.println("JobParameter:"+content.getJobParameter());
        System.out.println("ShardingItem:"+content.getShardingItem());
        System.out.println("ShardingParameter:"+content.getShardingParameter());
        System.out.println("ShardingTotalCount:"+content.getShardingTotalCount());
        System.out.println("TaskId:"+content.getTaskId());
        System.out.println("---------------------------------------");

    }
}