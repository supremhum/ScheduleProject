package com.example.scheduleproject.repository;

import com.example.scheduleproject.dto.ScheduleResponseDto;
import com.example.scheduleproject.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcTemplateScheduleRepository implements ScheduleRepository {

    //JdbcTemplate 를 사용하기 위한 작업
    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateScheduleRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public ScheduleResponseDto saveSchedule(Schedule schedule) {
        // INSERT 쿼리를 문자열로 삽입하지 않아도 된다
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate); // insert 란 명령어는 새로운 행 삽입이니깐. 데이터를 저장할때 사용
        // schedule 이란 테이블에서 키-생성자-사용 칼럼명-id에서. 여기서 트러블슈팅은 jdbc가 insert를 하기 때문에 date 부분 안건드리게 .usingcolumns 로 명시
        // default now() 기 때문에 값들이 들어오면 그것으로 대체된다. 여기서 jdbc의 insert 명령어가 밑의 param 과 연계되어 값을 넣어버리기 때문에 default가 동작하기 힘들었던것
        jdbcInsert.withTableName("schedule").usingGeneratedKeyColumns("id").usingColumns("author", "title", "password");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("author", schedule.getAuthor());
        parameters.put("title", schedule.getTitle());
        parameters.put("password", schedule.getPassword());
//        parameters.put("targetDate", schedule.getCreateDate());

        // 우리가 실제로 인서트를 하게되면 식별자가 auto_increas로 생성된다. 키값=id값을 Number 타입으로 반환하는 메서드
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        // a라는 스케줄은 데이터베이스 정보로 만들기
        // 그러면 날짜 시간 타입이 yyyy-mm-dd hh:mm:ss 로 이쁘게 나옴.
        Schedule scheduleByDB = findById(key.longValue());

        return new ScheduleResponseDto(scheduleByDB.getId(), scheduleByDB.getAuthor(), scheduleByDB.getTitle(), scheduleByDB.getCreateDate(), scheduleByDB.getUpdateDate());


    }

    @Override
    public List<ScheduleResponseDto> findAllSchedules() {
        return jdbcTemplate.query("SELECT * FROM schedule",scheduleRowMapper());
    }

    @Override
    public ScheduleResponseDto findScheduleById(Long id) {
        Schedule schedule = findById(id);
        ScheduleResponseDto dto = new ScheduleResponseDto(schedule.getId(),schedule.getAuthor(),schedule.getTitle(),schedule.getCreateDate(),schedule.getUpdateDate());
        return dto;
    }

    private RowMapper<ScheduleResponseDto> scheduleRowMapper() {
        return new RowMapper<ScheduleResponseDto>() {
                                                        @Override
                                                        public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                                                            return new ScheduleResponseDto(
                                                                    rs.getLong("id"),
                                                                    rs.getString("author"),
                                                                    rs.getString("title"),
                                                                    rs.getString("createDate"),
                                                                    rs.getString("updateDate")
                                                            );
                                                        }
                                                    };

    }



    // DB에서 ID를 기반으로 데이터 조회하는 메서드 추가
    private Schedule findById(Long id) {
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
