package com.example.demo.global.initData

import com.example.demo.global.utils.getLogger
import com.example.demo.posts.services.PostService
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BaseInitData(
	private val postService: PostService
) {
	val logger = getLogger()
	@Bean
	fun baseInitDataRunner() = ApplicationRunner{
		logger.debug("기본 데이터 생성 완료 ${postService.count()}")
	}
}