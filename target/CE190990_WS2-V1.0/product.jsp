<%@page import="utils.Lib"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
if (!Lib.isLogged(request)) {
    response.sendRedirect("login");
    return;
}
%>
<jsp:include page="components/header.jsp"/>
<div class="container">
    <h1 class="mb-2 mt-4">Products list</h1>
    <%
        String message = Lib.getDOMMessage(request, response);
        if (message != null)
            out.print(message);
    %>
    <div class="d-flex flex-row-reverse mb-1">
        <a class="btn bg-success text-white me-2" href="product?view=create">Create</a>
    </div>
    <table class="table table-striped">
        <tr>
            <th>ID</th>
            <th>Product Name</th>
            <th>Product Price</th>
            <th>Product Quantity</th>
            <th>Product Description</th>
            <th>Category Name</th>
            <th>Action</th>
        </tr>
        <c:forEach var="item" items="${list}">
            <tr>
                <td>${item.getId()}</td>
                <td>${item.getName()}</td>
                <td>${item.getPrice()}</td>
                <td>${item.getQuantity()}</td>
                <td>${item.getDesc()}</td>
                <td>${item.getCat().getName()}</td>
                <td>
                    <a href="product?view=update&id=${item.getId()}" class="btn bg-primary text-white me-2">Edit</a>
                    <a href="product?view=delete&id=${item.getId()}" class="btn bg-danger text-white">Delete</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
<jsp:include page="components/footer.jsp"/>
