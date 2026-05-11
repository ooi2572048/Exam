
<%-- 科目一覧JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
 
<c:import url="/common/base.jsp">
<c:param name="title">

        科目管理 | 得点管理システム
</c:param>
 
    <c:param name="content">
<section class="me-4">
<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">科目管理</h2>
 
            <div class="text-end mb-3 px-4">
<a href="SubjectCreate.action">新規登録</a>
</div>
 
            <div class="px-4">
<table class="table table-hover">
<thead>
<tr>
<th>科目コード</th>
<th>科目名</th>
<th></th>
<th></th>
</tr>
</thead>
<tbody>
<c:forEach var="subject" items="${subjects}">

<tr>
    <td>${subject.cd}</td>
    <td>${subject.name}</td>
    <td class="text-end">
        <a href="SubjectUpdate.action?cd=${subject.cd}">変更</a>
    </td>
    <td >
        <a href="SubjectDelete.action?cd=${subject.cd}">削除</a>
    </td>
</tr>


</c:forEach>
</tbody>
</table>
</div>
</section>
</c:param>
</c:import>
 
