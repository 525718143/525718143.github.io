package ptumall.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.HashMap;


@Component
@ConfigurationProperties(prefix = "jwt")
public class JWTUtils {
    private static String SING;
    private static Integer expireTime;

    public  void setSING(String SING) {
        JWTUtils.SING = SING;
    }

    public  void setExpireTime(Integer expireTime) {
        JWTUtils.expireTime = expireTime;
    }

    public  static String getToken(String useId,String userName){
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.MINUTE,expireTime);

        JWTCreator.Builder builder = JWT.create();

        HashMap<String, String> payload = new HashMap<>();
        payload.put("id",useId);
        payload.put("name",userName);
        payload.forEach((k,v)->{
            builder.withClaim(k,v);
        });
        String token = builder.withExpiresAt(instance.getTime()).sign(Algorithm.HMAC256(SING));
        return token;
    }

    public static DecodedJWT verify(String token){
        return JWT.require(Algorithm.HMAC256(SING)).build().verify(token);
    }
}
