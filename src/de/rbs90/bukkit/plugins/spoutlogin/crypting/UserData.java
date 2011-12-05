package de.rbs90.bukkit.plugins.spoutlogin.crypting;

public class UserData {
	private String name, email, pass;
	private Boolean approved;
	
	public UserData(String name, String email, String pass, Boolean approved) {
		this.setName(name);
		this.setEmail(email);
		this.setPass(pass);
		this.setApproved(approved);
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

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public Boolean getApproved() {
		return approved;
	}

	public void setApproved(Boolean approved) {
		this.approved = approved;
	}
	
	
}
