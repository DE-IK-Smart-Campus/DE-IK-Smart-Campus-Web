package hu.unideb.smartcampus.service.api.request.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import hu.unideb.smartcampus.persistence.entity.ConsultingDateEntity;
import hu.unideb.smartcampus.persistence.entity.FromToDateEmbeddedEntity;
import hu.unideb.smartcampus.persistence.entity.InstructorEntity;
import hu.unideb.smartcampus.persistence.repository.InstructorRepository;
import hu.unideb.smartcampus.shared.util.DateUtil;
import hu.unideb.smartcampus.shared.wrapper.inner.ConsultingDateWrapper;
import hu.unideb.smartcampus.shared.wrapper.inner.FromToDateWrapper;

/**
 * Retrieve instructor consulting dates service.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class RetrieveInstructorsConsultingDatesRequestServiceImpl
    implements RetrieveInstructorsConsultingDatesRequestService {

  private final InstructorRepository instructorRepository;

  /**
   * Constructor.
   */
  @Autowired
  public RetrieveInstructorsConsultingDatesRequestServiceImpl(
      InstructorRepository instructorRepository) {
    this.instructorRepository = instructorRepository;
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public List<ConsultingDateWrapper> getConsultingDatesByInstructorId(Long instructorId) {
    LocalDateTime from = LocalDateTime.now();
    LocalDateTime to = LocalDateTime.now().plus(7, ChronoUnit.DAYS);
    Set<ConsultingDateEntity> consultingDates = instructorRepository
        .getInstructorConsultingDatesByIdAndGivenDate(instructorId, from, to);

    return extractConsultingHour(consultingDates);

  }

  private ConsultingDateWrapper consultingDateToWrapper(ConsultingDateEntity entity) {
    return ConsultingDateWrapper.builder().consultingHourId(entity.getId())
        .fromToDates(convertToWrapperFromToDate(entity.getFromToDate()))
        .reservedSum(entity.getSum()).build();
  }

  private FromToDateWrapper convertToWrapperFromToDate(FromToDateEmbeddedEntity fromToDate) {
    return FromToDateWrapper.builder()
        .from(DateUtil.getInEpochLongByLocalDateTime(fromToDate.getFromDate()))
        .to(DateUtil.getInEpochLongByLocalDateTime(fromToDate.getToDate()))
        .build();
  }

  private List<ConsultingDateWrapper> extractConsultingHour(
      Set<ConsultingDateEntity> consultingDates) {
    return consultingDates.stream().map(this::consultingDateToWrapper).collect(Collectors.toList());
  }

  @Override
  public String getInstructorNameById(Long instructorId) {
    InstructorEntity entity = instructorRepository.findOne(instructorId);
    if (entity != null) {
      return entity.getName();
    }
    return "";
  }

}
