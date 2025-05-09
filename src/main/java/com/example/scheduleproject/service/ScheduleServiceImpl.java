package com.example.scheduleproject.service;

import com.example.scheduleproject.dto.MemberRequestDto;
import com.example.scheduleproject.dto.MemberResponseDto;
import com.example.scheduleproject.dto.ScheduleRequestDto;
import com.example.scheduleproject.dto.ScheduleResponseDto;
import com.example.scheduleproject.entity.Member;
import com.example.scheduleproject.entity.Schedule;
import com.example.scheduleproject.repository.ScheduleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }


    @Transactional
    @Override
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto dto) {

        if (dto.getMemberId()==null||dto.getAuthor()==null||dto.getTitle()==null||dto.getPassword()==null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "제목, 작성자, 멤버id와 비밀번호는 필수입니다");
        }

        // 멤버 아이디를 검색해서 존재하지 않으면 404notfound
        findMemberById(dto.getMemberId());

        Schedule schedule = new Schedule(dto.getMemberId(),dto.getAuthor(), dto.getTitle(), dto.getPassword());

//        ScheduleResponseDto responseDto = scheduleRepository.saveSchedule(schedule);
//        return responseDto;
        return scheduleRepository.saveSchedule(schedule);

    }

    @Override
    public List<ScheduleResponseDto> findAllSchedules(ScheduleRequestDto dto) {
        Map<Object,Object> authorUpdateMap = new HashMap<>();
        if (dto.getId()!=null) {
            authorUpdateMap.put("schedule_id",dto.getId());
        }if (dto.getMemberId()!=null) {
            authorUpdateMap.put("member_id",dto.getMemberId());
        }if (dto.getAuthor()!=null) {
            authorUpdateMap.put("author",dto.getAuthor());
        }if (dto.getTitle()!=null) {
            authorUpdateMap.put("title",dto.getTitle());
        }if (dto.getCreateDate()!=null) {
            authorUpdateMap.put("create_date",dto.getCreateDate());
        }if (dto.getUpdateDate()!=null) {
            authorUpdateMap.put("update_date",dto.getUpdateDate());
        }
        List<Schedule> allSchedulesList = scheduleRepository.findAllSchedules(authorUpdateMap);
        List<ScheduleResponseDto> allSchedules = new ArrayList<>();
        for (Schedule schedule : allSchedulesList) {
            allSchedules.add(new ScheduleResponseDto(schedule));
        }
        return allSchedules;
    }

    @Override
    public ScheduleResponseDto findScheduleById(Long id) {
//        Optional<Schedule> schedule = scheduleRepository.findScheduleById(id);
        Schedule schedule = scheduleRepository.findScheduleByIdOrElseThrow(id);
//        if (schedule.isEmpty()) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "못찾음");
//        }
        return new ScheduleResponseDto(schedule);


    }

    @Transactional
    @Override
    public ScheduleResponseDto updateScheduleById(Long id, String author, String title,String password) {
        if (title == null || author == null || password == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "제목과 작성자, 비밀번호는 필수입니다");
        }

        Schedule passwordById = scheduleRepository.findPasswordById(id);
        if (!password.equals(passwordById.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 같지 않습니다");
        }

        int updateRow = scheduleRepository.updateScheduleById(id, author, title);

        if (updateRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 id값이 없습니다");
        }

        Schedule schedule = scheduleRepository.findScheduleByIdOrElseThrow(id);

        return new ScheduleResponseDto(schedule);
    }

    @Override
    @Transactional
    public ScheduleResponseDto updateTitleById(Long id, String author, String title, String password) {
        if (author != null || title == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "제목만 보내야 합니다");
        }

        int updateRow = scheduleRepository.updateTitleById(id, title);

        if (updateRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id값을 찾을 수 없습니다");
        }

        Schedule passwordById = scheduleRepository.findPasswordById(id);
        if (!password.equals(passwordById.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 같지 않습니다");
        }

        Schedule schedule = scheduleRepository.findScheduleByIdOrElseThrow(id);

        return new ScheduleResponseDto(schedule);

    }

    @Override
    public void deleteScheduleById(Long id,String password) {
        Schedule passwordById = scheduleRepository.findPasswordById(id);
        if (!password.equals(passwordById.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 같지 않습니다");
        }
        int updateRow = scheduleRepository.deleteScheduleById(id);
        if (updateRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "찾을 수 없는 ID");
        }
    }

    @Override
    public MemberResponseDto saveMember(MemberRequestDto dto) {
        Member member = new Member(dto.getName(),dto.getEmail());

        return scheduleRepository.saveMember(member);
    }

    @Override
    public MemberResponseDto findMemberById(Long id) {
        Member member = scheduleRepository.findMemberByIdOrElseThrow(id);

        return new MemberResponseDto(member);
    }
}
