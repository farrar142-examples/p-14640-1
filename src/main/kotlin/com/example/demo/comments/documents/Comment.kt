package com.example.demo.comments.documents

import com.example.demo.global.elasticsearch.documents.BaseDocument
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType

@Document(indexName = "comments")
class Comment (
	@Field(type = FieldType.Keyword)
	val postId: String,
	@Field(type = FieldType.Text)
	val content: String,
	@Field(type = FieldType.Keyword)
	val author: String
): BaseDocument()