package com.example.like.repository

import com.example.like.model.Like
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LikeRepository : JpaRepository<Like, Long> {
    fun findByPostId(postId: Long): List<Like>
    fun findByCommentId(commentId: Long): List<Like>
}