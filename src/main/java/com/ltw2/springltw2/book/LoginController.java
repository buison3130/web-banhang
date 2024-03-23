package com.ltw2.springltw2.book;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {
    @PostMapping("/checklogin")
    public String addBook(User user, Model modal, HttpServletRequest req) {
        Connection connection = null;
        PreparedStatement ps = null;
        int result = 0;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sinhvien", "root", "sonchinh3031");

            String sql = "Select * from sinhvien.user a " //
                    + " where a.user = ? and a.password= ?";

            ps = connection.prepareStatement(sql);
            ps.setString(1, user.getUser());
            ps.setString(2, user.getPassword());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                HttpSession session = req.getSession();
                Integer role = rs.getInt("role");
                session.setAttribute("user", user.getUser());
                if(role == 1)          
                    return "redirect:/home";
                if(role == 2)
                    return "redirect:/books";
                
            } else {
                modal.addAttribute("err", "Your username or password is incorrect!");
                return "/login";
            }
            
            
        } // End of try block
        catch (Exception e) {
            e.printStackTrace();
        }
        return "error"; // tạo trang Error
    }

    @GetMapping("/login")
    public String dddd(User user) {

        try {

            return "/login";
        } // End of try block
        catch (Exception e) {
            e.printStackTrace();
        }
        return "error"; // tạo trang Error
    }
    
    @GetMapping("/logout")
    public String logout(HttpServletRequest req) {

        try {
            HttpSession session = req.getSession();         
            session.removeAttribute("user");
            return "redirect:/books";
        } // End of try block
        catch (Exception e) {
            e.printStackTrace();
        }
        return "error"; // tạo trang Error
    }
    
   
    
}
