package hu.unideb.smartcampus.service.api.rss;

import java.time.Instant;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import hu.unideb.smartcampus.service.api.context.FacebookClient;
import hu.unideb.smartcampus.service.api.context.FacebookTokenContext;
import hu.unideb.smartcampus.service.api.quartz.SpringContextAwareQuartzJob;
import hu.unideb.smartcampus.service.api.rss.facebook.FacebookEvent;
import hu.unideb.smartcampus.service.api.rss.facebook.FacebookEventResult;
import hu.unideb.smartcampus.service.api.rss.facebook.FacebookPage;
import hu.unideb.smartcampus.service.api.rss.facebook.FacebookPageLikes;

public class RssPollerQuartzJob extends SpringContextAwareQuartzJob {

  private static final Logger LOGGER = LoggerFactory.getLogger(RssPollerQuartzJob.class);

  @Autowired
  private EventProvider eventProvider;
  
  @Override
  protected void executeInternal(JobExecutionContext context) {

    List<Event> events = eventProvider.getEventsBetween(0L, 1492116038L);
    
    LOGGER.info("Események száma: {}", events.size());

  }

}
