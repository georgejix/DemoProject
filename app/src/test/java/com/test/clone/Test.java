/**
 * 
 */
package com.test.clone;

/**
 * @brief 
 * @details
 * @author Administrator
 *
 */
public class Test {

	@org.junit.Test
	public void main() {
		// TODO Auto-generated method stub
		Person p1 = new Person();
		p1.setName("zhangsan");
		p1.setAge(12);
		p1.getHobbies().add("football");
		Person p2 = (Person) p1.clone();
		p2.setAge(16);
		p2.getHobbies().add("basketball");
		System.out.println(p1);
		System.out.println(p2);
	}

}
