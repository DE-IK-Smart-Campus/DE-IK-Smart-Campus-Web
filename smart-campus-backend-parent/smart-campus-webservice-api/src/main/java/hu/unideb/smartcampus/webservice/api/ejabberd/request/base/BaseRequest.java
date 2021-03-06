package hu.unideb.smartcampus.webservice.api.ejabberd.request.base;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * A parent class for every XMPP request.
 *
 */
@Data
@AllArgsConstructor
public class BaseRequest implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * The main host.
   */
  protected final String host;

}
