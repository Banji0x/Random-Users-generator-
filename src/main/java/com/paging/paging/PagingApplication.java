package com.paging.paging;

//import com.paging.paging.model.Page;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
public class PagingApplication {
    public static void main(String[] args) {
        SpringApplication.run(PagingApplication.class, args);
    }
}


