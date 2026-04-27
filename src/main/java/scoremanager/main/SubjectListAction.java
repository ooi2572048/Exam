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
        // 1. セッションからログインしている教員情報を取得
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 未ログイン時の対策（セッション切れなど）
        if (teacher == null) {
            res.sendRedirect("../../login.jsp"); // 環境に合わせてパスは変更してください
            return;
        }

        // 2. DAOを使って、ログインユーザーの学校に紐づく科目一覧を取得
        SubjectDao dao = new SubjectDao();
        List<Subject> subjects = dao.filter(teacher.getSchool());

        // 3. 取得したリストをリクエストスコープにセット（JSPへ渡す準備）
        req.setAttribute("subjects", subjects);

        // 4. 科目一覧画面 (JSP) へフォワード
        req.getRequestDispatcher("subject_list.jsp").forward(req, res);
    }
}