package upc.edu.pe.ecochips.UTIL;

import jakarta.servlet.*;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

// ❌ DESHABILITADO - Ahora CORS se maneja en WebSecurityConfig
//@Component
//@Order(Ordered.HIGHEST_PRECEDENCE)
public class CORS implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        // chain.doFilter(req, res);
    }

    @Override
    public void destroy() {
    }
}