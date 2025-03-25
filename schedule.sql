USE sparta;

CREATE TABLE schedule
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY  COMMENT '스케줄 식별자',
    author VARCHAR(20)    NOT NULL        COMMENT '작성자',
    title VARCHAR(50)     NOT NULL        COMMENT '제목',
    password VARCHAR(100) NOT NULL        COMMENT '비밀번호',
    createDate DATETIME   DEFAULT NOW() COMMENT '일정 생성 날짜',
    updateDate DATETIME   DEFAULT NOW() COMMENT '일정 수정 날짜'
-- targetDate DATETIME   NULL            COMMENT '목표일'
); -- datetime에 서버시간으로 대체 고민

-- Value 가 SYSTEM 이다. 로컬 시간대를 따름.
SHOW VARIABLES LIKE 'time_zone';

-- 따라서 UTC로 서버 시간을 바꾸려 한다.
-- UTC 라는 약속된 규칙이 있고 RESPONSE 할때 UTC+몇이다 라고 응답해주면 된다고 생각해 봤지만
-- datetime엔 utc 정보가 없다는 이야기를 들었다. json에서 날짜 관련 정확한 규격은 없지만
-- 국제표준이란것은 있으니 String 안에서 규격화된 정보를 보내주자.

-- 테이블 삭제
DROP TABLE schedule;

-- 수정일 정렬 연습용 insert
INSERT INTO schedule (author,title,updateDate,password) VALUE ('임쟁작성1','SORTING TEST','2025-03-23 09:01:02','dlawod123');


-- ========================= Lv3 =========================

-- schedule 테이블에서 id 칼럼명 schedule_id 로 만들고 snake_case 로 만들기
-- 그러면 레파지토리에서 mapping을 다시 잡아줘야함
ALTER TABLE schedule RENAME COLUMN id TO schedule_id;
ALTER TABLE schedule RENAME COLUMN createDate TO create_date;
ALTER TABLE schedule RENAME COLUMN updateDate TO update_date;
-- DB 칼럼명 snake_case 로 바꾼뒤 테스트용
INSERT INTO schedule (author,title,update_date,password) VALUE ('lim','schedule update test','2025-03-23 09:01:02','1234');

-- member 테이블 만들기
CREATE TABLE member
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY  COMMENT '멤버 식별자',
    name VARCHAR(20)    NOT NULL        COMMENT '이름',
    email VARCHAR(50)     NULL        COMMENT '이메일',
    create_date DATETIME   DEFAULT NOW() COMMENT '가입일',
    update_date DATETIME   DEFAULT NOW() COMMENT '정보 수정일'
);
ALTER TABLE member RENAME COLUMN id TO member_id;

-- schedule 테이블에서 member 테이블과 연동되는 외래키(member_id) 만들기
ALTER TABLE schedule ADD COLUMN member_id BIGINT NULL COMMENT '외래키 멤버 식별 id';

-- member에 admin data 추가
INSERT INTO member (name,email,create_date,update_date) VALUE ('admin','kalystols@gmail.com','2025-03-25 11:14:30','2025-03-25 11:14:30');

-- schedule 테이블의 member_id 칼럼을 not null 로 만들기 위해 admin id = 1 로 일단 매칭
UPDATE schedule SET member_id = 1 WHERE member_id IS NULL;
-- schedule 의 member_id를 NOT NULL 설정
ALTER TABLE schedule MODIFY COLUMN member_id BIGINT NOT NULL;

-- 외래키 FK 설정 (schedule 의 member_id를 member 의 member_id와 대응)
ALTER TABLE schedule
    -- 제약조건을 추가하며 그 조건을 fk_schedule_member 로 명명
    -- 하지만 없어도 되는 코드. mySQL 이 알아서 명명해줌
    ADD CONSTRAINT fk_schedule_member
        -- 외래키(FOREIGM KEY)를 설정하겠다 (member_id) 라는 칼럼명에
        FOREIGN KEY (member_id)
            -- 그 외래키는 member테이블의 member_id 칼럼을 참조한다
            REFERENCES member(member_id);
                -- 추가로 멤버가 삭제되면 외래키를 null로 바꿔줄수있다.
                -- 일단 사용 안함. not null로 해두었기 때문
--                 ON DELETE SET NULL

-- 동명이인 만들기 Sam, 이메일은 다르며 없는 Sam들도 존재
INSERT INTO member (name,email,create_date,update_date) VALUE ('Sam','samsmith@gmail.com','2025-03-25 11:45:30','2025-03-25 11:50:31');
INSERT INTO member (name,email,create_date,update_date) VALUE ('Sam','samhammington@gmail.com','2025-03-25 11:46:30','2025-03-25 11:46:30');
INSERT INTO member (name,create_date,update_date) VALUE ('Sam','2025-03-25 11:46:30','2025-03-25 11:46:30');
INSERT INTO member (name) VALUE ('Sam');


