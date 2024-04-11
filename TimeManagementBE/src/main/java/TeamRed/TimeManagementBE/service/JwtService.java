package TeamRed.TimeManagementBE.service;

import java.security.Key;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
//import java.util.Map;
import java.util.Map;

import org.springframework.http.HttpHeaders;
//import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import TeamRed.TimeManagementBE.CustomAuthenticationToken;
import TeamRed.TimeManagementBE.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtService {
	static final long EXPIRATIONTIME = 1000*60*60*24;
	static final String PREFIX = "Bearer";
	static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256); //Key must be changed in production environment
	
	//Generates a signed JWT token
	//V1 public String getToken(String username, Map<String, Object> userDetails) {
	//V2 public String getToken(String username) {
	public String getToken(CustomUserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("user_id", userDetails.getId());
		claims.put("first_name", userDetails.getFirst_name());
		claims.put("last_name", userDetails.getLast_name());
		String token = Jwts.builder()
				.setSubject(userDetails.getUsername())
				.addClaims(claims)
				//.setClaims((Claims) userDetails)
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
				.signWith(key)
				.compact();
		return token;
	}
		
	//Gets a token from request Authorization header, verifies a token, gets username
	public UsernamePasswordAuthenticationToken getAuthUser(HttpServletRequest request) {
		String token = request.getHeader(HttpHeaders.AUTHORIZATION);
		//System.out.println("TOKEN: " + token);
		try {
			if (token != null) {
				/*String user = Jwts.parserBuilder()
						.setSigningKey(key)
						.build()
						.parseClaimsJws(token.replace(PREFIX, ""))
						.getBody()
						.getSubject();
				if (user != null) {
					return user;
				}*/
				Claims claims = Jwts.parserBuilder()
						.setSigningKey(key)
						.build()
						.parseClaimsJws(token.replace(PREFIX, ""))
						.getBody();
				//System.out.println("SUBJECT FROM CLAIM: " + claims.getSubject());
				//System.out.println("ALL CLAIMS: " + claims);
				//System.out.println("USER ID OBJECT: " + claims.get("user_id"));
				try {
				    Integer userIdObject = (Integer) claims.get("user_id");
					if (userIdObject != null) {
						long user_id = userIdObject.longValue();
					    CustomAuthenticationToken customtoken = new CustomAuthenticationToken(claims.getSubject(), null, Collections.emptyList(), user_id);
					    //System.out.println("USER ID: " + userId);
					    //System.out.println("TOKEN: " + customtoken);
					    return customtoken;
					} else {
					    System.out.println("User ID not found in claims");
					}
				    //System.out.println("userIdObject: " + userIdObject);
				} catch (ClassCastException e) {
				    System.out.println("Error casting user_id to Integer: " + e.getMessage());
				}
				//Integer userIdObject = (Integer) claims.get("user_id");
				//Long user_id = (Long) claims.get("user_id");
				//System.out.println("userIdObject: " + userIdObject);

				//System.out.println("USER ID: " + user_id);
				//CustomAuthenticationToken customtoken = new CustomAuthenticationToken(claims.getSubject(), null, Collections.emptyList(), user_id);
				//System.out.println("TOKEN: " + customtoken);
				//return new UsernamePasswordAuthenticationToken(claims.getSubject(), null, Collections.emptyList());
			}
			return null;
		} catch (Exception e) {
			throw new BadCredentialsException("Invalid token");
		}
	}
}
