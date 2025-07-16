package controller;

import dao.CategoryDAO;
import dao.ProductDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.Lib;

/**
 *
 * @author CE190990 - Nguyen Hoai An
 */
@WebServlet(name="ProductServlet", urlPatterns={"/product"})
public class ProductServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!Lib.isLogged(request)) {
            response.sendRedirect("login");
            return;
        }
        
        ProductDAO prodDAO = new ProductDAO();
        CategoryDAO catDAO = new CategoryDAO();
        
        String view = request.getParameter("view");
        String id = request.getParameter("id");
        if (view != null) {
            request.setAttribute("action", view);
            request.setAttribute("cat_list", catDAO.getAll());
            
            if (id != null)
                request.setAttribute("product", prodDAO.get(Integer.parseInt(id)));
            request.getRequestDispatcher("product_actions.jsp").forward(request, response);
            return;
        }
        
        request.setAttribute("list", prodDAO.getAll());
        request.getRequestDispatcher("product.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        
        boolean isSuccess = false;
        String action = request.getParameter("action");
        
        if (action == null)
            response.sendRedirect("product");
        
        String id = request.getParameter("id"),
            name = request.getParameter("name"),
            price = request.getParameter("price"),
            quantity = request.getParameter("quantity"),
            description = request.getParameter("description"),
            cat = request.getParameter("cat");
        String rootReq = String.format("product?view=%s&id=%s", action, id);
        
        if (!action.equals("delete"))
            if (!validateInputs(response, name, price, quantity, description, cat)) {
                response.sendRedirect(rootReq);
                return;
            }
        
        Lib.createCookieMessage(response, "1", String.format("%s%s successfully", action, (id != null ? " product ID " + id : "")));
        
        ProductDAO dao = new ProductDAO();
        boolean isValidProd = (!action.equals("create") ? dao.isValid(Integer.parseInt(id)) : true);
        
        if (isValidProd)
            try {
                switch (action) {
                    case "create":
                        dao.create(name, Long.parseLong(price), Integer.parseInt(quantity), description, Integer.parseInt(cat));
                        break;
                    case "update":
                        dao.update(Integer.parseInt(id), name, Long.parseLong(price), Integer.parseInt(quantity), description, Integer.parseInt(cat));
                        break;
                    case "delete":
                        dao.delete(Integer.parseInt(id));
                        break;
                    default:
                        break;
                }
                
                isSuccess = true;
            } catch (Exception ex) {
                isSuccess = false;
                Lib.createCookieMessage(response, "0", "Please input correct data type with fields");
            }
        else
            Lib.createCookieMessage(response, "0", "Product ID not found in database");
        
        if (isSuccess) 
            response.sendRedirect("product");
        else
            response.sendRedirect(rootReq);
    }
    
    public boolean validateInputs(HttpServletResponse response, String name, String price, String quantity, String description, String cat) {
        boolean isValid = false;
        if (name == null || name.isEmpty())
            Lib.createCookieMessage(response, "0", "name is required");
        else if (price == null || price.isEmpty())
            Lib.createCookieMessage(response, "0", "price is required");
        else if (quantity == null || quantity.isEmpty())
            Lib.createCookieMessage(response, "0", "quantity is required");
        else if (description == null || description.isEmpty())
            Lib.createCookieMessage(response, "0", "description is required");
        else if (cat == null || cat.isEmpty())
            Lib.createCookieMessage(response, "0", "cat is required");
        else {
            try {
                int priceNum = Integer.parseInt(price);
                if (priceNum < 0) {
                    Lib.createCookieMessage(response, "0", "price must be a number and greater or equals than 0");
                    return false;
                }
            } catch (Exception ex) {
                Lib.createCookieMessage(response, "0", "price must be a number and greater or equals than 0");
                return false;
            }
            
            try {
                int quantityNum = Integer.parseInt(quantity);
                if (quantityNum < 0) {
                    Lib.createCookieMessage(response, "0", "quantity must be a number and greater or equals than 0");
                    return false;
                }
            } catch (Exception ex) {
                Lib.createCookieMessage(response, "0", "quantity must be a number and greater or equals than 0");
                return false;
            }
            
            return true;
        }
        
        return false;
    }
}
