package hu.unideb.smartcampus.service.api.request.service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.google.common.collect.Sets;

import hu.unideb.smartcampus.persistence.entity.InstructorEntity;
import hu.unideb.smartcampus.persistence.entity.SubjectDetailsEntity;
import hu.unideb.smartcampus.persistence.repository.InstructorRepository;
import hu.unideb.smartcampus.persistence.repository.UserRepository;
import hu.unideb.smartcampus.shared.requestmessages.RetrieveSubjectsRequest;
import hu.unideb.smartcampus.shared.requestmessages.constants.RequestMessagesConstants;
import hu.unideb.smartcampus.shared.wrapper.inner.InstructorWrapper;
import hu.unideb.smartcampus.shared.wrapper.inner.SubjectWrapper;


/**
 * Test for {@link RetrieveSubjectsRequestServiceImplTest}.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({LocalDate.class, RetrieveSubjectsRequestServiceImpl.class})
@SuppressWarnings({"PMD.UnusedPrivateField", "PMD.UnusedLocalVariable"})
public class RetrieveSubjectsRequestServiceImplTest {

  /**
   * IE instructor name.
   */
  private static final String IE_INSTRUCTOR_NAME = "IE instructor";

  /**
   * IE instructor wrapper.
   */
  private static final InstructorWrapper IE_INSTRUCTOR_WRAPPER =
      InstructorWrapper.builder().name(IE_INSTRUCTOR_NAME).build();

  /**
   * AI instructor name.
   */
  private static final String AI_INSTRUCTOR_NAME = "AI instructor";

  /**
   * AI instructor wrapper.
   */
  private static final InstructorWrapper AI_INSTRCUTOR_WRAPPER =
      InstructorWrapper.builder().name(AI_INSTRUCTOR_NAME).build();

  /**
   * AI instructor.
   */
  private static final InstructorEntity AI_INSTRUCTOR =
      InstructorEntity.builder().name(AI_INSTRUCTOR_NAME).build();

  /**
   * IE instructor.
   */
  private static final InstructorEntity IE_INSTRUCTOR =
      InstructorEntity.builder().name(IE_INSTRUCTOR_NAME).build();

  /**
   * AI ID.
   */
  private static final long SUBJECT_ID_AI = 1L;

  /**
   * AI.
   */
  private static final String AI = "AI";

  /**
   * AI subject.
   */
  private static final SubjectDetailsEntity AI_SUBJECT =
      SubjectDetailsEntity.builder().subjectName(AI).build();

  /**
   * IE ID.
   */
  private static final Long SUBJECT_ID_IE = 2L;

  /**
   * IE.
   */
  private static final String IE = "IE";

  /**
   * Response wrapper with instructors.
   */
  private static final List<SubjectWrapper> RESPONSE_WRAPPER = Arrays.asList(
      SubjectWrapper.builder().name(AI).instructors(Arrays.asList(AI_INSTRCUTOR_WRAPPER)).build(),
      SubjectWrapper.builder().name(IE).instructors(Arrays.asList(IE_INSTRUCTOR_WRAPPER)).build());

  /**
   * Response wrapper without instructors.
   */
  private static final List<SubjectWrapper> RESPONSE_WRAPPER_WITHOUTH_INSTRUCTORS =
      Arrays.asList(SubjectWrapper.builder().name(AI).instructors(Collections.emptyList()).build(),
          SubjectWrapper.builder().name(IE).instructors(Collections.emptyList()).build());


  /**
   * IE subject.
   */
  private static final SubjectDetailsEntity IE_SUBJECT =
      SubjectDetailsEntity.builder().subjectName(IE).build();

  /**
   * User id.
   */
  private static final String USER_ID = "TestUser";

  /**
   * Message request.
   */
  private static final RetrieveSubjectsRequest MESSAGE_REQUEST = RetrieveSubjectsRequest.builder()
      .userId(USER_ID).messageType(RequestMessagesConstants.RETRIEVE_SUBJECTS_REQUEST).build();

  /**
   * Subjects.
   */
  private static final Set<SubjectDetailsEntity> SUBJECTS = Sets.newHashSet(AI_SUBJECT, IE_SUBJECT);

  /**
   * Service impl.
   */
  @InjectMocks
  private RetrieveSubjectsRequestServiceImpl service;

  /**
   * Consulting date repository mock.
   */
  @Mock
  private UserRepository userRepository;

  /**
   * Instructor repository.
   */
  @Mock
  private InstructorRepository instructorRepository;

  /**
   * Test get response subject without instructor.
   */
  @Test
  public void getResponseWhenSubjectHasNoInstructorShouldReturnValidResponseWrapper() {
    // given

    // when
    // FIXME Mock date!!!
    PowerMockito.mockStatic(LocalDate.class);
    Mockito.when(userRepository.getSubjectsWithinRangeByUsername(USER_ID, LocalDate.of(2017, 1, 1),
        LocalDate.of(2017, 5, 31))).thenReturn(SUBJECTS);

    // then
    List<SubjectWrapper> requestedSubjects = service.getSubjects(USER_ID);

    Assert.assertEquals(RESPONSE_WRAPPER_WITHOUTH_INSTRUCTORS, requestedSubjects);
  }

  /**
   * Test get response with user with no subjects.
   */
  @Test
  public void getResponseWhenUserHasNoSubjectsAndReturnValidResponseWrapper() {
    // given

    // when
    Mockito.when(userRepository.getSubjectsByUsername(USER_ID)).thenReturn(null);
    // then
    List<SubjectWrapper> requestedSubjects = service.getSubjects(USER_ID);

    Assert.assertEquals(Collections.emptyList(), requestedSubjects);
  }

  /**
   * Test get response with user with subjects.
   */
  /*
   * @Test public void getResponseWhenUserHasSubjectsAndReturnValidResponseWrapper() { // given
   * 
   * // when Mockito.when(userRepository.getSubjectsByUsername(USER_ID)).thenReturn(SUBJECTS);
   * Mockito.when(instructorRepository.getInstructorsBySubjectId(SUBJECT_ID_IE))
   * .thenReturn(Sets.newSet(IE_INSTRUCTOR));
   * Mockito.when(instructorRepository.getInstructorsBySubjectId(SUBJECT_ID_AI))
   * .thenReturn(Sets.newSet(AI_INSTRUCTOR)); // then SubjectRetrievalResponseWrapper response =
   * service.getResponse(MESSAGE_REQUEST);
   * 
   * Assert.assertEquals(RequestMessagesConstants.RETRIEVE_SUBJECTS_RESPONSE,
   * response.getMessageType()); Assert.assertEquals(RESPONSE_WRAPPER, response.getSubjects()); }
   */
}
