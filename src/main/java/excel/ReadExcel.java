package excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcel {
	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception {
		// File excelFile = new File("D:\\ejfile\\gz2.xlsx");
		// InputStream is = new FileInputStream(excelFile);
		// XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
		// if (xssfWorkbook == null) {
		// System.out.println("未读取到内容,请检查路径！");
		// return;
		// }
		// Sheet sheet = xssfWorkbook.getSheetAt(0);

		// 读取excel文件内容
		InputStream in = new FileInputStream("D:\\ejfile\\gz.xlsx");
		Workbook workbook = create(in);
		Sheet sheet = workbook.getSheetAt(0);
		Map<String, Map<String, String>> cellList = ExcelUtil.readExcel(sheet);

		// 修改文件名
		// File root = new File("D:\\ejfile\\old");
		// if (!root.exists()) {
		// System.err.println("目标源文件夹不存在!!");
		// }
		// File[] files = root.listFiles();
		// for (File f : files) {
		// String fn = f.getName().trim();
		// Map<String, String> map = cellList.get(fn);
		// if (map == null) {
		// System.err.println("excel表没有limr编号与文件夹名对应：" + fn);
		// continue;
		// }
		// String dateStr = map.get("查店日期");
		// if (dateStr == null) {
		// dateStr = "xxxxx";
		// }
		// String dn = map.get("Store Name");
		// if (dn == null) {
		// dn = "xxxxx";
		// }
		// String name = dateStr + "-" + dn + "-" + "照片";
		// int i = 1;
		// for (File ff : f.listFiles()) {
		// String oldName = ff.getName();
		// String lastName = oldName.substring(oldName.indexOf("."));
		// String newName = ff.getParent() + File.separator + name + i
		// + lastName;
		// ff.renameTo(new File(newName));
		// i++;
		// }
		// }
		System.out.println("修改完成。。。。");

		// 创建新文件夹
		File newRoot = new File("D:\\ejfile\\new");
		if (!newRoot.exists()) {
			newRoot.mkdirs();
		}
		System.out.println(cellList.size());
		Set<String> set = new HashSet<>();
		for (Map<String, String> m : cellList.values()) {
			String agency = m.get("agency");
			if (agency.equals("")) {
				agency = "xxxx";
			}
			// 替换非法文件符号
			agency = agency.replaceAll("[\\\\/:*?\"<>|]", "");
			set.add(agency);
			String city = m.get("city");
			File f = new File(newRoot.getPath() + File.separator + agency);
			if (!f.exists()) {
				f.mkdirs();
			}
			File ff = new File(f.getPath() + File.separator + city);
			if (!ff.exists()) {
				ff.mkdirs();
			}
		}
		System.out.println(set);
	}

	public static Workbook create(InputStream in)
			throws IOException, InvalidFormatException {
		if (!in.markSupported()) {
			in = new PushbackInputStream(in, 8);
		}
		if (POIFSFileSystem.hasPOIFSHeader(in)) {
			return new HSSFWorkbook(in);
		}
		if (POIXMLDocument.hasOOXMLHeader(in)) {
		}
		return new XSSFWorkbook(OPCPackage.open(in));
		// throw new IllegalArgumentException("你的excel版本目前poi解析不了");

	}
}
