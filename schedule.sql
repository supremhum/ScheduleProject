USE sparta;

CREATE TABLE schedule
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY  COMMENT '스케줄 식별자',
    author VARCHAR(20)    NOT NULL        COMMENT '작성자',
    title VARCHAR(50)     NOT NULL        COMMENT '제목',
    password VARCHAR(100) NOT NULL        COMMENT '비밀번호',
    createDate DATETIME   DEFAULT NOW() COMMENT '생성일',
    updateDate DATETIME   DEFAULT NOW() COMMENT '수정일'
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

-- ========================= Lv3용 =========================
-- schedule 테이블에서 id 칼럼명 schedule_id 로 만들고 snake_case 로 만들기
-- 그러면 레파지토리에서 mapping을 다시 잡아줘야함
ALTER TABLE schedule RENAME COLUMN id TO schedule_id;
ALTER TABLE schedule RENAME COLUMN createDate TO create_date;
ALTER TABLE schedule RENAME COLUMN updateDate TO update_date;
-- DB 칼럼명 snake_case 로 바꾼뒤 테스트용
INSERT INTO schedule (author,title,update_date,password) VALUE ('lim','schedule update test','2025-03-23 09:01:02','1234');

-- schedule 테이블에서 member 테이블과 연동되는 외래키(member_id 만들고
-- member 테이블 만들고
