### How is JSON Web Token (JWT) motivated?
HTTP is a stateless protocol. Every interaction in HTTP needs to contain all the information required for that interaction to complete successfully. Nothing is remembered.
There are two popular ways in which web servers manage sessions. They are using tokens. they are:
* Session Tokens
* JSON Web Tokens

Session ID + Cookie based authorization has been a popular authorization. But there are a few problems. While this technique works with traditional monolithic applications, they do not work with applications with modern microservice architectures. 
Every session is stored at a server level. When there are multiple servers, different servers will not be able to share the session information if this is stored on one server and another server tries to fetch it. A shared cache mechanism (redis) comes to the rescue. The drawback is there is one single point of failure, now. 
A sticky session technique helps the load balancer remember the server per user request, but this is not scalable.

### What is JWT?
Whenever a client successfully authenticates to the server, the server sends a **signed** JSON payload/object of all the information with respect to the request (not just the token) back to the client. The client is in possession of the token and uses this for every subsequent requests. This JSON object is known as a **JSON Web Token (JWT)**. The responsibility of the server to maintain the state is fully offloaded to the client. 
Session tokens are _reference tokens_. They refer to a state on the server. JWTs are _value tokens_. They contain the values (entire object). **JWT is only used for authorization purposes, post authentication.**
After a client receives a JWT from the server, in subsequent requests it can add the JWT in its header. The key as specified in JWT standard is `Authorization` and the value is `Bearer <JWT>`.
The server decodes the header and payload and calculates the signature and matches with the signature that was sent in the request. If a match occurs, the authorization is granted. Else an error is raised.

### Structure of JWT
A JWT string typically is a long string containing two dots. These dots separate the string into 3 parts. 

#### Parts to a JWT
 * Header
 * Payload
 * Signature
 
### Sample Encoded JWT
Everything is Base64 encoded. 
`eyJpZCI6IjIzMTMiLCJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiIsIm5hbWUiOiJBbmltZXNoIFBhdWwifQ.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.ZOTehx2muImN8MYktHc34yzjw_Mkm1GsygY3-UNiaFM`

### Sample Decoded JWT
#### Header
```
{
    "alg": "HS256", (the algorithm used to sign and later verify the signature. This algorithm requires a secret key which only the server has)
    "typ": "JWT" (potentially for other token types in the future)
}
```
#### Payload
The Base64 encoded string appears in the JWT between the two dots. 
```
{
      "sub": "1234567890",
      "name": "John Doe",
      "iat": 1516239022
}
```
#### Signature
This is used to verify the authenticity of the token.
```
HMACSHA256(
  base64UrlEncode(header) + "." +
  base64UrlEncode(payload),
  
my_signature

)
```

### What is OAuth?
OAuth is an open standard authorization mechanism where services can authorize with each other on behalf of a principal, once the principal provides permission. This is often referred to as delegated access. It is important to note that OAuth is used for Authorization and not for Authentication. OAuth essentially regulates/governs service to service authorization (authorization between services). It has very good use cases for authorization between microservices.

![OAuthExample](OAuthExample.png)
