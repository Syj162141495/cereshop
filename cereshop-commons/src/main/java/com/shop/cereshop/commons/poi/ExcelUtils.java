/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.poi;

import com.shop.cereshop.commons.domain.label.PlatformLabel;
import com.shop.cereshop.commons.domain.shop.ShopDataDetail;
import com.shop.cereshop.commons.domain.shop.ShopDetail;
import com.shop.cereshop.commons.utils.EmptyUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;
import java.util.List;

public class ExcelUtils {

    /**
     * 生成表格
     * @param excelName
     * @throws Exception
     */
    public static void createExcel(String excelName) throws Exception {

        //创建工作簿
        XSSFWorkbook wb = new XSSFWorkbook();
        //创建一个sheet
        XSSFSheet sheet = wb.createSheet();

        // 创建单元格样式
        XSSFCellStyle style =  wb.createCellStyle();
        style.setFillForegroundColor((short)4); //设置要添加表格北京颜色
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND); //solid 填充
        style.setAlignment(HorizontalAlignment.CENTER); //文字水平居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);//文字垂直居中
        style.setBorderBottom(BorderStyle.THIN); //底边框加黑
        style.setBorderLeft(BorderStyle.THIN);  //左边框加黑
        style.setBorderRight(BorderStyle.THIN); // 有边框加黑
        style.setBorderTop(BorderStyle.THIN); //上边框加黑
        //为单元格添加背景样式
        for (int i = 0; i < 6; i++) { //需要6行表格
            Row row =	sheet.createRow(i); //创建行
            for (int j = 0; j < 6; j++) {//需要6列
                row.createCell(j).setCellStyle(style);
            }
        }


