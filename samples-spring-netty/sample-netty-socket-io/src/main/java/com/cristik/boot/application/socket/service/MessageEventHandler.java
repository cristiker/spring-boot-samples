package com.cristik.sample.log4j2.socket.service;

import com.alibaba.fastjson.JSONObject;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.cristik.sample.log4j2.socket.entity.MessageInfo;
import com.cristik.sample.log4j2.socket.entity.TextMessage;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author cristik
 */

@Component
public class MessageEventHandler {

    private static final int limitSeconds = 60;
    private static SocketIOServer socketIoServer;

    private static Set<UUID> listClient = Sets.newConcurrentHashSet();
    //线程安全的map
    private static ConcurrentHashMap<String, SocketIOClient> webSocketMap = new ConcurrentHashMap<>();
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public MessageEventHandler(@Autowired SocketIOServer socketIOServer) {
        socketIoServer = socketIOServer;
    }

    public static void sendBuyLogEvent(TextMessage textMessage) {   //这里就是向客户端推消息了
        for (UUID clientId : listClient) {
            if (socketIoServer.getClient(clientId) == null) {
                continue;
            }
            socketIoServer.getClient(clientId).sendEvent("chat", JSONObject.toJSONString(textMessage));
        }
    }

    /**
     * 客户端连接的时候触发，前端js触发：socket = io.connect("http://192.168.9.209:9092");
     *
     * @param client
     */
    @OnConnect
    public void onConnect(SocketIOClient client) {
        logger.info("client {} has connected", client.getSessionId());
        listClient.add(client.getSessionId());
    }

    /**
     * 客户端关闭连接时触发：前端js触发：socket.disconnect();
     *
     * @param client
     */
    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        logger.info("client {} has disconnected", client.getSessionId());
        listClient.remove(client.getSessionId());
    }

    /**
     * 自定义消息事件，客户端js触发：socket.emit('messageevent', {msgContent: msg}); 时触发
     * 前端js的 socket.emit("事件名","参数数据")方法，是触发后端自定义消息事件的时候使用的,
     * 前端js的 socket.on("事件名",匿名函数(服务器向客户端发送的数据))为监听服务器端的事件
     *
     * @param client  　客户端信息
     * @param request 请求信息
     * @param data    　客户端发送数据{msgContent: msg}
     */
    @OnEvent(value = "chat")
    public void onEvent(SocketIOClient client, AckRequest request, MessageInfo data) {
        TextMessage textMessage = JSONObject.parseObject(data.getMsgContent(), TextMessage.class);
        logger.info("user {} send the message: {}", textMessage.getAuthor(), textMessage.getMessage());
        sendBuyLogEvent(textMessage);
    }
}
