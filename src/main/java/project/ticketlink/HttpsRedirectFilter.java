package project.ticketlink;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class HttpsRedirectFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if(!request.isSecure()){
            String redirectURL = "https://" + request.getServerName() + ":" + 8080 + request.getRequestURI();
            response.sendRedirect(redirectURL);
        }else {
            filterChain.doFilter(request, response);
        }


    }

    @Override
    public void destroy() {

    }
}
