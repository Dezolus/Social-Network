package com.example.like.service

import com.example.like.model.Like
import com.example.like.repository.LikeRepository
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class LikeService(
    private val likeRepository: LikeRepository,
    private val kafkaTemplate: KafkaTemplate<String, String>
) {

    fun likePost(userId: Long, postId: Long): Like {
        val like = Like(userId = userId, postId = postId)
        val savedLike = likeRepository.save(like)

        sendLikeCreatedEvent(savedLike)

        return savedLike
    }

    fun likeComment(userId: Long,postId: Long, commentId: Long): Like {
        val like = Like(userId = userId, postId = postId, commentId = commentId)
        val savedLike = likeRepository.save(like)

        sendLikeCreatedEvent(savedLike)

        return savedLike
    }

    fun getLikesByPostId(postId: Long): List<Like> {
        return likeRepository.findByPostId(postId)
    }

    fun getLikesByCommentId(commentId: Long): List<Like> {
        return likeRepository.findByCommentId(commentId)
    }

    private fun sendLikeCreatedEvent(like: Like) {
        val likeEvent = """
        {
        "id": ${like.id},
        "userId": ${like.userId},
        "postId": ${like.postId},
        "commentId": ${like.commentId},
        "createdAt": "${like.createdAt}"
        }
        """.trimIndent()
        kafkaTemplate.send("like_created", likeEvent)
    }
}