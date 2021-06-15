/**
 * 
 */
package com.test.clone;

import java.util.ArrayList;

/**
 * @brief 
 * @details
 * @author Administrator
 *
 */
public class Person implements Cloneable{
	private String name;
	private int age;
	private ArrayList<String> hobbies = new ArrayList<String>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public ArrayList<String> getHobbies() {
		return hobbies;
	}
	public void setHobbies(ArrayList<String> hobbies) {
		this.hobbies = hobbies;
	}
	@Override
	protected Object clone(){
		// TODO Auto-generated method stub
		Person person = null;
		try {
			person = (Person) super.clone();
			person.name = this.name;
			person.age = this.age;
			person.hobbies = (ArrayList<String>) this.hobbies.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return person;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "name=" + null == name ? "" : name + ",age=" + age + ",hobbies=" + hobbies.toArray().length;
	}
	
}
