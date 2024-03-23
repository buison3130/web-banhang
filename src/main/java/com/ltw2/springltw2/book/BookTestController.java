package com.ltw2.springltw2.book;

import java.io.IOException;

import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;
import org.springframework.util.xml.DomUtils;
@Controller
public class BookTestController {
	
	@GetMapping("/books")
	
	public String getBooks(Model model, HttpServletRequest req) throws IOException {
		 Connection connection = null;
		 Statement statement = null;
		 ResultSet resultSet = null;
		 List<Book> books = new ArrayList<Book>();
		try {
		    HttpSession session = req.getSession();
		    if(session.getAttribute("user") == null) {
		        return "login";
	        }
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sinhvien","root", "sonchinh3031");
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select*from sinhvien.book");
			
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String title = resultSet.getString("title");
				String price = resultSet.getString("price");
				String author = resultSet.getString("author");
				String describes = resultSet.getString("describes");
				Date date = resultSet.getDate("date");
				int bookpage = resultSet.getInt("bookpage");
				String category = resultSet.getString("category");
				books.add(new Book(id,title, author, price, describes,date,bookpage,category));
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("books",books);
		return "books";
	} 
	
	
	
//	View Detail 
	@GetMapping("/book/{id}")
	public String getBook(Model model, @PathVariable String id) {
		model.addAttribute("id", id);
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet result = null;

		Book book = new Book();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sinhvien", "root", "sonchinh3031");
			ps = connection.prepareStatement("select * from sinhvien.book where id = ?");
			ps.setInt(1, Integer.valueOf(id));
			result = ps.executeQuery();
			while (result.next()) {
				book.setId(result.getInt("id"));
				book.setTitle(result.getString("title"));
				book.setAuthor(result.getString("author"));
				book.setPrice(result.getString("price"));
				book.setDescribes(result.getString("describes"));
				book.setDate(result.getDate("date"));
				book.setBookpage(result.getInt("bookpage"));
				book.setCategory(result.getString("category"));
//				book.setImgSrc(result.getString("imgSrc"));
			}
		} // End of try block
		catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("book", book);
		return "book-detail";
	}
	
	
	
//	Add 
	@PostMapping("/book/save/{id}")
    public String addBook(Book book, @PathVariable String id) {
        Connection connection = null;
        PreparedStatement ps = null;
        int result = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sinhvien", "root", "sonchinh3031");
            ps = connection.prepareStatement("INSERT INTO sinhvien.book VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setInt(1, Integer.valueOf(book.getId()));
            ps.setString(2, book.getTitle());
            ps.setString(3, book.getAuthor());
            ps.setString(4, book.getPrice());
            ps.setString(5, book.getDescribes());
            ps.setDate(6, book.getDate());
            ps.setInt(7, Integer.valueOf(book.getBookpage()));
            ps.setString(8, book.getCategory());
            result = ps.executeUpdate();

            ps.close();
            connection.close();

            // Redirect the response to success bookpage
            return "redirect:/books";
        } // End of try block
        catch (Exception e) {
            e.printStackTrace();
        }
        return "error"; // tạo trang Error
    }

	
//	Update
	@PutMapping("/book/save/{id}")
	public String updateBook(Book book, @PathVariable String id) {
		Connection connection = null;
		PreparedStatement ps = null;
		int result = 0;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sinhvien", "root", "sonchinh3031");
			ps = connection.prepareStatement("UPDATE sinhvien.book SET title=?,author=?,price=?,describes=?,date=?,bookpage=?,category=? WHERE id=?");
			ps.setString(1, book.getTitle());
			ps.setString(2, book.getAuthor());
			ps.setString(3, book.getPrice());
			ps.setString(4, book.getDescribes());
			ps.setDate(5, book.getDate());
			ps.setInt(6, Integer.valueOf(book.getBookpage()));
			ps.setString(7, book.getCategory());
			ps.setInt(8, Integer.valueOf(book.getId()));
			result = ps.executeUpdate();

			ps.close();
			connection.close();

			// Redirect the response to success bookpage
			return "redirect:/books";
		} // End of try block
		catch (Exception e) {
			e.printStackTrace();
		}
		return "error"; // tạo trang Error
	}
	
//delete 	
	@RequestMapping("/book/delete/{id}")
    public String deleteStudent( @PathVariable String id) {
        Connection connection = null;
        PreparedStatement ps = null;
        int result = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sinhvien", "root", "sonchinh3031");
            ps = connection.prepareStatement("delete from sinhvien.book where id=?");
            ps.setInt(1, Integer.valueOf(id));
            result = ps.executeUpdate();

            ps.close();
            connection.close();

            // Redirect the response to success bookpage
            return "redirect:/books";
        } // End of try block
        catch (Exception e) {
            e.printStackTrace();
        }
        return "error"; // tạo trang Error
    }
	
@GetMapping("/home")
    
    public String getBookForAdmins(Model model, HttpServletRequest req) throws IOException {
         Connection connection = null;
         Statement statement = null;
         ResultSet resultSet = null;
         List<Book> books = new ArrayList<Book>();
        try {
            HttpSession session = req.getSession();
            if(session.getAttribute("user") == null) {
                return "login";
            }
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sinhvien","root", "sonchinh3031");
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select*from sinhvien.book");
            
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String price = resultSet.getString("price");
                String describes = resultSet.getString("describes");
                Date date = resultSet.getDate("date");
                int bookpage = resultSet.getInt("bookpage");
                String category = resultSet.getString("category");
                books.add(new Book(id,title, author,price, describes,date,bookpage,category));
            }
            
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("books",books);
        return "home";
    } 
	

@GetMapping("/book-view/{id}")
public String viewBook(Model model, @PathVariable String id) {
    model.addAttribute("id", id);
    Connection connection = null;
    PreparedStatement ps = null;
    ResultSet result = null;

    Book book = new Book();
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sinhvien", "root", "sonchinh3031");
        ps = connection.prepareStatement("select * from sinhvien.book where id = ?");
        ps.setInt(1, Integer.valueOf(id));
        result = ps.executeQuery();
        while (result.next()) {
            book.setId(result.getInt("id"));
            book.setTitle(result.getString("title"));
            book.setAuthor(result.getString("author"));
            book.setPrice(result.getString("price"));
            book.setDescribes(result.getString("describes"));
            book.setDate(result.getDate("date"));
            book.setBookpage(result.getInt("bookpage"));
            book.setCategory(result.getString("category"));

        }
    } // End of try block
    catch (Exception e) {
        e.printStackTrace();
    }
    model.addAttribute("book", book);
    return "book-view";
}



}

