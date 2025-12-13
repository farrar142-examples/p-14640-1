package com.example.demo.posts.documents

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.DateFormat
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType
import java.time.LocalDateTime

@Document(indexName = "posts")
class Post (
	@Field(type= FieldType.Text)
	private val title:String,
	@Field(type = FieldType.Text)
	private val content:String,
	@Field(type = FieldType.Keyword)
	private val author:String,
	@Field(type = FieldType.Date, format = [DateFormat.date_hour_minute_second_millis])
	private val createdAt: LocalDateTime = LocalDateTime.now(),
	@Field(type = FieldType.Date, format = [DateFormat.date_hour_minute_second_millis])
	private val modifedAt: LocalDateTime = LocalDateTime.now(),
){
	@Id
	lateinit var id:String
}