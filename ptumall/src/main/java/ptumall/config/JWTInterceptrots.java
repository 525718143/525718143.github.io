package ptumall.config;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import ptumall.utils.JWTUtils;
import ptumall.utils.Result;
import ptumall.utils.ResultCodeEnum;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JWTInterceptrots implements HandlerInterceptor {
    //ctrl + i 列出所有重写方法

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String message;
        String token = request.getHeader("token");
        try {
            JWTUtils.verify(token);
            return true;
        } catch (SignatureVerificationException e) {
            message = "无效签名";

        }catch (TokenExpiredException e){
            message = "token过期";
        }catch (AlgorithmMismatchException e){
            message = "算法不一致";
        }catch (Exception e){
            message = "token为空或者无效";
        }
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(Result.failure(ResultCodeEnum.UNAUTHORIZED,message));
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(json);
        return false;
    }
}