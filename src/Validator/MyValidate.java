/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Validator;

import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Enumeration;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;

/**
 *
 * @author X1
 */
public class MyValidate {
    
    //EXCEL VALIDATE
    public Boolean isNotEmptyCell(Cell cell, StringBuilder stb, String msg) {
        if (cell == null || cell.getCellType() == CellType.BLANK || cell.getCellType() == CellType.STRING && cell.getStringCellValue().trim().isEmpty()) {
            stb.append("Không thể import dữ liệu!").append("\n");
            stb.append(msg).append("\n");
            return false;
        } else {
            return true;
        }
    }

    public boolean isCellTypeInt(XSSFCell cell, StringBuilder errorMessage, String message) {
        if (cell == null) {
            errorMessage.append(message).append(" Cell is null.\n");
            return false;
        }

        switch (cell.getCellType()) {
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    errorMessage.append(message).append(" Date is not allowed.\n");
                    return false;
                }
                return true;
            case STRING:
            try {
                Integer.parseInt(cell.getStringCellValue());
                return true;
            } catch (NumberFormatException e) {
                errorMessage.append(message).append(" Cannot parse string to int.\n");
                return false;
            }
            case BLANK:
                errorMessage.append(message).append(" Cell is blank.\n");
                return false;
            default:
                errorMessage.append(message).append(" Unsupported cell type.\n");
                return false;
        }
    }

//    public Boolean isCellTypeInt(Cell cell, StringBuilder stb, String msg) {
//        // CHECK TYPE
//        double cellValue = cell.getNumericCellValue();
//        if (cell.getCellType() == CellType.NUMERIC && (cellValue % 1) == 0 && cellValue > 0) {
//            return true;
//        } else {
//            stb.append("Không thể import dữ liệu!").append("\n");
//            stb.append(msg).append("\n");
//            return false;
//        }
//        //    stb.append("Data nhập vào phải là kiểu số nguyên dương!").append("\n");
//    }
    
    public Boolean isCellTypeString(Cell cell, StringBuilder stb, String msg) {
        // CHECK TYPE
        if (cell.getCellType() == CellType.STRING) {
            return true;
        } else {
            stb.append("Không thể import dữ liệu!").append("\n");
            stb.append(msg).append("\n");
            return false;
        }
    }
    
    //JAVA VALIDATE
    public Boolean validPassCode(JPasswordField jpass, String msg, StringBuilder stb) {
        String passCode = new String(jpass.getPassword());
        if (passCode.matches("\\d{3}")) {
            return true;
        } else {
            if (stb != null) {
                stb.append("Mật khẩu sai định dạng!").append("\n");
                stb.append(msg);
            }
            return false;
        }
    }
    
    public Boolean isDateSelected(JDateChooser calendar, StringBuilder stb, String msg) {
        Date date = calendar.getDate();
        if (date == null) {
            calendar.setBackground(Color.yellow);
            stb.append(msg).append("\n");
            return false;
        }
        return true;
    }
    
    public Boolean isDateValid(JDateChooser calendar, StringBuilder stb, String msg) {
        Date date = calendar.getDate();
        if (date == null) {
            calendar.setBackground(Color.yellow);
            stb.append(msg).append("\n").append("Không được để trống ngày!").append("\n");
            return false;
        }

        LocalDate selectedDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate currentDate = LocalDate.now();

        if (selectedDate.isBefore(currentDate)) {
            calendar.setBackground(Color.yellow);
            stb.append(msg).append("Ngày tạo hóa đơn phải từ hôm nay trở đi!").append("\n");
            return false;
        }

        calendar.setBackground(Color.white);
        return true;
    }
    
    public Boolean isRadioButtonSelected(ButtonGroup buttonGroup, StringBuilder stb, String msg) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                return true;
            }
        }
        stb.append(msg).append("\n");
        return false;
    }

    public Boolean isEmpty(JTextField txt, StringBuilder stb, String msg) {
        if (txt.getText().trim().isEmpty()) {
            txt.setBackground(Color.YELLOW);
            stb.append(msg).append("\n");
            return false;
        }
        txt.setBackground(Color.WHITE);
        return true;
    }
    
    public Boolean isEmpty(JTextArea txt, StringBuilder stb, String msg) {
        if (txt.getText().trim().isEmpty()) {
            txt.setBackground(Color.YELLOW);
            stb.append(msg).append("\n");
            return false;
        }
        txt.setBackground(Color.WHITE);
        return true;
    }

    public Boolean isNumber(JTextField txt, StringBuilder stb, String msg, int type) {
        if (!isEmpty(txt, stb, msg)) {
            return false;
        } else {
            try {
                if (type > 0) {
                    double numberD = Double.parseDouble(txt.getText().trim());
                } else {
                    int numberI = Integer.parseInt(txt.getText().trim());
                }
            } catch (Exception e) {
                txt.setBackground(Color.YELLOW);
                stb.append(msg).append("\n");
                return false;
            }
        }
        txt.setBackground(Color.WHITE);
        return true;
    }

    public Boolean NumberLimit(JTextField txt, StringBuilder stb, String msg, int type, int min) {
        if (!isNumber(txt, stb, msg, type)) {
            return false;
        } else {
            int NumberI = Integer.parseInt(txt.getText().trim());
            if (NumberI < min) {
                txt.setBackground(Color.YELLOW);
                stb.append(msg).append("\n");
                return false;
            }
        }
        txt.setBackground(Color.WHITE);
        return true;
    }
    
    public Boolean isPhoneNumber(JTextField txt, String msg, StringBuilder stb) {
        if (!isEmpty(txt, stb, msg)) {
            return false;
        } else {
            String phone = "\\d{10}";
            if (txt.getText().trim().matches(phone)) {
                txt.setBackground(Color.WHITE);
                return true;
            } else {
                txt.setBackground(Color.YELLOW);
                stb.append(msg).append("\n");
                return false;
            }
        }
    }
    
    public Boolean isNumberGreater(JTextField txt, String msg, StringBuilder stb, int type, int min) {
        if (!isNumber(txt, stb, msg, type)) {
            return false;
        } else {
            double number = Double.parseDouble(txt.getText().trim());
            if (number < min) {
                txt.setBackground(Color.YELLOW);
                stb.append(msg).append("\n");
                return false;
            }
        }
        txt.setBackground(Color.WHITE);
        return true;
    }

}
