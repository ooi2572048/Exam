package scoremanager.main;

import bean.Student;
import bean.Teacher;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class StudentUpdateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res)
            throws Exception {

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // パラメータ取得
        String studentNo = req.getParameter("studentNo");
        String studentName = req.getParameter("studentName");
        String classNum = req.getParameter("classNum");
		String entYearStr = req.getParameter("entYear");
		int entYear = Integer.parseInt(entYearStr);

        boolean isAttend = req.getParameter("isAttend") != null;

        // Student に詰める
        Student student = new Student();
        student.setStudentNo(studentNo);
        student.setStudentName(studentName);
        student.setEntYear(entYear);
        student.setClassNum(classNum);
        student.setAttend(isAttend);
        student.setSchool(teacher.getSchool());

        // 更新
        StudentDao dao = new StudentDao();
        dao.save(student);

        // 完了画面へ
        req.setAttribute("student", student);
        req.getRequestDispatcher("student_update_done.jsp")
           .forward(req, res);
    }
}