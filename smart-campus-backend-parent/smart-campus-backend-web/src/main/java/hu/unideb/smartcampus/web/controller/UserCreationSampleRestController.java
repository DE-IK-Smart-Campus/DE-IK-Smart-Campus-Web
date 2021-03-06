package hu.unideb.smartcampus.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import hu.unideb.smartcampus.service.api.UserRegistrationService;
import hu.unideb.smartcampus.shared.exception.RegistrationFailedException;

/**
 * Controller for creating user.
 *
 */
@RestController
public class UserCreationSampleRestController {

  /**
   * Logger.
   */
  private static final Logger LOGGER =
      LoggerFactory.getLogger(UserCreationSampleRestController.class);

  /**
   * Registration service.
   */
  @Autowired
  private UserRegistrationService userRegistrationService;

  /**
   * Creates a user with username.
   * 
   * @param username the username of the user
   * @return the HTTP status of the operation
   */
  @GetMapping(path = "/create/{username}")
  @PreAuthorize("isFullyAuthenticated()")
  public ResponseEntity createUser(@PathVariable(name = "username") String username) {

    try {
      userRegistrationService.doRegister(username);
      return ResponseEntity.ok().build();
    } catch (RegistrationFailedException e) {
      LOGGER.error("Failed to registrate user", e);
      return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
    }

  }

}
