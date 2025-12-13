package com.example.demo.posts.repositories

import com.example.demo.posts.documents.Post
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository

interface PostRepository: ElasticsearchRepository<Post,String> {

}