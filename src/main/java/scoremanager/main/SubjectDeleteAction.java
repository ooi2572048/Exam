package scoremanager.main;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectDeleteAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        String cd = request.getParameter("cd");

        if (cd != null && teacher != null) {
            SubjectDao dao = new SubjectDao();
            Subject subject = dao.get(cd, teacher.getSchool());

            // JSPに渡す
            request.setAttribute("subject", subject);
        }

        // 確認画面へ
        request.getRequestDispatcher("subject_delete.jsp")
               .forward(request, response);
    }
}