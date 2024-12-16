-- schema.sql
CREATE TABLE IF NOT EXISTS member (
    member_no INTEGER GENERATED BY DEFAULT AS IDENTITY,
    user_id VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    nick_name VARCHAR(255) NOT NULL,
    PRIMARY KEY (member_no)
);