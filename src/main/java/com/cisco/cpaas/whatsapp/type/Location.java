package com.cisco.cpaas.whatsapp.type;

import com.cisco.cpaas.core.annotation.Nullable;
import lombok.Builder;
import lombok.Value;

/** Defines whatsapp {@link Content} that represents a location. */
@Value
@Builder(builderClassName = "Builder")
public final class Location implements Content {

  private final WhatsAppContentType contentType = WhatsAppContentType.LOCATION;

  private final double latitude;
  private final double longitude;
  private final String name;
  private final String address;

  private Location(
      double latitude, double longitude, @Nullable String name, @Nullable String address) {
    this.latitude = latitude;
    this.longitude = longitude;
    this.name = name;
    this.address = address;
  }

  /**
   * Convenience method to define new location content at the latitude and longitude.
   *
   * @param latitude The latitude.
   * @param longitude The longitude.
   * @return A new instance of {@link Location}.
   */
  public static Location of(double latitude, double longitude) {
    return new Location(latitude, longitude, null, null);
  }
}
