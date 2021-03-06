package hu.unideb.smartcampus.service.api.calendar.parser.impl;

import net.fortuna.ical4j.model.component.VEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import hu.unideb.smartcampus.service.api.calendar.parser.CalendarLocalDateTimeParser;

/**
 * Implementation for {@link CalendarLocalDateTimeParser}.
 **/
@Component
public class CalendarLocalDateTimeParserImpl implements CalendarLocalDateTimeParser {

  /**
   * {@inheritDoc}.
   */
  @Override
  public LocalDateTime parseStartLocalDateTime(final VEvent vEvent) {
    validateVEventByStartDate(vEvent);
    return LocalDateTime.ofInstant(Instant.ofEpochMilli(vEvent.getStartDate().getDate().getTime()),
        ZoneId.systemDefault());
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public LocalDateTime parseEndLocalDateTime(final VEvent vEvent) {
    validateVEventByEndDate(vEvent);
    return LocalDateTime.ofInstant(Instant.ofEpochMilli(vEvent.getEndDate(true).getDate().getTime()),
        ZoneId.systemDefault());
  }

  private void validateVEventByStartDate(final VEvent vEvent) {
    Assert.notNull(vEvent);
    Assert.notNull(vEvent.getStartDate().getDate());
  }

  private void validateVEventByEndDate(final VEvent vEvent) {
    Assert.notNull(vEvent);
    Assert.notNull(vEvent.getEndDate().getDate());
  }
}