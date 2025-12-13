package com.example.demo.global.initData

import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BaseInitData {
	@Bean
	fun baseInitDataRunner() = ApplicationRunner{
		println("기본 데이터 생성 완료")
	}
}