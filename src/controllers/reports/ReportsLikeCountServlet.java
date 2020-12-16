package controllers.reports;

import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Likes;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class ReportsLikeCountServlet
 */
@WebServlet("/reports/like_count")
public class ReportsLikeCountServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportsLikeCountServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        Report r = em.find(Report.class, Integer.parseInt(request.getParameter("id")));
        Likes l  = new Likes();

        l.setEmployee((Employee)request.getSession().getAttribute("login_employee"));

        l.setReport(r); // レポートIDをセット

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        l.setCreated_at(currentTime);
        l.setUpdated_at(currentTime);

        r.setLike_count(r.getLike_count()+1); //いいね！数を１加算する

        em.getTransaction().begin();
        em.persist(l);
        em.getTransaction().commit();
        em.close();

        Employee login_employee = (Employee)request.getSession().getAttribute("login_employee");
        if(r != null && login_employee.getId() == r.getEmployee().getId()) {
            request.setAttribute("report", r);
            request.setAttribute("_token", request.getSession().getId());
            request.getSession().setAttribute("report_id", r.getId());
        }

        request.getSession().setAttribute("flush", "いいね！しました。");
        request.getSession().removeAttribute("report_id");
        response.sendRedirect(request.getContextPath() + "/reports/index");

    }


}
