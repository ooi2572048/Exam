package bean;

import java.io.Serializable;

public class TestListSubject implements Serializable {
    private int entYear;
    private String studentNo;
    private String studentName;
    private String classNum;
    private int point;          // DBからの一時的な保持用
    private int point1 = -1;    // 1回目の点数（初期値-1は未受験を意味する）
    private int point2 = -1;    // 2回目の点数
    private int no;             // テスト回数(1か2)保持用
    private String subjectName;

    public TestListSubject() {}

    // 既存のゲッターセッター
    public int getEntYear() { return entYear; }
    public void setEntYear(int entYear) { this.entYear = entYear; }
    public String getStudentNo() { return studentNo; }
    public void setStudentNo(String studentNo) { this.studentNo = studentNo; }
    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
    public String getClassNum() { return classNum; }
    public void setClassNum(String classNum) { this.classNum = classNum; }
    public int getPoint() { return point; }
    public void setPoint(int point) { this.point = point; }
    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }

    // 追加したゲッターセッター
    public int getPoint1() { return point1; }
    public void setPoint1(int point1) { this.point1 = point1; }
    public int getPoint2() { return point2; }
    public void setPoint2(int point2) { this.point2 = point2; }
    public int getNo() { return no; }
    public void setNo(int no) { this.no = no; }
}