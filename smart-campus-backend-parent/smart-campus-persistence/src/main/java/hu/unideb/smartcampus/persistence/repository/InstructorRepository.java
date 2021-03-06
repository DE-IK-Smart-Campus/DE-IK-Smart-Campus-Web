package hu.unideb.smartcampus.persistence.repository;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hu.unideb.smartcampus.persistence.entity.ConsultingDateEntity;
import hu.unideb.smartcampus.persistence.entity.InstructorEntity;

/**
 * Instructor repository.
 */
@Repository
public interface InstructorRepository extends JpaRepository<InstructorEntity, Long> {

  /**
   * Find instructor by name.
   */
  InstructorEntity findByName(String name);
  
  /**
   * Find instructor by neptun identifier..
   */
  InstructorEntity findByNeptunIdentifier(String neptunIdentifier);

  /**
   * Get instructor's consulting hours by instructor's name.
   */
  Set<ConsultingDateEntity> getInstructorConsultingHoursByInstructorName(String name);

  /**
   * Get instructors by subject name.
   */
  Set<InstructorEntity> getInstructorsBySubjectName(String subjectName);

  /**
   * Get instructors consulting dates from given date.
   */
  Set<ConsultingDateEntity> getInstructorConsultingDatesByIdAndGivenDate(Long instructorId, LocalDateTime from, LocalDateTime to);
  
  /**
   * Get instrcutor by consulting date id.
   */
  InstructorEntity getInstructorByConsultingDateId(Long consultingDateId);
}
