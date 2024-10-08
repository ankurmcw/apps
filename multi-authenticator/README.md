Objective:
---------
Implement a service using Spring-Boot that exposes REST endpoints and provides configurable authentication schemes

Requirements:
-------------

* The REST endpoint should take a path parameter. e.g /{platform}/api/v1
* The REST endpoint should be authenticated.
* The service should support multiple authentication schemes such as Basic, HMAC, JWT, etc.
* The service should use different authentication schemes for different platforms.

Approach:
---------

To implement this service I have made use of Spring beans and application properties.
* I have created a Spring bean for each authentication scheme.

* Each bean is given a name.
  
  ```java
  @Component("jwt")
  public class JwtAuthenticationHandler {
      public void validate(String authorizationToken) throws UnauthorizedException {
      }
  } 
  ```
        
* Each platform is mapped to an authentication scheme in the application.properties file

    ```properties
    authentication.platforms.platform-a=basic
    authentication.platforms.platform-b=hmac
    authentication.platforms.platform-c=jwt
    ```

* A custom filter is registered to perform the following.
  * Intercept the request.
  * Retrieve platform name from the request URI.
  * Lookup the authentication scheme registered for the platform in the application properties.
  * Retrieve the specific bean from the BeanFactory.
  * Validate the authorization token received in the header.


