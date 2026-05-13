<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:import url="/common/base.jsp">
    <c:param name="title">科目削除確認</c:param>

    <c:param name="content">
        <section>

<h3>削除確認</h3>

<p>この成績を削除しますか？</p>

<ul>
    <li>学生番号: ${studentNo}</li>
    <li>科目コード: ${subjectCd}</li>
    <li>回数: ${num}</li>
</ul>

<form method="post" action="TestDeleteExecute.action">
    <input type="hidden" name="studentNo" value="${studentNo}">
    <input type="hidden" name="subjectCd" value="${subjectCd}">
    <input type="hidden" name="num" value="${num}">

    <button type="submit" class="btn btn-danger">削除する</button>
</form>

<form action="TestListStudentExecute.action">
    <input type="hidden" name="f5" value="${studentNo}">
    <a href="TestList.action">成績一覧へ戻る</a>
</form>
       </section>
    </c:param>
</c:import>