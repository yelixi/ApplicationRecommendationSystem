package com.example.demo.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author whz
 * @date 2021/3/30 下午8:25
 **/
@Component
public class SocketConstant {
    public static String SERVER_HOST;
    public static Integer SERVER_PORT;
    public static final String INTERRUPT_FLAG = "over";

    /**
     * 智能填报推荐:
     * 考生分数、
     * 倾向报考的专业、
     * 倾向报考的地区、
     * 是否关注有硕士点、
     * 是否关注有博士点、
     * 是否关注有国家重点实验室
     */
    public static final Integer SMART_FILL=0;
    /**
     * 了解大学：
     * 分数、
     * 省份、
     * 学校层次（985/211等等）、
     * 学校类型（综合类/理工类等等）、
     * 专业大类、
     * 是否关注硕士点、
     * 是否关注博士点、
     * 是否关注国家重点实验室、
     * 学费
     */
    public static final Integer SEE_UNIVERSITY=1;

    @Value("${socket.host}")
    public void setServerHost(String host) {
        SERVER_HOST = host;
    }

    @Value("${socket.port}")
    public void setServerPort(Integer port) {
        SERVER_PORT = port;
    }


}
