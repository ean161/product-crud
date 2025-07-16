<%@page import="utils.Lib"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:include page="components/header.jsp"/>
<div class="container d-flex justify-content-center align-items-center" style="min-height: 80vh;">
    <div class="w-100" style="max-width: 400px;">
        <h1 class="text-center mb-4">Login</h1>
        <%
            String message = Lib.getDOMMessage(request, response);
            if (message != null)
                out.print(message);
        %>
        <form action="login" method="POST">
            <div class="mb-3">
                <label for="username" class="form-label">Username</label>
                <input type="text" class="form-control" id="u_id" name="username" placeholder="Enter username" />
            </div>
            <div class="mb-3">
                <label for="password" class="form-label">Password</label>
                <input type="password" class="form-control" id="u_pwd" name="password" placeholder="Enter your password" />
            </div>
            <div class="d-grid col-md-3">
                <button type="submit" class="btn btn-primary">
                    <i class="bi bi-person-circle"></i> Login
                </button>
            </div>
        </form>
    </div>
</div>
<jsp:include page="components/footer.jsp"/>

