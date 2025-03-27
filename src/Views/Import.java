/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Views;

import Models.SanPham;
import Services.SanPhamService;
import Validator.MyValidate;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author X1
 */
public class Import extends javax.swing.JFrame {
    SanPhamService spService = new SanPhamService();
    DecimalFormat formatter = new DecimalFormat("#,###");
    
    /**
     * Creates new form Import
     */
    
    public Import() {
        initComponents();
        setLocationRelativeTo(null);
    }
    
    //VALIDATE
//    public boolean validateExcelDataByColumn(XSSFRow excelRow) {
//        StringBuilder stb = new StringBuilder();
//        MyValidate v = new MyValidate();
//
//        int checkCell = Math.min(excelRow.getLastCellNum(), 8);
//        boolean isValid = true;
//
//        boolean[] columnInt = {true, true, true, true, true, false, false, true};
//
//        for (int cellIndex = 0; cellIndex < checkCell; cellIndex++) {
//            XSSFCell cell = excelRow.getCell(cellIndex, XSSFRow.MissingCellPolicy.CREATE_NULL_AS_BLANK);
//            boolean cellValid = v.isNotEmptyCell(cell, stb, "Cột " + (cellIndex + 1) + " đang bị trống!\n");
//
//            if (!cellValid) {
//                isValid = false;
//                continue;
//            }
//
//            if (cellIndex >= columnInt.length) {
//                stb.append("Cột ").append(cellIndex + 1).append(" vượt quá giới hạn kiểm tra.\n");
//                isValid = false;
//                break;
//            }
//
//            if (columnInt[cellIndex]) {
//                if (!v.isCellTypeInt(cell, stb, "Lỗi định dạng, data nhập vào phải là kiểu số nguyên dương!")) {
//                    isValid = false;
//                }
//            } else {
//                if (!v.isCellTypeString(cell, stb, "Lỗi định dạng, data nhập vào phải là 1 chuỗi ký tự!")) {
//                    isValid = false;
//                }
//            }
//        }
//        return isValid;
//    }
      
