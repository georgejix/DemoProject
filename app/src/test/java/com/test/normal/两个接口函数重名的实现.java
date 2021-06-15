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
public class 两个接口函数重名的实现 {
    interface Interface1{
        void a();
    }
    interface Interface2{
        void a();
    }
    class BB implements Interface1, Interface2{

        @Override
        public void a() {
            System.out.println("aaaaaaa");
        }
    }
    void test1(Interface1 i){
        i.a();
    }
    void test2(Interface2 i){
        i.a();
    }
    @Test
    public void main(){
        BB bb = new BB();
        test1(bb);
        test2(bb);
    }
}
