
 
 
package scoremanager.main;
 
import java.util.List;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;
 
public class SubjectListAction extends Action {
 
    @Override

    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // 1. セッションからログイン中の教員情報を取得

        HttpSession session = req.getSession();

        Teacher teacher = (Teacher) session.getAttribute("user");
 
        // 未ログインの場合はログイン画面へリダイレクト

        if (teacher == null) {

            res.sendRedirect("../../login.jsp");

            return;

        }
 
        // 2. DAOを使って、その教員の学校に紐づく科目一覧を取得

        SubjectDao dao = new SubjectDao();

        List<Subject> subjects = dao.filter(teacher.getSchool());
 
        // 3. 取得したリストをリクエストスコープに保存（JSPに渡すため）

        req.setAttribute("subjects", subjects);
 
        // 4. JSPへフォワード

        req.getRequestDispatcher("subject_list.jsp").forward(req, res);

    }

}
 