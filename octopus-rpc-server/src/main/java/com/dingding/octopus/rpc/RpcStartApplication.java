package com.dingding.octopus.rpc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class RpcStartApplication {

	public static void main(String[] args) {

		SpringApplication.run(RpcStartApplication.class, args);
		//try {
		//	Http2Server.remain(args);
		//} catch (Exception e) {
		//	e.printStackTrace();
		//}
		//while (true){}
	}
}
