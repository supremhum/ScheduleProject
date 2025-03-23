package com.example.scheduleproject.service;

import com.example.scheduleproject.dto.ScheduleRequestDto;
import com.example.scheduleproject.dto.ScheduleResponseDto;
import com.example.scheduleproject.entity.Schedule;
import com.example.scheduleproject.repository.ScheduleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ScheduleServiceImpl implements ScheduleService{

    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl (ScheduleRepository scheduleRepository) {
        this.scheduleRepository=scheduleRepository;
    }


    @Override
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto dto) {

        Schedule schedule = new Schedule(dto.getAuthor(), dto.getTitle(), dto.getPassword());

        ScheduleResponseDto responseDto = scheduleRepository.saveSchedule(schedule);
        return responseDto;
//        return scheduleRepository.saveSchedule(schedule);

    }

    @Override
    public List<ScheduleResponseDto> findAllSchedules() {
        return scheduleRepository.findAllSchedules();
    }

    @Override
    public ScheduleResponseDto findScheduleById(Long id) {
        Optional<Schedule> schedule = scheduleRepository.findScheduleById(id);
        if (schedule.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"못찾음");
        }
        return new ScheduleResponseDto(schedule.get());


    }

    @Transactional
    @Override
    public ScheduleResponseDto updateScheduleById(Long id, String author, String title) {
        if (title==null||author==null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"제목과 작성자는 필수입니다");
        }

        int updateRow = scheduleRepository.updateScheduleById(id,author,title);

        if(updateRow==0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"해당 id값이 없습니다");
        }

        Optional<Schedule> optionalSchedule = scheduleRepository.findScheduleById(id);

//        ScheduleResponseDto rdto = new ScheduleResponseDto(schedule);

        return new ScheduleResponseDto(optionalSchedule.get());
    }
}
