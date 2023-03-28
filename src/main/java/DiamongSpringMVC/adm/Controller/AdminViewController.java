package DiamongSpringMVC.Controller.Admin;

import java.io.File;
import java.io.IOException;

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
import DiamongSpringMVC.MySQL.Repo.*;
import DiamongSpringMVC.Entity.*;
import DiamongSpringMVC.Entity.Category;
import DiamongSpringMVC.MySQL.Model.*;
import DiamongSpringMVC.Entity.User;
import DiamongSpringMVC.MySQL.Repo.UserRepository;

@Controller
@RequestMapping("/admin")
public class AdminViewController {

	private @Autowired UserRepository userRepository;
	private @Autowired CategoryRepository categoryRepository;
	private @Autowired ProductRepository productRepository;
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
	public String post(@RequestParam String command, @RequestParam(required = false) Long id,
			@RequestParam String cateName, Model m) {

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
		return "redirect:" + url;
	}

	@GetMapping("/manager_product")
	public ModelAndView product() {
		ModelAndView mv = new ModelAndView("admin/manager_product");
		mv.addObject("prods", productRepository.findAll());
		return mv;
	}

	@GetMapping("/product/add")
	public ModelAndView productAdd() {
		ModelAndView mv = new ModelAndView("admin/insert_product");
		mv.addObject("cates", categoryRepository.findAll());
		return mv;
	}

	@GetMapping("/product/update_product/{id}")
	public ModelAndView productUpdate(@PathVariable Long id) {
		ModelAndView mv = new ModelAndView("admin/update_product");
		Product p = productRepository.findById(id).get();
		mv.addObject("cates", categoryRepository.findAll());
		mv.addObject("product", p);
		mv.addObject("cate", categoryRepository.findById(p.getCategoryId()).get());
		return mv;
	}

	@PostMapping("/product/manager_product")
	public @ResponseBody CustomUrl prodUpdate(@RequestBody ProductDto dto, HttpServletRequest request) {
		String error = "";
		if (!StringUtils.isEmpty(dto.getProdName())) {
			error = "Vui lòng nhập tên sản phẩm!";
			request.setAttribute("error", error);
		}

		Product p = new Product(dto.getCateId(), dto.getProdName(), "/resources/images/" + dto.getImage(),
				dto.getPrice(), dto.getDescrip());
		p.setProductBuy(dto.getBuy());
		p.setProductProvider(dto.getProvider());
		p.setProductQuantity(dto.getQuantity());
		CustomUrl url = new CustomUrl();
		try {
			switch (dto.getCommand()) {
			case "insert":
				productRepository.save(p);
				url.setUrl("/admin/manager_product");
				break;
			case "update":
				p.setProductID(dto.getProdId());
				productRepository.save(p);
				url.setUrl("/admin/manager_product");
				break;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return url;
	}

	@PostMapping("/product/file")
	public @ResponseBody String uploadProductFile(@ModelAttribute MultipartFile hinhanh, HttpServletRequest request) {
		ServletContext servletContext = request.getServletContext();
		String contextPath = servletContext.getRealPath("/");
		String filename = contextPath + "/resources/images/" + hinhanh.getOriginalFilename();

		try {
			hinhanh.transferTo(new File(filename));
		} catch (IllegalStateException e1) {
			System.out.println(e1);
		} catch (IOException e1) {
			System.out.println(e1);
		}

		return hinhanh.getOriginalFilename();
	}

	@GetMapping("/product/delete/{id}")
	public String productDelete(@PathVariable Long id) {
		productRepository.deleteById(id);
		return "redirect:/admin/manager_product";
	}
}
