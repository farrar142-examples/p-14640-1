package com.example.demo.comments.services

import com.example.demo.comments.documents.Comment
import com.example.demo.comments.repositories.CommentRepository
import com.example.demo.posts.documents.Post
import org.springframework.stereotype.Service

@Service
class CommentService (
	private val commentRepository: CommentRepository
){
	fun findCommentsByPostId(postId: String)=commentRepository.findByPostId(postId)
	fun count()= commentRepository.count()
	fun create(post: Post, content:String, author:String)=
		Comment(
			postId = post.id,
			content = content,
			author = author
		).let(commentRepository::save)

}