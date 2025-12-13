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
	}
}