#include <ESP8266WiFi.h>
#include <ESP8266HTTPClient.h>
#include <ESP8266WebServer.h>

const char* ssid = "TP-Link_AP_7E94";
const char* password = "tplink1234321";
//const float weight1 = 90.5;
//const float weight2 = 50.5;
//const float weight3 = 0;
boolean isZero1 = false;
boolean isZero2 = false;
boolean isZero3 = false;
boolean isZero4 = false;
boolean isZero5 = false;

WiFiServer server(80);

void setup() {
  Serial.begin(9600); //Serial connection
  WiFi.begin(ssid, password);   //WiFi connection
  while (WiFi.status() != WL_CONNECTED) {  //Wait for the WiFI connection completion
    delay(500);
  }
  pinMode(LED_BUILTIN, OUTPUT);
  digitalWrite(LED_BUILTIN, LOW);
  delay(2000);
  digitalWrite(LED_BUILTIN, HIGH);
  server.begin();
  Serial.println("Wifi OK");
  Serial.println(WiFi.localIP());

  setToInitial();

}

void loop() {
//    server.handleClient();
  Serial.println(digitalRead(D1));
  Serial.println(digitalRead(D2));
  Serial.println(digitalRead(D6));
  Serial.println(digitalRead(D7));
  Serial.println(digitalRead(D5));

  if (digitalRead(D1) == 1 && !isZero1) {
    isZero1 = true;
    sendRequest("http://192.168.1.4:8080/api/place/binSignal/A1/0");
  }

  if (digitalRead(D2) == 1 && !isZero2) {
    isZero2 = true;
    sendRequest("http://192.168.1.4:8080/api/place/binSignal/A2/0");
  }

  if (digitalRead(D6) == 1 && !isZero3) {
    isZero3 = true;
    sendRequest("http://192.168.1.4:8080/api/place/binSignal/A3/0");
  }

  if (digitalRead(D7) == 1 && !isZero4) {
    isZero4 = true;
    sendRequest("http://192.168.1.4:8080/api/place/binSignal/A4/0");
  }

  if (digitalRead(D5) == 1 && !isZero5) {
    isZero5 = true;
    sendRequest("http://192.168.1.4:8080/api/place/binSignal/A5/0");
  }

  WiFiClient client = server.available();
  if (!client) {
    return;
  }

  while (!client.available()) {}
  String request = client.readStringUntil('\r');
  Serial.println(request);
  client.flush();
  // Match the request
  if (request.indexOf("/get_weight") != -1)  {
    digitalWrite(LED_BUILTIN, LOW);
    sendRequest("http://192.168.1.4:8080/api/place/binSignal/A1/50.5");
    delay(1000);
    sendRequest("http://192.168.1.4:8080/api/place/binSignal/A2/32");
    delay(1000);
    sendRequest("http://192.168.1.4:8080/api/place/binSignal/A3/41");
    delay(1000);
    sendRequest("http://192.168.1.4:8080/api/place/binSignal/A4/68");
    delay(1000);
    sendRequest("http://192.168.1.4:8080/api/place/binSignal/A5/46");
    delay(1000);
    digitalWrite(LED_BUILTIN, HIGH);
    setToInitial();
  }
  client.println("HTTP/1.1 200 OK");
  client.println("Content-Type: text/html");
  client.println("OK");

}

void setToInitial() {
  isZero1 = false;
  isZero2 = false;
  isZero3 = false;
  isZero4 = false;
  isZero5 = false;

  pinMode(D1, OUTPUT);
  digitalWrite(D1, LOW);
  pinMode(D1, INPUT);

  pinMode(D2, OUTPUT);
  digitalWrite(D2, LOW);
  pinMode(D2, INPUT);

  pinMode(D6, OUTPUT);
  digitalWrite(D6, LOW);
  pinMode(D6, INPUT);

  pinMode(D7, OUTPUT);
  digitalWrite(D7, LOW);
  pinMode(D7, INPUT);

  pinMode(D5, OUTPUT);
  digitalWrite(D5, LOW);
  pinMode(D5, INPUT);
}

void sendRequest(String url) {
  if (WiFi.status() == WL_CONNECTED) { //Check WiFi connection status
    HTTPClient http;    //Declare object of class HTTPClient
    http.begin(url); //Specify request destination
    int httpCode = http.GET(); //Send the request
    if (httpCode > 0) { //Check the returning code
      String payload = http.getString();   //Get the request response payload
    }
    http.end();  //Close connection
  } else {
    Serial.println("Error in WiFi connection");
  }
}
