package com.example.demo.posts.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import kotlin.test.Test
import kotlin.test.assertEquals

@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTests {
	@Autowired
	lateinit var mockMvc: MockMvc
	val mapper = ObjectMapper()
	@Test
	fun `PostController Test`(){
		val result = mockMvc.perform (get("/api/v1/posts"))
			.andExpect(status().isOk)
			.andReturn()

		val postList = mapper.readTree(result.response.contentAsString)
		assertEquals(postList.size(),10)

		val post = postList.get(0)
		val postId = post.get("id").asText()
		val detailResult = mockMvc.perform (get("/api/v1/posts/$postId"))
			.andExpect(status().isOk)
			.andReturn()
		val detailPost = mapper.readTree(detailResult.response.contentAsString)
		assertEquals(postId,detailPost.get("id").asText())

	}

	@Test
	fun `PostController Test - Data Not Found`(){
		val result = mockMvc.perform (get("/api/v1/posts/invalid-id"))
			.andExpect(status().is4xxClientError)
			.andReturn()
		val errorResponse = mapper.readTree(result.response.contentAsString)
		assertEquals("Post not found with id: invalid-id",errorResponse.get("message").asText())
	}

	@Test
	fun `PostController Test - Create Post`(){
		val newPost = mapOf(
			"title" to "New Post Title",
			"content" to "New Post Content",
			"author" to "New Author"
		)
		val postJson = mapper.writeValueAsString(newPost)

		val result = mockMvc.perform (
			org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/api/v1/posts")
				.contentType("application/json")
				.content(postJson)
		)
			.andExpect(status().isCreated)
			.andReturn()

		val createdPost = mapper.readTree(result.response.contentAsString)
		assertEquals("New Post Title", createdPost.get("title").asText())
		assertEquals("New Post Content", createdPost.get("content").asText())
		assertEquals("New Author", createdPost.get("author").asText())
	}
}