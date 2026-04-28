package scoremanager.main;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectDeleteExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        String cd = request.getParameter("cd");

        if (cd != null && teacher != null) {
            Subject subject = new Subject();
            subject.setCd(cd);
            subject.setSchool(teacher.getSchool());

            SubjectDao dao = new SubjectDao();
            dao.delete(subject);
        }


		request.getRequestDispatcher("subject_delete_done.jsp")
		       .forward(request, response);

    }
}