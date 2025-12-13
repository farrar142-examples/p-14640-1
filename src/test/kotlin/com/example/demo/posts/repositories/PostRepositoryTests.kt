package com.example.demo.posts.repositories

import com.example.demo.posts.documents.Post
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.temporal.ChronoUnit
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull

@SpringBootTest
class PostRepositoryTests {
	@Autowired
	lateinit var postRepository: PostRepository
	@Test
	fun `글 생성 테스트`(){
		Post(title = "첫번째 글", content = "내용입니다",author="작성자")
			.let(postRepository::save)
			.also{
				assertNotNull(it.id)
				println("생성된 글 아이디: ${it.id}")
				val op = postRepository.findById(it.id).orElseThrow()
				val lp = postRepository.findAll().last()
				assertEquals(
					op.createdAt,
					lp.createdAt
				)
				assertNotEquals(it.createdAt,lp.createdAt)
				print("생성일: ${it.createdAt} , 조회된 생성일: ${op.createdAt} , 마지막 글의 생성일: ${lp.createdAt} ")
			}
			.let(postRepository::delete)
	}
}