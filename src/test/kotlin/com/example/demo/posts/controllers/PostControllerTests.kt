package com.example.demo.posts.controllers

import com.example.demo.posts.services.PostService
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
	@Autowired
	lateinit var postService: PostService
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
	@Test
	fun `PostController Test - Update Post`(){
		// First, create a new post to update
		val newPost = mapOf(
			"title" to "Post to Update",
			"content" to "Content before update",
			"author" to "Author before update"
		)
		val postJson = mapper.writeValueAsString(newPost)

		val createResult = mockMvc.perform (
			org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/api/v1/posts")
				.contentType("application/json")
				.content(postJson)
		)
			.andExpect(status().isCreated)
			.andReturn()

		val createdPost = mapper.readTree(createResult.response.contentAsString)
		val postId = createdPost.get("id").asText()

		// Now, update the created post
		val updatedPost = mapOf(
			"title" to "Updated Post Title",
			"content" to "Updated Post Content",
			"author" to "Updated Author"
		)
		val updatedPostJson = mapper.writeValueAsString(updatedPost)
		val prevCount = postService.count()
		val updateResult = mockMvc.perform (
			org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put("/api/v1/posts/$postId")
				.contentType("application/json")
				.content(updatedPostJson)
		)
			.andExpect(status().isOk)
			.andReturn()
		val newCount = postService.count()
		assertEquals(prevCount,newCount,"Post count should remain the same after update")
		val updatedPostResponse = mapper.readTree(updateResult.response.contentAsString)
		assertEquals("Updated Post Title", updatedPostResponse.get("title").asText())
		assertEquals("Updated Post Content", updatedPostResponse.get("content").asText())
		assertEquals("Updated Author", updatedPostResponse.get("author").asText())
	}

	@Test
	fun `PostController Test - Delete Post`(){
		// First, create a new post to delete
		val newPost = mapOf(
			"title" to "Post to Delete",
			"content" to "Content to be deleted",
			"author" to "Author to be deleted"
		)
		val postJson = mapper.writeValueAsString(newPost)

		val createResult = mockMvc.perform (
			org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/api/v1/posts")
				.contentType("application/json")
				.content(postJson)
		)
			.andExpect(status().isCreated)
			.andReturn()

		val createdPost = mapper.readTree(createResult.response.contentAsString)
		val postId = createdPost.get("id").asText()

		// Now, delete the created post
		val prevCount = postService.count()
		val deleteResult = mockMvc.perform (
			org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete("/api/v1/posts/$postId")
		)
			.andExpect(status().isNoContent)
			.andReturn()
		val newCount = postService.count()
		assertEquals(prevCount - 1, newCount, "Post count should decrease by one after deletion")
	}
}