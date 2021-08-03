package com.example.demo.resource;



import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	//@Size(min = 3, max = 50)
	private String name;
	
//	@NotBlank
//	@Email(message = "Please enter a valid e-mail address")
	private String email;
	
//	@NotBlank
//	@Size(min = 8, max = 15)
	private String password;
	private String phone;
	private double cost;
	
	//ArrayList<String> products;
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CartItem> cart;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "u_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "r_id", referencedColumnName = "id"))
    private Collection<Role> roles;
		   
	public User() {
		//this.products = new ArrayList<Product>();
		cart = new ArrayList<CartItem>();
	}

	public User(Long id, String name, String email, String password, String phone, float cost) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.cost = cost;;
		
		cart = new ArrayList<CartItem>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}
	
	public List<CartItem> getCart() {
		return cart;
	}

	public void setCart(List<CartItem> cart) {
		this.cart = cart;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

	public void addItem(CartItem item)
    {
    	if (item != null) {
    		if (cart == null)
    			cart = new ArrayList<CartItem>();
    		item.setUser(this);
    		cart.add(item);
        	System.out.println("added the dishes:" + item.toString());
        	cost = cost + item.getPrice();
        	System.out.println("total cost:" + cost);
    	}
    }
	
	public void deleteCartItem(CartItem item) {
    	cart.remove(item);
    	cost = cost - item.getPrice();
    	item.setUser(null);
    }
	
	@Override
	public String toString() {
		return "User [name=" + name + ", email=" + email + ", password=" + password + ", cost="
				+ cost + "]";
	}

}
