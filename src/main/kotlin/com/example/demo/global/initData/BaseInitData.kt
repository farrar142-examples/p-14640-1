package com.example.demo.global.initData

import com.example.demo.global.utils.getLogger
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BaseInitData {
	val logger = getLogger()
	@Bean
	fun baseInitDataRunner() = ApplicationRunner{
		logger.debug("기본 데이터 생성 완료")
	}
}