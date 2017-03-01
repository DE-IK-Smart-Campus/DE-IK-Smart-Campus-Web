package hu.unideb.smartcampus.webservice.api.provider.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.WebTarget;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import hu.unideb.smartcampus.webservice.api.provider.ClientProvider;

/**
 * ClientProvider implementation.
 */
@Service
public class ClientProviderImpl implements ClientProvider {

  private static final Logger LOGGER = LoggerFactory.getLogger(ClientProviderImpl.class);

  private static final String SLASH = "/";
  private static final String COLON = ":";

  /**
   * Ejabberd REST API host.
   */
  @Value("${smartcampus.ejabberd.api.host}")
  private String host;

  /**
   * Ejabberd REST API port.
   */
  @Value("${smartcampus.ejabberd.api.port}")
  private Integer port;

  /**
   * Ejabberd REST API endpoint.
   */
  @Value("${smartcampus.ejabberd.api.endpoint}")
  private String endpoint;

  /**
   * {@inheritDoc}.
   */
  @Override
  public WebTarget createClientByUrl(String url) {
    Client client = ClientBuilder.newClient();

    if (LOGGER.isDebugEnabled()) {
      client.register(new ClientRequestFilter() {

        @Override
        public void filter(ClientRequestContext requestContext) throws IOException {
          LOGGER.debug(requestContext.getEntity().toString());
        }
      });
    }

    return client.target(buildUrl() + url);
  }

  private String buildUrl() {
    return host + COLON + port + SLASH + endpoint;
  }
}
