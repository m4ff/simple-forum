/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import db.DBManager;
import db.Group;
import db.User;
import db.Post;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.LinkedList;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author paolo
 */
public class Home extends HttpServlet {

    private static final String CONTENT_HTML
            = "<ul data-role=\"listview\" data-inset=\"true\">"
            + "<li><a href=\"forum/groups\">My groups</a></li>"
            + "<li><a href=\"forum/create\">Create group</a></li>"
            + "<li><a href=\"forum/invites\">Invites</a></li>"
            + "<li><a href=\"forum/avatar\">Avatar Upload</a></li>"
            + "<li><a href=\"/logout\" data-ajax=\"false\">Logout</a></li>"
            + "</ul>";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        User user = (User) request.getSession().getAttribute("user");
        Cookie[] cookies = request.getCookies();
        Date loginTime = null;
        DBManager dbmanager = (DBManager) request.getServletContext().getAttribute("dbmanager");
        for (Cookie cookie : cookies) {
            if ("loginTime".equals(cookie.getName())) {
                loginTime = new Date(Long.parseLong(cookie.getValue()));
                break;
            }
        }
        if (loginTime == null) {
            loginTime = new Date();
        }
        String welcomeMessage
                = "<ul data-role=\"listview\" data-inset=\"true\">"
                + "<li><h1>"
                + "Welcome " + user.getName()
                + "</h1>";
        if (loginTime != null) {
            welcomeMessage
                    += "<p>"
                    + "You logged in " + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(loginTime)
                    + "</p>";
        }
        welcomeMessage += "</li></ul>";

        Date lastDate = (Date) request.getAttribute("lastTimeLogged");
        String upToDate = "";
        if (lastDate != null) {
            upToDate = "<ul data-role=\"listview\" data-inset=\"true\">\n";

            LinkedList<Post> lastPosts = dbmanager.getPostsFromDate(lastDate, user);
            Iterator<Post> i = lastPosts.iterator();
            while (i.hasNext()) {
                Post considering = i.next();
                upToDate += "<li><h2>" + considering.getGroup().getName() + "</h2>"
                        + "<p>" + considering.getText() + "</p></li>\n";
            }
            upToDate += "</ul>";
        }
        LinkedList<Group> invites = dbmanager.getInvites(user);
        String newInv = null;
        if (invites.size() > 0) {
            newInv = "<div data-role=\"ui-body ui-body-d ui-corner-all\" data-inset=\"true\">You have " + invites.size() + " new invite/s</div>\n";
        }

        if (newInv != null) {
            HTML.printPage(out, "Forum home", welcomeMessage + CONTENT_HTML + newInv + upToDate);
        } else {
            HTML.printPage(out, "Forum home", welcomeMessage + CONTENT_HTML + upToDate);
        }
    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Home page";
    }// </editor-fold>

}
