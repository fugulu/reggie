package com.itheima.reggie.util;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import lombok.extern.slf4j.Slf4j;

/**
 * 短信发送工具类
 */
@Slf4j
public class SMSUtils {

    /**
     * 发送短信
     * @param signName 签名
     * @param templateCode 模板
     * @param phoneNumbers 手机号
     * @param code 验证码
     * @return 返回发送的状态码
     */
    public static String sendMessage(String signName, String templateCode, String phoneNumbers, String code) {
        //这里需要修改发送短信的ID和密码
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "client_id", "client_secret");
        IAcsClient client = new DefaultAcsClient(profile);

        SendSmsRequest request = new SendSmsRequest();
        request.setSysRegionId("cn-hangzhou");
        request.setPhoneNumbers(phoneNumbers);
        request.setSignName(signName);
        request.setTemplateCode(templateCode);
        request.setTemplateParam("{\"code\":\"" + code + "\"}");
        String respCode = null;
        try {
            //获取发送的响应结果
            SendSmsResponse response = client.getAcsResponse(request);
            //得到响应的状态码
            log.info("请求id: " + response.getRequestId() + "，业务号：" + response.getBizId() + "，响应码：" + response.getCode() + "，响应消息：" + response.getMessage());
            respCode = response.getCode();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return respCode;
    }

}
