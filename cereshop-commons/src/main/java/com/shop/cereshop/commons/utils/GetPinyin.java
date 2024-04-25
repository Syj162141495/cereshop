/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.utils;


import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class GetPinyin {

    public static String getPingYin(String str){
        char[] t1=null;
        t1=str.toCharArray();
        String[] t2=new String[t1.length];
        HanyuPinyinOutputFormat t3=new HanyuPinyinOutputFormat();
        t3.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        t3.setVCharType(HanyuPinyinVCharType.WITH_V);
        String t4="";
        int t0=t1.length;
        try {
            for (int i = 0; i < t0; i++) {
                //判断是否为汉字字符
                if(Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")){
                    t2= PinyinHelper.toHanyuPinyinStringArray(t1[i],t3);
                    t4+=t2[0];
                }else {
                    //t4 +=java.lang.Character.toString(t1[i]);
                }
            }
        }catch (BadHanyuPinyinOutputFormatCombination e1){
            e1.printStackTrace();
        }
        return t4;
    }

    /**
     * 获取中文首字母
     * @param str
     * @return
     */
    public static String getPingYinHeadChar(String str){
        String convert="";
        for (int i = 0; i < str.length(); i++) {
            char word=str.charAt(i);
            String[] pinyinArray=PinyinHelper.toHanyuPinyinStringArray(word);
            if(pinyinArray!=null){
                convert+=pinyinArray[0].charAt(0);
            }else {
                //convert+=word;
            }
        }
        return convert.toUpperCase();
    }

}