    public boolean validateData() {
        int pos = tblImportSP.getSelectedRow();

        Integer excelMaSP = (Integer) tblImportSP.getValueAt(pos, 0);
        Integer excelMaDM = (Integer) tblImportSP.getValueAt(pos, 1);
        Integer excelMaMau = (Integer) tblImportSP.getValueAt(pos, 2);
        Integer excelMaSize = (Integer) tblImportSP.getValueAt(pos, 3);
        Double excelGiaNhap = (Double) tblImportSP.getValueAt(pos, 4);
        Double excelGiaBan = (Double) tblImportSP.getValueAt(pos, 5);
        String excelTrangThai = (String) tblImportSP.getValueAt(pos, 6);
        String excelTenSP = (String) tblImportSP.getValueAt(pos, 7);
        Integer excelSLT = (Integer) tblImportSP.getValueAt(pos, 8);

        if (spService.checkId(excelMaSP)) {
            JOptionPane.showMessageDialog(this, "Id sản phẩm bị trùng, không thể thêm!", "POLYPOLO thông báo", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!(excelMaSP instanceof Integer) || excelMaSP <= 0) {
            JOptionPane.showMessageDialog(this, "Sai định dạng, Id phải là số nguyên dương!", "POLYPOLO thông báo", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!spService.checkName(excelTenSP)) {
            JOptionPane.showMessageDialog(this, "Tên sản phẩm bị trùng, không thể thêm!", "POLYPOLO thông báo", 0);
            return false;
        }
        if (!spService.checkIdCat(excelMaDM)) {
            JOptionPane.showMessageDialog(this, "Mã danh mục không tồn tại, không thể thêm!", "POLYPOLO thông báo", 0);
            return false;
        }
        if (!(excelMaDM instanceof Integer) || excelMaDM <= 0) {
            JOptionPane.showMessageDialog(this, "Sai định dạng, mã danh mục phải là số nguyên dương!", "POLYPOLO thông báo", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!spService.checkIdColor(excelMaMau)) {
            JOptionPane.showMessageDialog(this, "Mã màu không tồn tại, không thể thêm!", "POLYPOLO thông báo", 0);
            return false;
        }
        if (!(excelMaMau instanceof Integer) || excelMaMau <= 0) {
            JOptionPane.showMessageDialog(this, "Sai định dạng, mã màu phải là số nguyên dương!", "POLYPOLO thông báo", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!spService.checkIdSz(excelMaSize)) {
            JOptionPane.showMessageDialog(this, "Mã size không tồn tại, không thể thêm!", "POLYPOLO thông báo", 0);
            return false;
        }
        if (!(excelMaSize instanceof Integer) || excelMaSize <= 0) {
            JOptionPane.showMessageDialog(this, "Sai định dạng, mã size phải là số nguyên dương!", "POLYPOLO thông báo", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!(excelGiaNhap instanceof Double) || excelGiaNhap <= 0) {
            JOptionPane.showMessageDialog(this, "Giá nhập phải là số và lớn hơn 0!", "POLYPOLO thông báo", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!(excelGiaBan instanceof Double) || excelGiaBan <= 0) {
            JOptionPane.showMessageDialog(this, "Giá bán phải là số và lớn hơn 0!", "POLYPOLO thông báo", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!(excelTrangThai instanceof String)) {
            JOptionPane.showMessageDialog(this, "Trạng thái phải là chuỗi ký tự!", "POLYPOLO thông báo", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!(excelSLT instanceof Integer) || (Integer) excelSLT < 0) {
            JOptionPane.showMessageDialog(this, "Sai định dạng, số lượng phải là số nguyên không âm!", "POLYPOLO thông báo", JOptionPane.ERROR_MESSAGE);
            return false;
        } else {
            return true;
        }
    }
    
    void importExcelToTable() {
        DefaultTableModel model = (DefaultTableModel) tblImportSP.getModel();
        model.setRowCount(0);

        File excelFile;
        FileInputStream fis = null;
        BufferedInputStream excelBIS = null;
        XSSFWorkbook excelImportToJTable = null;
        String defaultCurrentDirectoryPath = "C:\\Users\\X1\\OneDrive\\Documents\\Custom Office Templates";
        JFileChooser excelFileChooser = new JFileChooser(defaultCurrentDirectoryPath);
        excelFileChooser.setDialogTitle("Lựa File Excel");
        FileNameExtensionFilter fnef = new FileNameExtensionFilter("EXCEL FILES", "xls", "xlsx", "xlsm");
        excelFileChooser.setFileFilter(fnef);
        int excelChooser = excelFileChooser.showOpenDialog(null);

        if (excelChooser == JFileChooser.APPROVE_OPTION) {
            try {
                excelFile = excelFileChooser.getSelectedFile();
                txtFilePath.setText(excelFile.toString());
                fis = new FileInputStream(excelFile);
                excelBIS = new BufferedInputStream(fis);
                excelImportToJTable = new XSSFWorkbook(excelBIS);
                XSSFSheet excelSheet = excelImportToJTable.getSheetAt(0);

                for (int row = 1; row <= excelSheet.getLastRowNum(); row++) { //START FROM ROW 2
                    XSSFRow excelRow = excelSheet.getRow(row);

                    if (excelRow != null) {
                        boolean isEmptyRow = true;

                        for (int cellNum = 0; cellNum < excelRow.getLastCellNum(); cellNum++) {
                            XSSFCell cell = excelRow.getCell(cellNum, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                            if (cell != null && cell.getCellType() != CellType.BLANK) {
                                isEmptyRow = false;
                                break;
                            }
                        }

                        if (!isEmptyRow) {
                            Integer excelMaSP = (int) excelRow.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getNumericCellValue();
                            Integer excelMaDM = (int) excelRow.getCell(1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getNumericCellValue();
                            Integer excelMaMau = (int) excelRow.getCell(2, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getNumericCellValue();
                            Integer excelMaSize = (int) excelRow.getCell(3, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getNumericCellValue();
                            Double excelGiaNhap = excelRow.getCell(4, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getNumericCellValue();
                            Double excelGiaBan = excelRow.getCell(5, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getNumericCellValue();         
                            String excelTrangThai = excelRow.getCell(6, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();
                            String excelTenSP = excelRow.getCell(7, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();
                            Integer excelSLT = (int) excelRow.getCell(8, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getNumericCellValue();

                            String giaN = formatter.format(excelGiaNhap);
                            String giaB = formatter.format(excelGiaBan);
                            model.addRow(new Object[]{excelMaSP, excelMaDM, excelMaMau, excelMaSize, giaN, giaB, excelTrangThai, excelTenSP, excelSLT});
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fis != null) {
                        fis.close();
                    }
                    if (excelBIS != null) {
                        excelBIS.close();
                    }
                    if (excelImportToJTable != null) {
                        excelImportToJTable.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    //GETMODEL
    public SanPham getModelSP() {
        int pos = tblImportSP.getSelectedRow();
        
        if (pos < 0) {
            return null;
        } 
        return new SanPham(
                Integer.valueOf(spService.getListSP().get(spService.getListSP().size() - 1).getMaSP() + 1),
                (String) tblImportSP.getValueAt(pos, 7), // tenSP
                (Integer) tblImportSP.getValueAt(pos, 1), // maDM
                (String) tblImportSP.getValueAt(pos, 6), // trangT
                (Double) tblImportSP.getValueAt(pos, 4), // giaN
                (Double) tblImportSP.getValueAt(pos, 5), // giaB
                (Integer) tblImportSP.getValueAt(pos, 3), // maSz
                (Integer) tblImportSP.getValueAt(pos, 2), // maMau
                (Integer) tblImportSP.getValueAt(pos, 8) // soL
        );
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtFilePath = new javax.swing.JTextField();
        btnChooseFile = new javax.swing.JButton();
        btnImport = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblImportSP = new javax.swing.JTable();
        btnImport1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnChooseFile.setText("Choose File");
        btnChooseFile.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnChooseFileMouseClicked(evt);
            }
        });

        btnImport.setText("IMPORT");
        btnImport.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnImportMouseClicked(evt);
            }
        });

        tblImportSP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "MaSP", "MDM", "Mã Màu", "Mã Size", "Giá Nhập", "Giá Bán", "Trạng Thái", "Tên SP", "Số Lượng"
            }
        ));
        jScrollPane1.setViewportView(tblImportSP);

        btnImport1.setText("IMPORT");
        btnImport1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnImport1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtFilePath, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(btnChooseFile))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(btnImport, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnImport1, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 848, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFilePath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnChooseFile))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnImport))
                    .addComponent(btnImport1))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnChooseFileMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnChooseFileMouseClicked
        // IMPORT
        importExcelToTable();
    }//GEN-LAST:event_btnChooseFileMouseClicked

    private void btnImportMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnImportMouseClicked
        // ADD
        try {
            if (validateData()) {
                JOptionPane.showMessageDialog(this, spService.addImport(getModelSP()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnImportMouseClicked

    private void btnImport1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnImport1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnImport1MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        FlatMacLightLaf.setup();
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Import().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChooseFile;
    private javax.swing.JButton btnImport;
    private javax.swing.JButton btnImport1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblImportSP;
    private javax.swing.JTextField txtFilePath;
    // End of variables declaration//GEN-END:variables
}
