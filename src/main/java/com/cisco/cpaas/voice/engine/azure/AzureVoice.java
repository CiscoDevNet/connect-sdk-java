package com.cisco.cpaas.voice.engine.azure;

import com.cisco.cpaas.voice.engine.Style;
import com.cisco.cpaas.voice.engine.Voice;
import lombok.Builder;
import lombok.Value;

/** Implementation of voice that is specific to Azure's text to speech engine. */
@Value
@Builder(builderClassName = "Builder")
public final class AzureVoice implements Voice {

  private final Engine engine = Engine.AZURE;
  private final Gender gender;
  private final String voice;
  private final Style style;
  private final String language;

  public static class Builder {}
}
