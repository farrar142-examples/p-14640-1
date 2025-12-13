package com.example.demo.posts.repositories

import com.example.demo.posts.documents.Post
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@SpringBootTest
class PostRepositoryTests {
	@Autowired
	lateinit var postRepository: PostRepository
	@Test
	fun `글 생성 테스트`(){
		Post(title = "첫번째 글", content = "내용입니다",author="작성자")
			.also{
				assertEquals(0, postRepository.count())
			}
			.let(postRepository::save)
			.also{
				assertNotNull(it.id)
				print("생성된 글 아이디: ${it.id}")
			}
			.let(postRepository::delete)
	}
}