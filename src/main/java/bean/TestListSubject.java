package bean;

import java.io.Serializable;

public class TestListSubject implements Serializable {
    private int entYear;
    private String studentNo;
    private String studentName;
    private String classNum;
    private int point;          // DBからの一時保持用
    private int point1 = -1;    // 1回目の点数
    private int point2 = -1;    // 2回目の点数
    private int no;             // テスト回数保持用

    public TestListSubject() {}

    // ゲッター・セッター
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
    public int getPoint1() { return point1; }
    public void setPoint1(int point1) { this.point1 = point1; }
    public int getPoint2() { return point2; }
    public void setPoint2(int point2) { this.point2 = point2; }
    public int getNo() { return no; }
    public void setNo(int no) { this.no = no; }
}