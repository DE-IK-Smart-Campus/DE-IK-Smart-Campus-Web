package hu.unideb.smartcampus.service.api.converter.todomain;


import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import hu.unideb.smartcampus.persistence.entity.CourseAppointmentEntity;
import hu.unideb.smartcampus.persistence.entity.CustomEventEntity;
import hu.unideb.smartcampus.persistence.entity.SubjectDetailsEntity;
import hu.unideb.smartcampus.persistence.entity.UserEntity;
import hu.unideb.smartcampus.service.api.calendar.domain.subject.SubjectDetails;
import hu.unideb.smartcampus.service.api.domain.CourseAppointment;
import hu.unideb.smartcampus.service.api.domain.CustomEvent;
import hu.unideb.smartcampus.service.api.domain.User;

@Component
public class UserEntityToUserConverter implements Converter<UserEntity, User> {

  private final Converter<SubjectDetailsEntity, SubjectDetails> subjectDetailsConverter;

  private final Converter<CustomEventEntity, CustomEvent> customEventConverter;

  private final Converter<CourseAppointmentEntity, CourseAppointment> courseConverter;

  @Autowired
  public UserEntityToUserConverter(
      final Converter<SubjectDetailsEntity, SubjectDetails> subjectDetailsConverter,
      final Converter<CustomEventEntity, CustomEvent> customEventConverter,
      final Converter<CourseAppointmentEntity, CourseAppointment> courseConverter) {
    this.subjectDetailsConverter = subjectDetailsConverter;
    this.customEventConverter = customEventConverter;
    this.courseConverter = courseConverter;
  }

  @Override
  public User convert(final UserEntity userEntity) {
    return userEntity == null ? null : convertUserEntityToUser(userEntity);
  }

  private User convertUserEntityToUser(final UserEntity userEntity) {
    return User.builder()
        .id(userEntity.getId())
        .username(userEntity.getUsername())
        .password(userEntity.getPassword())
        .fullName(userEntity.getFullName())
        .neptunIdentifier(userEntity.getNeptunIdentifier())
        .role(userEntity.getRole())
        .subjectDetailsList(
            convertSubjectDetailsSetToSubjectDetailsEntitySet(userEntity.getActualSubjects()))
        .mucChatList(userEntity.getMucChatList())
        .singleChatList(userEntity.getSingleChatList())
        .customEventList(
            convertCustomEventListToCustomEventEntityList(userEntity.getCustomEvents()))
        .courseAppointmentList(
            convertCourseAppointmentToCourseAppointmentEntity(userEntity.getCourseAppointments()))
        .build();
  }

  private List<CourseAppointment> convertCourseAppointmentToCourseAppointmentEntity(
      Set<CourseAppointmentEntity> courseAppointments) {
    return courseAppointments == null ? null : courseAppointments.stream()
        .map(course -> courseConverter.convert(course))
        .collect(Collectors.toList());
  }

  private List<CustomEvent> convertCustomEventListToCustomEventEntityList(
      List<CustomEventEntity> customEvents) {
    return customEvents == null ? null : customEvents.stream()
        .map(customEventEntity -> customEventConverter.convert(customEventEntity))
        .collect(Collectors.toList());
  }

  private List<SubjectDetails> convertSubjectDetailsSetToSubjectDetailsEntitySet(
      final Set<SubjectDetailsEntity> subjectDetailsEntitySet) {
    return subjectDetailsEntitySet == null ? null : subjectDetailsEntitySet.stream()
        .map(subjectDetailsEntity -> subjectDetailsConverter.convert(subjectDetailsEntity))
        .collect(Collectors.toList());
  }

}
