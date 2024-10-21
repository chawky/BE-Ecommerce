package Stream.project.stream.common;


import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.TextCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.logging.Logger;

@Component
public class JwtUtils {

    private static final Logger logger = Logger.getLogger(JwtUtils.class.getName());

    @Autowired
    PasswordEncoder encoder;

    public String generateJwtToken(String username) {

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, "secret".getBytes())
                .setSubject(username)
                .setIssuedAt(new Date())
               .setExpiration(new Date(System.currentTimeMillis() + 15 * 60 * 1000)) // 15 minutes = 15 * 60 * 1000 milliseconds
                .compact();


    }


    public String getUserNameFromToken(String token) {
        return Jwts.parser().setSigningKey("secret").parseClaimsJws(token).getBody().getSubject();

    }
    public static Boolean validateToken(String token) {
        try {
            String secretKey = TextCodec.BASE64URL.encode("secret");
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        }catch (SignatureException e) {
            logger.info(e.getMessage());
            throw new SignatureException(e.getMessage());
        }
        catch (MalformedJwtException e) {
            logger.info(e.getMessage());
        }
        catch (UnsupportedJwtException e) {
            logger.info(e.getMessage());
        }
        catch (IllegalArgumentException e) {
            logger.info(e.getMessage());
        }
        return false;


    }

}
