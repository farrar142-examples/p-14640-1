package com.example.demo.posts.documents

import com.example.demo.global.elasticsearch.documents.BaseDocument
import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.DateFormat
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType
import java.time.LocalDateTime

@Document(indexName = "posts")
class Post (
	@Field(type= FieldType.Text)
	val title:String,
	@Field(type = FieldType.Text)
	val content:String,
	@Field(type = FieldType.Keyword)
	val author:String,
): BaseDocument()