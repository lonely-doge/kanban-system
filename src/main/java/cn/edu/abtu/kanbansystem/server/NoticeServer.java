package cn.edu.abtu.kanbansystem.server;

import cn.edu.abtu.kanbansystem.bean.dto.NoticeDTO;
import cn.edu.abtu.kanbansystem.config.CustomSpringConfigurator;
import cn.edu.abtu.kanbansystem.handler.server.notice.NoticeDecoder;
import cn.edu.abtu.kanbansystem.handler.server.notice.NoticeEncoder;
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

/**
 * @author Richard
 * @date 2022/5/9 10:58
 */
@Slf4j
@Component
@ServerEndpoint(
        value = "/notice/{id}",
        decoders = {NoticeDecoder.class},
        encoders = {NoticeEncoder.class},
        configurator = CustomSpringConfigurator.class
)
@CrossOrigin
public class NoticeServer {

    private Session session;

    private final Gson gson;

    @Autowired
    public NoticeServer(Gson gson) {
        this.gson = gson;
    }


    /**
     * 存储所有的用户连接
     */
    private static final Map<String, Session> NOTICE_MAP = new ConcurrentHashMap<>();


    @OnOpen
    public void onOpen(@PathParam("id") String id, Session session) {
        this.session = session;
        NOTICE_MAP.put(id, session);
    }

    @OnMessage
    public void onMessage(NoticeDTO notice) throws IOException {
        if (NOTICE_MAP.get(notice.getDestination()) != null) {
            NOTICE_MAP.get(notice.getDestination()).getBasicRemote().sendText(gson.toJson(notice));
        }
    }

    @OnClose
    public void onClose() {
        // 用户退出时，从 map 中删除信息
        for (Map.Entry<String, Session> entry : NOTICE_MAP.entrySet()) {
            if (this.session.getId().equals(entry.getValue().getId())) {
                NOTICE_MAP.remove(entry.getKey());
                return;
            }
        }
    }

    @OnError
    public void onError(Throwable error) {
        error.printStackTrace();
    }

}
