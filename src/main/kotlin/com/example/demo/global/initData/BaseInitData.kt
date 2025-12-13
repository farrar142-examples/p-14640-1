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
		if(postService.count() > 0L){
			logger.debug("기본 데이터가 이미 존재합니다. ${postService.count()}")
			return@ApplicationRunner
		}
		logger.debug("기본 데이터 생성 시작")
		for(i in 1..10){
			postService.create(
				title = "기본 글 제목 $i",
				content = "기본 글 내용 $i",
				author = "관리자"
			)
		}
		logger.debug("기본 데이터 생성 완료 ${postService.count()}")
	}
}