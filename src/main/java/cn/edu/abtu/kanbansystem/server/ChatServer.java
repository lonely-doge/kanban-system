package cn.edu.abtu.kanbansystem.server;

import cn.edu.abtu.kanbansystem.bean.system.Message;
import cn.edu.abtu.kanbansystem.config.CustomSpringConfigurator;
import cn.edu.abtu.kanbansystem.handler.server.chat.MessageDecoder;
import cn.edu.abtu.kanbansystem.handler.server.chat.MessageEncoder;
import cn.edu.abtu.kanbansystem.util.RedisUtil;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Richard
 * @date 2022/2/14 10:46
 */
@Slf4j
@Component
@ServerEndpoint(
        value = "/chat/{id}",
        decoders = {MessageDecoder.class},
        encoders = {MessageEncoder.class},
        configurator = CustomSpringConfigurator.class
)
@CrossOrigin
public class ChatServer {

    private Session session;

    private final Gson gson;

    private final RedisUtil redis;

    @Autowired
    public ChatServer(Gson gson, RedisUtil redis) {
        this.gson = gson;
        this.redis = redis;
    }

    /**
     * 存储所有的用户连接
     */
    private static final Map<String, Session> CHAT_MAP = new ConcurrentHashMap<>();


    @OnOpen
    public void onOpen(@PathParam("id") String id, Session session) {

        this.session = session;
        // 根据 /websocket/{id} 中传入的用户 id 作为键，存储每个用户的 session
        CHAT_MAP.put(id, session);
    }

    @OnMessage
    public void onMessage(Message message) throws IOException {
        // 根据消息实体中的消息发送者和接受者的 id 组成信箱存储的键
        // 按两人id升序并以 - 字符分隔为键
        String key = Stream.of(message.getOrigin(), message.getDestination())
                .sorted()
                .collect(Collectors.joining("-"));
        // 将信息存储到 redis 中
        redis.pushMessage(key, message);
        // 如果用户在线就将信息发送给指定用户
        if (CHAT_MAP.get(message.getDestination()) != null) {
            CHAT_MAP.get(message.getDestination()).getBasicRemote().sendText(gson.toJson(message));
        }
    }

    @OnClose
    public void onClose() {
        // 用户退出时，从 map 中删除信息
        for (Map.Entry<String, Session> entry : CHAT_MAP.entrySet()) {
            if (this.session.getId().equals(entry.getValue().getId())) {
                CHAT_MAP.remove(entry.getKey());
                return;
            }
        }
    }

    @OnError
    public void onError(Throwable error) {
        error.printStackTrace();
    }
}
