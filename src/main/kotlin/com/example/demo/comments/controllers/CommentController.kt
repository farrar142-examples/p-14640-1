package com.example.demo.comments.controllers

import com.example.demo.comments.services.CommentService
import com.example.demo.global.exception.DataNotFoundException
import com.example.demo.posts.services.PostService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
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
}