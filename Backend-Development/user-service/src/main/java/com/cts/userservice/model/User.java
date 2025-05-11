package com.cts.userservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import com.cts.userservice.enums.Roles;

	/**
 	* Represents a User in the online food delivery system.
 	* There are three user roles - Customer, Owner, Agent.
 	* This entity is mapped to "user" table.
 	* 
 	* @author Harish Raju M R
 	* @since 22 Feb 2025
 	*/


@Entity
@Table(name="user")
public class User {
	
	 /**
     * The unique identifier for the user.
     * Uses UUID as the primary key.
     */
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID userId;

	/**
     * Represents name of the user.
     */
	@Column(nullable = false)
	private String name;

	/**
     * Represents email of the user.
     */
	@Column(nullable = false, unique = true)
	private String email;

	/**
     * Represents email of the user.
     */
	@Column(nullable = false, unique = true)
	private Long phone;

	/**
     * Represents the present address of the user.
     */
	@Column(nullable = false, unique = true)
	private String address;

	/**
     * The roles can be "CUSTOMER", "OWNER" or "AGENT".
     */
	@Enumerated(EnumType.STRING)
	private Roles role;
	
	/**
     * Holds the encrypted user password.
     */
	@Column(nullable = false)
	private String password;

	public User(){

	}

	public User(String name, String email, Long phone, String address, Roles role, String password) {
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.role = role;
		this.password = password;
	}

	/**
	 * @return the userId
	 */
	public UUID getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the phone
	 */
	public long getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(long phone) {
		this.phone = phone;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the role
	 */
	public Roles getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(Roles role) {
		this.role = role;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
