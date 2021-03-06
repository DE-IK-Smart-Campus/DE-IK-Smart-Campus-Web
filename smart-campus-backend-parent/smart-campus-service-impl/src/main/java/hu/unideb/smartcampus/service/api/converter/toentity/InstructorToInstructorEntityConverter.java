package hu.unideb.smartcampus.service.api.converter.toentity;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.google.common.collect.Sets;

import hu.unideb.smartcampus.persistence.entity.ConsultingDateEntity;
import hu.unideb.smartcampus.persistence.entity.InstructorEntity;
import hu.unideb.smartcampus.persistence.entity.SubjectDetailsEntity;
import hu.unideb.smartcampus.service.api.calendar.domain.subject.SubjectDetails;
import hu.unideb.smartcampus.service.api.domain.ConsultingDate;
import hu.unideb.smartcampus.service.api.domain.Instructor;

@Component
public class InstructorToInstructorEntityConverter implements Converter<Instructor, InstructorEntity> {

  private final Converter<SubjectDetails, SubjectDetailsEntity> subjectDetailsConverter;

  private final Converter<ConsultingDate, ConsultingDateEntity> consultingDateConverter;

  @Autowired
  public InstructorToInstructorEntityConverter(final Converter<SubjectDetails, SubjectDetailsEntity> subjectDetailsConverter,
                                               final Converter<ConsultingDate, ConsultingDateEntity> consultingDateConverter) {
    this.subjectDetailsConverter = subjectDetailsConverter;
    this.consultingDateConverter = consultingDateConverter;
  }

  @Override
  public InstructorEntity convert(final Instructor instructor) {
    return instructor == null ? null : convertInstructorToInstructorEntity(instructor);
  }

  private InstructorEntity convertInstructorToInstructorEntity(final Instructor instructor) {
    return InstructorEntity.builder()
        .id(instructor.getId())
        .name(instructor.getName())
        .neptunIdentifier(instructor.getNeptunIdentifier())
        .subjects(convertSubjectDetailsSetToSubjectDetailsEntitySet(instructor.getSubjects()))
        .consultingDates(convertConsultingDateSetToConsultingDateEntitySet(instructor.getConsultingDates()))
        .build();
  }

  private Set<SubjectDetailsEntity> convertSubjectDetailsSetToSubjectDetailsEntitySet(final Set<SubjectDetails> subjectDetailsSet) {
    return subjectDetailsSet == null ? Sets.newHashSet() : subjectDetailsSet.stream()
        .map(subjectDetails -> subjectDetailsConverter.convert(subjectDetails))
        .collect(Collectors.toSet());
  }

  private Set<ConsultingDateEntity> convertConsultingDateSetToConsultingDateEntitySet(final Set<ConsultingDate> consultingDateSet) {
    return consultingDateSet == null ? Sets.newHashSet() : consultingDateSet.stream()
        .map(consultingDate -> consultingDateConverter.convert(consultingDate))
        .collect(Collectors.toSet());
  }
}
