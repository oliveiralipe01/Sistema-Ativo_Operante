package unoeste.fipp.ativooperante.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AccessFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String token = request.getHeader("Authorization");

        if(token != null && token.startsWith("Bearer ")) {

            token = token.substring(7);

            if(JWTTokenProvider.verifyToken(token)) {

                Claims claims = JWTTokenProvider.getAllClaimsFromToken(token);

                String nivel = claims.get("nivel").toString();

                String rota = request.getRequestURI();

                // ADMIN
                if(rota.contains("/adm") && !nivel.equals("1")) {
                    response.setStatus(403);
                    response.getWriter().write("Acesso negado");
                    return;
                }

                // CIDADAO
                if(rota.contains("/cidadao")
                        && !(nivel.equals("1") || nivel.equals("2"))) {

                    response.setStatus(403);
                    response.getWriter().write("Acesso negado");
                    return;
                }

                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
        }

        response.setStatus(401);
        response.getWriter().write("Token invalido");
    }
}