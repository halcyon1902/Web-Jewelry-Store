package DiamongSpringMVC.Controller.Admin;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.webstore.app.entity.Bill;
import com.webstore.app.entity.BillDetail;
import com.webstore.app.entity.Category;
import com.webstore.app.entity.Product;
import com.webstore.app.entity.User;
import com.webstore.app.model.CustomUrl;
import com.webstore.app.model.Message;
import com.webstore.app.model.ProductDto;
import com.webstore.app.model.Value;
import com.webstore.app.mysql.repo.BillDetailRepository;
import com.webstore.app.mysql.repo.BillRepository;
import com.webstore.app.mysql.repo.CategoryRepository;
import com.webstore.app.mysql.repo.ProductRepository;
import com.webstore.app.mysql.repo.UserRepository;

@Controller
@RequestMapping("/admin")
public class AdminViewController {

  private @Autowired UserRepository userRepository;
  private @Autowired CategoryRepository categoryRepository;
  private @Autowired ProductRepository productRepository;
  private @Autowired BillRepository billRepository;
  private @Autowired BillDetailRepository billDetailRepository;

  @GetMapping("/login")
  public ModelAndView login() {
    ModelAndView mv = new ModelAndView("/admin/login");
    return mv;
  }

  @PostMapping("/login")
  public String submitLogin(@RequestParam String email, @RequestParam String password, HttpServletRequest request) {
    String url = "";
    User user = userRepository.findAdmin(email, password);
    if (user != null) {
      request.getSession().setAttribute("useradmin", user);
      url = "admin/home";
    } else {
      request.getSession().setAttribute("error", "Email hoặc mật khẩu không đúng.!");
      url = "admin/login";
    }
    return "redirect:/" + url;
  }

  @RequestMapping("/home")
  public ModelAndView home(HttpServletRequest request) {

    HttpSession session  = request.getSession();
    if (session.getAttribute("useradmin") == null) {
      return new ModelAndView("redirect:/admin/login");
    }

    ModelAndView mv = new ModelAndView("admin/index");

    return mv;
  }
}
