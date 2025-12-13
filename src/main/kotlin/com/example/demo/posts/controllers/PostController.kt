package com.example.demo.posts.controllers

import com.example.demo.global.exception.DataNotFoundException
import com.example.demo.posts.documents.Post
import com.example.demo.posts.services.PostService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/posts")
class PostController(
	private val postService: PostService
) {
	@GetMapping
	fun getAll() =
		postService.findAll()

	@GetMapping("/{id}")
	fun getById(@PathVariable id:String) =
		postService.findById(id)?: throw DataNotFoundException("Post not found with id: $id")

	data class CreatePostRequest(
		val title:String,
		val content:String,
		val author:String
	)
	@PostMapping
	fun create(@RequestBody request: CreatePostRequest): ResponseEntity<Post> {
		val createdPost = postService.create(
			title = request.title,
			content = request.content,
			author = request.author
		)
		return ResponseEntity.status(201).body(createdPost)
	}
	data class UpdatePostRequest(
		val title:String?,
		val content:String?,
		val author:String?
	)
	@PutMapping("/{id}")
	fun update(@PathVariable id:String, @RequestBody post: UpdatePostRequest): Post {
		val existingPost = postService.findById(id)
			?: throw DataNotFoundException("Post not found with id: ${id}")
		return postService.update(
			existingPost,
			title = post.title,
			content = post.content,
			author = post.author
		)
	}
	@DeleteMapping("/{id}")
	fun delete(@PathVariable id:String): ResponseEntity<Void>{
		val existingPost = postService.findById(id)
			?: throw DataNotFoundException("Post not found with id: ${id}")
		postService.delete(existingPost)
		return ResponseEntity.noContent().build()
	}

}