package com.example.demo.comments.controllers

import com.example.demo.comments.services.CommentService
import com.example.demo.global.exception.DataNotFoundException
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
@RequestMapping("/api/v1/posts/{postId}/comments")
class CommentController (
	private val commentService: CommentService,
	private val postService: PostService
){
	@GetMapping
	fun getCommentsByPostId(@PathVariable postId: String) =
		postService.findById(postId)?.let {
			commentService.findCommentsByPostId(postId)
		} ?: throw DataNotFoundException("Post not found with id: $postId")

	@GetMapping("/{commentId}")
	fun findById(
		@PathVariable postId: String,
		@PathVariable commentId: String
	)= postService.findById(postId)?.let {
		commentService.findById(commentId)
			?: throw DataNotFoundException("Comment not found with id: $commentId")
	} ?: throw DataNotFoundException("Post not found with id: $postId")

	data class CreateCommentRequest(
		val content:String,
		val author:String
	)

	@PostMapping
	fun create(
		@PathVariable postId: String,
		@RequestBody body: CreateCommentRequest
		)= postService.findById(postId)?.let { post ->
			ResponseEntity.status(201).body(commentService.create(
				post = post,
				content = body.content,
				author = body.author
			))
		} ?: throw DataNotFoundException("Post not found with id: $postId")

	@PutMapping("/{commentId}")
	fun update(
		@PathVariable postId: String,
		@PathVariable commentId: String,
		@RequestBody body: CreateCommentRequest
	)
	= postService.findById(postId)?.let { post ->
		commentService.findById(commentId)?.let { comment ->
			commentService.update(
				comment = comment,
				content = body.content,
				author = body.author
			)
		} ?: throw DataNotFoundException("Comment not found with id: $commentId")
	}
}