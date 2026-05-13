package scoremanager.main;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class TestDeleteAction extends Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        String studentNo = req.getParameter("studentNo");
        String subjectCd = req.getParameter("subjectCd");
        String num = req.getParameter("num");

        req.setAttribute("studentNo", studentNo);
        req.setAttribute("subjectCd", subjectCd);
        req.setAttribute("num", num);

        req.getRequestDispatcher("test_delete.jsp").forward(req, res);
    }
}
