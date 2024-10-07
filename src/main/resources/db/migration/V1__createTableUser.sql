CREATE TABLE User(
    id bigint(20) NOT NULL AUTO_INCREMENT,
    createdAt datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    name varchar(50) NOT NULL,
    email varchar(50) NOT NULL,
    password varchar(100) NOT NULL,
    role enum('STUDENT', 'INSTRUCTOR') CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT 'STUDENT',
    PRIMARY KEY (id),
    CONSTRAINT UC_Email UNIQUE (email)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;
CREATE TABLE Course(
    id bigint(20) NOT NULL AUTO_INCREMENT,
    createdAt datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    name varchar(50) NOT NULL,
    code varchar(10) NOT NULL,
    description varchar(500) NOT NULL,
    instructorEmail varchar(50) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT UC_code UNIQUE (code)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

CREATE TABLE Registration (
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    course_code VARCHAR(10) NOT NULL,
    studentEmail VARCHAR(50) NOT NULL,
    createdAt datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (course_code) REFERENCES Course(code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;
