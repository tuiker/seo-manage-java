package com.business.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: GaoLu
 * @Date: 2023-11-13 10:37
 * @Description: 定时器
 */
@Slf4j
@Configuration
//@EnableScheduling
public class SchedulingConfig {

    //@Scheduled(cron = "0 0 0 * * *") // 每天凌晨执行一次(0:0:0)
    //@Scheduled(cron = "0 */10 * * * ?") // 每10分钟执行一次
    //@Scheduled(cron = "*/5 * * * * ?") // 每5秒执行一次

    /**
     * @Description 每半小时同步SDK数据
     * @Author GaoLu
     * @Date 2023/11/13
     * @Return
     **/
//    @Scheduled(cron = "0 */30 * * * ?") // 每30分钟执行一次
//    public void getToken() {
//        try {
//            log.info("定时任务开始.......");
//            //testService.test("刚刚");
//        } catch (Exception e) {
//            log.error("定时任务出错", e);
//        }
//    }

}
