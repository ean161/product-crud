<%@page import="utils.Lib"%>
<%@page import="dao.ProductDAO"%>
<%@page import="model.Product"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    if (!Lib.isLogged(request)) {
        response.sendRedirect("login");
        return;
    }
    
    String action = (String) request.getAttribute("action");
    String acFirstUpper = (action != null ? action.substring(0, 1).toUpperCase() + action.substring(1) : null);
    Product product = (Product) request.getAttribute("product");
    
    ProductDAO dao = new ProductDAO();
    boolean isValidProd = (!action.equals("create") ? dao.isValid(product.getId()) : true);
        
    if (!isValidProd) {
        Lib.createCookieMessage(response, "0", "product ID not found in database");
        response.sendRedirect("product");
    }
%>

<jsp:include page="components/header.jsp"/>
<div class="container">
    <h1 class="mb-2 mt-4">
        <% out.print(action.equals("create") ? "Create new" : acFirstUpper); %>
        product
    </h1>
    <%
        String message = Lib.getDOMMessage(request, response);
        if (message != null)
            out.print(message);
    %>
    <form action="product" method="POST">
        <input type="hidden" value="${action}" name="action"/>
        <c:if test="${action != 'create'}">
            <div class="mb-3">
                <label for="name" class="form-label">Product ID</label>
                <input type="number" class="form-control" name="id-display" value="${product.getId()}" disabled>
            </div>
            <input type="number" class="form-control" name="id" value="${product.getId()}" hidden>
        </c:if>
        <div class="mb-3">
            <label for="name" class="form-label">Product Name</label>
            <input type="text" class="form-control" name="name" value="${product.getName()}" ${action == 'delete' ? 'disabled' : ''}>
        </div>
        <div class="mb-3">
            <label for="price" class="form-label">Product Price</label>
            <input min="0" type="number" class="form-control" name="price" value="${product.getPrice()}" ${action == 'delete' ? 'disabled' : ''}>
        </div>
        <div class="mb-3">
            <label for="quantity" class="form-label">Product Quantity</label>
            <input min="0" type="number" class="form-control" name="quantity" value="${product.getQuantity()}" ${action == 'delete' ? 'disabled' : ''}>
        </div>
        <div class="mb-3">
            <label for="description" class="form-label">Product Description</label>
            <input type="text" class="form-control" name="description" value="${product.getDesc()}" ${action == 'delete' ? 'disabled' : ''}>
        </div>
        <div class="mb-3">
            <label for="cat" class="form-label">Category Name</label>
            <select class="form-control" name="cat" ${action == 'delete' ? 'disabled' : ''}>
                <c:forEach var="item" items="${cat_list}">
                    <option
                        <c:if test="${product.getCat().getId() == item.getId()}">selected</c:if>
                        value="${item.getId()}">
                        ${item.getName()}
                    </option>
                </c:forEach>
            </select>
        </div>
        <a href="product" class="btn bg-secondary text-white me-2">Back</a>
        <button class="btn bg-<% out.print(action.equals("delete") ? "danger" : "primary"); %> text-white" type="submit"><% out.print(acFirstUpper); %></button>
    </form>
</div>
<jsp:include page="components/footer.jsp"/>
