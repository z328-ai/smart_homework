package com.itheima.javarbac;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.EnvironmentPBEConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement//开启事务管理
@MapperScan("com.itheima.javarbac.mapper")
public class JavaRbacApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaRbacApplication.class, args);
    }


    //配置加密
    @Bean
    public StandardPBEStringEncryptor jiami(){
        StandardPBEStringEncryptor standardPBEStringEncryptor = new StandardPBEStringEncryptor();
        EnvironmentPBEConfig config = new EnvironmentPBEConfig();

        config.setAlgorithm("PBEWithMD5AndDES");  // 加密的算法，这个算法是默认的
        config.setPassword("12345");   // 加密的密钥
        standardPBEStringEncryptor.setConfig(config);
        return standardPBEStringEncryptor;
    }

}
