package com.cisco.cpaas.sms.type;

/** Available values that represent the state of the message send attempt. */
public enum SendStatus {
  QUEUED,
  SENT,
  DELIVERED,
  FAILED
}
