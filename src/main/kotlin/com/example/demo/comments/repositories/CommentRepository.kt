package com.example.demo.comments.repositories

import com.example.demo.comments.documents.Comment
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository

interface CommentRepository : ElasticsearchRepository<Comment,String> {
}