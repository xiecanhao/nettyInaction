package excel;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;

/**
 * Excel读取工具
 * 
 * @author yaowenhao
 * @date 2014年8月6日 下午1:21:10
 */
public class ExcelUtil {
	/** 表头行数 */
	public static final int HEAD_LINE = 2;
	/** 第一行内容行数 */
	public static final int FIRST_CONTENT_LINE = 3;

	/**
	 * 读取Excel有效内容
	 * 
	 * @param sheet
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Map<String, String>> readExcel(Sheet sheet)
			throws Exception {
		List<String> needHead = new ArrayList<>();
		needHead.add("limr编号");
		needHead.add("agency");
		needHead.add("city");
		needHead.add("Store Name");
		needHead.add("查店日期");

		int rowNum = sheet.getLastRowNum(); // 总行数
		// int startLine = sheet.getFirstRowNum();
		Row firstRow = sheet.getRow(HEAD_LINE); // 表示数据类型的行
		if (firstRow == null) {
			return null;
		}
		Map<String, Integer> head2index = new HashMap<>();
		int cellSize = firstRow.getLastCellNum();
		for (int i = 0; i < cellSize; i++) {
			Object data = getCellValue(firstRow.getCell(i));
			String cellStr = data.toString().trim();
			if (needHead.contains(cellStr)) {
				head2index.put(cellStr, i);
			}
		}
		// System.out.println(head2index);

		List<Map<String, String>> rowData = new ArrayList<Map<String, String>>();
		Map<String, Map<String, String>> rowMapData = new HashMap<>();
		for (int i = FIRST_CONTENT_LINE; i <= rowNum; i++) {
			Row row = sheet.getRow(i);
			if (row == null) {
				continue;
			}
			Map<String, String> temp = new HashMap<>();
			for (Entry<String, Integer> entry : head2index.entrySet()) {
				String key = entry.getKey();
				int index = entry.getValue();
				Object data = getCellValue(row.getCell(index));
				String value = data.toString().trim();
				temp.put(key, value);
				if (key.equals("limr编号")) {
					rowMapData.put(value, temp);
				}
			}
			rowData.add(temp);
		}
		return rowMapData;
	}

	/**
	 * 获取单元格数据内容
	 * 
	 * @param cell
	 *            HSSFCell Excel单元格
	 * @return Object
	 */
	public static Object getCellValue(Cell cell) {
		Object strCell = null;
		if (cell == null) {
			return strCell;
		}
		cell.setCellType(XSSFCell.CELL_TYPE_STRING); // TODO a magic!!!
		strCell = cell.getStringCellValue();
		return strCell;
		// switch (cell.getCellType()) {
		// case XSSFCell.CELL_TYPE_BLANK: // blank类型
		// break;
		// case XSSFCell.CELL_TYPE_STRING: // String类型
		// strCell = cell.getStringCellValue();
		// break;
		// case XSSFCell.CELL_TYPE_BOOLEAN: // booelan类型
		// strCell = cell.getBooleanCellValue();
		// break;
		// case XSSFCell.CELL_TYPE_NUMERIC: // numeric类型(包含了日期类型)
		// boolean isDate = HSSFDateUtil.isCellDateFormatted(cell);
		// if (isDate) {
		// strCell = cell.getDateCellValue();
		// } else {
		// strCell = cell.getNumericCellValue();
		// if (String.valueOf(strCell).indexOf("E") != -1) {
		// strCell = new DecimalFormat("0")
		// .format(cell.getNumericCellValue());
		// }
		// }
		// break;
		// case XSSFCell.CELL_TYPE_FORMULA: // 公式类型
		// try {
		// strCell = (int) cell.getNumericCellValue();
		// } catch (Exception e) {
		// strCell = cell.getStringCellValue();
		// }
		// break;
		// }
		// return strCell;
	}

	/**
	 * 导出一个Excel表格
	 * 
	 * @param sheetTitle
	 * @param modelHeader
	 * @param dataset
	 * @param out
	 * @throws Exception
	 */
	public static <T> HSSFWorkbook exportExcel(String sheetTitle,
			LinkedHashMap<String, String> modelHeader, Collection<T> dataset) {
		try {
			// 声明一个工作薄
			HSSFWorkbook workbook = new HSSFWorkbook();

			// 生成一个表格
			HSSFSheet sheet = workbook.createSheet(sheetTitle);

			// 生成一个样式
			HSSFCellStyle style = workbook.createCellStyle();

			// 设置这些样式
			style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			style.setBorderRight(HSSFCellStyle.BORDER_THIN);
			style.setBorderTop(HSSFCellStyle.BORDER_THIN);
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

			// 生成字体
			HSSFFont font = workbook.createFont();
			font.setColor(HSSFColor.VIOLET.index);
			font.setFontHeightInPoints((short) 12);
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

			HSSFFont font2 = workbook.createFont();
			font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);

			HSSFFont font3 = workbook.createFont();
			font3.setColor(HSSFColor.BLUE.index);

			// 把字体应用到当前的样式
			style.setFont(font);

			// 生成并设置另一个样式
			HSSFCellStyle style2 = workbook.createCellStyle();
			style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
			style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
			style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
			style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

			// 把字体应用到当前的样式
			style2.setFont(font2);

			// 声明一个画图的顶级管理器
			HSSFPatriarch patriarch = sheet.createDrawingPatriarch();

			// 定义注释的大小和位置,详见文档
			HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(
					0, 0, 0, 0, (short) 4, 2, (short) 6, 5));

