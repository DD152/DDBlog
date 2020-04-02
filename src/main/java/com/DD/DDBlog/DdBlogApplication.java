package com.DD.DDBlog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.DD.DDBlog.dao")
public class DdBlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(DdBlogApplication.class, args);
	}

}
