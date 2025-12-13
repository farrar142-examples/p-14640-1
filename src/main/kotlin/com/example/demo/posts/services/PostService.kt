package com.example.demo.posts.services

import com.example.demo.posts.documents.Post
import com.example.demo.posts.repositories.PostRepository
import org.springframework.stereotype.Service

@Service
class PostService (
	private val postRepository: PostRepository
){
	fun count(): Long{
		return postRepository.count()
	}
	fun create(title:String,content:String,author:String): Post{
		return Post(
			title = title,
			content = content,
			author = author
		).let(postRepository::save)
	}
	fun findAll():List<Post> = postRepository.findAll().toList()
	fun findById(id:String): Post? = postRepository.findById(id).orElse(null)
}