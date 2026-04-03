CREATE TABLE comments
(
    id      UUID DEFAULT uuidv7() NOT NULL,
    post_id UUID DEFAULT uuidv7(),
    content VARCHAR(140)          NOT NULL,
    created TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW()    NOT NULL,
    updated TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW()    NOT NULL,
    CONSTRAINT pk_comments PRIMARY KEY (id)
);

CREATE TABLE posts
(
    id      UUID DEFAULT uuidv7() NOT NULL,
    title   VARCHAR(60)           NOT NULL,
    body    VARCHAR(280)          NOT NULL,
    created TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW()    NOT NULL,
    updated TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW()    NOT NULL,
    CONSTRAINT pk_posts PRIMARY KEY (id)
);

ALTER TABLE comments
    ADD CONSTRAINT FK_COMMENTS_ON_POST FOREIGN KEY (post_id) REFERENCES posts (id);

CREATE INDEX idx_comments_post_id ON comments (post_id);