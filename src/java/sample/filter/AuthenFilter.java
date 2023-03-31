/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.filter;

import dto.UserDTO;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author MY LAPTOP
 */
@WebFilter(filterName = "AuthenFilter", urlPatterns = {"/*"})
public class AuthenFilter implements Filter {
    
    private static final boolean debug = true;
    private static final String ROLE_US = "US";
    private static final String ROLE_AD = "AD";
    private static List<String> USER_FUNCTIONS;
    private static List<String> ADMIN_FUNCTIONS;
    private static List<String> NON_AUTHEN_FUNCTIONS;
    private static final String LOGIN_PAGE = "login.jsp";

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;
    
    public AuthenFilter() {
        USER_FUNCTIONS = new ArrayList<>();
        USER_FUNCTIONS.add("shoping-home.jsp");
        USER_FUNCTIONS.add("shoping-cart.jsp");
        USER_FUNCTIONS.add("checkout.jsp");
        USER_FUNCTIONS.add("home");
        
        ADMIN_FUNCTIONS = new ArrayList<>();
        ADMIN_FUNCTIONS.add("admin.jsp");
        ADMIN_FUNCTIONS.add("create.jsp");

        NON_AUTHEN_FUNCTIONS = new ArrayList<>();
        NON_AUTHEN_FUNCTIONS.add("login.jsp");
        NON_AUTHEN_FUNCTIONS.add("login-google");
        NON_AUTHEN_FUNCTIONS.add("MainController");
        NON_AUTHEN_FUNCTIONS.add("LoginController");
        NON_AUTHEN_FUNCTIONS.add(".jpg");
        NON_AUTHEN_FUNCTIONS.add(".png");
        NON_AUTHEN_FUNCTIONS.add(".gif");
        NON_AUTHEN_FUNCTIONS.add(".css");
        NON_AUTHEN_FUNCTIONS.add("bootstrap.min.js");
        NON_AUTHEN_FUNCTIONS.add("slicknav.js");
        NON_AUTHEN_FUNCTIONS.add("main.js");
        NON_AUTHEN_FUNCTIONS.add(".woff");
        NON_AUTHEN_FUNCTIONS.add(".woff2");
        NON_AUTHEN_FUNCTIONS.add(".ttf");
        NON_AUTHEN_FUNCTIONS.add("testing");
        NON_AUTHEN_FUNCTIONS.add("jquery-3.3.1.min.js");
    }    
    
    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("AuthenFilter:DoBeforeProcessing");
        }

        // Write code here to process the request and/or response before
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log items on the request object,
        // such as the parameters.
        /*
	for (Enumeration en = request.getParameterNames(); en.hasMoreElements(); ) {
	    String name = (String)en.nextElement();
	    String values[] = request.getParameterValues(name);
	    int n = values.length;
	    StringBuffer buf = new StringBuffer();
	    buf.append(name);
	    buf.append("=");
	    for(int i=0; i < n; i++) {
	        buf.append(values[i]);
	        if (i < n-1)
	            buf.append(",");
	    }
	    log(buf.toString());
	}
         */
    }    
    
    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("AuthenFilter:DoAfterProcessing");
        }

        // Write code here to process the request and/or response after
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log the attributes on the
        // request object after the request has been processed. 
        /*
	for (Enumeration en = request.getAttributeNames(); en.hasMoreElements(); ) {
	    String name = (String)en.nextElement();
	    Object value = request.getAttribute(name);
	    log("attribute: " + name + "=" + value.toString());

	}
         */
        // For example, a filter might append something to the response.
        /*
	PrintWriter respOut = new PrintWriter(response.getWriter());
	respOut.println("<P><B>This has been appended by an intrusive filter.</B>");
         */
    }

 
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        try {
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse res = (HttpServletResponse) response;
            String uri = req.getRequestURI();
            for (String value : NON_AUTHEN_FUNCTIONS) {
                if(uri.contains(value)) {
                    chain.doFilter(request, response);
                    return;
                }
            }
            int index = uri.lastIndexOf("/");
            String resource = uri.substring(index + 1);
            HttpSession session = req.getSession();
            // authen user
            UserDTO user = (UserDTO) session.getAttribute("LOGIN_USER");
            if(session == null || user == null) {
                res.sendRedirect(LOGIN_PAGE);
            }else {
                String roleID = user.getRoleID();
                //phan quyen role, truy cap nguon tai nguyen dc cho phep
                if(ROLE_AD.equals(roleID) && ADMIN_FUNCTIONS.contains(resource)) {
                    chain.doFilter(request, response);
                    //user dangw nhap voi quyen US
                    //vaf truy cap tai nguyen dc phep
                }else if(ROLE_US.equals(roleID) && USER_FUNCTIONS.contains(resource)) {
                    chain.doFilter(request, response);
                }else {
                    res.sendRedirect(LOGIN_PAGE);
                }
            }
        } catch (Exception e) {
        }
    }

    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {        
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {        
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {                
                log("AuthenFilter:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("AuthenFilter()");
        }
        StringBuffer sb = new StringBuffer("AuthenFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }
    
    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);        
        
        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);                
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                // PENDING! Localize this for next official release
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");                
                pw.print(stackTrace);                
                pw.print("</pre></body>\n</html>"); //NOI18N
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        }
    }
    
    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }
    
    public void log(String msg) {
        filterConfig.getServletContext().log(msg);        
    }
    
}
