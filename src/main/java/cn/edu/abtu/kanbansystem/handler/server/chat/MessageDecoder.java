package cn.edu.abtu.kanbansystem.handler.server.chat;

import cn.edu.abtu.kanbansystem.bean.system.Message;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/**
 * @author Richard
 * @date 2022/2/14 10:28
 */
@Slf4j
@Component
public class MessageDecoder implements Decoder.Text<Message> {


    @Override
    public Message decode(String s) throws DecodeException {
        return new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create()
                .fromJson(s, Message.class);
    }

    @Override
    public boolean willDecode(String s) {
        return true;
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
