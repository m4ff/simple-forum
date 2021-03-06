/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servlet;

import java.io.PrintWriter;

/**
 *
 * @author paolo
 */
public class HTML {
    
    private static final String PRE_TITLE = 
            "<!doctype html>"
            + "<html>"
            + "<head>"
            + "<title>";
    private static final String POST_TITLE =
            "</title>"
            + "<meta charset=\"UTF-8\">"
            + "<meta name=\"viewport\" content=\"width=device-width\">"
            + "<link rel=\"stylesheet\" href=\"/static/jquery.mobile-1.3.2.min.css\">"
            + "<script type=\"text/javascript\" src=\"/static/jquery-1.9.1.min.js\"></script>"
            + "<script type=\"text/javascript\" src=\"/static/jquery.mobile-1.3.2.min.js\"></script>"
            + "<script type=\"text/javascript\" src=\"/static/utils.js\"></script>"
            + "</head>"
            + "<body>"
            + "<div data-role=\"page\">"
            + "<div data-role=\"header\">";
    private static final String POST_HEADER =
            "</div>"
            + "<div data-role=\"content\">";
    private static final String BOTTOM =
            "</div>"
            + "</div>"
            + "</body>"
            + "</html>";
    
    public static void printPage(PrintWriter out, String title, String content) {
        out.print(PRE_TITLE);
        out.print(title);
        out.print(POST_TITLE);
        out.print("<h1>");
        out.print(title);
        out.print("</h1>");
        out.print(POST_HEADER);
        out.print(content);
        out.print(BOTTOM);
    }
    
    public static void printPage(PrintWriter out, String title, String backUrl, String content) {
        out.print(PRE_TITLE);
        out.print(title);
        out.print(POST_TITLE);
        out.print("<a data-icon=\"back\" href=\""); out.print(backUrl); out.print("\">Back</a>"); 
        out.print("<h1>");
        out.print(title);
        out.print("</h1>");
        out.print(POST_HEADER);
        out.print(content);
        out.print(BOTTOM);
    }
    
    public static void print404(PrintWriter out) {
        printPage(out, "404", "<h2>Error 404: page not found</h2>");
    } 
    
}
