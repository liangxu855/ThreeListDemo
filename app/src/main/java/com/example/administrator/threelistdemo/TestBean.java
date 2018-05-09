package com.example.administrator.threelistdemo;

import java.util.List;

public class TestBean {

   public  List<FirstListBean> firstListBeanList ;

   class FirstListBean {
       public String firstItem;
       public  List<SecondListBean> secondListBeanList ;


   }

   class SecondListBean{
       public String secondItem;
       public  List<ThirdListBean> thirdListBeanList ;
   }

   class ThirdListBean{
       public String thirdItem;
   }
}
