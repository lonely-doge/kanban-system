package cn.edu.abtu.kanbansystem.util;

import cn.edu.abtu.kanbansystem.bean.constant.HttpCode;
import cn.edu.abtu.kanbansystem.bean.system.IpAddress;
import cn.edu.abtu.kanbansystem.exception.BaseException;
import cn.hutool.http.HttpUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Richard
 * @date 2022/5/7 16:17
 */
public class IpUtil {


    /**
     * 获取客户端ip地址
     */
    public static IpAddress getIP(final HttpServletRequest request) {
        if (request == null) {
            throw new BaseException(HttpCode.FAILURE, "getIpAddr method HttpServletRequest Object is null");
        }
        String ipStr = request.getHeader("x-forwarded-for");
        if (StringUtils.isBlank(ipStr) || "unknown".equalsIgnoreCase(ipStr)) {
            ipStr = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ipStr) || "unknown".equalsIgnoreCase(ipStr)) {
            ipStr = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ipStr) || "unknown".equalsIgnoreCase(ipStr)) {
            ipStr = request.getRemoteAddr();
        }

        // 多个路由时，取第一个非unknown的ip
        final String[] arr = ipStr.split(",");
        for (final String str : arr) {
            if (!"unknown".equalsIgnoreCase(str)) {
                ipStr = str;
                break;
            }
        }
        //目的是将localhost访问对应的ip 0:0:0:0:0:0:0:1 转成 127.0.0.1
        String ip = "0:0:0:0:0:0:0:1".equals(ipStr) ? "127.0.0.1" : ipStr;
        String res = HttpUtil.get("https://m.ip138.com/iplookup.asp?ip=" + ip);
        String realAddress = res.split("<tbody>")[1]
                .split("</tbody>")[0]
                .split("</td>")[1]
                .split("<td>")[1].trim();
        return new IpAddress(ip, realAddress);
    }
}
