package com.mall.service.utils;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by DRAGON on 2017/11/2.
 */
public class WechatMessageQueue {


    private static final int initDeplayTime = 1;
    private static final int period = 1;

    private static WechatMessageQueue wechatMessageQueue;

    private WechatMessageQueue(){
        super();
    }

    public static WechatMessageQueue getInstance(){
        if(wechatMessageQueue == null){
            synchronized (WechatMessageQueue.class){
                if (wechatMessageQueue == null){
                    wechatMessageQueue = new WechatMessageQueue();
                }
            }
        }
        return wechatMessageQueue;
    }

    //队列
    private Queue<WechatMessage> queue = new ConcurrentLinkedQueue<WechatMessage>();
    //定时任务
    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

    public boolean addData(WechatMessage data){
        if(queue == null){
            queue = new ConcurrentLinkedQueue<WechatMessage>();
        }
        return queue.add(data);
    }

    public WechatMessage getData(){
        synchronized (WechatMessageQueue.class){
            if (!queue.isEmpty()){
                return queue.poll();
            }
           return null;
        }
    }


    public void start(SendMessageListener sendMessageListener){
        scheduledExecutorService.scheduleAtFixedRate(new SendTask(sendMessageListener),initDeplayTime,period, TimeUnit.SECONDS);
    }

    public void stop(){
        scheduledExecutorService.shutdown();
    }

    private class SendTask implements Runnable{

        SendMessageListener sendMessageListener;
        public SendTask(SendMessageListener sendMessageListener) {
            this.sendMessageListener = sendMessageListener;
        }

        @Override
        public void run() {
            System.out.println("task is running");
            if (this.sendMessageListener != null){
                WechatMessage message = getData();
                if (message != null){
                    this.sendMessageListener.sendMessagge(message);
                }
            }
        }
    }

    public interface SendMessageListener{
        void sendMessagge(WechatMessage message);
    }



/*    public static void main(String args[]) {

        WechatMessageQueue wmq = WechatMessageQueue.getInstance();

        wmq.start(new SendMessageListener() {
            @Override
            public void sendMessagge(WechatMessage message) {
                System.out.println(message);
            }
        });


    }*/


}
