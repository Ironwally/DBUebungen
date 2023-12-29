package de.hska.iwii.db1.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
public class Customer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Column(name = "first_name")
	private String firstName;
	
    @NotNull
    @Column(name = "last_name")
    private String lastName;
    
    @NotNull
    @Column(name = "email")
    private String email;
    
    public Customer()
    {
    	
    }
    
    public Customer(String firstName, String lastName, String email)
    {
    	this.firstName = firstName;
    	this.lastName = lastName;
    	this.email = email;
    }
}
