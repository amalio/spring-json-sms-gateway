SPRING JSON SMS Gateway
=====================
This project is a gateway for sending SMS messages based on Spring Framework. Take advantage of Spring MVC, Spring Security and Spring AOP features.

The persistent layer has been switched to **JPA (Hibernate)**, for native JDBC checkout jdbc branch. 

## Features
- [x] Ready for send SMS via SMSC with SMPP Protocol
- [x] Authentication System
- [x] JSON Bulk procesing SMS for requests and responses
- [x] Scheduled o Instant SMS's
- [x] ACK Sending

## Installation

1. Setup your database. You can use the supplied setup database.sql file
3. Copy supplied gateway.properties in your /etc/amalio/ folder. Create folder if no exists. I'm pretty sure no exists. ;)
4. Edit gateway.properties setting the user name, password and other correct values
5. Deploy spring-json-sms-gateway.war in your servlet container

## Usage

### Making Requests
All requests must include the http basic authentication credentials. And simply use the required json. For new and updates of SMS use HTTP POST method. For delete, simply use HTTP DELETE method. if you want test sending set test on true.

### Send an instant SMS
```json
{
    "sms_request": [
    {
      "msisdn": "34646974525",
      "sender": "amalio",
      "text": "message text"
    }
  ]
}
```

### Send a instant SMS with ACK requirement
```json
{ 
  "sms_request": [
    {
      "msisdn": "34646974525",
      "sender": "amalio",
      "text": "message text",
      "subid": "subid1",
      "ack_url": "http://www.anurl.com/ack"
    }
  ]
}
```

### Schedule a SMS
```json
{
    "sms_request": [
    {
      "msisdn": "34646974525",
      "sender": "amalio",
      "text": "message text",
      "datetime": "2015-12-27 10:00"
    }
  ]
}
```

### Updating a scheduled SMS
```json
{
  "sms_request": [
    {
      "id": "subid1",
      "msisdn": "34646974525",
      "sender": "amalio",
      "text": "message text",
      "datetime": "2015-12-27 11:00"
    }
  ]
}
```

### Delete a scheduled SMS (remember method DELETE)
```json
{
  "sms_request": [
    {
      "id": "subid1"
    }
  ]
}
```

### Making several requests
```json
{
    "sms_request": [
    {
      "id": 0,
      "msisdn": "34646974525",
      "sender": "amalio",
      "text": "the text of SMS with an ñ",
      "subid": "subid1",
      "ack_url": "http://www.opteral.com/ack",
      "datetime": null,
      "test": false
    },
    {
      "id": null,
      "msisdn": "34646974525",
      "sender": "amalio",
      "text": "the text of SMS with an ñ",
      "datetime": "2015-12-27 10:00",
      "subid": "subid2",
      "ack_url": "http://www.opteral.com/ack",
      "test": false
    },
    {
      "id": 1,
      "msisdn": "34646974525",
      "sender": "amalio",
      "text": "the text of SMS with an ñ",
      "datetime": "2015-12-27 11:00",
      "subid": "subid3",
      "ack_url": "http://www.opteral.com/ack",
      "test": false
    },
    {
      "msisdn": "34646974525",
      "sender": "amalio",
      "text": "the text of SMS with an ñ",
      "datetime": null,
      "test": true,
    }
  ]
}
```
### Parsing Responses
An OK response looks like this:  
```json
{
"response_code":"OK",
"sms_responses":[
    {
    "request_ok":true,
    "status":"ACCEPTD",
    "id":1201,
    "subid":"subid1"},
    {
    "request_ok":true,
    "status":"ACCEPTD",
    "id":1202,
    "subid":"subid2"
    }
  ]
}
```
If Authetication fails then response looks like this:  
```json
{
"response_code":"ERROR_LOGIN",
" msg":"Athetication failed"
}
```  
If gateway fails then response looks like this:  
```json
{
"response_code":"ERROR_GENERAL",
" msg":"an explanation message"
}
```  
If an SMS fails then response looks like this:  
```json
{
"response_code":"OK",
"sms_responses":[
    {
    "request_ok":true,
    "status":"REJECTD",
    "id":0,
    "subid":"subid1"
    },
    {
    "request_ok":true,
    "status":"ACCEPTD",
    "id":1202,
    "subid":"subid2"
    }
  ]
}
```
### ACK
If ack_url is set, gateway sends ACK delivery when SMS is delivered to  supplied url like this:
```json
{
"id":861,
"subid":"subid1",
"msisdn":"34646548725",
"delivered":"2015-04-08 09:27",
"sms_status":"DELIVRD"
}
```
  
## License

spring-json-sms-gateway is released under the Unlicense. See LICENSE for details.

(In other words, do with it what the hell you want)
