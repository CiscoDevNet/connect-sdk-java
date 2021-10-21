package com.imiconnect.cpaas.whatsapp.parser;

import com.imiconnect.cpaas.core.annotation.Nullable;
import com.imiconnect.cpaas.core.type.ErrorResponse;
import com.imiconnect.cpaas.whatsapp.type.Content;
import com.imiconnect.cpaas.whatsapp.type.WhatsAppMsgStatus;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.Instant;

import static com.imiconnect.cpaas.core.util.Preconditions.validArgument;

/**
 * Jackson deserializer for the {@link WhatsAppMsgStatus}. This is required since polymorphic
 * deserialization of the various {@link Content} types do not work with the JsonUnwrapped
 * annotation.
 *
 * <p>In the message status, the content types are a single property of the respective type while
 * the serialized response from the http response contains all values as a flat structure. To
 * eliminate the need to write a custom deserializer for each content type, the original json node
 * is cloned so that it can be read two times. Once to manually parse the non content values, and
 * the second time to automatically parse the content values into the proper content type.
 */
public final class WhatsAppMsgStatusDeserializer extends StdDeserializer<WhatsAppMsgStatus> {

  public WhatsAppMsgStatusDeserializer() {
    super(WhatsAppMsgStatus.class);
  }

  @Override
  public WhatsAppMsgStatus deserialize(JsonParser p, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {

    JsonNode node = p.getCodec().readTree(p);
    String messageId = asText(node, "messageId");
    String from = asText(node, "from");
    String to = asText(node, "to");
    String correlationId = asTextOrNull(node, "correlationId");
    String statusStr = asText(node, "status");
    WhatsAppMsgStatus.Status status = WhatsAppMsgStatus.Status.valueOf(statusStr);
    String acceptedTimeStr = asText(node, "acceptedTime");
    Instant acceptedTime = Instant.parse(acceptedTimeStr);
    String statusTimeStr = asText(node, "statusTime");
    Instant statusTime = Instant.parse(statusTimeStr);

    // Read the root node a second time to get the polymorphic parts of the flattened json into the
    // proper object type.
    Content content = node.traverse(p.getCodec()).readValueAs(Content.class);

    JsonNode errorStatusNode = node.get("error");
    ErrorResponse errorResponse = null;
    if (errorStatusNode != null) {
      errorResponse = p.getCodec().treeToValue(errorStatusNode, ErrorResponse.class);
    }

    return new WhatsAppMsgStatus(
        null, // request id, not part of json body
        messageId,
        acceptedTime,
        from,
        to,
        correlationId,
        status,
        statusTime,
        content,
        errorResponse);
  }

  private @Nullable String asTextOrNull(JsonNode rootNode, String nodeName) {
    JsonNode node = rootNode.get(nodeName);
    return node == null ? null : node.asText();
  }

  private String asText(JsonNode rootNode, String nodeName) {
    JsonNode node = rootNode.get(nodeName);
    validArgument(node != null, "\"" + nodeName + "\" is null.");
    return node.asText();
  }
}
