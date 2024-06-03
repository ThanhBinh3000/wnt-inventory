package vn.com.gsoft.inventory.util.system;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Export Excel common methods
 * 
 * @version 1.0
 * 
 * @author hoanglb
 *
 */

@Slf4j
public class ExportExcel {

	// Title of the displayed export table
	private String title;
	// Column name of export table
	private String[] rowName;

	private List<Object[]> dataList = new ArrayList<Object[]>();

	HttpServletResponse response;

	private String fileName;

	// Constructor, passing in data to export
	public ExportExcel(String title, String fileName, String[] rowName, List<Object[]> dataList,
                       HttpServletResponse response) {
		this.dataList = dataList;
		this.rowName = rowName;
		this.title = title;
		this.response = response;
		this.fileName = fileName;
	}

	/*
	 * Export data
	 */
	public void export() throws Exception {
		try {
			XSSFWorkbook workbook = new XSSFWorkbook(); // Create workbook object
			XSSFSheet sheet = workbook.createSheet(title); // Create sheet

			// Generate table header row
//			XSSFRow rowm = sheet.createRow(0);
//			XSSFCell cellTiltle = rowm.createCell(0);

			// sheet style definition [getColumnTopStyle()/getStyle() are all user-defined
			// methods - below - extensible]
			XSSFCellStyle columnTopStyle = this.getColumnTopStyle(workbook);// Get column header style object
			XSSFCellStyle style = this.getStyle(workbook); // Cell style object

//			sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, (rowName.length - 1)));
//			cellTiltle.setCellStyle(columnTopStyle);
//			cellTiltle.setCellValue(title);

			// Define the number of columns required
			int columnNum = rowName.length;
			XSSFRow rowRowName = sheet.createRow(0); // Create row at index 2 (second row from top row)

			// Set column headers to cells in sheet
			for (int n = 0; n < columnNum; n++) {
				XSSFCell cellRowName = rowRowName.createCell(n); // Create cells corresponding to the number of column
																	// headers
				cellRowName.setCellType(CellType.STRING); // Set the data type of the column header cell
				XSSFRichTextString text = new XSSFRichTextString(rowName[n]);
				cellRowName.setCellValue(text); // Set the value of the column header cell
				cellRowName.setCellStyle(columnTopStyle); // Set column header cell style
			}

			// Set the queried data to the cell corresponding to the sheet
			for (int i = 0; i < dataList.size(); i++) {

				Object[] obj = dataList.get(i);// Traverse each object
				XSSFRow row = sheet.createRow(i + 1);// Number of rows required to create

				for (int j = 0; j < obj.length; j++) {
					XSSFCell cell = null; // Set cell data type
					if (j == 0) {
						cell = row.createCell(j, CellType.NUMERIC);
						cell.setCellValue(i + 1);
					} else {
						cell = row.createCell(j, CellType.STRING);
						if (!"".equals(obj[j]) && obj[j] != null) {
							cell.setCellValue(obj[j].toString()); // Set cell value
						}
					}
					cell.setCellStyle(style); // Set cell style
				}
			}
			// Let the column width automatically adapt to the exported column length
			for (int colNum = 0; colNum < columnNum; colNum++) {
				int columnWidth = sheet.getColumnWidth(colNum) / 256;
				for (int rowNum = 2; rowNum < sheet.getLastRowNum(); rowNum++) {
					XSSFRow currentRow;
					// The current row has not been used
					if (sheet.getRow(rowNum) == null) {
						currentRow = sheet.createRow(rowNum);
					} else {
						currentRow = sheet.getRow(rowNum);
					}
					if (currentRow.getCell(colNum) != null) {
						XSSFCell currentCell = currentRow.getCell(colNum);
						if (currentCell.getCellType() == CellType.STRING) {
							int length = currentCell.getStringCellValue().getBytes().length;
							if (columnWidth < length) {
								columnWidth = length;
							}
						}
					}
				}
				if (colNum == 0) {
					sheet.setColumnWidth(colNum, (columnWidth - 2) * 256);
				} else {
					sheet.setColumnWidth(colNum, (columnWidth + 4) * 256);
				}
			}

			if (workbook != null) {
				try {
					response.reset();
					String headStr = "attachment; filename=\"" + fileName + "\"";
					response.setContentType("APPLICATION/OCTET-STREAM");
					response.setHeader("Content-Disposition", headStr);
					OutputStream out = response.getOutputStream();
					workbook.write(out);
					out.close();
				} catch (IOException e) {
					response.reset();
					log.error(e.getMessage());
				}
			}

		} catch (Exception e) {
			response.reset();
			log.error(e.getMessage());
		}

	}

