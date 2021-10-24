package com.imiconnect.connect.whatsapp.type;

import com.imiconnect.connect.core.util.Preconditions;
import com.imiconnect.connect.whatsapp.type.template.MediaHeader;
import com.imiconnect.connect.whatsapp.type.template.QuickReply;
import com.imiconnect.connect.whatsapp.type.template.Substitution;
import lombok.Singular;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.imiconnect.connect.core.util.Preconditions.notNullOrBlank;

/** Defines whatsapp {@link Content} representing a templated message. */
@Value
public final class Template implements Content {

  private final WhatsAppContentType contentType = WhatsAppContentType.TEMPLATE;
  private final String templateId;
  private final MediaHeader mediaHeader;
  private final List<QuickReply> quickReplies;
  private final Map<String, Substitution> substitutions;

  @lombok.Builder(builderClassName = "Builder")
  private Template(
      String templateId,
      MediaHeader mediaHeader,
      List<QuickReply> quickReplies,
      @Singular Map<String, Substitution> substitutions) {

    this.templateId = notNullOrBlank(templateId, "templateId");
    this.mediaHeader = mediaHeader;
    Preconditions.validArgument(
        quickReplies != null && quickReplies.size() <= 3,
        "A maximum of 3 quick replies can be added to a templated message.");
    this.quickReplies = quickReplies;
    this.substitutions = substitutions;
  }

  public static class Builder {

    private List<QuickReply> quickReplies;

    public Builder quickReply(QuickReply quickReply) {
      if (quickReplies == null) {
        quickReplies = new ArrayList<>();
      }
      quickReplies.add(quickReply);
      return this;
    }

    public Builder quickReplies(List<QuickReply> quickReplies) {
      this.quickReplies = quickReplies;
      return this;
    }

  }
}
