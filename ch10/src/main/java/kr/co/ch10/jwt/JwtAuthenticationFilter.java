package kr.co.ch10.jwt;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.ch10.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private static final String AUTH_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 요청 주소에서 마지막 문자열 추출
        String uri = request.getRequestURI();
        int i = uri.lastIndexOf("/");
        String path = uri.substring(i);
        log.info("doFileterInternal...1 :" +path);

        // 토큰 추출
        String header = request.getHeader(AUTH_HEADER);
        log.info("doFileterInternal...2 :" + header);
        String token = null;
        if(header != null && header.startsWith(TOKEN_PREFIX)){
            token = header.substring(TOKEN_PREFIX.length());
        }
        log.info("doFileterInternal...3 :" + token);
        if(token != null){
            try {
                log.info("doFileterInternal...4");
                jwtProvider.validateToken(token);

                log.info("doFileterInternal...5");
                // refresh 요청일 경우 (새로운 access token 발급 요청)
                if(path.equals("/refresh")){
                    log.info("doFileterInternal...6");
                    Claims claims = jwtProvider.getClaims(token);
                    String uid = (String) claims.get("username");
                    String role = (String) claims.get("role");

                    User user = User.builder()
                                    .uid(uid)
                                    .role(role)
                                    .build();

                    log.info("doFileterInternal...7 :" + user.toString());
                    String accessToken = jwtProvider.createToken(user, 1);

                    log.info("doFileterInternal...8" + accessToken);
                    response.setStatus(HttpServletResponse.SC_CREATED);
                    response.getWriter().println(accessToken);
                    return;
                }

            }catch (JwtMyException e){
                log.info("doFileterInternal...9");
                e.sendResponseError(response);
                return;
            }

            log.info("doFileterInternal...10");

            // 인증 처리
            Authentication authentication = jwtProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        log.info("doFileterInternal...11");

        // 다음 필터 이동
        filterChain.doFilter(request, response);
    }
}
