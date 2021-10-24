package com.imiconnect.connect.whatsapp.type;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

/**
 * Enumeration of the various content types available to send in a {@link WhatsAppMsg}. The classes
 * are stored in a lookup map with the key as the enum name to simplify without the need to first
 * convert the string name into an enum value.
 */
public enum WhatsAppContentType {
  AUDIO(Audio.class),
  CONTACTS(Contacts.class),
  DOCUMENT(Document.class),
  IMAGE(Image.class),
  LOCATION(Location.class),
  STICKER(Sticker.class),
  TEXT(Text.class),
  TEMPLATE(Template.class),
  VIDEO(Video.class);

  private static final Map<String, Class<?>> CLASS_MAP = initMap();

  private final Class<?> clazz;

  private <T extends Content> WhatsAppContentType(Class<T> clazz) {
    this.clazz = clazz;
  }

  /**
   * Gets the class associated with the specific content type. The lookup key is case insensitive
   * for all caps or all lowercase values, not a combination.
   *
   * @return the content type class.
   */
  @SuppressWarnings("unchecked")
  public static <T extends Content> Class<T> getType(String name) {
    return (Class<T>) CLASS_MAP.get(name);
  }

  private static Map<String, Class<?>> initMap() {
    Map<String, Class<?>> map = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    for (WhatsAppContentType type : WhatsAppContentType.values()) {
      map.put(type.name(), type.clazz);
    }
    return Collections.unmodifiableMap(map);
  }
}
