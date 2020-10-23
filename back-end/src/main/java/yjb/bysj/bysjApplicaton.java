package yjb.bysj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("yjb.bysj.mapper") // 指定要扫描的Mapper类的包的路径
public class bysjApplicaton {
    public static void main(String[] args) {
        SpringApplication.run(bysjApplicaton.class, args);
    }
}
