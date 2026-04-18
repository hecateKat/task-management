-- ============================================================
-- V1 – Initial schema
-- ============================================================

CREATE TABLE IF NOT EXISTS users
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    username   VARCHAR(100)  NOT NULL UNIQUE,
    password   VARCHAR(255)  NOT NULL,
    email      VARCHAR(255)  NOT NULL UNIQUE,
    first_name VARCHAR(100),
    last_name  VARCHAR(100),
    role       VARCHAR(50)   NOT NULL DEFAULT 'USER',
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    is_deleted BOOLEAN       NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS projects
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    description TEXT,
    start_date  DATE,
    end_date    DATE,
    status      VARCHAR(50)  NOT NULL DEFAULT 'INITIATED',
    is_deleted  BOOLEAN      NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS project_users
(
    project_id BIGINT NOT NULL,
    user_id    BIGINT NOT NULL,
    PRIMARY KEY (project_id, user_id),
    CONSTRAINT fk_pu_project FOREIGN KEY (project_id) REFERENCES projects (id),
    CONSTRAINT fk_pu_user    FOREIGN KEY (user_id)    REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS tasks
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    description TEXT,
    priority    VARCHAR(50)  NOT NULL DEFAULT 'MEDIUM',
    status      VARCHAR(50)  NOT NULL DEFAULT 'NOT_STARTED',
    due_date    TIMESTAMP,
    project_id  BIGINT,
    assignee_id BIGINT,
    is_deleted  BOOLEAN      NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_task_project  FOREIGN KEY (project_id)  REFERENCES projects (id),
    CONSTRAINT fk_task_assignee FOREIGN KEY (assignee_id) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS comments
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    text       TEXT         NOT NULL,
    timestamp  TIMESTAMP    NOT NULL,
    task_id    BIGINT,
    user_id    BIGINT,
    is_deleted BOOLEAN      NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_comment_task FOREIGN KEY (task_id) REFERENCES tasks (id),
    CONSTRAINT fk_comment_user FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS attachments
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    dropbox_file_id  VARCHAR(255),
    file_name        VARCHAR(255),
    upload_date      TIMESTAMP,
    task_id          BIGINT,
    is_deleted       BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_attachment_task FOREIGN KEY (task_id) REFERENCES tasks (id)
);

CREATE TABLE IF NOT EXISTS labels
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(100) NOT NULL,
    color      VARCHAR(50),
    is_deleted BOOLEAN      NOT NULL DEFAULT FALSE
);

