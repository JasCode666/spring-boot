package com.jas.util;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

public class CustomBeanUtils {

    /**
     * 目的 : 使BeanUtils拷貝功能不拷貝資料為null的值
     * 先將傳送過來的物件用BeanWrapper打包
     * 再使用PropertyDescriptor取得BeanWrapper打包物件的內容
     * 使用迴圈找出value為null的內容並丟入String陣列再回傳
     * 即可實現BeanUtils傳送不拷貝的String陣列
     */
    public static String[] getNullPropertyNames(Object source) {
        BeanWrapper beanWrapper = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds =  beanWrapper.getPropertyDescriptors();
        List<String> nullPropertyNames = new ArrayList<>();
        for (PropertyDescriptor pd : pds) {
            String propertyName = pd.getName();
            if (beanWrapper.getPropertyValue(propertyName) == null)
                nullPropertyNames.add(propertyName);
        }
        return nullPropertyNames.toArray(new String[nullPropertyNames.size()]);
    }
}
