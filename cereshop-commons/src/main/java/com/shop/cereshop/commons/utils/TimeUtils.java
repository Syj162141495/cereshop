/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日期工具类
 *
 * @author yuanyao@wistronits.com
 * create on 2019-04-19 17:00
 */
public class TimeUtils {

    /**
     * 将指定字符串转换成年月格式  yyyy-MM
     * @return
     */
    public static String setYearMonth(String yearMonth) {
        try {
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
            return sdf.format(sdf.parse(yearMonth));
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 通过时间秒毫秒数判断两个时间的间隔(天)
     * @param startTime
     * @param endTime
     * @return
     */
    public static int differentDaysByMillisecond(String startTime,String endTime) throws Exception{
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date date1=sdf.parse(startTime);
        Date date2=sdf.parse(endTime);
        int days = (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24));
        return days;
    }

    /**
     * 判断指定时间是否在当前时间之后
     * @param time
     * @return
     */
    public static boolean compareAfterTime(String time) throws Exception{
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.parse(time).getTime()>=new Date().getTime();
    }

    /**
     * 获取今天日期  yyyy-MM-dd
     * @return
     */
    public static String today() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format( new Date());
    }

    /**
     * 获取昨天日期  yyyy-MM-dd
     * @return
     */
    public static String yesterday() {
        Calendar cal=Calendar.getInstance();
        cal.add(Calendar.DATE,-1);
        Date d=cal.getTime();
        SimpleDateFormat sp=new SimpleDateFormat("yyyy-MM-dd");
        String ZUOTIAN=sp.format(d);
        return ZUOTIAN;
    }

