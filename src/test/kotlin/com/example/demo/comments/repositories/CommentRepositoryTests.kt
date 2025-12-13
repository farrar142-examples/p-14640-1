package com.example.demo.comments.repositories

import com.example.demo.comments.documents.Comment
import com.example.demo.posts.repositories.PostRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.assertNotEquals


@SpringBootTest
class CommentRepositoryTests {
	@Autowired
	lateinit var commentRepository: CommentRepository
	@Autowired
	lateinit var postRepository: PostRepository

	@Test
	fun `CommentRepository Test - create index`(){
		val count = commentRepository.count()
		val post =postRepository.findAll().first()
		println("Comment count: $count")
		val comment = Comment(
			postId = post.id!!,
			content = "This is a comment.",
			author = "Commenter"
		).let(commentRepository::save)
		assertNotEquals(count,commentRepository.count())
		assertNotNull(comment.id)
	}
}