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

public class SubjectUpdateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // 1. パラメータ取得
        String cd = req.getParameter("cd");
        String name = req.getParameter("name");

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 2. バリデーションチェック
        List<String> errors = new ArrayList<>();
        if (name == null || name.isEmpty()) {
            errors.add("科目名を入力してください");
        } else if (name.length() > 20) {
            errors.add("科目名は20文字以内で入力してください");
        }

        // 3. エラーがある場合は入力画面に戻す
        if (!errors.isEmpty()) {
            Subject subject = new Subject();
            subject.setCd(cd);
            subject.setName(name);
            req.setAttribute("errors", errors);
            req.setAttribute("subject", subject);
            req.getRequestDispatcher("subject_update.jsp").forward(req, res);
            return;
        }

        // 4. 更新処理
        Subject subject = new Subject();
        subject.setCd(cd);
        subject.setName(name);
        subject.setSchool(teacher.getSchool());

        SubjectDao dao = new SubjectDao();
        boolean result = dao.save(subject); // save内でUPDATEが走る想定

        if (result) {
            // 完了画面へ
            req.getRequestDispatcher("subject_update_done.jsp").forward(req, res);
        } else {
            errors.add("更新に失敗しました");
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("subject_update.jsp").forward(req, res);
        }
    }
}