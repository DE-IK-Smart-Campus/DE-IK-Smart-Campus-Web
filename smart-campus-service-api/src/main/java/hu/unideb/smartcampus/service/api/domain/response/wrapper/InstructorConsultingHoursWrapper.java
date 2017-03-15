package hu.unideb.smartcampus.service.api.domain.response.wrapper;

import java.util.List;

import hu.unideb.smartcampus.service.api.domain.response.wrapper.inner.ConsultingHourWrapper;
import lombok.Builder;
import lombok.Data;

/**
 * Instructor consulting hours wrapper.
 */
@Data
public class InstructorConsultingHoursWrapper extends BaseWrapper {

  /**
   * Consulting hours.
   */
  private final List<ConsultingHourWrapper> consultingHours;

  /**
   * Constructs a InstructorConsultingHoursWrapper instance.
   */
  @Builder
  public InstructorConsultingHoursWrapper(final String messageType,
      final List<ConsultingHourWrapper> consultingHours) {
    super(messageType);
    this.consultingHours = consultingHours;
  }



}