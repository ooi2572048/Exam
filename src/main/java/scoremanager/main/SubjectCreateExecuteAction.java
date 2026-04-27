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

public class SubjectCreateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // 1. パラメータの取得
        String cd = req.getParameter("cd");
        String name = req.getParameter("name");

        // 2. セッションからログインしている教員情報を取得
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // ※エラーハンドリング（未ログイン状態のチェック）
        if (teacher == null) {
            // 未ログインの場合はログイン画面へリダイレクト等の処理
            res.sendRedirect("../../login.jsp");
            return;
        }

        // 3. バリデーションチェック (文字数等のサーバー側チェック)
        List<String> errors = new ArrayList<>();
        if (cd == null || cd.length() != 3) {
            errors.add("科目コードは3文字で入力してください。");
        }
        if (name == null || name.isEmpty() || name.length() > 20) {
            errors.add("科目名は1文字以上20文字以下で入力してください。");
        }

        if (!errors.isEmpty()) {
            // エラーがある場合は入力値を保持したまま元の画面へ戻す
            req.setAttribute("errors", errors);
            req.setAttribute("cd", cd);
            req.setAttribute("name", name);
            req.getRequestDispatcher("subject_create.jsp").forward(req, res);
            return;
        }

        // 4. Beanの生成とデータセット
        Subject subject = new Subject();
        subject.setCd(cd);
        subject.setName(name);
        subject.setSchool(teacher.getSchool()); // 教員が所属する学校をセット

        // 5. DAOを呼び出してデータベースへ保存
        SubjectDao dao = new SubjectDao();
        
        // ※ 既に同じ科目コードが存在しないかチェックする処理を入れるのが一般的です
        Subject existingSubject = dao.get(cd, teacher.getSchool());
        if (existingSubject != null) {
            errors.add("入力された科目コードは既に登録されています。");
            req.setAttribute("errors", errors);
            req.setAttribute("cd", cd);
            req.setAttribute("name", name);
            req.getRequestDispatcher("subject_create.jsp").forward(req, res);
            return;
        }

        boolean result = dao.save(subject);

        // 6. 結果に応じた画面遷移
        if (result) {
            // 登録成功時は完了画面または一覧画面へリダイレクト
            // ※ここでは完了画面へフォワードする想定
            req.getRequestDispatcher("subject_create_done.jsp").forward(req, res);
        } else {
            // 登録失敗時
            errors.add("登録処理中にエラーが発生しました。");
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("subject_create.jsp").forward(req, res);
        }
    }
}