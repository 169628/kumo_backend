package tw.idv.rainbow.security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        JsonObject respBody = new JsonObject();

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // cut "Bearer "

            if (!jwtUtil.validateToken(token)) {
                respBody.addProperty("status", 2);
                respBody.addProperty("message", "please login again");
                response.getWriter().write(respBody.toString());
                return;
            }

            // you must give the username to SecurityContextHolder, so spring can list who is already login
            String username = jwtUtil.getUsername(token);
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(username, null,
                            List.of());
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }

        // if you post login api, it will go here
        filterChain.doFilter(request, response);
    }
}
