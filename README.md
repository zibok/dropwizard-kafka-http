# dropwizard-kafka-http

This project creates an Apache Kafka HTTP endpoint for producing and consuming messages.

## dropwizard-kafka-http api methods

### Produce messages
```
POST /message

topic=$topic

message=$message0
key=$key0

message=$message1
key=$key1, ... etc

Produces messages on topic.

Parameters:
topic   - required topic name
key     - required key text. Multiple values are possible
message - required message text. Multiple values are possible

Note: passed keys count should match messages count.

Errors:
400     - wrong parameters passed
```

### Consume messages
```
GET /message?topic=$topic&timeout=$timeout
Returns consumed messages available on topic.

Parameters:
topic   - required topic name
timeout - optional timeout in ms

Errors:
400     - wrong parameters passed
```

