package hu.unideb.smartcampus.shared.calendar;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * A property holder for calendar related constants.
 *
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CaldendarConstants {

  /**
   * Regexp to parse calendar summaries.
   */
  @SuppressWarnings("checkstyle:AvoidEscapedUnicodeCharacters")
  public static final String SUMMARY_REGEXP =
      "(?<className>.*)\\s\\((?<lectureCode>.*)\\)\\s-\\s(?<classCode>.*)\\s-\\s(?<teacherName>.*)\\s-\\s\u00D3rarend";

  /**
   * Group name for class name.
   */
  public static final String CLASS_NAME = "className";

  /**
   * Group name for lecture code.
   */
  public static final String LECTURE_CODE = "lectureCode";

  /**
   * Group name for class code.
   */
  public static final String CLASS_CODE = "classCode";

  /**
   * Group name for teacher name.
   */
  public static final String TEACHER_NAME = "teacherName";
}
