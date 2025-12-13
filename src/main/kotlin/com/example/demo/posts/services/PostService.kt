package com.example.demo.posts.services

import com.example.demo.posts.repositories.PostRepository
import org.springframework.stereotype.Service

@Service
class PostService (
	private val postRepository: PostRepository
){
	fun count(): Long{
		return postRepository.count()
	}
}