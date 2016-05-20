package com.mum.scrum.filter;

import com.mum.scrum.utility.Utility;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Properties;

/**
 * Created by 984609 on 4/7/2016.
 */
public class TokenValidationFilter implements Filter {
    Properties prop = new Properties();
    ObjectMapper mapper = new ObjectMapper();


    @Override
    public void destroy() {
        // ...
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        InputStream inputStream = null;

        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            inputStream = classLoader.getResourceAsStream("config.properties");
            prop.load(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response, FilterChain chain)
            throws IOException, ServletException {


        if (!Boolean.valueOf(prop.getProperty("token.authentication.enabled"))
                || ((HttpServletRequest) request).getServletPath().contains("login")) {  //TODO exact match
            chain.doFilter(request, response);
        } else {
            String token = request.getParameter("token");
            if (!Utility.authenticateToken(token, prop.getProperty("token.secret.key"))) {
                response.setContentType("application/json");
                ((HttpServletResponse)response).setStatus(HttpServletResponse.SC_FORBIDDEN);
                PrintWriter out = response.getWriter();
                out.print(mapper.writeValueAsString(Utility.populateViewModel(
                        Utility.NOT_LOGGED_IN_STATUS_CODE, Arrays.asList("Not logged in"))));//TODO will add json object
                out.flush();
                return;
            }
            chain.doFilter(request, response);


        }


    }
}
