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

    private List<TestListSubject> postFilter(ResultSet res) throws SQLException {
        List<TestListSubject> list = new ArrayList<>();
        while (res.next()) {
            TestListSubject ts = new TestListSubject();
            ts.setEntYear(res.getInt("ENT_YEAR"));
            ts.setStudentNo(res.getString("STUDENT_NO")); 
            ts.setStudentName(res.getString("STUDENT_NAME")); 
            ts.setClassNum(res.getString("CLASS_NUM"));
            ts.setPoint(res.getInt("POINT"));
            ts.setNo(res.getInt("NO")); // 何回目のテストかを取得
            list.add(ts);
        }
        return list;
    }

    public List<TestListSubject> filter(School school, int entYear, String classNum, Subject subject) throws Exception {
        List<TestListSubject> list = new ArrayList<>();
        // TEST.NO を追加
        String sql = "SELECT STUDENT.ENT_YEAR, STUDENT.STUDENT_NO, STUDENT.STUDENT_NAME, STUDENT.CLASS_NUM, TEST.POINT, TEST.NO " +
                     "FROM STUDENT " +
                     "JOIN TEST ON STUDENT.STUDENT_NO = TEST.STUDENT_NO " +
                     "WHERE STUDENT.SCHOOL_CD = ? AND STUDENT.ENT_YEAR = ? AND STUDENT.CLASS_NUM = ? AND TEST.SUBJECT_CD = ? " +
                     "ORDER BY STUDENT.STUDENT_NO ASC, TEST.NO ASC"; // 学生番号順に並べる

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, school.getSchoolCd());
            statement.setInt(2, entYear);
            statement.setString(3, classNum);
            statement.setString(4, subject.getCd());
            try (ResultSet resultSet = statement.executeQuery()) {
                list = postFilter(resultSet);
            }
        }
        return list;
    }

    public List<TestListSubject> filter(String studentNo, School school) throws Exception {
        List<TestListSubject> list = new ArrayList<>();
        String sql = "SELECT STUDENT.ENT_YEAR, STUDENT.STUDENT_NO, STUDENT.STUDENT_NAME, STUDENT.CLASS_NUM, TEST.POINT, TEST.NO " +
                     "FROM STUDENT " +
                     "JOIN TEST ON STUDENT.STUDENT_NO = TEST.STUDENT_NO " +
                     "WHERE STUDENT.STUDENT_NO = ? AND STUDENT.SCHOOL_CD = ? " +
                     "ORDER BY TEST.NO ASC";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, studentNo);
            statement.setString(2, school.getSchoolCd());
            try (ResultSet resultSet = statement.executeQuery()) {
                list = postFilter(resultSet);
            }
        }
        return list;
    }
}