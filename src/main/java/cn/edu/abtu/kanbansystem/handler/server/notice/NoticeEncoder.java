package cn.edu.abtu.kanbansystem.handler.server.notice;

import cn.edu.abtu.kanbansystem.bean.dto.NoticeDTO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
public class NoticeEncoder implements Encoder.Text<NoticeDTO> {


    @Override
    public String encode(NoticeDTO notice) throws EncodeException {
        return JSON.toJSONString(notice);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }

}
