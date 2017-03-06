package hu.unideb.smartcampus.service.api.domain.response.wrapper;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Instructor wrapper.
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class InstructorWrapper extends BaseWrapper {

  /**
   * Instructor's id.
   */
  private Long instructorId;

  /**
   * Instructor's name.
   */
  private String name;

  /**
   * Constructs an InstructorWrapper instance.
   */
  @Builder
  public InstructorWrapper(Long instructorId, String name) {
    this.instructorId = instructorId;
    this.name = name;
  }



}
