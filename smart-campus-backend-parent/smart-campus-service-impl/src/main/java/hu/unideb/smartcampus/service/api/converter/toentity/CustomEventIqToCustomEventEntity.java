package hu.unideb.smartcampus.service.api.converter.toentity;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import hu.unideb.smartcampus.persistence.entity.CustomEventEntity;
import hu.unideb.smartcampus.shared.iq.request.element.CustomEventIqElement;
import hu.unideb.smartcampus.shared.util.DateUtil;

/**
 * Custom event IQ to Custom event entity.
 */
@Component
public class CustomEventIqToCustomEventEntity
    implements Converter<CustomEventIqElement, CustomEventEntity> {

  @Override
  public CustomEventEntity convert(CustomEventIqElement source) {
    return CustomEventEntity.builder()
        .guid(source.getGuid())
        .eventName(source.getEventName())
        .eventPlace(source.getEventPlace())
        .eventDescription(source.getEventDescription())
        .eventRepeat(source.getEventRepeat())
        .eventWhen(getLocalDateTime(source.getEventWhen()).toLocalDate())
        .eventStart(getLocalDateTime(source.getEventStart()))
        .eventEnd(getLocalDateTime(source.getEventEnd()))
        .reminder(source.getReminder())
        .build();
  }

  private LocalDateTime getLocalDateTime(Long source) {
    return source != null
        ? DateUtil.getInLocalDateTimeByEpochSecond(source)
        : LocalDateTime.MIN;
  }

}
