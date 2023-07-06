package com.itheima.javarbac.Service.impl;

import com.alibaba.fastjson.JSONObject;
import com.itheima.javarbac.Service.EmailService;
import com.itheima.javarbac.pojo.Result;
import lombok.experimental.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;
import java.util.UUID;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String sendEmail;
    //创建邮箱对象
    private static SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

    @Override
    public Result sendEmail(String email, HttpServletRequest request) {
        System.out.println(sendEmail);
//        定义发送人邮箱
        simpleMailMessage.setFrom(sendEmail);
//        定义收件人邮箱
        simpleMailMessage.setTo(email);
        String password = UUID.randomUUID().toString();
        String subPassword = password.substring(0,6);
//        将密码存在session中
        request.getSession().setAttribute("password",subPassword);
        simpleMailMessage.setText("您的密码是:"+subPassword);
        //如果发送者地址写错抛出运行时异常
        try{
            javaMailSender.send(simpleMailMessage);
        }catch (Exception e){
            return Result.error("请输入正确邮箱");
        }

        return Result.success(200,"请在邮箱处查看自己密码",subPassword );
    }

    @Override
    public Result getCode(String email) {
        int count=1;
        while(count--!=0) {
            String codeNum = "";
            int[] code = new int[3];
            Random random = new Random();
            //自动生成验证码
            for (int i = 0; i < 6; i++) {
                int num = random.nextInt(10) + 48;
                int uppercase = random.nextInt(26) + 65;
                int lowercase = random.nextInt(26) + 97;
                code[0] = num;
                code[1] = uppercase;
                code[2] = lowercase;
                codeNum += (char) code[random.nextInt(3)];
            }
            //标题
            simpleMailMessage.setSubject("您的邮箱验证码是:"+codeNum);
            //内容
            simpleMailMessage.setText("您好！，感谢支持冰咖啡的小站。您的验证码为："+"<h2>"+codeNum+"</h2>"+"千万不能告诉别人哦！");
            //        定义发送人邮箱
            simpleMailMessage.setFrom(sendEmail);
           //        定义收件人邮箱
            simpleMailMessage.setTo(email);
            javaMailSender.send(simpleMailMessage);
            return Result.success(200,"邮箱查看验证码",codeNum);
        }
        return null;
    }
}
