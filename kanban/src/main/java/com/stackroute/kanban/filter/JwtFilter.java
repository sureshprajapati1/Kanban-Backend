package com.stackroute.kanban.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request=(HttpServletRequest)servletRequest;
        HttpServletResponse response= (HttpServletResponse) servletResponse;
        String authHeader=request.getHeader("authorization");
        if (authHeader==null || !authHeader.startsWith("Bearer")){
            throw new ServletException("Token Is Missing or Token Is Not Bearer Type");
        }
        else {
            String token=authHeader.substring(7);
//            try {
                Claims claims = Jwts.parser()
                        .setSigningKey("idontsay")
                        .parseClaimsJws(token)
                        .getBody();
            System.out.println(claims);
                request.setAttribute("userdetailsemailid",claims.get("email"));
//            }
//            catch (JwtException ex){
//                throw new RuntimeException(ex);
//            }

            filterChain.doFilter(request,response);
        }
    }
}
