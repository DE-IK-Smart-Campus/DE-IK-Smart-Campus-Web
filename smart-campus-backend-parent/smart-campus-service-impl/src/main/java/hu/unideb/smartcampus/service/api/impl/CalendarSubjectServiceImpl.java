package hu.unideb.smartcampus.service.api.impl;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.unideb.smartcampus.service.api.CalendarSubjectService;
import hu.unideb.smartcampus.service.api.SubjectEventService;
import hu.unideb.smartcampus.service.api.calendar.domain.subject.AppointmentTime;
import hu.unideb.smartcampus.service.api.calendar.domain.subject.SubjectEvent;
import hu.unideb.smartcampus.shared.iq.request.CalendarSubjectsIqRequest;
import hu.unideb.smartcampus.shared.iq.request.element.AppointmentTimeIqElement;
import hu.unideb.smartcampus.shared.iq.request.element.CalendarSubjectIqElement;

/**
 * Calendar subject service implementation.
 */
@Service
public class CalendarSubjectServiceImpl implements CalendarSubjectService {

  private static final ZoneOffset HUNGARIAN_OFFSET = ZoneOffset.ofHours(1);

  @Autowired
  private SubjectEventService subjectEventService;


  @Transactional(readOnly = true)
  @Override
  public List<CalendarSubjectIqElement> getSubjectEvents(CalendarSubjectsIqRequest iq) {
    List<SubjectEvent> allSubjectEventByUsername =
        subjectEventService.getAllSubjectEventByUsername(iq.getStudent());
    List<CalendarSubjectIqElement> subjectEvents = convertToIqElements(allSubjectEventByUsername);
    return subjectEvents;
  }

  private List<CalendarSubjectIqElement> convertToIqElements(
      List<SubjectEvent> allSubjectEventByUsername) {
    List<CalendarSubjectIqElement> subjectEvents = new ArrayList<>();
    for (SubjectEvent subjectEvent : allSubjectEventByUsername) {
      subjectEvents.add(buildIqElement(subjectEvent));
    }
    return subjectEvents;
  }

  private CalendarSubjectIqElement buildIqElement(SubjectEvent subjectEvent) {
    return CalendarSubjectIqElement.builder()
        .subjectName(subjectEvent.getSubjectDetails().getSubjectName())
        .where(subjectEvent.getRoomLocation())
        .appointmentTimes(convertToIqElement(subjectEvent.getAppointmentTimeList()))
        .description(createDescriptionByTeachers(subjectEvent)).build();
  }

  private String createDescriptionByTeachers(SubjectEvent subjectEvent) {
    StringBuilder builder = new StringBuilder();
    List<String> teacherNames = subjectEvent.getSubjectDetails().getTeacherNames();
    build(builder, teacherNames);
    return builder.toString();
  }

  private void build(StringBuilder builder, List<String> teacherNames) {
    Integer counter = 0;
    for (String teacher : teacherNames) {
      counter++;
      builder.append(teacher).append(appendColonOrWhiteSpace(teacherNames, counter));
    }
  }

  private String appendColonOrWhiteSpace(List<String> teacherNames, Integer counter) {
    return counter.equals(teacherNames.size()) ? "" : ", ";
  }

  private List<AppointmentTimeIqElement> convertToIqElement(
      List<AppointmentTime> appointmentTimeList) {
    return appointmentTimeList.stream().map(this::convertToIqElement).collect(Collectors.toList());
  }

  private AppointmentTimeIqElement convertToIqElement(AppointmentTime appointmentTime) {
    return AppointmentTimeIqElement.builder()
        .from(appointmentTime.getStartDateTime().toEpochSecond(HUNGARIAN_OFFSET))
        .to(appointmentTime.getEndDateTime().toEpochSecond(HUNGARIAN_OFFSET)).when(appointmentTime
            .getStartDateTime().toLocalDate().atStartOfDay().toEpochSecond(HUNGARIAN_OFFSET))
        .build();
  }

  @Transactional(readOnly = true)
  @Override
  public List<CalendarSubjectIqElement> getSubjectEventsWithinPeriod(CalendarSubjectsIqRequest iq) {
    LocalDate startPeriod = getInLocalDate(iq.getStartPeriod());
    LocalDate endPeriod = getInLocalDate(iq.getEndPeriod());
    List<SubjectEvent> subjectsWithinRange =
        subjectEventService.getSubjectEventWithinRangeByUsername(iq.getStudent(), startPeriod,
            endPeriod);
    List<CalendarSubjectIqElement> subjectEvents = convertToIqElements(subjectsWithinRange);
    return subjectEvents;
  }

  private LocalDate getInLocalDate(Long period) {
    return Instant.ofEpochMilli(period * 1000).atZone(ZoneId.systemDefault()).toLocalDate();
  }

}