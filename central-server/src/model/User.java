package model;

import datatype.*;
import jakarta.persistence.*;

@Entity
public abstract class User {
	
	@Id
	private String nickname;
	private String name;
	private String email;
	private String password;
	private String image;
	
	public User(String nickname, String name, String email, String password, String image) {
		this.nickname = nickname;
		this.name = name;
		this.email = email;
		this.password = password;
		this.image = image;
	}

	public abstract Boolean isAirline();
	
	public abstract Boolean isClient();
	
	public abstract DTUsuario getDTO();

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getNombre() {
		return name;
	}

	public void setNombre(String name) {
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

	public void setPassword(String p) {
		this.password = p;
	}

	public String getImageUrl() {
		return image;
	}

	public void setImageUrl(String image) {
		this.image = image;
	}
	
	@Override
    public String toString() {
        return "User{" +
                "nickname='" + nickname + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