	/*
	 * Column header cell style
	 */
	public XSSFCellStyle getColumnTopStyle(XSSFWorkbook workbook) {

		// Set font
		XSSFFont font = workbook.createFont();
		// Set font size
		font.setFontHeightInPoints((short) 11);
		// Bold font
		font.setBold(true);
		// Set font name
		font.setFontName("Times New Roman");
		// Set the style;
		XSSFCellStyle style = workbook.createCellStyle();
		// Set the bottom border;
		style.setBorderBottom(BorderStyle.THIN);
		// Set the bottom border color;
		style.setBottomBorderColor(IndexedColors.BLACK.index);
		// Set the left border;
		style.setBorderLeft(BorderStyle.THIN);
		// Set the left border color;
		style.setLeftBorderColor(IndexedColors.BLACK.index);
		// Set the right border;
		style.setBorderRight(BorderStyle.THIN);
		// Set the right border color;
		style.setRightBorderColor(IndexedColors.BLACK.index);
		// Set the top border;
		style.setBorderTop(BorderStyle.THIN);
		// Set the top border color;
		style.setTopBorderColor(IndexedColors.BLACK.index);
		// Use the font set by the application in the style;
		style.setFont(font);
		// Set auto wrap;
		style.setWrapText(false);
		// Set the style of horizontal alignment to center alignment;
		style.setAlignment(HorizontalAlignment.CENTER);
		// Set the vertical alignment style to center alignment;
		style.setVerticalAlignment(VerticalAlignment.CENTER);

		return style;

	}

	/*
	 * Column data information cell style
	 */
	public XSSFCellStyle getStyle(XSSFWorkbook workbook) {
		// Set font
		XSSFFont font = workbook.createFont();
		// Set font size
		// font.setFontHeightInPoints((short)10);
		// Bold font
		// font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		// Set font name
		font.setFontName("Times New Roman");
		// Set the style;
		XSSFCellStyle style = workbook.createCellStyle();
		// Set the bottom border;
		style.setBorderBottom(BorderStyle.THIN);
		// Set the bottom border color;
		style.setBottomBorderColor(IndexedColors.BLACK.index);
		// Set the left border;
		style.setBorderLeft(BorderStyle.THIN);
		// Set the left border color;
		style.setLeftBorderColor(IndexedColors.BLACK.index);
		// Set the right border;
		style.setBorderRight(BorderStyle.THIN);
		// Set the right border color;
		style.setRightBorderColor(IndexedColors.BLACK.index);
		// Set the top border;
		style.setBorderTop(BorderStyle.THIN);
		// Set the top border color;
		style.setTopBorderColor(IndexedColors.BLACK.index);
		// Use the font set by the application in the style;
		style.setFont(font);
		// Set auto wrap;
		style.setWrapText(false);
		// Set the style of horizontal alignment to center alignment;
		style.setAlignment(HorizontalAlignment.CENTER);
		// Set the vertical alignment style to center alignment;
		style.setVerticalAlignment(VerticalAlignment.CENTER);

		return style;

	}

	public static MergeCellObj buildMergeCell(Row row, String value, int firstRow, int lastRow, int firstCol, int lastCol) {
		return MergeCellObj.builder()
				.firstRow(firstRow)
				.lastRow(lastRow)
				.firstCol(firstCol)
				.lastCol(lastCol)
				.value(value)
				.row(row)
				.build();
	}

	public static void createCell(Row row, int columnCount, Object value, CellStyle style, XSSFSheet sheet) {
		sheet.autoSizeColumn(columnCount);
		Cell cell = row.createCell(columnCount);
		cell.setCellStyle(style);
		if (value == null) {
			cell.setCellValue("");
			return;
		}
		if (value instanceof Integer) {
			cell.setCellValue((Integer) value);
		} else if (value instanceof Boolean) {
			cell.setCellValue((Boolean) value);
		} else if (value instanceof Double) {
			cell.setCellValue((Double) value);
		} else {
			cell.setCellValue((String) value);
		}
	}
}