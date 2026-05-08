<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">
    <c:param name="title">学生情報変更完了</c:param>

    <c:param name="content">
		<div id="wrap_box">
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2">学生情報登録</h2>
			<div id="wrap_box">
				<p class="text-center" style="background-color:#8cc3a9">変更が完了しました</p>

				<br>
				<br>
				<br>
		入学年度：${student.entYear}<br>
        学生番号：${student.studentNo}<br>
        氏名：${student.studentName}<br>
        クラス：${student.classNum}<br>

        <br>
        <a href="StudentList.action">学生一覧へ戻る</a>
    </c:param>
</c:import>