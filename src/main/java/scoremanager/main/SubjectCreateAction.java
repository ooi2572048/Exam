package scoremanager.main;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class SubjectCreateAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // 特別な事前処理がなければ、そのままJSPへフォワードする
        // フォワード先のパスはFrontControllerの仕様に合わせて適宜変更してください
        req.getRequestDispatcher("subject_create.jsp").forward(req, res);
    }
}