package com.imiconnect.cpaas.voice.engine.azure;

import com.imiconnect.cpaas.voice.engine.Voice;
import lombok.Builder;
import lombok.Value;

/** Implementation of voice that is specific to Azure's text to speech engine. */
@Value
@Builder(builderClassName = "Builder")
public final class AzureVoice implements Voice {

  private final Engine engine = Engine.AZURE;
  private final String style = "NEURAL";
  private final Gender gender;
  private final String voice;
  private final String language;

  /** Fluent BUILDER for the AzureVoice. */
  public static class Builder {}
}
