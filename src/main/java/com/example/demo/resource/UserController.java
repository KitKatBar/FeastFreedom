package com.example.demo.resource;


import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@SessionAttributes("user")
public class UserController {

	@Autowired
	private UserService uService;
	
	@Autowired
	private KitchenServiceImpl kService;
	
	@Autowired
	private MenuItemServiceImpl mService; 
	
	@GetMapping("/user_homepage")
	public String userHome(Principal principal, Model model) {
		String email = principal.getName();
		User user = new User();
		List<User> list = uService.findUserList();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getEmail().equals(email)) {
				user = list.get(i);
				model.addAttribute("user", user);
				break;
			}
		}
		
		return "user_homepage";
	}
	
	//@RequestMapping(value = "/save", method = RequestMethod.POST)
	@PostMapping("/save")
	public String saveUser(@ModelAttribute("user") User user, Model model) {
		uService.saveUser(user);
		System.out.println(user);
		return "register_success";
	}
	
	@GetMapping("/user_order_kitchen")
	public String viewKitchens(@ModelAttribute("user") User user, Model model) {
		System.out.println("check u_id order kitchen: " + user.getId());
		List<Kitchen> list = kService.findKitchenList();
		model.addAttribute("kitchens", list);
		//List<MenuItem> listProducts = proservice();
		//model.addAttribute("listProducts", listProducts);
		//List<CartItem> list = userservice.getCart(user);
		//user.setCart(list);
		//model.addAttribute("user", user);
		//User user = userservice.get(1);
		//model.addAttribute("user", user);
		return "user_view_kitchen";
	}
	
	@RequestMapping("/user_order_kitchen/{id}")
	public String selectKitchen(@PathVariable(name = "id") Long id, @ModelAttribute("user") User user,
			Model model, RedirectAttributes redirectAttributes) {
		Kitchen k = kService.findKitchen(id);
		redirectAttributes.addAttribute("kitchen", k);
		System.out.println("check u_id order kitchen: " + user.getId());
		System.out.println("k_id before pass: " + k.getId());
		return "redirect:/user_order_menu";
	}
	
	@GetMapping("/user_order_menu")
	public String viewMenu(@ModelAttribute("kitchen") Kitchen kitchen, @ModelAttribute("user") User user, 
			Model model) {
		System.out.println("k_id after pass: " + kitchen.getId());
		//Kitchen k = (Kitchen) model.getAttribute("kitchen");
		//System.out.println("k_id of model: " + k.getId());
		System.out.println("check u_id order kitchen: " + user.getId());
		List<MenuItem> list = mService.findMenu(kitchen);
		kitchen.setMenu(list);
		model.addAttribute("kitchen", kitchen);
		model.addAttribute("items", list);
		//List<MenuItem> listProducts = proservice();
		//model.addAttribute("listProducts", listProducts);
		//List<CartItem> list = userservice.getCart(user);
		//user.setCart(list);
		//model.addAttribute("user", user);
		//User user = userservice.get(1);
		//model.addAttribute("user", user);
		return "user_view_menu";
	}
	
	@RequestMapping("/confirm")
	public String confirmorder(@ModelAttribute("user") User user, Model model) {
		//User user = userservice.get(1);
		//model.addAttribute("user", user);
		System.out.println(user);
		//List<ResUser> listUsers = service.listAll();
		//model.addAttribute("listUsers", listUsers);
		//List<Product> listProducts = proservice.listAll();
	//	model.addAttribute("listProducts", listProducts);
		//model.addAllAttributes("user", user);
		return "confirm";
	}
	
	@GetMapping("/user_view_cart")
	public String viewCart(@ModelAttribute("user") User user, Model model) {
		//User user = userservice.get(1);
		//model.addAttribute("user", user);
		//System.out.println("cart size: " + user.getCart().size());
		//System.out.println(user);
		//List<ResUser> listUsers = service.listAll();
		//model.addAttribute("listUsers", listUsers);
		//List<CartItem> list = userservice.getCart(user);
		//user.setCart(list);
		//model.addAttribute("items", list);
		//model.addAttribute("products", list);
		//model.addAllAttributes("user", user);
		List<CartItem> list = uService.getCart(user);
		user.setCart(list);
		model.addAttribute("items", list);
		return "user_view_cart";
	}
	
	@RequestMapping("/add/{id}")
	public String addItem(@ModelAttribute("kitchen") Kitchen kitchen, @PathVariable(name = "id") Long id,
			@ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
		System.out.println("k_id after pass: " + kitchen.getId());
		System.out.println("check u_id order kitchen: " + user.getId());
		MenuItem item = mService.findMenuItem(kitchen, id);
		//model.addAttribute("product", product);	
		//User user = userservice.get(1);
		//model.addAttribute("user", user);
		System.out.println("user id: " + user.getId());
		System.out.println("user name:" + user.getName());
		System.out.println("before conversion");
		CartItem converted = mService.convert(item);
		//System.out.println("item info: " + item.getName() + item.getBrand() + item.getMadein() + item.getPrice());
		System.out.println("after conversion");
		//converted.setUser(user);
		System.out.println("before add");
		//user.addItem(converted);
		uService.addToCart(user, converted);
		System.out.println("added product");
		redirectAttributes.addAttribute("kitchen", kitchen);
		uService.saveUser(user);
		
		//List<ResUser> listUsers = service.listAll();
		//model.addAttribute("listUsers", listUsers);
		//List<Product> listProducts = proservice.listAll();
	//	model.addAttribute("listProducts", listProducts);
		//model.addAllAttributes("user", user);
		return "redirect:/user_order_menu?added";
	}
	
	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable(name="id") Long id, @ModelAttribute("user") User user) {
		uService.deleteCartItem(user, id);
		return "redirect:/user_view_cart?deleted";
	}
	
	/*@RequestMapping("/edit/{id}")
	public ModelAndView showEditProductPage(@PathVariable(name = "id") int id) {
		ModelAndView mav = new ModelAndView("edit_product");
		Product product = proservice.get(id);
		mav.addObject("product", product);
		
		return mav;
	}*/


	
	

	
	/*

	
	
	@RequestMapping("/")
	public String viewHomePage(Model model) {
		List<Product> listProducts = pservice.listAll();
		model.addAttribute("listProducts", listProducts);
		
		return "show";
	}
	/*
	
	
	@RequestMapping("/delete/{id}")
	public String deleteUser(@PathVariable(name = "id") int id) {
		service.delete(id);
		return "redirect:/";		
	}
	*/
}