			// 设置注释内容
			comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));

			// 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
			comment.setAuthor("wabao");

			// 产生表格标题行
			HSSFRow row = sheet.createRow(0);
			Set<String> headerNameSet = modelHeader.keySet(); // 存放实际数据的属性名的Set
			int rowIndex = 0;// 列号
			for (Entry<String, String> entry : modelHeader.entrySet()) {
				HSSFCell cell = row.createCell(rowIndex);
				cell.setCellStyle(style);
				cell.setCellValue(new HSSFRichTextString(entry.getValue()));

				sheet.setColumnWidth(rowIndex++,
						entry.getValue().getBytes().length * 2 * 192);// 设置列宽度
			}

			// 遍历集合数据，产生数据行
			Iterator<T> dataIt = dataset.iterator();
			int rowIndex2 = 1;// 数据行的列号
			while (dataIt.hasNext()) {

				int sheelIndex = rowIndex2 / 65535 + 1;
				if (rowIndex2 % 65535 == 0) {
					// 重新建一个sheel表格 1：需要插入一个标题行
					rowIndex2 = 1;// 数据行的列号

					// 生成一个表格
					sheet = workbook.createSheet(sheetTitle + sheelIndex);

					// 声明一个画图的顶级管理器
					patriarch = sheet.createDrawingPatriarch();

					// 定义注释的大小和位置,详见文档
					comment = patriarch.createComment(new HSSFClientAnchor(0, 0,
							0, 0, (short) 4, 2, (short) 6, 5));

					// 设置注释内容
					comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));

					// 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
					comment.setAuthor("wabao");

					// 产生表格标题行
					row = sheet.createRow(0);
					rowIndex = 0;// 列号
					for (Entry<String, String> entry : modelHeader.entrySet()) {
						HSSFCell cell = row.createCell(rowIndex);
						cell.setCellStyle(style);
						cell.setCellValue(
								new HSSFRichTextString(entry.getValue()));

						sheet.setColumnWidth(rowIndex++,
								entry.getValue().getBytes().length * 2 * 192);// 设置列宽度
					}
				}
				row = sheet.createRow(rowIndex2++); // 每条数据创建一行
				T t = dataIt.next();

				// 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
				final Method[] methods = t.getClass().getMethods(); // 方法数组
				int cellIndex = 0;
				HashMap<String, String> valueMap = new HashMap<String, String>();
				for (Method method : methods) {
					String methodName = method.getName();
					// 方法名操作的属性名
					String fieldName = "";
					if (methodName.startsWith("get")) {
						fieldName = methodName.substring(3);
					} else if (methodName.startsWith("is")) {
						fieldName = methodName.substring(2);
					}
					if (fieldName.length() > 0
							&& method.getParameterTypes().length == 0) {
						if (fieldName.length() == 1) {
							fieldName = fieldName.toLowerCase();
						} else if (!Character
								.isUpperCase(fieldName.charAt(1))) {
							fieldName = fieldName.substring(0, 1).toLowerCase()
									+ fieldName.substring(1);
						}
					}
					if (!headerNameSet.contains(fieldName)) {// 如果某个属性，不在headNameSet中，那么就丢弃它。
						continue;
					}
					Object elementObj = method.invoke(t);// get方法取得的元素
					String cellContent = "";// 每格显示的内容
					if (elementObj != null) {
						if (elementObj instanceof Date) {
							cellContent = DateUtils.format((Date) elementObj,
									"yyyy-MM-dd HH:mm:ss");
						} else {
							cellContent = elementObj.toString();
						}
					}
					valueMap.put(fieldName, cellContent);

				}
				// 创建一行
				for (String key : headerNameSet) {
					// 创建一格
					HSSFCell cell = row.createCell(cellIndex++);
					cell.setCellStyle(style2);
					HSSFRichTextString richString = new HSSFRichTextString(
							valueMap.get(key));
					richString.applyFont(font3);
					cell.setCellValue(richString);
				}

			}
			return workbook;
		} catch (Exception e) {
			System.err.println("生成Excel表格失败!" + e.getMessage());
		}
		return null;
	}
}
