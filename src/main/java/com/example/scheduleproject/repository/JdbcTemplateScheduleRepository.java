package com.example.scheduleproject.repository;

import com.example.scheduleproject.dto.ScheduleResponseDto;
import com.example.scheduleproject.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
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
        jdbcInsert.withTableName("schedule").usingGeneratedKeyColumns("id"); // schedule 이란 테이블에서 키생성자사용 칼럼명id에서

        Map<String,Object> parameters = new HashMap<>();
        parameters.put("writer",schedule.getWriter());
        parameters.put("title",schedule.getTitle());
        parameters.put("password",schedule.getPassword());
//        parameters.put("create_date",schedule.getCreate_date());
//        parameters.put("update_date",schedule.getUpdate_date());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        // return new ScheduleResponseDto(key.longValue(),schedule.getWriter(), schedule.getTitle(), schedule.getCreate_date(), schedule.getUpdate_date());
        return new ScheduleResponseDto(key.longValue(),schedule);

    }
}