        //合并单元格
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 0));//合并单元格，cellRangAddress四个参数，第一个起始行，第二终止行，第三个起始列，第四个终止列
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 5));

        //tian入数据
        XSSFRow row = sheet.getRow(0); //获取第一行
        row.getCell(1).setCellValue("2018期末考试"); //在第一行中创建一个单元格并赋值
        XSSFRow row1 = sheet.getRow(1); //获取第二行，为每一列添加字段
        row1.getCell(1).setCellValue("语文");
        row1.getCell(2).setCellValue("数学");
        row1.getCell(3).setCellValue("英语");
        row1.getCell(4).setCellValue("物理");
        row1.getCell(5).setCellValue("化学");
        XSSFRow row2 = sheet.getRow(2); //获取第三行
        row2.getCell(0).setCellValue("张三");
        XSSFRow row3 = sheet.getRow(3); //获取第四行
        row3.getCell(0).setCellValue("张三");
        XSSFRow row4 = sheet.getRow(4); //获取第五行
        row4.getCell(0).setCellValue("张三");
        XSSFRow row5 = sheet.getRow(5); //获取第五行
        row5.getCell(0).setCellValue("张三");
        //将数据写入文件
        FileOutputStream out = new FileOutputStream(excelName);
        wb.write(out);

    }

    /**
     * 店铺导出
     * @param titles
     * @param list
     * @return
     * @throws Exception
     */
    public static XSSFWorkbook demo(List<String> titles, List<String> list) throws Exception {

        //创建工作簿
        XSSFWorkbook wb = new XSSFWorkbook();
        //创建一个sheet
        XSSFSheet sheet = wb.createSheet();

        // 创建单元格样式
        XSSFCellStyle style =  wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER); //文字水平居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);//文字垂直居中
        style.setBorderBottom(BorderStyle.THIN); //底边框加黑
        style.setBorderLeft(BorderStyle.THIN);  //左边框加黑
        style.setBorderRight(BorderStyle.THIN); // 有边框加黑
        style.setBorderTop(BorderStyle.THIN); //上边框加黑

        //创建第一行和列并设置值
        XSSFRow row = sheet.createRow(0);
        for (int j = 0; j < titles.size(); j++) {
            row.createCell(j).setCellValue(titles.get(j));
            row.getCell(j).setCellStyle(style);
        }
        //创建后面的行和列并设置值
        for (int i = 0; i < list.size(); i++) {
            String str = list.get(i);
            //获取行
            XSSFRow sheetRow=sheet.createRow(i+1);
            //添加列数据
            sheetRow.createCell(0).setCellValue("");
            for (int n = 0; n < 1; n++) {
                sheet.autoSizeColumn(n);
                sheet.setColumnWidth(n, sheet.getColumnWidth(n) * 35 / 10);
                sheetRow.getCell(n).setCellStyle(style);
            }
        }
        return wb;
    }

    public static XSSFWorkbook createPlatformLabelExcel(List<String> titles, List<PlatformLabel> list) {

        //创建工作簿
        XSSFWorkbook wb = new XSSFWorkbook();
        //创建一个sheet
        XSSFSheet sheet = wb.createSheet();

        // 创建单元格样式
        XSSFCellStyle style =  wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER); //文字水平居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);//文字垂直居中
        style.setBorderBottom(BorderStyle.THIN); //底边框加黑
        style.setBorderLeft(BorderStyle.THIN);  //左边框加黑
        style.setBorderRight(BorderStyle.THIN); // 有边框加黑
        style.setBorderTop(BorderStyle.THIN); //上边框加黑

        //创建第一行和列并设置值
        XSSFRow row = sheet.createRow(0);
        for (int j = 0; j < titles.size(); j++) {
            row.createCell(j).setCellValue(titles.get(j));
            row.getCell(j).setCellStyle(style);
        }
        //创建后面的行和列并设置值
        for (int i = 0; i < list.size(); i++) {
            PlatformLabel label = list.get(i);
            //获取行
            XSSFRow sheetRow=sheet.createRow(i+1);
            //添加列数据
            sheetRow.createCell(0).setCellValue(label.getLabelName());
            sheetRow.createCell(1).setCellValue(label.getUsers());
            if(label.getLabelType().equals(1)){
                sheetRow.createCell(2).setCellValue("手动标签");
            }else {
                sheetRow.createCell(2).setCellValue("自动标签");
            }
            String condition="";
            if(!EmptyUtils.isEmpty(label.getConditions())){
                for (String str:label.getConditions()) {
                    condition+=str+"\r\n";
                }
            }
            sheetRow.createCell(3).setCellValue(condition);
            for (int n = 0; n < 4; n++) {
                sheet.autoSizeColumn(n);
                sheet.setColumnWidth(n, sheet.getColumnWidth(n) * 35 / 10);
                sheetRow.getCell(n).setCellStyle(style);
            }
        }
        return wb;
    }

    public static XSSFWorkbook createShopDetailExcel(List<String> titles, List<ShopDetail> list) {

        //创建工作簿
        XSSFWorkbook wb = new XSSFWorkbook();
        //创建一个sheet
        XSSFSheet sheet = wb.createSheet();

        // 创建单元格样式
        XSSFCellStyle style =  wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER); //文字水平居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);//文字垂直居中
        style.setBorderBottom(BorderStyle.THIN); //底边框加黑
        style.setBorderLeft(BorderStyle.THIN);  //左边框加黑
        style.setBorderRight(BorderStyle.THIN); // 有边框加黑
        style.setBorderTop(BorderStyle.THIN); //上边框加黑

        //创建第一行和列并设置值
        XSSFRow row = sheet.createRow(0);
        for (int j = 0; j < titles.size(); j++) {
            row.createCell(j).setCellValue(titles.get(j));
            row.getCell(j).setCellStyle(style);
        }
        //创建后面的行和列并设置值
        for (int i = 0; i < list.size(); i++) {
            ShopDetail detail = list.get(i);
            //获取行
            XSSFRow sheetRow=sheet.createRow(i+1);
            //添加列数据
            sheetRow.createCell(0).setCellValue(detail.getShopName());
            sheetRow.createCell(1).setCellValue(detail.getShopCode());
            sheetRow.createCell(2).setCellValue(detail.getProducts());
            sheetRow.createCell(3).setCellValue(detail.getPersons());
            sheetRow.createCell(4).setCellValue(detail.getOrders());
            sheetRow.createCell(5).setCellValue(detail.getCustomers());
            sheetRow.createCell(6).setCellValue(String.valueOf(detail.getPrice()));
            sheetRow.createCell(7).setCellValue(String.valueOf(detail.getTotal()));
            for (int n = 0; n < 8; n++) {
                sheet.autoSizeColumn(n);
                sheet.setColumnWidth(n, sheet.getColumnWidth(n) * 35 / 10);
                sheetRow.getCell(n).setCellStyle(style);
            }
        }
        return wb;
    }

    public static XSSFWorkbook createSeckillShopDetailExcel(List<String> titles, List<ShopDataDetail> list) {

        //创建工作簿
        XSSFWorkbook wb = new XSSFWorkbook();
        //创建一个sheet
        XSSFSheet sheet = wb.createSheet();

        // 创建单元格样式
        XSSFCellStyle style =  wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER); //文字水平居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);//文字垂直居中
        style.setBorderBottom(BorderStyle.THIN); //底边框加黑
        style.setBorderLeft(BorderStyle.THIN);  //左边框加黑
        style.setBorderRight(BorderStyle.THIN); // 有边框加黑
        style.setBorderTop(BorderStyle.THIN); //上边框加黑

        //创建第一行和列并设置值
        XSSFRow row = sheet.createRow(0);
        for (int j = 0; j < titles.size(); j++) {
            row.createCell(j).setCellValue(titles.get(j));
            row.getCell(j).setCellStyle(style);
        }
        //创建后面的行和列并设置值
        for (int i = 0; i < list.size(); i++) {
            ShopDataDetail detail = list.get(i);
            //获取行
            XSSFRow sheetRow=sheet.createRow(i+1);
            //添加列数据
            sheetRow.createCell(0).setCellValue(detail.getShopName());
            sheetRow.createCell(1).setCellValue(detail.getShopCode());
            sheetRow.createCell(2).setCellValue(detail.getProducts());
            sheetRow.createCell(3).setCellValue(detail.getVisit());
            sheetRow.createCell(4).setCellValue(detail.getOrders());
            sheetRow.createCell(5).setCellValue(detail.getFinish());
            sheetRow.createCell(6).setCellValue(String.valueOf(detail.getTotal()));
            for (int n = 0; n < 7; n++) {
                sheet.autoSizeColumn(n);
                sheet.setColumnWidth(n, sheet.getColumnWidth(n) * 35 / 10);
                sheetRow.getCell(n).setCellStyle(style);
            }
        }
        return wb;
    }
}
