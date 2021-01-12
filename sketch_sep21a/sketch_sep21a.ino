#include <ESP8266WiFi.h>
#include <ESP8266HTTPClient.h>
#include <ESP8266WebServer.h>

const char* ssid = "TP-Link_AP_7E94";
const char* password = "tplink1234321";

const char* label = "A2";
//float weight = 50.5;
float weight = 32;
//float weight = 41;
//float weight = 78;
//float weight = 46;

boolean isZero = false;

WiFiServer server(80);

void setup() {
  Serial.begin(9600); //Serial connection
  WiFi.begin(ssid, password);   //WiFi connection
  while (WiFi.status() != WL_CONNECTED) {  //Wait for the WiFI connection completion
    delay(500);
  }
  pinMode(LED_BUILTIN, OUTPUT);
  pinMode(D0, OUTPUT);
  digitalWrite(D0, LOW);
  //  delay(2000);
  //  digitalWrite(LED_BUILTIN, HIGH);
  server.begin();
  Serial.println("Wifi OK");
  Serial.println(WiFi.localIP());
  Serial.println(WiFi.macAddress());

  setToInitial();

}

void loop() {
  //    server.handleClient();
  Serial.println(digitalRead(D1));

  if (digitalRead(D1) == 1 && !isZero) {
    isZero = true;
    weight = 0;
    sendRequest("http://192.168.1.4:8080/api/place/binSignal/" + String(label) + "/" + String(weight));
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
    sendRequest("http://192.168.1.4:8080/api/place/binSignal/" + String(label) + "/" + String(weight));
    digitalWrite(LED_BUILTIN, HIGH);
    setToInitial();
  }
  client.println("HTTP/1.1 200 OK");
  client.println("Content-Type: text/html");
  client.println("OK");

}

void setToInitial() {
  isZero = false;

  pinMode(D1, OUTPUT);
  digitalWrite(D1, LOW);
  pinMode(D1, INPUT);

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
