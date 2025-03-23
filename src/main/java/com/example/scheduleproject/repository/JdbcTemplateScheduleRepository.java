package com.example.scheduleproject.repository;

import com.example.scheduleproject.dto.ScheduleResponseDto;
import com.example.scheduleproject.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

@Repository
public class JdbcTemplateScheduleRepository implements ScheduleRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateScheduleRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public ScheduleResponseDto saveSchedule(Schedule schedule) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate); // insert 란 명령어는 새로운 행 삽입이니깐.
        jdbcInsert.withTableName("schedule").usingGeneratedKeyColumns("id"); // schedule 이란 테이블에서 키-생성자-사용 칼럼명-id에서

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("author", schedule.getAuthor());
        parameters.put("title", schedule.getTitle());
        parameters.put("password", schedule.getPassword());
//        parameters.put("targetDate", schedule.getCreateDate());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        // a라는 스케줄은 데이터베이스 정보로 만들기
        // 그러면 날짜 시간 타입이 yyyy-mm-dd hh:mm:ss 로 이쁘게 나옴.
        Schedule a = findById(key.longValue());
        return new ScheduleResponseDto(key.longValue(), a.getAuthor(), a.getTitle(), a.getCreateDate(), a.getUpdateDate());


    }

    // DB에서 ID를 기반으로 데이터 조회하는 메서드 추가
    public Schedule findById(Long id) {
        String sql = "SELECT * FROM schedule WHERE id = ?";

        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) ->
                new Schedule(
                        rs.getLong("id"),
                        rs.getString("author"),
                        rs.getString("title"),
                        rs.getString("password"),
                        rs.getString("createDate"),
                        rs.getString("updateDate")
                )
        );
    }
}
