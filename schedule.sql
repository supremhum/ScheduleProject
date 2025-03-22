USE schedule;

CREATE TABLE schedule
(
    id  BIGINT  AUTO_INCREMENT    PRIMARY KEY COMMENT '스케줄 식별자',
    writer  VARCHAR(20) NOT NULL    COMMENT '작성자',
    title  VARCHAR(50)  NOT NULL COMMENT    '제목',
    password    VARCHAR(100)    NOT NULL    COMMENT '비밀번호',
    create_date DATETIME    NOT NULL    COMMENT '생성일',
    update_date DATETIME    NOT NULL    COMMENT '수정일'
);