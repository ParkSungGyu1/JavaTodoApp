DROP TABLE IF EXISTS todo;
DROP TABLE IF EXISTS member;

#일반 ver
CREATE TABLE todo (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      title VARCHAR(255),
                      username VARCHAR(255),
                      description VARCHAR(255),
                      password VARCHAR(255),
                      created_at VARCHAR(10),
                      updated_at VARCHAR(10)
);


#외래키 ver
CREATE TABLE todo (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      title VARCHAR(255),
                      description VARCHAR(255),
                      password VARCHAR(255),
                      member_id BIGINT,
                      created_at VARCHAR(10),
                      updated_at VARCHAR(10),
                      FOREIGN KEY (member_id) REFERENCES member(id)
);

CREATE TABLE member (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        username VARCHAR(255),
                        email VARCHAR(255),
                        created_at VARCHAR(10),
                        updated_at VARCHAR(10)
);

#유저 추가 쿼리
INSERT INTO member (username, email, created_at, updated_at)
VALUES ('박유저', 'user@example.com', '2024-10-03', '2024-10-04');

SELECT * FROM todo
left join member m on todo.member_id = m.id;
