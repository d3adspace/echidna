# Echidna
Echidna is supposed to solve our problem of a missing simple and lightweight REST Framework. 

# Installation / Usage

- Install [Maven](http://maven.apache.org/download.cgi)
- Clone this repo
- Install: ```mvn clean install```

**Maven dependencies**

_Echidna:_
```xml
<dependency>
    <groupId>de.d3adspace</groupId>
    <artifactId>echidna-server</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

# Example

_Server:_
```java
EchidnaConfig config = new EchidnaConfigBuilder()
	.setServerPort(80)
	.setServerHost("localhost")
	.setResourceClasses(Collections.singletonList(new ExampleResource()))
	.createEchidnaConfig();
		
EchidnaServer echidnaServer = EchidnaServerFactory.createEchidnaServer(config);
		
echidnaServer.start();
```

_Resource Example:_
```java
@Path("/rest/v1")
public static class ExampleResource {
		
	@GET
	@Path("/user/del")
	@Consumes("text/html")
	@Produces("text/plain")
	public HTTPResponse onUserDel(HTTPRequest request) {
		return HTTPResponse.newBuilder()
			.setStatus(HTTPStatus.OK)
			.setBody(HTTPBody.fromString("Hey"))
			.setHeaders(new HTTPHeaders())
			.createHTTPResponse();
		}
		
	@GET
	@Path("/user/del/{userId}")
	@Consumes("text/html")
	@Produces("text/plain")
	public HTTPResponse onUserDelId(HTTPRequest request, String userId) {
		return HTTPResponse.newBuilder()
			.setStatus(HTTPStatus.OK)
			.setBody(HTTPBody.fromString("Hey du " + userId))
			.setHeaders(new HTTPHeaders())
			.createHTTPResponse();
	}
		
	@POST
	@Path("/user/add")
	@Consumes("text/html")
	@Produces("text/plain")
	public HTTPResponse onUserDelIdPost(HTTPRequest request, @PostKey("test") String userId) {
		System.out.println("Parameter: " + userId);
			
		return HTTPResponse.newBuilder()
			.setStatus(HTTPStatus.OK)
			.setBody(HTTPBody.fromString("Hey du " + userId))
			.setHeaders(new HTTPHeaders())
			.createHTTPResponse();
	}
}
```

_Client Example:_
```java
EchidnaClient client = new EchidnaClient();
HTTPRequest request = HTTPRequest
	.newBuilder()
	.setLocation("rest/v1/user/add")
	.setMethod(HTTPMethod.POST)
	.setVersion("HTTP/1.1")
	.setHeaders(new HTTPHeaders())
	.createHTTPRequest();
		
HTTPBody body = HTTPBody.newBodyBuilder("test", "IchBinDerBeste").build();		
HTTPResponse response = null;

try {
	response = client.request(new URL("http://localhost/rest/v1/user/add"), request, body);
} catch (MalformedURLException e) {
	e.printStackTrace();
}
		
System.out.println("Got Response: " + response);
```
