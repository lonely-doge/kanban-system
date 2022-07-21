package cn.edu.abtu.kanbansystem.handler.server.notice;

import cn.edu.abtu.kanbansystem.bean.dto.NoticeDTO;
import com.alibaba.fastjson.JSON;
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
public class NoticeDecoder implements Decoder.Text<NoticeDTO> {


    @Override
    public NoticeDTO decode(String s) throws DecodeException {
        log.info("notice content = {}", s);
        return JSON.parseObject(s, NoticeDTO.class);
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
