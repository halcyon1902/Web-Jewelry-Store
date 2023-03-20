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
	  @GetMapping("/manager_category")
  public ModelAndView get() {
    ModelAndView mv = new ModelAndView("admin/manager_category");
    mv.addObject("cates", categoryRepository.findAll());
    return mv;
  }

  @GetMapping("/manager_category/delete/{id}")
  public String get(@PathVariable Long id) {
    categoryRepository.deleteById(id);
    return "redirect:/admin/manager_category";
  }

  @GetMapping("/update_category/{id}/{name}")
  public ModelAndView get(@PathVariable Long id, @PathVariable String name) {
    ModelAndView mv = new ModelAndView("admin/update_category");
    mv.addObject("cate", categoryRepository.findByIdAndName(id, name));
    return mv;
  }

  @GetMapping("/category/add")
  public ModelAndView cateegoryAdd() {
    return new ModelAndView("admin/insert_category");
  }


  @PostMapping("/manager_category")
  public String post(@RequestParam String command, @RequestParam(required = false) Long id, @RequestParam String cateName, Model m) {

    String url = "", error = "";
    if (StringUtils.isEmpty(cateName)) {
      error = "Vui lòng nhập tên danh mục!";
      m.addAttribute("error", error);
    }

    try {
      if (error.length() == 0) {
        switch (command) {
        case "insert":
          categoryRepository.save(new Category(cateName));
          url = "/admin/manager_category";
          break;
        case "update":
          categoryRepository.save(new Category(id, cateName));
          url = "/admin/manager_category";
          break;
        }
      } else {
        url = "/admin/insert_category";
      }
    } catch (Exception e) {
      System.out.println(e);
    }
    return "redirect:"+url;
  }

}
