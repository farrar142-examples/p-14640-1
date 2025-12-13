package com.example.demo.comments.controllers

import com.example.demo.comments.services.CommentService
import com.example.demo.posts.services.PostService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
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
	val mapper = ObjectMapper()
	@Test
	fun `CommentController Test - Get Comments by Post ID`(){
		val post = postService.findAll().first()
		mockMvc.perform(get("/api/v1/posts/${post.id}/comments"))
			.andExpect(status().isOk)
			.andReturn()
	}

	@Test
	fun `CommentController Test - Post Comment`(){
		val post = postService.findAll().first()
		val newComment = mapOf(
			"content" to "This is a test comment.",
			"author" to "Test Author"
		)
		val commentJson = mapper.writeValueAsString(newComment)
		mockMvc.perform(
			post("/api/v1/posts/${post.id}/comments")
				.content(commentJson)
				.contentType("application/json")
		)
			.andExpect(status().isCreated)
			.andReturn()
	}

	@Test
	fun `CommentController Test - Get Comment by ID Not Found`(){
		val result = mockMvc.perform (get("/api/v1/posts/invalid-id/comments"))
			.andExpect(status().is4xxClientError)
			.andReturn()
		val errorResponse = mapper.readTree(result.response.contentAsString)
		assert(errorResponse.get("message").asText().contains("Post not found with id: invalid-id"))
	}

	@Test
	fun `CommentController Test - Get Comment by ID`(){
		val post = postService.findAll().first()
		val comment = commentService.create(post, "Sample Comment", "Sample Author")
		val result = mockMvc.perform (get("/api/v1/posts/${post.id}/comments/${comment.id}"))
			.andExpect(status().isOk)
			.andReturn()
		val response = mapper.readTree(result.response.contentAsString)
		assert(response.get("id").asText() == comment.id)
	}

	@Test
	fun `CommentController Test - Put Comment`(){
		val post = postService.findAll().first()
		val comment = commentService.create(post, "Old Comment", "Old Author")
		val updatedComment = mapOf(
			"content" to "Updated Comment",
			"author" to "Updated Author"
		)
		val commentJson = mapper.writeValueAsString(updatedComment)
		val count = commentService.count()
		val result = mockMvc.perform(
			put("/api/v1/posts/${post.id}/comments/${comment.id}")
				.contentType("application/json")
				.content(commentJson)
		)
			.andExpect(status().isOk)
			.andReturn()
		assert(commentService.count() == count)
		val response = mapper.readTree(result.response.contentAsString)
		assert(response.get("content").asText() == "Updated Comment")
		assert(response.get("author").asText() == "Updated Author")
		assert(response.get("id").asText() == comment.id)
	}
}