package Stream.project.stream.models.security;


import java.util.Date;
import java.util.logging.Logger;

import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.logging.Logger;

import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.SignatureAlgorithm;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtUtils {

    private static final Logger logger = Logger.getLogger(JwtUtils.class.getName());

    @Autowired
    PasswordEncoder encoder;

    public String generateJwtToken(String username) {

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, "secret")
                .setSubject(username)
                .setIssuedAt(new Date())
                .compact();


    }


    public String getUserNameFromToken(String token) {
        return Jwts.parser().setSigningKey("secret").parseClaimsJws(token).getBody().getSubject();

    }
    public Boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey("secret").parseClaimsJws(token);
            return true;
        }catch (SignatureException e) {
            logger.info(e.getMessage());
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
