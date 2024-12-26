-- schema.sql
CREATE TABLE IF NOT EXISTS member (
    member_no INTEGER GENERATED BY DEFAULT AS IDENTITY,
    member_id VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    nick_name VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    gender VARCHAR(1) NOT NULL,
    reg_dt DATETIME,
    mod_dt DATETIME,
    PRIMARY KEY (member_no)
);

CREATE TABLE IF NOT EXISTS todo_list (
    todo_id INTEGER GENERATED BY DEFAULT AS IDENTITY,
    member_no INTEGER NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    status VARCHAR(20) DEFAULT 'PENDING',
    due_date DATETIME,
    reg_dt DATETIME DEFAULT CURRENT_TIMESTAMP,
    mod_dt DATETIME,
    reg_no INTEGER,
    PRIMARY KEY (todo_id),
    FOREIGN KEY (member_no) REFERENCES member (member_no) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS notice (
    notice_id INTEGER GENERATED BY DEFAULT AS IDENTITY,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    reg_dt DATETIME DEFAULT CURRENT_TIMESTAMP,
    mod_dt DATETIME,
    reg_no INTEGER,
    PRIMARY KEY (notice_id),
    FOREIGN KEY (reg_no) REFERENCES member (member_no) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS admin_roles (
    role_id INTEGER GENERATED BY DEFAULT AS IDENTITY,
    member_no INTEGER NOT NULL,
    role_name VARCHAR(50) NOT NULL,
    reg_dt DATETIME DEFAULT CURRENT_TIMESTAMP,
    mod_dt DATETIME,
    reg_no INTEGER,
    PRIMARY KEY (role_id),
    FOREIGN KEY (member_no) REFERENCES member (member_no) ON DELETE CASCADE
);
