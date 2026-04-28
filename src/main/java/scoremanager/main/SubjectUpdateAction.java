package scoremanager.main;

import java.util.ArrayList;
import java.util.List;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectUpdateAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // 1. パラメータから科目コード（cd）を取得
        String cd = req.getParameter("cd");

        // 2. セッションからログイン中の教員情報を取得
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 3. DAOを使って該当する科目情報を取得
        SubjectDao dao = new SubjectDao();
        // teacher.getSchool() を使って、その学校の科目を特定する
        Subject subject = dao.get(cd, teacher.getSchool());

        // 4. エラーハンドリング（画像1枚目の「科目が存在していません」に対応）
        if (subject == null) {
            List<String> errors = new ArrayList<>();
            errors.add("科目が存在していません");
            req.setAttribute("errors", errors);
        }

        // 5. 取得した科目情報をリクエストスコープにセット（JSPでの表示用）
        req.setAttribute("subject", subject);

        // 6. 編集画面（subject_update.jsp）へフォワード
        req.getRequestDispatcher("subject_update.jsp").forward(req, res);
    }
}