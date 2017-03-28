package hu.unideb.smartcampus.shared.iq.context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;

import hu.unideb.smartcampus.shared.iq.provider.BaseSmartCampusIqProvider;
import hu.unideb.smartcampus.shared.iq.request.BaseSmartCampusIq;

/**
 * IQ class context holder.
 */
public final class IqClassContext {

  /**
   * Context path.
   */
  private static final String CONTEXT_PATH_IQ = "hu.unideb.smartcampus.shared.iq.request";

  private static final String CONTEXT_PATH_PROVIDER = "hu.unideb.smartcampus.shared.iq.provider";

  /**
   * IQ classes.
   */
  private static final Class<? extends BaseSmartCampusIq>[] IQ_CLASSES;

  /**
   * IQ provider classes.
   */
  private static final Class<? extends BaseSmartCampusIqProvider>[] IQ_PROVIDER_CLASSES;

  static {
    Set<Class<? extends BaseSmartCampusIq>> iqs = getIqs();
    Set<Class<? extends BaseSmartCampusIqProvider>> providers = getProviderClasses();
    IQ_CLASSES = convertToClasses(iqs);
    IQ_PROVIDER_CLASSES = convertToClasses(providers);
  }

  /**
   * Get provider classes.
   */
  public static Class<? extends BaseSmartCampusIqProvider>[] getIqProviderClasses() {
    return IQ_PROVIDER_CLASSES.clone();
  }

  private static Set<Class<? extends BaseSmartCampusIqProvider>> getProviderClasses() {
    return getByClass(CONTEXT_PATH_PROVIDER, BaseSmartCampusIqProvider.class);
  }

  /**
   * Get IQ classes.
   * 
   * @return IQ classes.
   */
  public static Class<? extends BaseSmartCampusIq>[] getIqClasses() {
    return IQ_CLASSES.clone();
  }

  private static <T> Class<? extends T>[] convertToClasses(Set<Class<? extends T>> allClasses) {
    List<Class<? extends T>> classList = convertToList(allClasses);
    return classList.toArray(new Class[classList.size()]);
  }

  private static <T> List<Class<? extends T>> convertToList(Set<Class<? extends T>> allClasses) {
    List<Class<? extends T>> result = new ArrayList<>();
    for (Class<? extends T> clazz : allClasses) {
      result.add(clazz);
    }
    return result;
  }

  private static Set<Class<? extends BaseSmartCampusIq>> getIqs() {
    return getByClass(CONTEXT_PATH_IQ, BaseSmartCampusIq.class);
  }

  private static <T> Set<Class<? extends T>> getByClass(String contextPath, Class<T> clazz) {
    Reflections reflections = new Reflections(contextPath);
    return reflections.getSubTypesOf(clazz);
  }

  private IqClassContext() {
    // for PMD
  }

  public static Map<Class<? extends BaseSmartCampusIq>, Class<? extends BaseSmartCampusIqProvider>> getIqWithProvider()
      throws InstantiationException, IllegalAccessException {
    Map<Class<? extends BaseSmartCampusIq>, Class<? extends BaseSmartCampusIqProvider>> result =
        new HashMap<>();
    for (Class<? extends BaseSmartCampusIqProvider> clazz : IQ_PROVIDER_CLASSES) {
      BaseSmartCampusIqProvider instance = clazz.newInstance();
      result.put(instance.getHandledIq(), clazz);
    }
    return result;
  }

}
