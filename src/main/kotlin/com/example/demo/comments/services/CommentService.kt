package com.example.demo.comments.services

import com.example.demo.comments.repositories.CommentRepository
import org.springframework.stereotype.Service

@Service
class CommentService (
	private val commentRepository: CommentRepository
){
	fun findCommentsByPostId(postId: String)=commentRepository.findByPostId(postId)
	fun count()= commentRepository.count()

}