package cn.itcast.jdbc.domain;

import java.util.Date;

public class User {

	private int id;
	private String name;
	private Date birthday;
	private Float money;
	public User(){
		
	}
	public User(String name){
		this.name=name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public Float getMoney() {
		return money;
	}
	public void setMoney(Float money) {
		this.money = money;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", birthday=" + birthday + ", money=" + money + "]";
	}
	
	
}
