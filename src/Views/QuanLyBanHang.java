/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package Views;

import Models.DanhMuc;
import Models.HoaDon;
import Models.HoaDonChiTiet;
import Models.MyReceipts;
import Models.NhanSu;
import Models.SanPhamChiTiet;
import Services.HoaDonService;
import Services.UserService;
import Validator.MyValidate;
import ViewModels.HD_GioHangViewModel;
import ViewModels.HD_HoaDonViewModel;
import ViewModels.HD_InvoiceViewModel;
import ViewModels.HD_SanPhamViewModel;
import ViewModels.UserViewModel;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.border.DashedBorder;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.layout.LayoutArea;
import com.itextpdf.layout.layout.LayoutContext;
import com.itextpdf.layout.layout.LayoutResult;
import com.itextpdf.layout.property.TextAlignment;
import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import javax.swing.DefaultComboBoxModel;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author X1
 */
public class QuanLyBanHang extends javax.swing.JInternalFrame {
    HoaDonService hdService = new HoaDonService();
    UserService uService = new UserService();
    DecimalFormat formatter = new DecimalFormat("#,###");
    
    /**
     * Creates new form QuanLyBanHang
     */
    
    public QuanLyBanHang() {
        initComponents();
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);
        load();
        validateFalse();
    }
    
    NumberFormat format = NumberFormat.getNumberInstance(new Locale("vi", "VN"));
    
    //<editor-fold defaultstate="collapsed" desc=" LOAD ">
    void loadTableGioHang(ArrayList<HD_GioHangViewModel> ls){
        DefaultTableModel model = (DefaultTableModel) tblHoaDonChiTiet.getModel();
        model.setRowCount(0);
        
        for (HD_GioHangViewModel gioHang : ls) {
            String formattedDonGia = format.format(gioHang.getDonGia());
            String formattedThanhTien = format.format(gioHang.getThanhTien());
        //    String formattedDonGia = formatter.format(gioHang.getDonGia());
        //    String formattedThanhTien = formatter.format(gioHang.getThanhTien());
            
            model.addRow(new Object []{
                gioHang.getMaHDCT(), gioHang.getMaSP(), gioHang.getTenSP(), gioHang.getMauSac()
                    , gioHang.getKichCo(), gioHang.getSoLuong()
                    , formattedDonGia, formattedThanhTien
            });
        }
    }
    void loadTableSanPham(ArrayList<HD_SanPhamViewModel> ls){
        DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
        model.setRowCount(0);
        for (HD_SanPhamViewModel sanPham : ls) {
            String formattedDonGia = formatter.format(sanPham.getDonGia());
            
            model.addRow(new Object []{
                sanPham.getMaSP(), sanPham.getTenSP(), sanPham.getTenDM()
                    , sanPham.getMaSac(), sanPham.getKichCo()
                    , formattedDonGia, sanPham.getSoLuong(), sanPham.getTrangThai()
            });
        }
    }
    void loadComboBoxSanPham(ArrayList<DanhMuc> ls) {
        DefaultComboBoxModel cboModel = (DefaultComboBoxModel) cboDanhMuc.getModel();
        HashSet<String> dmSet = new HashSet<>();

        for (DanhMuc dm : ls) {
            String sanPham = dm.getTenDM();
            if (!dmSet.contains(sanPham)) {
                cboModel.addElement(sanPham);
                dmSet.add(sanPham);
            }
        }
    }
    void loadComboBoxtrangThai(ArrayList<HoaDon> ls){
        DefaultComboBoxModel cboModel = (DefaultComboBoxModel) cboTrangThai.getModel();
        HashSet<String> trangThaiSet = new HashSet<>();

        for (HoaDon hoaDon : ls) {
            String trangThai = hoaDon.getTrangThai();
            if (!trangThaiSet.contains(trangThai)) {
                cboModel.addElement(trangThai);
                trangThaiSet.add(trangThai);
            }
        }
    }
    void loadComboBoxtenNV(ArrayList<NhanSu> ls){
        DefaultComboBoxModel cboModel = (DefaultComboBoxModel) cboTenNV.getModel();
        cboModel.removeAllElements();
        for (NhanSu nv : ls) {
            cboModel.addElement(nv.getTenNhanVien());
        }
    }
    void loadComboBoxPhuongThuc(ArrayList<HoaDon> ls){
        DefaultComboBoxModel cboModel = (DefaultComboBoxModel) cboPhuongThuc.getModel();
        HashSet<String> phuongThucSet = new HashSet<>();

        for (HoaDon hoaDon : ls) {
            String phuongThuc = hoaDon.getPhuongThuc();
            if (!phuongThucSet.contains(phuongThuc)) {
                cboModel.addElement(phuongThuc);
                phuongThucSet.add(phuongThuc);
            }
        }
    }
    //</editor-fold>
    
    void load(){
        loadComboBoxPhuongThuc(hdService.getList());
        loadTableHoaDonView(hdService.getListHoaDon());
        loadTableSanPham(hdService.getListSanPham());
        loadComboBoxtenNV(hdService.getListTenNV()); 
        loadComboBoxtrangThai(hdService.getList());
        loadComboBoxSanPham(hdService.getListDM());
    }
    void loadTableHoaDonView(ArrayList<HD_HoaDonViewModel> ls){
        DefaultTableModel model = (DefaultTableModel) tblHoaDon.getModel();
        model.setRowCount(0);
        for (HD_HoaDonViewModel hoaDon : ls) {
            
            model.addRow(new Object []{
                hoaDon.getMaHD(), hoaDon.getTenNV(), hoaDon.getTenKH(), hoaDon.getPhuongThuc(),
                hoaDon.getNgayLap(), hoaDon.getTrangThai()
            });
        }
    }
    void loadTableHoaDonToForm(int maHD) {
        HD_HoaDonViewModel hoaDonViewDetails = hdService.getListHDById(maHD);
        
        if (hoaDonViewDetails != null) {
            cboTrangThai.setSelectedItem(hoaDonViewDetails.getTrangThai());
            cboTenNV.setSelectedItem(hoaDonViewDetails.getTenNV());
            txtTenKH.setText(hoaDonViewDetails.getTenKH());
            txtSDT.setText(hoaDonViewDetails.getSoDT());
            cboPhuongThuc.setSelectedItem(hoaDonViewDetails.getPhuongThuc());
            dcsNgayLap.setDate(hoaDonViewDetails.getNgayLap());
        }
    }
    
    public static UserViewModel u;

    private String d;

    public String Data(String d) {
        this.d = d;
        return d;
    }
    
    //<editor-fold defaultstate="collapsed" desc=" MyValidate ">
    //VALIDATE
    public Boolean validateTel() {
        StringBuilder stb = new StringBuilder();
        MyValidate v = new MyValidate();

        v.isEmpty(txtSDT, stb, "Chưa nhập số điện thoại để tìm!");
        v.isPhoneNumber(txtSDT, "Vui lòng nhập đúng định dạng SĐT!", stb);

        if (stb.length() > 0) {
            JOptionPane.showMessageDialog(this, stb);
            return false;
        } else {
            return true;
        }
    }
    public Boolean validateHoaDon(){
        StringBuilder stb = new  StringBuilder();
        MyValidate v = new MyValidate();
        
        v.isEmpty(txtTenKH, stb, "Chưa nhập tên khách hàng!");
        v.isDateSelected(dcsNgayLap, stb, "Chưa chọn ngày tạo hóa đơn!");
        v.isDateValid(dcsNgayLap, stb, "Ngày tạo hóa đơn không hợp lệ!\n");
        if (stb.length()>0) {
            JOptionPane.showMessageDialog(this, stb);
            validateFalse();
            return false;
        }else{
            return true;
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc=" Clear form">
    //CLEAR FORM
    void clearForm(){
        cboTenNV.setSelectedItem("Loi Choi");
        txtTenKH.setText("");
        txtSDT.setText("");
        cboPhuongThuc.setSelectedItem("Chuyển khoản");
        lblTongTien.setText("0");
        dcsNgayLap.setDate(null);
    }
    void clearGioHang() {
        DefaultTableModel model = (DefaultTableModel) tblHoaDonChiTiet.getModel();
        model.setRowCount(0);
    }
    void clearData(){
        txtTenKH.setBackground(Color.white);
        txtSDT.setBackground(Color.WHITE);
        dcsNgayLap.setBackground(Color.white);
    }
    //</editor-fold>
    //CHECK TRANGTHAI
    Boolean validateTrue(){
        btnThanhToan.setEnabled(true);
        btnInHD.setEnabled(true);
        btnXoaSanPham.setEnabled(true);
        return true;
    }
    Boolean validateFalse(){
        btnThanhToan.setEnabled(false);
        btnXoaSanPham.setEnabled(false);
        return true;
    }
    //GET MODEL
    public HoaDon getModel(){
        java.util.Date utilDate = dcsNgayLap.getDate();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
   
        String tenNv = cboTenNV.getSelectedItem().toString();
        Integer maNv = hdService.getIdByName(tenNv).getMaNhanVien();
        String tenKh = txtTenKH.getText();
        String phuongThuc = cboPhuongThuc.getSelectedItem().toString();
        
        HoaDon hd = new HoaDon(maNv, tenNv, tenKh, phuongThuc, sqlDate);
        return hd;
    }
    public HoaDon getModelThanhToan(){
        HoaDon hd = new HoaDon();
        
        java.util.Date utilDate = dcsNgayLap.getDate();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        
        int pos = tblHoaDon.getSelectedRow();
        hd.setMaHD((Integer) tblHoaDon.getValueAt(pos, 0));
        hd.setTenNV(cboTenNV.getSelectedItem().toString());
        hd.setTenKH(txtTenKH.getText());
        hd.setSoDT(txtSDT.getText());
        hd.setPhuongThuc(cboPhuongThuc.getSelectedItem().toString());
        lblTongTien.setText(hdService.getTotal(pos).getTongTien().toString());
        hd.setNgayLap(sqlDate);
        
        return hd;
    }
    
    //INVOICE ELEMENTS
    static Cell storeCell(String txt) {
        return new Cell().add(txt).setFontSize(15f).setBold().setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT);
    }
    static Cell get10fLeftCell(String txt, Boolean isBold) {
        Cell customerCell = new Cell().add(txt).setFontSize(12f).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT);
        return isBold ? customerCell.setBold():customerCell;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblHoaDon = new javax.swing.JTable();
        btnSearchHD = new javax.swing.JButton();
        cboTrangThai = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        btnXoaSanPham = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblHoaDonChiTiet = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtSDT = new javax.swing.JTextField();
        txtTenKH = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        cboPhuongThuc = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        btnTaoHD = new javax.swing.JButton();
        btnMoi = new javax.swing.JButton();
        btnThanhToan = new javax.swing.JButton();
        btnXuatDS = new javax.swing.JButton();
        btnInHD = new javax.swing.JButton();
        lblTongTien = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        cboTenNV = new javax.swing.JComboBox<>();
        jSeparator1 = new javax.swing.JSeparator();
        btnSearchKH = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        dcsNgayLap = new com.toedter.calendar.JDateChooser();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        btnSearch = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtSearch1 = new javax.swing.JTextField();
        cboDanhMuc = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        btnFilterSP = new javax.swing.JButton();

        setMaximumSize(new java.awt.Dimension(990, 610));
        setMinimumSize(new java.awt.Dimension(990, 610));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP)), "Danh sách Hóa Đơn", javax.swing.border.TitledBorder.LEADING, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Segoe UI", 3, 14))); // NOI18N

        tblHoaDon.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        tblHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Tên NV", "Tên KH", "PTTT", "Ngày Lập", "Trạng Thái"
            }
        ));
        tblHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHoaDonMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblHoaDon);

        btnSearchHD.setText("SEARCH");
        btnSearchHD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSearchHDMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 524, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(cboTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSearchHD, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSearchHD)
                    .addComponent(cboTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(8, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP)), "Giỏ hàng\n", javax.swing.border.TitledBorder.LEADING, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Segoe UI", 3, 14))); // NOI18N

        btnXoaSanPham.setText("XÓA SẢN PHẨM");
        btnXoaSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnXoaSanPhamMouseClicked(evt);
            }
        });

        tblHoaDonChiTiet.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "IDSP", "Sản Phẩm", "Màu Sắc", "Kích Cỡ", "Số Lượng", "Đơn Giá", "Thành Tiền"
            }
        ));
        tblHoaDonChiTiet.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHoaDonChiTietMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblHoaDonChiTiet);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 524, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnXoaSanPham)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(btnXoaSanPham)
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tạo Hóa Đơn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Segoe UI", 3, 16), new java.awt.Color(0, 0, 204))); // NOI18N
        jPanel2.setMaximumSize(new java.awt.Dimension(401, 409));
        jPanel2.setMinimumSize(new java.awt.Dimension(401, 409));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel3.setText("Tên KH:");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel6.setText("Tổng HĐ:");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel7.setText("Người Tạo:");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel13.setText("Hình thức TT:");

        jPanel5.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        btnTaoHD.setText("TẠO HĐ");
        btnTaoHD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTaoHDMouseClicked(evt);
            }
        });

        btnMoi.setText("MỚI");
        btnMoi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnMoiMouseClicked(evt);
            }
        });

        btnThanhToan.setText("THANH TOÁN");
        btnThanhToan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnThanhToanMouseClicked(evt);
            }
        });

        btnXuatDS.setText("XUẤT DANH SÁCH");
        btnXuatDS.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnXuatDSMouseClicked(evt);
            }
        });

        btnInHD.setText("IN HÓA ĐƠN");
        btnInHD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnInHDMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(32, Short.MAX_VALUE)
                .addComponent(btnTaoHD)
                .addGap(18, 18, 18)
                .addComponent(btnThanhToan)
                .addGap(18, 18, 18)
                .addComponent(btnMoi)
                .addGap(25, 25, 25))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addComponent(btnXuatDS)
                .addGap(18, 18, 18)
                .addComponent(btnInHD, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTaoHD)
                    .addComponent(btnMoi)
                    .addComponent(btnThanhToan))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnXuatDS)
                    .addComponent(btnInHD))
                .addContainerGap())
        );

        lblTongTien.setText("0");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel14.setText("Ngày Lập:");

        btnSearchKH.setText("SEARCH");
        btnSearchKH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSearchKHMouseClicked(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 3, 13)); // NOI18N
        jLabel5.setText("SĐT Khách:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jSeparator1)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel6))
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(0, 2, Short.MAX_VALUE)
                                .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnSearchKH))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cboTenNV, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(lblTongTien, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(cboPhuongThuc, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(dcsNgayLap, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearchKH)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(cboTenNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(cboPhuongThuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTongTien)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dcsNgayLap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP)), "Danh sách Sản Phẩm\n", javax.swing.border.TitledBorder.LEADING, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Segoe UI", 3, 14))); // NOI18N

        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "IDSP", "Sản Phẩm", "Danh Mục", "Màu Sắc", "Kích Cỡ", "Đơn Giá", "Số Lượng", "Trạng Thái"
            }
        ));
        tblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblSanPham);

        btnSearch.setText("SEARCH");
        btnSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSearchMouseClicked(evt);
            }
        });

        jLabel2.setText("Tìm kiếm theo tên:");

        jLabel1.setText("Lọc:");

        btnFilterSP.setText("SEARCH");
        btnFilterSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnFilterSPMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cboDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnFilterSP, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(txtSearch1, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(txtSearch1)
                        .addComponent(btnSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cboDanhMuc)
                            .addComponent(jLabel1)
                            .addComponent(btnFilterSP))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 381, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(40, 40, 40))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonMouseClicked
        // HOADON CLICK
        DecimalFormat format = new DecimalFormat("#,###");
        
        int pos = tblHoaDon.getSelectedRow();
        if (pos != -1) {
            Integer maHD = (Integer) tblHoaDon.getValueAt(pos, 0);
            double tongTien = hdService.getTotal(maHD).getTongTien();
            String formattedTongTien = format.format(tongTien);
            lblTongTien.setText(formattedTongTien);
            
            String trangThai = (String) tblHoaDon.getValueAt(pos, 5);
            if (trangThai.equalsIgnoreCase("Đã thanh toán")) {
                validateFalse();
                btnInHD.setEnabled(true);
            } else {
                validateTrue();
            }
            loadTableGioHang(hdService.getListGioHangById(maHD));
            loadTableHoaDonToForm(maHD);
        }

    }//GEN-LAST:event_tblHoaDonMouseClicked

    private void btnThanhToanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThanhToanMouseClicked
        if (validateHoaDon()) {
            String result = hdService.thanhToan(getModelThanhToan());
            JOptionPane.showMessageDialog(this, result);
            loadTableHoaDonView(hdService.getListHoaDon());
            clearForm();
            clearGioHang();
        }
    }//GEN-LAST:event_btnThanhToanMouseClicked

    private void btnTaoHDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTaoHDMouseClicked
        //ADD HOADON
        int result = JOptionPane.showConfirmDialog(this, "Bạn muốn tạo hóa đơn?", "POLYPOLO xác nhận", JOptionPane.YES_NO_OPTION);

        if (validateHoaDon() && result == JOptionPane.YES_OPTION) {
            validateTrue();
            String kq = hdService.add(getModel());
            JOptionPane.showMessageDialog(this, kq);
            //LOAD LIST CHX THANHTOANN
            loadTableHoaDonView(hdService.getListByTrangThai("Chưa thanh toán"));
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Đã hủy thao tác tạo hóa đơn!", "POLYPOLO thông báo", 0);
        }
    }//GEN-LAST:event_btnTaoHDMouseClicked

    private void btnSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSearchMouseClicked
        // SEARCH
        String name = txtSearch1.getText();
        ArrayList<HD_SanPhamViewModel> ls = hdService.getListBySearchName(name);
        loadTableSanPham(ls);
    }//GEN-LAST:event_btnSearchMouseClicked

    private void tblSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMouseClicked
        // CLICK SANPHAM -> GIOHANG
        if (tblHoaDon.getSelectedRow() != -1) {
            String soL = JOptionPane.showInputDialog(this, "Nhập số lượng SP muốn mua: ", "POLYPOLO xác nhận", 0);
            if (soL != null && !soL.isEmpty()) {
                int soLuongMua = Integer.parseInt(soL);

                int pos = tblSanPham.getSelectedRow();
                HD_SanPhamViewModel sp = hdService.getListSanPham().get(pos);

                if (sp.getSoLuong() >= soLuongMua) {
                    int posHD = tblHoaDon.getSelectedRow();
                    Integer maHD = Integer.valueOf(tblHoaDon.getValueAt(posHD, 0).toString());
                    Integer maSP = (Integer) tblSanPham.getValueAt(pos, 0);
                    String giaV = tblSanPham.getValueAt(pos, 5).toString();
                    Double gia = Double.parseDouble(giaV);
                    String formatGia = formatter.format(gia);
                    
                    if (hdService.checkSPExists(maHD, maSP)) {
                        hdService.mergeSP(soLuongMua, maHD, maSP);
                    } else {
                        HoaDonChiTiet hdct = new HoaDonChiTiet(maHD, maSP, soLuongMua, gia);
                        JOptionPane.showMessageDialog(this, hdService.addHDCT(hdct));
                    }

                    hdService.updateSP(sp.getSoLuong() - soLuongMua, maSP);

                    loadTableGioHang(hdService.getListGioHangById(maHD));
                    loadTableSanPham(hdService.getListSanPham());

                    Double tongTien = hdService.getTotal(maHD).getTongTien();
                    String formattedTongTien = formatter.format(tongTien);
                    lblTongTien.setText(formattedTongTien);
                } else {
                    JOptionPane.showMessageDialog(this, "Số lượng sản phẩm hiện tại không đủ!", "POLYPOLO thông báo", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Bạn chưa nhập số lượng!", "POLYPOLO thông báo", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Bạn cần chọn hoặc tạo hóa đơn mới trước khi thêm sản phẩm vào giỏ hàng!", "POLYPOLO thông báo", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_tblSanPhamMouseClicked

    private void btnMoiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMoiMouseClicked
        // RESET
        clearForm();
        clearData();
        clearGioHang();
        JOptionPane.showInternalMessageDialog(this, "Làm mới thành công!", "POLYPOLO thông báo!", 0);
        loadTableHoaDonView(hdService.getListHoaDon());
        validateFalse();
        btnInHD.setEnabled(false);
    }//GEN-LAST:event_btnMoiMouseClicked

    private void tblHoaDonChiTietMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonChiTietMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblHoaDonChiTietMouseClicked

    private void btnSearchHDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSearchHDMouseClicked
        //SEARCH
        if (cboTrangThai.getSelectedItem().equals("Đã thanh toán")) {
            loadTableHoaDonView(hdService.getListByTrangThai("Đã thanh toán"));
            validateFalse();
            btnInHD.setEnabled(true);
        } else {
            loadTableHoaDonView(hdService.getListByTrangThai("Chưa thanh toán"));
            validateTrue();
        }
    }//GEN-LAST:event_btnSearchHDMouseClicked

    private void btnXoaSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXoaSanPhamMouseClicked
        //DELETE SP
        int pos = tblHoaDonChiTiet.getSelectedRow();
        int posHD = tblHoaDon.getSelectedRow();
        if (pos == -1) {
            JOptionPane.showMessageDialog(this, "Hãy chọn sản phẩm từ giỏ hàng để xóa!", "POLYPOLO thông báo", JOptionPane.WARNING_MESSAGE);
        }else{
            Integer maHDCT = Integer.valueOf(tblHoaDonChiTiet.getValueAt(pos, 0).toString());
            Integer maSPCT = Integer.valueOf(tblHoaDonChiTiet.getValueAt(pos, 1).toString());
            Integer soL = Integer.valueOf(tblHoaDonChiTiet.getValueAt(pos, 5).toString());
            Integer maHD = Integer.valueOf(tblHoaDon.getValueAt(posHD, 0).toString());
            JOptionPane.showMessageDialog(this, hdService.deleteProduct(maHDCT));    
            
            SanPhamChiTiet spct = hdService.getById(maSPCT);
            spct.setSoLuong(spct.getSoLuong()+soL);
            
            hdService.updateSPTon(spct.getSoLuong(), maSPCT);
            
            loadTableGioHang(hdService.getListGioHangById(maHD));
            loadTableSanPham(hdService.getListSanPham());
        }
    }//GEN-LAST:event_btnXoaSanPhamMouseClicked

    private void btnSearchKHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSearchKHMouseClicked
        //SEARCH
        if (validateTel()) {
            String soDT = txtSDT.getText();
            loadTableHoaDonView(hdService.searchTel(soDT));
        }
    }//GEN-LAST:event_btnSearchKHMouseClicked

    private void btnFilterSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnFilterSPMouseClicked
        // FILTER
        String danhMuc = cboDanhMuc.getSelectedItem().toString();
        loadTableSanPham(hdService.getListByDanhMuc(danhMuc));
    }//GEN-LAST:event_btnFilterSPMouseClicked

    private void btnInHDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnInHDMouseClicked
        // IN HOADON
        Integer pos = tblHoaDon.getSelectedRow();
        Integer maHD = (Integer) tblHoaDon.getValueAt(pos, 0);
        String trangT = (String) tblHoaDon.getValueAt(pos, 5);
        String path = "C:\\Users\\X1\\OneDrive\\Documents\\Custom Office Templates\\invoice.pdf";
        String fontPath = "C:\\Users\\X1\\OneDrive\\Documents\\resouces\\VietFontsWeb1_ttf\\vuArial.ttf";
        String logoPath = "C:\\Users\\X1\\OneDrive\\Documents\\NetBeansProjects\\POLYPOLO\\src\\Icons\\invoice-BW_logo.png";
        String qrPath = "C:\\Users\\X1\\OneDrive\\Documents\\NetBeansProjects\\POLYPOLO\\src\\Icons\\invoice-qr.png";
        
        if (trangT.equalsIgnoreCase("Đã thanh toán")) {
            try (PdfWriter pdfWriter = new PdfWriter(path); PdfDocument pdfDocument = new PdfDocument(pdfWriter); Document doc = new Document(pdfDocument)) {

                pdfDocument.setDefaultPageSize(PageSize.A4);
                PdfFont font = PdfFontFactory.createFont(fontPath, PdfEncodings.IDENTITY_H, true);
                ImageData logo = ImageDataFactory.create(logoPath);

                Image iLogo = new Image(logo);
                iLogo.scaleToFit(200, Float.MAX_VALUE);
                float x = (pdfDocument.getDefaultPageSize().getWidth() - iLogo.getImageScaledWidth()) / 2;
                float y = (pdfDocument.getDefaultPageSize().getHeight() - iLogo.getImageScaledHeight()) / 2;
                iLogo.setFixedPosition(x, y);
                iLogo.setOpacity(0.2f).setFontSize(30f);
                doc.add(iLogo);

                float twoCol = 300f;
                float twoCol150 = twoCol + 150f;
                float nawWidth[] = {twoCol};
                float twoColWidth[] = {twoCol150, twoCol};
                float fiveCol = 100f;
                float fiveColWidth[] = {40f, 210f, 70f, 95f, 95f};
                float fullWidth[] = {fiveCol * 5 + 10};

                //ROW 1 
                Table table = new Table(twoColWidth);
                table.addCell(new Cell().add("HÓA ĐƠN").setFont(font).setBorder(Border.NO_BORDER).setBold().setFontSize(20f));
                table.addCell(new Cell().add("Polo Loves - Live, Love, Polo \nTel: 0375 908 159\n Add: 21 Châu Long, Ba Đình, HN").setBorder(Border.NO_BORDER).setFont(font));
                doc.add(table.setMarginBottom(12f));

                Border b = new SolidBorder(com.itextpdf.kernel.color.Color.LIGHT_GRAY, 2f);
                Table divider = new Table(fullWidth);
                divider.setBorder(b);
                doc.add(divider.setMarginBottom(12f));

                //ROW 2
                Table twoColTable = new Table(twoColWidth);
                twoColTable.addCell(storeCell("Thông tin Khách Hàng").setFont(font));
                doc.add(twoColTable.setMarginBottom(6f));

                ArrayList<HD_InvoiceViewModel> lsKH = hdService.getListKHById(maHD);
                for (int i = 0; i < lsKH.size(); i++) {
                    Table twoColTable2 = new Table(twoColWidth);
                    twoColTable2.addCell(get10fLeftCell("Họ Tên:", true).setFont(font));
                    twoColTable2.addCell(get10fLeftCell("Invoice No:", true).setFont(font));
                    twoColTable2.addCell(get10fLeftCell(lsKH.get(i).getTenKhachHang(), false).setFont(font));
                    twoColTable2.addCell(get10fLeftCell(String.valueOf("HD00" + lsKH.get(i).getMaHD()), false));
                    twoColTable2.addCell(get10fLeftCell("Số Điện Thoại:", true));
                    twoColTable2.addCell(get10fLeftCell("Loại Khách Hàng: ", true)).setFont(font);
                    twoColTable2.addCell(get10fLeftCell(lsKH.get(i).getSoDT(), false).setFont(font));
                    twoColTable2.addCell(get10fLeftCell(lsKH.get(i).getLoaiKH(), false));
                    twoColTable2.addCell(get10fLeftCell("Địa Chỉ:", true).setFont(font));
                    twoColTable2.addCell(get10fLeftCell("Phương Thức Thanh Toán:", true).setFont(font));
                    twoColTable2.addCell(get10fLeftCell(lsKH.get(i).getDiaChi(), false));
                    twoColTable2.addCell(get10fLeftCell(lsKH.get(i).getPhuongThuc(), false).setFont(font));

                    doc.add(twoColTable2.setMarginBottom(4f));
                }

                Table divider2 = new Table(fullWidth);
                Border bdg = new DashedBorder(com.itextpdf.kernel.color.Color.DARK_GRAY, 0.5f);
                doc.add(divider2.setBorder(bdg).setMarginBottom(7f));

                //ROW 3
                Paragraph thirdPara = new Paragraph("Danh Sách Sản Phẩm").setFont(font).setFontSize(15f).setMarginBottom(8f);
                doc.add(thirdPara.setBold());

                Table fiveColTable1 = new Table(fiveColWidth);
                fiveColTable1.setBackgroundColor(com.itextpdf.kernel.color.Color.BLACK, 0.7f);
                fiveColTable1.addCell(new Cell().add("STT").setBold().setFontColor(com.itextpdf.kernel.color.Color.WHITE).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER));
                fiveColTable1.addCell(new Cell().add("Tên Sản Phẩm").setFont(font).setBold().setFontColor(com.itextpdf.kernel.color.Color.WHITE).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER));
                fiveColTable1.addCell(new Cell().add("Số Lượng").setFont(font).setBold().setFontColor(com.itextpdf.kernel.color.Color.WHITE).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER));
                fiveColTable1.addCell(new Cell().add("Đơn Giá").setFont(font).setBold().setFontColor(com.itextpdf.kernel.color.Color.WHITE).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER));
                fiveColTable1.addCell(new Cell().add("Thành Tiền").setFont(font).setBold().setFontColor(com.itextpdf.kernel.color.Color.WHITE).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER));
                doc.add(fiveColTable1);

                ArrayList<HD_GioHangViewModel> lsHD = hdService.printInvoiceById(maHD);
                for (int i = 0; i < lsHD.size(); i++) {
                    Table fiveColTable2 = new Table(fiveColWidth);
                    fiveColTable2.addCell(new Cell().add(String.valueOf(i + 1)).setTextAlignment(TextAlignment.CENTER));
                    fiveColTable2.addCell(new Cell().add(lsHD.get(i).getTenSP()).setTextAlignment(TextAlignment.CENTER).setFont(font));
                    fiveColTable2.addCell(new Cell().add(String.valueOf(lsHD.get(i).getSoLuong())).setTextAlignment(TextAlignment.CENTER));

                    double donG = lsHD.get(i).getDonGia();
                    String donGia = formatter.format(donG);
                    fiveColTable2.addCell(new Cell().add(donGia).setTextAlignment(TextAlignment.CENTER));

                    double thanhT = lsHD.get(i).getThanhTien();
                    String thanhTien = formatter.format(thanhT);
                    fiveColTable2.addCell(new Cell().add(thanhTien).setTextAlignment(TextAlignment.CENTER));

                    doc.add(fiveColTable2.setBorder(Border.NO_BORDER));
            }

                // ROW 4        
                Table totalTable = new Table(fiveColWidth);
                totalTable.addCell(new Cell().add("").setBorder(Border.NO_BORDER));
                totalTable.addCell(new Cell().add("").setBorder(Border.NO_BORDER));
                double total = hdService.getTotal(maHD).getTongTien();
                String tongT = formatter.format(total);
                Text boldText = new Text("Tổng tiền: ").setBold().setFontSize(15f).setFont(font);
                Text regularText = new Text(tongT + " VND").setFont(font).setFontSize(15f);
                Paragraph footPara = new Paragraph().add(boldText).add(regularText);
                totalTable.addCell(new Cell(1, 3).add(footPara).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.RIGHT));

                doc.add(totalTable.setMargins(12f, 0, 12f, 0));
                doc.add(divider.setMarginBottom(12f));
                
                // ROW 5
                Table termsTable = new Table(fullWidth);
                termsTable.addCell(new Cell().add("Điều kiện & Chính sách đổi trả:").setFont(font).setBold().setBorder(Border.NO_BORDER)).setFontSize(13f);
                ArrayList<String> ls = new ArrayList<>();
                ls.add("• Khách hàng được đổi hàng hoặc trả hàng hoàn tiền trong vòng 7 ngày sau khi nhận được hàng.\n");
                ls.add("• Sản phẩm phải còn nguyên tem mác, nguyên trạng như lúc nhận hàng và có hóa đơn mua hàng.\n");
                ls.add("• Việc trả hàng chỉ được thực hiện khi sản phẩm bị lỗi hoặc những sự cố phát sinh do lỗi từ phía shop.\n");

                for (String tnc : ls) {
                    termsTable.addCell(new Cell().add(tnc).setBorder(Border.NO_BORDER).setFont(font).setFontSize(11f));
                }

                doc.add(termsTable.setMarginBottom(12f));
                doc.add(divider2.setMarginBottom(8f));
                doc.add(divider.setMarginBottom(12f));
                
                //ROW 6
                Table dateTime = new Table(fullWidth);
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter date = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                DateTimeFormatter time = DateTimeFormatter.ofPattern("HH:mm:ss");
                String nowD = now.format(date);
                String nowT = now.format(time);

                dateTime.addCell(new Cell().add("**** " + nowD + " | " + nowT + " ****").setFont(font).setFontSize(13f).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER));
                doc.add(dateTime);
                
                //ROW 7
                Table ngX = new Table(fullWidth);
                ngX.addCell(new Cell().add("( " + uService.getTenDN(Login.dataStatic) + " )").setFont(font).setFontSize(13f).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER));
                doc.add(ngX.setMarginBottom(10f));

                Table divider3 = new Table(nawWidth);
                Border fin = new DashedBorder(com.itextpdf.kernel.color.Color.DARK_GRAY, 0.5f);
                doc.add(divider3.setBorder(fin).setMarginBottom(12f).setHorizontalAlignment(com.itextpdf.layout.property.HorizontalAlignment.CENTER));
                
                //ROW 8
                float bottomMargin = 20;
                float qrHeight = 80;
                float spaceBetweenQRAndFooter = 10;
                float pageWidth = pdfDocument.getDefaultPageSize().getWidth();
                float pageHeight = pdfDocument.getDefaultPageSize().getHeight();

                ImageData qr = ImageDataFactory.create(qrPath);
                Image iQR = new Image(qr).scaleToFit(qrHeight, qrHeight);
                float qrXPosition = (pageWidth - iQR.getImageScaledWidth()) / 2;

                float qrYPosition = bottomMargin;
                iQR.setFixedPosition(qrXPosition, qrYPosition);
                doc.add(iQR);
                float tableWidth = pageWidth - (2 * bottomMargin);
                Table footerTable = new Table(1);
                footerTable.setWidth(tableWidth);

                footerTable.addCell(new Cell().add("Cảm ơn Quý Khách vì đã mua hàng!\n").setFont(font).setTextAlignment(TextAlignment.CENTER).setBold().setBorder(Border.NO_BORDER).setFontSize(14f));
                footerTable.addCell(new Cell().add("Hòm thư đóng góp ý kiến: polypolo@email.com").setFont(font).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER).setFontSize(12f));

                LayoutResult result = footerTable.createRendererSubTree().setParent(doc.getRenderer()).layout(new LayoutContext(new LayoutArea(0, new Rectangle(pageWidth, 1000))));
                float tableHeight = result.getOccupiedArea().getBBox().getHeight();
                float footerYPosition = qrYPosition + qrHeight + spaceBetweenQRAndFooter;
                footerTable.setFixedPosition(bottomMargin, footerYPosition, tableWidth);

                doc.add(footerTable);

