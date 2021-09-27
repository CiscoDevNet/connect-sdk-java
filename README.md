# java-sdk
Java SDK for CPaaS Web Services

## Requirements

### Java Versions

This SDK supports the following Java versions. OpenJDK versions can be found [here](https://adoptium.net).

- Java 8
- Java 11

## Getting Started

To start using the API only takes a few simple steps. First, import the binaries into your project.
The latest versions can be found on [maven central](https://search.maven.org/search?q=g:com.cisco).

```xml
<dependency>
    <groupId>com.cisco</groupId>
    <artifactId>cpass-sdk-java</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

Then create one of the available clients depending on the types of messages you need to send.

```java
SmsClient smsClient = SmsClient.create().withBaseUrl(URL).withApiToken(API_KEY).build();
```

Sending a message is then as simple as passing in the desired message and telephone numbers to use.

```java
SendSmsResponse response = smsClient.sendMessage(SmsMessage.of("hello world!")
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
| 1.0+ | 1 | 1 | 1 |

## Contributing

TBD

## License

&copy; 2021 Cisco Systems, Inc. and/or its affiliates. All Rights Reserved. See [LICENSE](LICENSE) for details.
