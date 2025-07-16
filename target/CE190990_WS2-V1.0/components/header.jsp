<%@page import="utils.Lib"%>
<%
    String userID = Lib.getCookie(request, "userID");
    String userName = Lib.getCookie(request, "userName");
    
    
%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Product Page</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <style>
            a {
                color: gray;
                text-decoration: none;
            }
        </style>
    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <div class="container">
                <a class="navbar-brand" href="#">Products</a>
                <div class="collapse navbar-collapse justify-content-end">
                    <ul class="navbar-nav">
                        <li class="nav-item">
                            <a class="nav-link" href="product">Products</a>
                        </li>
                        <li class="nav-item">
                            <%
                                if (userID != null) {
                            %>
                            <span class="nav-link">Hi, <% out.print(userName); %>, <a href="logout">logout</a></span>
                            <%
                                } else {
                            %>
                            <a class="nav-link" href="login">Login</a>
                            <%
                                }
                            %>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>