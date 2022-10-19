package com.pvting.reentrantlock;

import android.os.SystemClock;

import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class BeanUtil {
    private static final HashMap<String,Client> clientMap = new HashMap<>();
    private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    /**
     * 全局保存client
     * @param host   ip:port
     * @param client
     */
    public static void addClient(String host, Client client) {
        try{
            lock.writeLock().lock();
            clientMap.put(host, client);
        }finally {
            lock.writeLock().unlock();
        }
    }
    /**
     * 移除断开连接的client
     * @param host  ip:port
     */
    public static void removeClient(String host) {
        try{
            lock.writeLock().lock();
            System.out.println("1112");
            clientMap.remove(host);
        }finally {
            lock.writeLock().unlock();
        }
    }
    /**
     * 获取client
     * @param host  ip:port
     */
    public static Client getClient(String host) {
        try{
            lock.readLock().lock();
            if (clientMap.containsKey(host)) {
                return clientMap.get(host);
            }
            return null;
        }finally {
            lock.readLock().unlock();
        }
    }

    public static boolean sendMessage(Client.Message message) {
        boolean result = false;
        try{
            lock.readLock().lock();
            for (String key : clientMap.keySet()) {
                SystemClock.sleep(500);
                clientMap.get(key).sendMessage(message);
                result = true;
            }
            return result;
        }finally {
            lock.readLock().unlock();
        }

    }

    public static boolean send0002Message() {
        try{
            lock.readLock().lock();
            boolean result = false;
            for (String key : clientMap.keySet()) {
                if(Objects.equals(key, "6")){
                    SystemClock.sleep(5000);
                }

                System.out.println("key:"+key+"::client"+clientMap.get(key));
                clientMap.get(key).send0002();
                result = true;
            }
            return result;
        }finally {
            lock.readLock().unlock();
        }
    }
}