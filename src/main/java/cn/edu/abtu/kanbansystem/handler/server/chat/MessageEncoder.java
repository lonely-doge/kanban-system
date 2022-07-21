package cn.edu.abtu.kanbansystem.handler.server.chat;

import cn.edu.abtu.kanbansystem.bean.system.Message;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * @author Richard
 * @date 2022/2/14 10:30
 */
@Slf4j
@Component
public class MessageEncoder implements Encoder.Text<Message> {


    @Override
    public String encode(Message message) throws EncodeException {
        return new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create()
                .toJson(message);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }

}
