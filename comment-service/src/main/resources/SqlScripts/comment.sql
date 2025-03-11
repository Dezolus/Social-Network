CREATE TABLE IF NOT EXISTS comments (
    id BIGINT PRIMARY KEY NOT NULL,
    user_id BIGINT NOT NULL,
    post_id BIGINT NOT NULL,
    content VARCHAR(1000) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL
)