//        Table footerTable = new Table(fullWidth);
//        footerTable.addCell(new Cell().add("Cảm ơn Quý Khách vì đã mua hàng!\n").setFont(font).setTextAlignment(TextAlignment.CENTER).setBold().setBorder(Border.NO_BORDER).setFontSize(14f));
//        footerTable.addCell(new Cell().add("Hòm thư đóng góp ý kiến: polypolo@email.com\n").setFont(font).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER).setFontSize(12f));
//        footerTable.addCell(new Cell().add("*********************************************").setFont(font).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER).setFontSize(12f));
//        doc.add(footerTable);
//  
//        ImageData qr = ImageDataFactory.create(qrPath);
//        Image iQR = new Image(qr).scaleToFit(80, 80); 
//        float bottomMargin = 20;
//        float qrYPosition = bottomMargin;
//        float pageWidth = pdfDocument.getDefaultPageSize().getWidth();
//        float qrXPosition = (pageWidth - iQR.getImageScaledWidth()) / 2;
//        iQR.setFixedPosition(qrXPosition, qrYPosition);
//        doc.add(iQR);
                doc.close();
                System.out.println("in thanh congg");
            } catch (Exception e) {
                e.printStackTrace();
            } 
            JOptionPane.showInternalMessageDialog(this, "Đã in hóa đơn thành công!", "POLYPOLO thông báo", 0);
        }
        else{
            JOptionPane.showMessageDialog(this, "Bạn cần thanh toán để in hóa đơn!", "POLYPOLO thông báo", 0);
        }
    }//GEN-LAST:event_btnInHDMouseClicked

    private void btnXuatDSMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXuatDSMouseClicked
        // XUAT DS 
        try {
            XSSFWorkbook workBook = new XSSFWorkbook();
            XSSFSheet sheet = workBook.createSheet("Danh Sách Hóa Đơn");
            
            //STYLE TITLE
            XSSFRow titleRow = sheet.createRow(0);
            org.apache.poi.ss.usermodel.Cell titleCell = titleRow.createCell(0);
            titleCell.setCellValue("Danh Sách Hóa Đơn POLYPOLO"); 
            sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(0,0,0,9)); 
            XSSFFont font = workBook.createFont();
            font.setFontHeightInPoints((short) 19);
            font.setBold(true);
            XSSFCellStyle style = workBook.createCellStyle();
            style.setFont(font);
            style.setAlignment(HorizontalAlignment.CENTER);
            style.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.CENTER);
            titleCell.setCellStyle(style);
           
            //DATE
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String currentDate = sdf.format(new Date());
            
            SimpleDateFormat sdfHrs = new SimpleDateFormat("HH:mm:ss");
            String currentTime = sdfHrs.format(new Date());
            
            XSSFRow dateRow = sheet.createRow(1);
            org.apache.poi.ss.usermodel.Cell dateCell = dateRow.createCell(0);
            dateCell.setCellValue("Ngày: " + currentDate + " | Giờ: " +currentTime);
            sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(1, 1, 0, 9)); // Merge từ cột 0 đến 9
            XSSFCellStyle dateStyle = workBook.createCellStyle();
            dateStyle.setAlignment(HorizontalAlignment.RIGHT);
            dateCell.setCellStyle(dateStyle);
            
            //STYLE FONT
            Font headerFont = workBook.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 11);
            
            CellStyle headerStyle = workBook.createCellStyle();
            headerStyle.setFont(headerFont);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);

            //ADD
            XSSFRow row = null;
            org.apache.poi.ss.usermodel.Cell cell = null;
            row = sheet.createRow(3);

            cell = row.createCell(0, org.apache.poi.ss.usermodel.CellType.STRING);
            cell.setCellValue("STT");

            cell = row.createCell(1, org.apache.poi.ss.usermodel.CellType.STRING);
            Sheet s = cell.getSheet();
            cell.setCellValue("Mã Hóa Đơn");
            s.autoSizeColumn(1);

            cell = row.createCell(2, org.apache.poi.ss.usermodel.CellType.STRING);
            cell.setCellValue("Tên Khách Hàng");
            s.autoSizeColumn(2);
            
            cell = row.createCell(3, org.apache.poi.ss.usermodel.CellType.STRING);
            cell.setCellValue("Loại Khách Hàng");
            s.autoSizeColumn(3);
            
            cell = row.createCell(4, org.apache.poi.ss.usermodel.CellType.STRING);
            cell.setCellValue("Số Lượng Mua");
            s.autoSizeColumn(4);

            cell = row.createCell(5, org.apache.poi.ss.usermodel.CellType.STRING);
            cell.setCellValue("Tổng Tiền");
            s.autoSizeColumn(5);

            cell = row.createCell(6, org.apache.poi.ss.usermodel.CellType.STRING);
            cell.setCellValue("Phương Thức Thanh Toán");
            s.autoSizeColumn(6);

            cell = row.createCell(7, org.apache.poi.ss.usermodel.CellType.STRING);
            cell.setCellValue("Tên Nhân Viên");

            cell = row.createCell(8, org.apache.poi.ss.usermodel.CellType.STRING);
            cell.setCellValue("Trạng Thái");

            cell = row.createCell(9, org.apache.poi.ss.usermodel.CellType.STRING);
            cell.setCellValue("Ngày Lập");

            CellStyle cellStyleFormatNumber = null;

            if (cellStyleFormatNumber == null) {
                short format = (short) BuiltinFormats.getBuiltinFormat("#,##0");
                Workbook workbook = row.getSheet().getWorkbook();
                cellStyleFormatNumber = workbook.createCellStyle();
                cellStyleFormatNumber.setDataFormat(format);
            }

            for (int i = 0; i < 10; i++) {
                cell = row.getCell(i);
                cell.setCellStyle(headerStyle);
            }
            ArrayList<MyReceipts> ls = hdService.getMyReceipts();
            for (int i = 0; i < ls.size(); i++) {
                row = sheet.createRow(4 + i);

                //STYLE FONT
                CellStyle docuStyle = workBook.createCellStyle();
                docuStyle.setAlignment(HorizontalAlignment.CENTER);
                
                cell = row.createCell(0, org.apache.poi.ss.usermodel.CellType.NUMERIC);
                cell.setCellValue(i + 1);
                cell.setCellStyle(docuStyle);

                cell = row.createCell(1, org.apache.poi.ss.usermodel.CellType.NUMERIC);
                cell.setCellValue("HD" + ls.get(i).getMaHD());
                cell.setCellStyle(docuStyle);

                cell = row.createCell(2, org.apache.poi.ss.usermodel.CellType.STRING);
                cell.setCellValue(ls.get(i).getTenKH());
                s.autoSizeColumn(2);

                cell = row.createCell(3, org.apache.poi.ss.usermodel.CellType.STRING);
                cell.setCellValue(ls.get(i).getLoaiKH());
                cell.setCellStyle(docuStyle);
                s.autoSizeColumn(3);

                cell = row.createCell(4, org.apache.poi.ss.usermodel.CellType.NUMERIC);
                cell.setCellValue(ls.get(i).getSoL());
                cell.setCellStyle(docuStyle);

                cell = row.createCell(5, org.apache.poi.ss.usermodel.CellType.STRING);
                cell.setCellValue(ls.get(i).getTongTien());
                cell.setCellStyle(docuStyle);
                cell.setCellStyle(cellStyleFormatNumber);

                cell = row.createCell(6, org.apache.poi.ss.usermodel.CellType.STRING);
                cell.setCellValue(ls.get(i).getPhuongThuc());
                cell.setCellStyle(docuStyle);
                
                cell = row.createCell(7, org.apache.poi.ss.usermodel.CellType.STRING);
                cell.setCellValue(ls.get(i).getTenNV());
                s.autoSizeColumn(7);

                cell = row.createCell(8, org.apache.poi.ss.usermodel.CellType.STRING);
                cell.setCellValue(ls.get(i).getTrangThai());
                s.autoSizeColumn(8);
                cell.setCellStyle(docuStyle);

                cell = row.createCell(9, org.apache.poi.ss.usermodel.CellType.STRING);
                String formattedDate = sdf.format(ls.get(i).getNgayLap());
                cell.setCellValue(formattedDate);
                s.autoSizeColumn(9);
                cell.setCellStyle(docuStyle);
            }
            
            //FILTER
            int lastRow = sheet.getLastRowNum();
            sheet.setAutoFilter(new CellRangeAddress(3, lastRow, 8, 8));
            
            //STYLE
            CellStyle footerStyle = workBook.createCellStyle();
            footerStyle.setFont(headerFont);
            footerStyle.setAlignment(HorizontalAlignment.LEFT);
            
            //ROW CUỐI
            int rowS = 5 + ls.size();

            XSSFRow doanhTRow = sheet.createRow(rowS);
            org.apache.poi.ss.usermodel.Cell doanhTCell = doanhTRow.createCell(1);

            doanhTCell.setCellValue("Tổng Doanh Thu:");
            doanhTCell.setCellStyle(footerStyle);
            double total = ls.stream().mapToDouble(MyReceipts::getTongTien).sum();
            org.apache.poi.ss.usermodel.Cell totalCell = doanhTRow.createCell(2);
            totalCell.setCellValue(total);
            totalCell.setCellStyle(cellStyleFormatNumber);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);

            org.apache.poi.ss.usermodel.Cell ngX = doanhTRow.createCell(7);
            ngX.setCellValue("Người Xuất:");
            ngX.setCellStyle(footerStyle);
            org.apache.poi.ss.usermodel.Cell thongT = doanhTRow.createCell(8);
            thongT.setCellValue(uService.getName(Login.dataStatic));
            sheet.autoSizeColumn(7);
            sheet.autoSizeColumn(8);
            
            int rowT = rowS + 2;
            XSSFRow tongHDRow = sheet.createRow(rowT);
            org.apache.poi.ss.usermodel.Cell tongHD = tongHDRow.createCell(1);
            tongHD.setCellValue("Tổng Số Hóa Đơn:");
            tongHD.setCellStyle(footerStyle);
            org.apache.poi.ss.usermodel.Cell tongHDCell = tongHDRow.createCell(2);
            tongHDCell.setCellValue(ls.size());
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
      
            File file = new File("C:\\Users\\X1\\OneDrive\\Documents\\Custom Office Templates\\POLYPOLO_ls.xlsx");
            try {
                FileOutputStream fis = new FileOutputStream(file);
                workBook.write(fis);
                fis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        JOptionPane.showInternalMessageDialog(this, "Đã xuất danh sách hóa đơn thành công!", "POLYPOLO thông báo", 0);
    }//GEN-LAST:event_btnXuatDSMouseClicked
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFilterSP;
    private javax.swing.JButton btnInHD;
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnSearchHD;
    private javax.swing.JButton btnSearchKH;
    private javax.swing.JButton btnTaoHD;
    private javax.swing.JButton btnThanhToan;
    private javax.swing.JButton btnXoaSanPham;
    private javax.swing.JButton btnXuatDS;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cboDanhMuc;
    private javax.swing.JComboBox<String> cboPhuongThuc;
    private javax.swing.JComboBox<String> cboTenNV;
    private javax.swing.JComboBox<String> cboTrangThai;
    private com.toedter.calendar.JDateChooser dcsNgayLap;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblTongTien;
    private javax.swing.JTable tblHoaDon;
    private javax.swing.JTable tblHoaDonChiTiet;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtSearch1;
    private javax.swing.JTextField txtTenKH;
    // End of variables declaration//GEN-END:variables
}
