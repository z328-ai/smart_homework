package com.itheima.javarbac;

import com.itheima.javarbac.Service.EmailService;
import com.itheima.javarbac.Service.HomeworkService;
import com.itheima.javarbac.pojo.Result;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.EnvironmentPBEConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JavaRbacApplicationTests {
 @Autowired
 private EmailService emailService;
 @Autowired
 private HomeworkService homeworkService;
    @Test
    void contextLoads() {
    }
    @Test
    public void testEncrypt() throws Exception {
        StandardPBEStringEncryptor standardPBEStringEncryptor = new StandardPBEStringEncryptor();
        EnvironmentPBEConfig config = new EnvironmentPBEConfig();

        config.setAlgorithm("PBEWithMD5AndDES");  // 加密的算法，这个算法是默认的
        config.setPassword("12345");   // 加密的密钥
        standardPBEStringEncryptor.setConfig(config);
        String plainText = "hello";
        String encryptedText = standardPBEStringEncryptor.encrypt("zw123");
        String decrypt = standardPBEStringEncryptor.decrypt("8ekqgxF4VPHoOjd5Fw7XUA==");
        System.out.println(decrypt);
    }


}
