package com.example.scheduleproject.repository;

import com.example.scheduleproject.dto.ScheduleRequestDto;
import com.example.scheduleproject.dto.ScheduleResponseDto;
import com.example.scheduleproject.entity.Schedule;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class JdbcTemplateScheduleRepository implements ScheduleRepository {

    //JdbcTemplate 를 사용하기 위한 작업. 의존성 추가라고 한다.
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

        // 우리가 실제로 인서트를 하게되면 식별자가 auto_increas로 생성된다. 키값=id값을 Number 타입으로 반환하는 메서드
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        // a라는 스케줄은 데이터베이스 정보로 만들기
        // 그러면 날짜 시간 타입이 yyyy-mm-dd hh:mm:ss 로 이쁘게 나옴.
        Optional<Schedule> schedule2 = findScheduleById(key.longValue());
        if (schedule2.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "DB에 저장이 실행되지 않았음");
        }
        return new ScheduleResponseDto(schedule2.get());

    }

    @Override
    public List<Schedule> findAllSchedules(Map<Object, Object> authorUpdateMap) {
        // 수정일이 오늘로부터 가장 가까운 것(시간까지)부터 보여지게 정렬하고
        // 보여지는 것은 date까지만 보이게 한다.
        String string = "";
        int count = 0;
        List<String> list = new ArrayList<>();
        for (Object temp:authorUpdateMap.keySet()) {
            if(temp.equals("updateDate")) {
                string=string + "date(" + temp + ")" + "= ? AND ";
            } else {
            string=string + temp + "= ? AND "; }
            list.add(String.valueOf(authorUpdateMap.get(temp)));
            count++;
        }
        if (count==0) {
            return jdbcTemplate.query("SELECT id,author,title,date(createDate) createDate,date(updateDate) updateDate , updateDate sort FROM schedule ORDER BY sort desc ", scheduleRowMapperV2());
        } else{
            return jdbcTemplate.query("SELECT id,author,title,date(createDate) createDate,date(updateDate) updateDate, updateDate sort FROM schedule WHERE "+string+" true ORDER BY sort desc ",list.toArray(),scheduleRowMapperV2());
        }
    }


    @Override
    public Optional<Schedule> findScheduleById(Long id) {

        List<Schedule> result = jdbcTemplate.query("SELECT * FROM schedule WHERE id = ?", scheduleRowMapperV2(), id);

        return result.stream().findAny();
    }

    @Override
    public int updateScheduleById(Long id, String author, String title) {
        // 쿼리를 수행한 로우 수가 반환되는 것이다.
        int updatedRow = jdbcTemplate.update("update schedule set author = ?, title = ?,updateDate = now() WHERE id = ?", author, title, id);
        return updatedRow;
    }

    @Override
    public int updateTitleById(Long id, String title) {
        int updatedRow = jdbcTemplate.update("update schedule set title = ?,updateDate = now() WHERE id = ?", title, id);
        return updatedRow;
    }

    @Override
    public int deleteScheduleById(Long id) {
        int updateRow = jdbcTemplate.update("delete from schedule where id = ? ", id);
        return updateRow;
    }

    @Override
    public Schedule findScheduleByIdOrElseThrow(Long id) {
        List<Schedule> result = jdbcTemplate.query("SELECT * FROM schedule WHERE id = ?", scheduleRowMapperV2(), id);

        return result.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"찾을 수 없는 id 값 = "+ id ));
    }

    @Override
    public Schedule findPasswordById(Long id) {
        findScheduleByIdOrElseThrow(id);
        List<Schedule> result = jdbcTemplate.query("SELECT * FROM schedule WHERE id = ?", scheduleRowMapperV3(), id);

        return result.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"찾을 수 없는 id 값 = "+ id ));
    }

    // DB에서 ID를 기반으로 데이터 조회하는 메서드
    private RowMapper<Schedule> scheduleRowMapperV2() {
        return new RowMapper<Schedule>() {
            @Override
            public Schedule mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Schedule(rs.getLong("id"), rs.getString("author"), rs.getString("title"), rs.getString("createDate"), rs.getString("updateDate"));
            }
        };
    }

    // DB에서 ID 기반으로 비밀번호만 조회하는 메서드
    private RowMapper<Schedule> scheduleRowMapperV3() {
        return new RowMapper<Schedule>() {
            @Override
            public Schedule mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Schedule(rs.getLong("id"), rs.getString("password"));
            }
        };
    }
}
