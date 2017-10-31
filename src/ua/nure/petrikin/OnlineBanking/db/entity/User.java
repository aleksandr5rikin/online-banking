package ua.nure.petrikin.OnlineBanking.db.entity;

public class User extends Entity{
	
	private static final long serialVersionUID = -2567512172246623125L;
	
	private int roleId;
	private int userStatusId;
	private String login;
	private String password;
	private String name;
	private String email;
	private String token;
	private String salt;
	
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}

	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public int getUserStatusId() {
		return userStatusId;
	}
	public void setUserStatusId(int userStatusId) {
		this.userStatusId = userStatusId;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
}
