package com.cristik.common.bean;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.util.List;

public class FastBeanUtilsTest {

    public static void main(String[] args) {
        List<A> aList = Lists.newArrayList();
        for (int i = 0; i < 10000000L; i++) {
            A a = new A().setA(1)
                    .setB(2)
                    .setTest(true)
                    .setC("bbbb")
                    .setCheck(false);
            aList.add(a);
        }
        long begin1 = System.currentTimeMillis();
        aList.stream().forEach(e->{
            B b = FastBeanUtils.copy(e, B.class);
        });
        System.out.println(System.currentTimeMillis() - begin1);
        long begin2 = System.currentTimeMillis();
        aList.stream().forEach(e->{
            BeanUtils.copyProperties(e, new B());
        });
        System.out.println(System.currentTimeMillis() - begin2);
    }


    @Data
    @Accessors(chain = true)
    public static class A {
        private Integer a;
        private int b;
        private String c;
        private Boolean test;
        private boolean check;
    }

    @Data
    public static class B {
        private Integer a;
        private int b;
        private String c;
        private Boolean test;
        private boolean check;
    }

}