package edu.acc.j2ee.hubbub;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class DbStartupListener implements ServletContextListener {
    private Connection conn = null;
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String jdbcUrl = sce.getServletContext().getInitParameter("jdbc.url");
        String jdbcUser =sce.getServletContext().getInitParameter("jdbc.user");
        String jdbcPass =sce.getServletContext().getInitParameter("jdbc.pass");
        try {
            conn = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPass);
            HubbubDao dao = new HubbubDao(conn);
            sce.getServletContext().setAttribute("dao", dao);
        }
        catch (SQLException sqle) {
            throw new DaoException(sqle);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // get all the daos out of context scope
        try { conn.close(); }
        catch (SQLException sqle) {}
    }
}
