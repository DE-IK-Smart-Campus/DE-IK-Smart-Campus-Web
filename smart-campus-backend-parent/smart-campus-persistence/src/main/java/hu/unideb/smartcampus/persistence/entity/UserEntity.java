package hu.unideb.smartcampus.persistence.entity;

import static hu.unideb.smartcampus.shared.exclusion.FieldExclusion.EXCLUDE_PASSWORD;
import static hu.unideb.smartcampus.shared.table.ColumnName.UserColumnName.COLUMN_NAME_FULLNAME;
import static hu.unideb.smartcampus.shared.table.ColumnName.UserColumnName.COLUMN_NAME_NEPTUN_IDENTIFIER;
import static hu.unideb.smartcampus.shared.table.ColumnName.UserColumnName.COLUMN_NAME_PASSWORD;
import static hu.unideb.smartcampus.shared.table.ColumnName.UserColumnName.COLUMN_NAME_ROLE;
import static hu.unideb.smartcampus.shared.table.ColumnName.UserColumnName.COLUMN_NAME_USERNAME;
import static hu.unideb.smartcampus.shared.table.TableName.TABLE_NAME_USER;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import hu.unideb.smartcampus.shared.enumeration.Role;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * UserEntity which represents the user.
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true, exclude = EXCLUDE_PASSWORD)
@Entity
@Table(name = TABLE_NAME_USER,
    uniqueConstraints = @UniqueConstraint(columnNames = COLUMN_NAME_USERNAME))
@NamedQueries({
    @NamedQuery(name = "UserEntity.getSubjectsByUsername",
        query = "SELECT actual FROM UserEntity u join u.actualEvents events join events.subjectDetailsEntity actual WHERE u.username = ?1"),
    @NamedQuery(name = "UserEntity.getSubjectsWithinRangeByUsername",
        query = "SELECT actual FROM UserEntity u join u.actualEvents events join events.subjectDetailsEntity actual WHERE u.username = ?1 AND actual.startPeriod BETWEEN ?2 AND ?3"),
    @NamedQuery(name = "UserEntity.getIdByUsername",
        query = "SELECT u.id FROM UserEntity u WHERE u.username = ?1"),
    @NamedQuery(name = "UserEntity.getSingleChatListByUsername",
        query = "SELECT list FROM UserEntity u join u.singleChatList list WHERE u.username = ?1"),
    @NamedQuery(name = "UserEntity.getMucChatListByUsername",
        query = "SELECT list FROM UserEntity u join u.mucChatList list WHERE u.username = ?1"),
    @NamedQuery(name = "UserEntity.getCourseAppointmensWithinRange",
        query = "SELECT course FROM UserEntity u join u.courseAppointments course WHERE u.username = ?1 AND course.subjectEvent.subjectDetailsEntity.startPeriod BETWEEN ?2 AND ?3"),
    @NamedQuery(name = "UserEntity.getCourseAppointmentsByUsername",
        query = "SELECT course FROM UserEntity u join u.courseAppointments course WHERE u.username = ?1"),
    @NamedQuery(name = "UserEntity.getSubjectEventsByUsername",
        query = "SELECT events FROM UserEntity u join u.actualEvents events WHERE u.username = ?1"),
    @NamedQuery(name = "UserEntity.getSubjectEventsWithinRangeByUsername",
        query = "SELECT events FROM UserEntity u join u.actualEvents events WHERE u.username = ?1 AND events.subjectDetailsEntity.startPeriod BETWEEN ?2 AND ?3"),
    @NamedQuery(name = "UserEntity.getCourseAppointmentsBySubjectEvent",
        query = "SELECT course FROM UserEntity u join u.courseAppointments course WHERE u.username = ?1 AND course.subjectEvent = ?2")})
public class UserEntity extends BaseEntity<Long> {

  /**
   * The username of the user.
   */
  @NotNull
  @Size(min = 2, max = 32)
  @Column(name = COLUMN_NAME_USERNAME)
  private String username;

  /**
   * The name of the user.
   */
  @NotNull
  @Size(min = 2, max = 64)
  @Column(name = COLUMN_NAME_FULLNAME)
  private String fullName;

  /**
   * The password of the user.
   */
  @NotNull
  @Size(min = 5, max = 250)
  @Column(name = COLUMN_NAME_PASSWORD)
  private String password;

  /**
   * Neptun identifier of the user.
   */
  @NotNull
  @Size(min = 6, max = 6)
  @Column(name = COLUMN_NAME_NEPTUN_IDENTIFIER)
  private String neptunIdentifier;

  /**
   * The role of the user.
   */
  @NotNull
  @Column(name = COLUMN_NAME_ROLE)
  @Enumerated(EnumType.STRING)
  private Role role;

  /**
   * Actual semester subjects.
   */
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "user_subject_event_relation",
      joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
      inverseJoinColumns = {
          @JoinColumn(name = "subject_event_id", referencedColumnName = "id")})
  private Set<SubjectEventEntity> actualEvents;

  /**
   * User custom events.
   */
  @OneToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "user_custom_events",
      joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "custom_event_id", referencedColumnName = "id"))
  private List<CustomEventEntity> customEvents;

  /**
   * Joined MUC rooms JID list.
   */
  @ElementCollection(fetch = FetchType.LAZY)
  @CollectionTable(name = "user_muc_chat")
  private Set<String> mucChatList;

  /**
   * Chat partners.
   */
  @ElementCollection(fetch = FetchType.LAZY)
  @CollectionTable(name = "user_single_chat")
  private Set<String> singleChatList;

  /**
   * Course appointments.
   */
  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
  @JoinTable(name = "user_course_appointment",
      joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "course_appointment_id", referencedColumnName = "id"))
  private Set<CourseAppointmentEntity> courseAppointments;

  /**
   * Builder pattern for creating user.
   */
  @Builder
  public UserEntity(final Long id, final String username, final String password, final Role role,
      final Set<SubjectEventEntity> actualEvents, final List<CustomEventEntity> customEvents,
      final Set<String> mucChatList, final Set<String> singleChatList, final String fullName,
      final String neptunIdentifier, final Set<CourseAppointmentEntity> courseAppointments) {
    super(id);
    this.username = username;
    this.password = password;
    this.role = role;
    this.actualEvents = actualEvents;
    this.customEvents = customEvents;
    this.fullName = fullName;
    this.neptunIdentifier = neptunIdentifier;
    this.mucChatList = mucChatList;
    this.singleChatList = singleChatList;
    this.courseAppointments = courseAppointments;
  }
}
