/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author paolo
 */
public class Post {

    private final int id;
    private final String text;
    private final Date date;
    private final User creator;
    private final Group group;

    Post(int id, String text, Date date, User creator, HashMap<String, GroupFile> groupFiles, Group group) {
        text = text.replaceAll("<", "&lt;");
        text = text.replaceAll(">", "&gt;");
        String pattern = "\\$\\$(.+?)\\$\\$";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(text);
        String filesPath = "/static/files/" + group.getId() + "/";
        while (m.find()) {
            String g = m.group(1);
            String rep = null;
            if (groupFiles.get(g) != null) {
                try {
                    rep = "<a target=\"_blank\" href=\"" + filesPath + URLEncoder.encode(g, "UTF-8") + "\">" + g + "</a>";
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(Post.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                if (g.startsWith("http://") || g.startsWith("https://") || g.startsWith("ftp://") || g.startsWith("ftp://") || g.startsWith("ftps://")) {
                    rep = "<a target=\"_blank\" href=\"" + g + "\">" + g + "</a>";
                } else {
                    rep = "<a target=\"_blank\" href=\"http://" + g + "\">" + g + "</a>";
                }
            }
            text = text.replaceFirst(pattern, rep);
        }

        this.id = id;
        this.text = text;
        this.date = date;
        this.creator = creator;
        this.group = group;
    }

    public int getId() {
        return this.id;
    }

    public String getText() {
        return text;
    }

    public Date getDate() {
        return date;
    }

    public User getCreator() {
        return creator;
    }

    public Group getGroup() {
        return group;
    }
}
