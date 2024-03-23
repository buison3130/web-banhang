package com.ltw2.springltw2.book;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegiterController {
    @PostMapping("/registered")
    public String addUser(User u, Model modal) {
        Connection connection = null;
        PreparedStatement ps = null;
        int result = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sinhvien", "root", "sonchinh3031");
            
            String sql = "Select * from sinhvien.user a " + " where a.user = ?";
            ps = connection.prepareStatement(sql);
            ps.setString(1, u.getUser());
            ResultSet rs = ps.executeQuery();
    
            if (rs.next()) {
               modal.addAttribute("erro", "This username already in use!");
                return "/register";
            }

            ps = connection.prepareStatement("INSERT INTO sinhvien.user VALUES (?, ?, ?, 1)");
            
            ps.setString(1, u.getUser());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getPassword());
           
            result = ps.executeUpdate();

            ps.close();
            connection.close();

            // Redirect the response to success bookpage
            return "/login";
        } // End of try block
        catch (Exception e) {
            e.printStackTrace();
        }
        return "error"; // tạo trang Error
    }
    
    @GetMapping("/register")
    public String ff(HttpServletRequest req) {

        try {
          
            return "/register";
        } // End of try block
        catch (Exception e) {
            e.printStackTrace();
        }
        return "error"; // tạo trang Error
    }
    
}
