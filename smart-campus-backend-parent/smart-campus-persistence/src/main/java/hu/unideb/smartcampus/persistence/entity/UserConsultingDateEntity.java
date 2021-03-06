package hu.unideb.smartcampus.persistence.entity;

import static hu.unideb.smartcampus.shared.table.ColumnName.UserConsultingDateColumnName.COLUMN_NAME_DURATION;
import static hu.unideb.smartcampus.shared.table.ColumnName.UserConsultingDateColumnName.COLUMN_NAME_REASON;
import static hu.unideb.smartcampus.shared.table.TableName.TABLE_NAME_USER_CONSULTING_DATE;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * User consulting date entity.
 *
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode
@ToString(callSuper = true)
@Entity
@Table(name = TABLE_NAME_USER_CONSULTING_DATE)
@NamedQueries({@NamedQuery(
    name = "UserConsultingDateEntity.getUserConsultingDatesByConsultingDateId",
    query = "SELECT uc FROM UserConsultingDateEntity uc WHERE uc.consultingDate.id IN (?1)"),
    @NamedQuery(
        name = "UserConsultingDateEntity.getUserConsultingDatesByInstructorId",
        query = "SELECT uc FROM UserConsultingDateEntity uc, InstructorEntity instr join instr.consultingDates icd WHERE instr.neptunIdentifier = ?1 AND uc.consultingDate.id in (icd)"),
    @NamedQuery(
        name = "UserConsultingDateEntity.getUserConsultingDatesByInstructorIdBetweenRange",
        query = "SELECT uc FROM UserConsultingDateEntity uc, InstructorEntity instr join instr.consultingDates icd WHERE instr.neptunIdentifier = ?1 AND uc.consultingDate.id in (icd) AND uc.consultingDate.fromToDate.fromDate BETWEEN ?2 AND ?3")})
public class UserConsultingDateEntity extends BaseEntity<Long> {

  /**
   * User.
   */
  @NotNull
  @OneToOne(fetch = FetchType.LAZY)
  private UserEntity user;

  /**
   * Consulting date.
   */
  @NotNull
  @OneToOne(fetch = FetchType.LAZY)
  private ConsultingDateEntity consultingDate;

  /**
   * Reason of signin up.
   */
  @NotNull
  @Column(name = COLUMN_NAME_REASON)
  private String reason;

  /**
   * Reason of signin up.
   */
  @Column(name = COLUMN_NAME_DURATION)
  private String duration;

  /**
   * Constructs a UserConsultingDateEntity entity.
   */
  @Builder
  public UserConsultingDateEntity(final Long id, final UserEntity user,
      final ConsultingDateEntity consultingDate,
      final String reason, final String duration) {
    super(id);
    this.user = user;
    this.consultingDate = consultingDate;
    this.reason = reason;
    this.duration = duration;
  }



}
