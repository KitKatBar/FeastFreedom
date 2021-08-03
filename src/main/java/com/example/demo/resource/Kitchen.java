package com.example.demo.resource;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

//@FieldMatch.List({
    //@FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match!"),
//})
@Entity
@Table(name="kitchens")
public class Kitchen implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//@Column(unique = true)
	//@NotNull
	//@Size(min = 1, max = 50)
	private String name;
	
	//@Column(unique = true)
	//@NotNull
	//@Size(min = 1, max = 50)
	private String email;
	
	//@NotNull
	//@Min(value = 10)
	private String password;
	
	//@NotNull
	//@Min(value = 10)
	//@Transient
	//private String confirmPassword;
	
	private int starttime;
	private int endtime;
	private String [] daysOpen;
	
	

	@OneToMany(mappedBy = "kitchen", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<MenuItem> menu;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "kitchen_roles",
            joinColumns = @JoinColumn(
                    name = "k_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "r_id", referencedColumnName = "id"))
    private Collection<Role> roles;

	public Kitchen() {
		menu = new ArrayList<MenuItem>();
	}
	
	public Kitchen(String name, String email, String password) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		menu = new ArrayList<MenuItem>();
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
	public String[] getDaysOpen() {
	      return daysOpen;
	   }

	public int getStarttime() {
		return starttime;
	}

	public void setStarttime(int starttime) {
		this.starttime = starttime;
	}

	public int getEndtime() {
		return endtime;
	}

	public void setEndtime(int endtime) {
		this.endtime = endtime;
	}

	public void setDaysOpen(String[] daysOpen) {
		this.daysOpen = daysOpen;
	}
	/*
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }*/
    /*
    public List<Boolean> getWorkingDays() {
		return workingDays;
	}

	public void setWorkingDays(List<Boolean> workingDays) {
		this.workingDays = workingDays;
	}
	*/
    
    public void addMenuItem(MenuItem item) {
    	if (item != null) {
    		if (menu == null)
    			menu = new ArrayList<MenuItem>();
    		item.setKitchen(this);
    		menu.add(item);
    	}
    }
    
    public void deleteMenuItem(MenuItem item) {
    	menu.remove(item);
    	item.setKitchen(null);
    }
    
	public List<MenuItem> getMenu() {
		return menu;
	}

	public void setMenu(List<MenuItem> menu) {
		this.menu = menu;
	}
	
	public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }
	
	@Override
	public boolean equals(Object o) {
		if(o == this) { return true; }
		if(!(o instanceof Kitchen)) { return false; }
		Kitchen k = (Kitchen) o;
		if (k.getName() == null) {
			return email.equalsIgnoreCase(k.getEmail())
				&& password.equalsIgnoreCase(k.getPassword());
		}
		else {
			return name.equalsIgnoreCase(k.getName())
				&& email.equalsIgnoreCase(k.getEmail())
				&& password.equalsIgnoreCase(k.getPassword());
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Name: " + name + ", Email: " + email + ", Menu: [");
		for (int i = 0; i < menu.size(); i++) {
			sb.append("Item Name: " + menu.get(i).getItemName() + ", ");
			sb.append("Veg: " + menu.get(i).isVeg() + ", ");
			sb.append("Price: " + menu.get(i).getPrice() + "]");
		}
		return sb.toString();
	}
}
