package com.example.like.model

import jakarta.persistence.*
import lombok.*
import org.hibernate.proxy.HibernateProxy
import java.time.Instant

@Getter
@Setter
@Builder
@NoArgsConstructor
@Table(name = "likes")
@Entity
data class Like(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "user_id")
    val userId: Long, // Id пользователя который поставил лайк
    @Column(name = "post_id")
    val postId: Long, // Id поста если лайк посту
    @Column(name = "comment_id")
    val commentId: Long? = null, // Id комментария если лайк к комментарию
    @Column(name = "created_at")
    val createdAt: Instant = Instant.now()
) {
    final override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null) return false
        val oEffectiveClass =
            if (other is HibernateProxy) other.hibernateLazyInitializer.persistentClass else other.javaClass
        val thisEffectiveClass =
            if (this is HibernateProxy) this.hibernateLazyInitializer.persistentClass else this.javaClass
        if (thisEffectiveClass != oEffectiveClass) return false
        other as Like

        return id != null && id == other.id
    }

    final override fun hashCode(): Int =
        if (this is HibernateProxy) this.hibernateLazyInitializer.persistentClass.hashCode() else javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id," +
                " userId = $userId," +
                " postId = $postId," +
                " commentId = $commentId," +
                " createdAt = $createdAt)"
    }
}