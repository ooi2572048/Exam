package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;
import bean.TestListSubject;

public class TestListSubjectDao extends Dao {

    /**
     * ResultSetからBeanのリストへの変換（科目検索用）
     */
    private List<TestListSubject> postFilter(ResultSet res) throws SQLException {
        List<TestListSubject> list = new ArrayList<>();
        while (res.next()) {
            TestListSubject ts = new TestListSubject();
            ts.setEntYear(res.getInt("ENT_YEAR"));
            ts.setStudentNo(res.getString("STUDENT_NO")); 
            ts.setStudentName(res.getString("STUDENT_NAME")); 
            ts.setClassNum(res.getString("CLASS_NUM"));
            
            // 点数の処理：NULLの場合は-1をセット
            int point = res.getInt("POINT");
            if (res.wasNull()) {
                ts.setPoint(-1);
            } else {
                ts.setPoint(point);
            }
            
            ts.setNo(res.getInt("NO")); // 回数(1または2)
            list.add(ts);
        }
        return list;
    }

    /**
     * 科目情報を条件に成績一覧を取得する（横並び表示用）
     */
    public List<TestListSubject> filter(School school, int entYear, String classNum, Subject subject) throws Exception {
        List<TestListSubject> list = new ArrayList<>();
        
        // STUDENTをベースにTESTをLEFT JOINすることで、点数がない学生も表示対象にする
        String sql = "SELECT STUDENT.ENT_YEAR, STUDENT.STUDENT_NO, STUDENT.STUDENT_NAME, STUDENT.CLASS_NUM, TEST.POINT, TEST.NO " +
                     "FROM STUDENT " +
                     "LEFT JOIN TEST ON STUDENT.STUDENT_NO = TEST.STUDENT_NO " +
                     "  AND STUDENT.SCHOOL_CD = TEST.SCHOOL_CD " +
                     "  AND TEST.SUBJECT_CD = ? " +
                     "WHERE STUDENT.SCHOOL_CD = ? AND STUDENT.ENT_YEAR = ? AND STUDENT.CLASS_NUM = ? " +
                     "ORDER BY STUDENT.STUDENT_NO ASC, TEST.NO ASC";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, subject.getCd());
            statement.setString(2, school.getSchoolCd());
            statement.setInt(3, entYear);
            statement.setString(4, classNum);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                list = postFilter(resultSet);
            }
        }
        return list;
    }
}