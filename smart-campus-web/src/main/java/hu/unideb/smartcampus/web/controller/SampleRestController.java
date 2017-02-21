package hu.unideb.smartcampus.web.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.unideb.smartcampus.service.api.SampleService;

/**
 * Sample rest controller.
 */
@RestController
public class SampleRestController {

  /**
   * Sample dependency.
   */
  private final SampleService sampleService;

  /**
   * Constructor for dependency injection.
   */
  @Autowired
  public SampleRestController(final SampleService sampleService) {
    this.sampleService = sampleService;
  }

  /**
   * Sample endpoint.
   * @return {@link ResponseEntity}
   */
  @GetMapping(path = "/sample")
  public ResponseEntity<String> getSample() {
    this.sampleService.sampleDependencyValidationMethod();
    return ResponseEntity.ok("Content");
  }
}