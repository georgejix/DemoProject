package com.test.normal; /**
 * 
 */

import org.junit.Test;

/**
 * @brief 
 * @details
 * @author Administrator
 *
 */
public class 枚举继承接口 {

	@Test
	public void main() {
		枚举继承接口 testEnum = new 枚举继承接口();
		testEnum.t1();
	}

	private void t1() {
		Color color = Color.RED;
		int i = 100;
		while(i-- > 0 ) {
			color = color.change();
		}
	}
	
	interface ColorInterface{
		public Color change();
	}
	
	enum Color implements ColorInterface{
		RED("r"){

			@Override
			public Color change() {
				// TODO Auto-generated method stub
				System.out.println("red");
				return YELLOW;
			}
			
		},
		YELLOW("y"){

			@Override
			public Color change() {
				// TODO Auto-generated method stub
				System.out.println("yellow");
				return BLUE;
			}
			
		},
		BLUE("b"){

			@Override
			public Color change() {
				// TODO Auto-generated method stub
				System.out.println("blue");
				return GREEN;
			}
			
		},
		GREEN("g"){

			@Override
			public Color change() {
				// TODO Auto-generated method stub
				System.out.println("green");
				return RED;
			}
			
		};
		private Color(String s){
			System.out.println(s);
		}
	}
}
