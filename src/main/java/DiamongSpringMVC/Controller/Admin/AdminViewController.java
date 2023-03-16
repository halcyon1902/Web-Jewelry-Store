package DiamongSpringMVC.Controller.Admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.servlet.ModelAndView;

import DiamongSpringMVC.Entity.User;
import DiamongSpringMVC.MySQL.Repo.UserRepository;

@Controller
@RequestMapping("/admin")
public class AdminViewController {

	private @Autowired UserRepository userRepository;
	/*
	 * private @Autowired CategoryRepository categoryRepository; private @Autowired
	 * ProductRepository productRepository; private @Autowired BillRepository
	 * billRepository; private @Autowired BillDetailRepository billDetailRepository;
	 */

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

		HttpSession session = request.getSession();
		if (session.getAttribute("useradmin") == null) {
			return new ModelAndView("redirect:/admin/login");
		}

		ModelAndView mv = new ModelAndView("admin/index");

		return mv;
	}

	@GetMapping("/logout")
	public ModelAndView logout(HttpServletRequest request) {
		request.getSession().removeAttribute("useradmin");
		return new ModelAndView("/admin/login");
	}

}
