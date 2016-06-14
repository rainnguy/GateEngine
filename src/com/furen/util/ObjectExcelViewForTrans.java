package com.furen.util;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.furen.util.PageData;
import com.furen.util.Tools;
/**
* 导入到EXCEL
* 类名称：ObjectExcelView.java
* 类描述： 
* @author xiajj
* 作者单位：南京双富信息科技有限公司 
* 联系方式：
* @version 1.0
 */
public class ObjectExcelViewForTrans extends AbstractExcelView{

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//1.创建文件
		Date date = new Date();
		String filename = Tools.date2Str(date, "yyyyMMddHHmmss");
		//2.创建sheet
		HSSFSheet sheet;
		HSSFCell cell;
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment;filename="+filename+".xls");
		sheet = workbook.createSheet("sheet1");
		//3.写入大分类标题
		@SuppressWarnings("unchecked")
		List<String> titleItemList = (List<String>) model.get("titleItem");
		int lenItem = titleItemList.size();
		HSSFCellStyle headerStyleForItem = workbook.createCellStyle(); //标题样式
		headerStyleForItem.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		headerStyleForItem.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		HSSFFont headerFontForItem = workbook.createFont();	//标题字体
		headerFontForItem.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		headerFontForItem.setFontHeightInPoints((short)11);
		headerStyleForItem.setFont(headerFontForItem);
		short widthForItem = 20*7,heightForItem=25*20;
		sheet.setDefaultColumnWidth(widthForItem);
		CellRangeAddress cra=null;
        
		for(int i=0; i<lenItem; i++){ //设置标题
			cra = new CellRangeAddress(0, 0, i*7+1, i*7+7);        
	        //在sheet里增加合并单元格  
	        sheet.addMergedRegion(cra); 
			String titleItem = titleItemList.get(i);
			cell = getCell(sheet, 0, i*7+1);
			cell.setCellStyle(headerStyleForItem);
			setText(cell,titleItem);
		}
		sheet.getRow(0).setHeight(heightForItem);
		//4.写入小分类标题
		List<String> titleDetailList = (List<String>) model.get("titleDetail");
		int lenDetail = titleDetailList.size();
		short widthForDetail = 20,heightForDetail=25*20;
		sheet.setDefaultColumnWidth(widthForDetail);
		for(int i=0; i<lenDetail; i++){ //设置标题
			String titleDetail = titleDetailList.get(i);
			cell = getCell(sheet, 1, i);
			cell.setCellStyle(headerStyleForItem);//与大分类用异样的式样即可
			setText(cell,titleDetail);
		}
		sheet.getRow(1).setHeight(heightForDetail);
		//5.写入内容
		HSSFCellStyle contentStyleSuceess = workbook.createCellStyle(); //内容样式-对账成功
		contentStyleSuceess.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFCellStyle contentStyleFail = workbook.createCellStyle(); //内容样式-对账失败
		contentStyleFail.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		contentStyleFail.setFillForegroundColor(IndexedColors.RED.getIndex());
		contentStyleFail.setFillPattern(CellStyle.SOLID_FOREGROUND);
		List<PageData> varList = (List<PageData>) model.get("varList");
		int varCount = varList.size();
		for(int i=0; i<varCount; i++){
			PageData vpd = varList.get(i);
			for(int j=0;j<lenDetail;j++){
				String varstr = vpd.getString("var"+(j+1)) != null ? vpd.getString("var"+(j+1)) : "";
				cell = getCell(sheet, i+2, j);
				if(vpd.getString("var0").equals("0")){
					//对账失败用红色背景式样
					cell.setCellStyle(contentStyleFail);
				}else{
					//对账成功用普通式样
					cell.setCellStyle(contentStyleSuceess);
				}
				setText(cell,varstr);
			}
		}
	}
}
