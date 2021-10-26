# connect-sdk-java
Java SDK for IMIconnect Messaging Platform Web Services

## Requirements

### Java Versions

This SDK supports the following Java versions. OpenJDK versions can be found [here](https://adoptium.net).

- Java 8
- Java 11

## Getting Started

To start using the API only takes a few simple steps.

### 1. Sign up for an account on [imiconnect](https://sandbox.imiconnect.io/login).

### 2. import the binaries into your project.
The latest releases can be found on [maven central](https://search.maven.org/search?q=g:com.imiconnect).

```xml
<dependency>
    <groupId>com.imiconnect</groupId>
    <artifactId>connect-sdk-java</artifactId>
    <version>1.0</version>
</dependency>
```

### 3. Create a client
Depending on the type of messages you need to send, one of `SmsClient`, `WhatsAppClient`, or `VoiceClient` are available.

```
SmsClient client = SmsClient.create()
      .withBaseUrl(URL)
      .withApiKey(API_KEY)
      .build();
```

### 4, Send a message
```
SendSmsResponse response =
        client.sendMessage(SmsMessage.of("hello world!")
                .from("35353")
                .to("+15550001111")
                .build());
```

## Versions

This project follows the [semantic versioning model](https://semver.org/).

### API Version Compatibility Matrix

This SDK supports the SMS, voice, and WhatsApp API's. The supported API versions are listed below.

| SDK | SMS | WhatsApp | Voice |
|:---:|:---:|:--------:|:-----:|
| 1.0 | 1 | 1 | 1 |

## License

&copy; 2021 Cisco Systems, Inc. and/or its affiliates. All Rights Reserved. See [LICENSE](LICENSE) for details.
