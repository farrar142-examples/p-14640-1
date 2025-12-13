package com.example.demo.comments.controllers

import com.example.demo.comments.services.CommentService
import com.example.demo.posts.services.PostService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class CommentControllerTests {
	@Autowired
	lateinit var mockMvc: MockMvc
	@Autowired
	lateinit var commentService: CommentService
	@Autowired
	lateinit var postService: PostService

	@Test
	fun `CommentController Test - Get Comments by Post ID`(){
		val post = postService.findAll().first()
		mockMvc.perform(get("/api/v1/posts/${post.id}/comments"))
			.andExpect(status().isOk)
			.andReturn()
	}
}