    /**
     * 获取今天日期  yyyyMMdd
     * @return
     */
    public static String todayTime() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
        return sdf.format( new Date());
    }

    /**
     * 获取今天日期的时分  HH:mm
     * @return
     */
    public static String hhmm() {
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
        return sdf.format( new Date());
    }

    /**
     * 获取今天日期的时分秒  HH:mm:ss
     * @return
     */
    public static String hhmmss() {
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
        return sdf.format( new Date());
    }

    /**
     * 获取今天日期  yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String yyMMddHHmmss() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format( new Date());
    }

    /**
     * 判断当前时间是否在某个时间段内
     * @param start
     * @param end
     */
    public static boolean isBelong(String start,String end){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        Date now =null;
        Date beginTime = null;
        Date endTime = null;
        try {
            now = df.parse(df.format(new Date()));
            beginTime = df.parse(start);
            endTime = df.parse(end);
        } catch (Exception e) {
            e.printStackTrace();
        }

        boolean flag = belongCalendar(now, beginTime, endTime);
        return flag;
    }

    /**
     * 判断当前时间是否在时间段内
     * @param nowTime
     * @param beginTime
     * @param endTime
     * @return
     */
    public static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取次日指定时间
     * @param time
     * @return
     */
    public static String getTomorrow(int time){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_YEAR, 1);
        cal.set(Calendar.HOUR_OF_DAY, time);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sdf.format(cal.getTime());
        return dateStr;
    }

    /**
     * 获取当前时间1天后的时间
     * @return
     */
    public static String get24HourAfter() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar=new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE,1);
        String date=format.format(calendar.getTime());
        return date;
    }

    /**
     * 获取当前时间指定分钟后的时间
     * @return
     */
    public static String getMinuteAfter(int time) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar=new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE,time);
        String date=format.format(calendar.getTime());
        return date;
    }

    /**
     * 获取指定日期几天后的时间
     */
    public static String getMoreDayAfter(String date,int day) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar=new GregorianCalendar();
        calendar.setTime(format.parse(date));
        calendar.add(Calendar.DATE,day);
        return format.format(calendar.getTime());
    }

    /**
     * 获取指定日期几小时后的时间
     */
    public static String getMoreHourAfter(String date,int hour) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar=new GregorianCalendar();
        calendar.setTime(format.parse(date));
        calendar.add(Calendar.HOUR,hour);
        return format.format(calendar.getTime());
    }

    /**
     * 获取指定日期几年后的时间
     */
    public static String getMoreYearAfter(String date,int year) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar=new GregorianCalendar();
        calendar.setTime(format.parse(date));
        calendar.add(Calendar.YEAR,year);
        return format.format(calendar.getTime());
    }

    /**
     * 比较指定日期是否在今天之前
     */
    public static boolean compareBeforeToday(String time) throws ParseException{
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.parse(time).getTime()<=new Date().getTime();
    }

    public static String getYearMonth() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        return format.format(new Date());
    }

    /**
     * 获取指定日期之间的天数
     */
    public static long getBetweenDays(String startTime,String endTime){
        // 获取日期
        Date date1 = parseDate(startTime, "yyyy-MM-dd");
        Date date2 = parseDate(endTime, "yyyy-MM-dd");

        // 获取相差的天数
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date1);
        long timeInMillis1 = calendar.getTimeInMillis();
        calendar.setTime(date2);
        long timeInMillis2 = calendar.getTimeInMillis();

        long betweenDays =  (timeInMillis2 - timeInMillis1) / (1000L*3600L*24L);
        return betweenDays;
    }

    /**
     * 将指定的日期字符串转换成日期
     * @param dateStr 日期字符串
     * @param pattern 格式
     * @return 日期对象
     */
    public static Date parseDate(String dateStr, String pattern)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            throw  new RuntimeException("日期转化错误");
        }

        return date;
    }

    /**
     * 获取当月第一天日期MM/dd
     */
    public static String getMonthFirst(){
        // 获取当前年份、月份、日期
        Calendar cale = Calendar.getInstance();
        // 获取当月第一天和最后一天
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String firstday;
        // 获取前月的第一天
        cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 0);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        firstday = format.format(cale.getTime());
        return firstday;
    }

    /**
     * 获取当月最后一天日期MM/dd
     */
    public static String getMonthLast(){
        // 获取当前年份、月份、日期
        Calendar cale = Calendar.getInstance();
        // 获取当月第一天和最后一天
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String lastday;
        // 获取前月的最后一天
        cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 1);
        cale.set(Calendar.DAY_OF_MONTH, 0);
        lastday = format.format(cale.getTime());
        return lastday;
    }

    /**
     * 获取指定年月份当月的每一天日期
     * @param yearParam
     * @param monthParam
     * @return
     */
    public static List<String> getDayByMonth(int yearParam, int monthParam){
        List list = new ArrayList();
        Calendar aCalendar = Calendar.getInstance(Locale.CHINA);
        aCalendar.set(yearParam,monthParam,1);
        int year = aCalendar.get(Calendar.YEAR);//年份
        int month = aCalendar.get(Calendar.MONTH);//月份
        int day = aCalendar.getActualMaximum(Calendar.DATE);
        for (int i = 1; i <= day; i++) {
            String aDate=null;
            if(month<10&&i<10){
                aDate = String.valueOf(year)+"-0"+month+"-0"+i;
            }
            if(month<10&&i>=10){
                aDate = String.valueOf(year)+"-0"+month+"-"+i;
            }
            if(month>=10&&i<10){
                aDate = String.valueOf(year)+"-"+month+"-0"+i;
            }
            if(month>=10&&i>=10){
                aDate = String.valueOf(year)+"-"+month+"-"+i;
            }
            list.add(aDate);
        }
        return list;
    }

    /**
     * 根据指定两个时间获取间距时间戳（毫秒级）
     * @param startTime
     * @param endTime
     * @return
     * @throws Exception
     */
    public static Long getCountDownByTime(String startTime,String endTime) throws Exception{
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date start = format.parse(startTime);
        Date end = format.parse(endTime);
        Long time=end.getTime()-start.getTime();
        return time;
    }

    /**
     * 比较第一个参数日期是否大于第二个参数日期
     */
    public static boolean compareTo(String startTime,String endTime) throws Exception{
        if(!EmptyUtils.isEmpty(startTime)&&!EmptyUtils.isEmpty(endTime)){
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date bt=format.parse(startTime);
            Date et=format.parse(endTime);
            return bt.after(et) || bt.equals(et);
        }
        return false;
    }

    /**
     * 获取前者时间之后N年的时间
     * @param date
     * @param year
     * @return
     */
    public static String rollYear(String date, int year) throws Exception{
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date d=format.parse(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.YEAR, year);
        return format.format(cal.getTime());
    }

    /**
     * 获取起始时间范围内的每一天时间
     * @param begintTime
     * @param endTime
     * @return
     */
    public static List<String> findDaysStr(String begintTime, String endTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dBegin = null;
        Date dEnd = null;
        try {
            dBegin = sdf.parse(begintTime);
            dEnd = sdf.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<String> daysStrList = new ArrayList<String>();
        daysStrList.add(sdf.format(dBegin));
        Calendar calBegin = Calendar.getInstance();
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(dEnd);
        while (dEnd.after(calBegin.getTime())) {
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            String dayStr = sdf.format(calBegin.getTime());
            daysStrList.add(dayStr);
        }
        return daysStrList;
    }

    /**
     * 获取当前日期前7天的时间
     * @return
     */
    public static List<String> getBeforeSevenDay(){
        String [] arr = new String[7];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = null;
        for (int i=0;i<7;i++){
            c=Calendar.getInstance();
            c.add(Calendar.DAY_OF_MONTH, - i-1);
            arr[6-i] =sdf.format(c.getTime());

        }
        return Arrays.asList(arr);
    }

    /**
     * 取到 hours 以前时间
     * @param hours
     * @return
     */
    public static String headDate(String date ,int hours) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal.setTime(format.parse(date));
        cal.add(Calendar.HOUR_OF_DAY, -hours);
        return format.format(cal.getTime());
    }
}
