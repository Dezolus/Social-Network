package com.example.like.controller

import com.example.like.model.Like
import com.example.like.service.LikeService
import org.springframework.web.bind.annotation.*

@RestController
class LikeController (val likeService: LikeService) {

    @PostMapping("/post")
    fun likePost(@RequestParam userId: Long, @RequestParam postId: Long): Like {
        return likeService.likePost(userId, postId)
    }

    @PostMapping("/comment")
    fun comment(@RequestParam userId: Long, @RequestParam postId: Long, @RequestParam commentId: Long): Like {
        return likeService.likeComment(userId, postId, commentId)
    }

    @GetMapping("/post/{postId}")
    fun post(@PathVariable postId: Long): List<Like> {
        return likeService.getLikesByPostId(postId)
    }

    @GetMapping("/comment/{commentId}")
    fun comment(@PathVariable commentId: Long): List<Like> {
        return likeService.getLikesByCommentId(commentId)
    }
}