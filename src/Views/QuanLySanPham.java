/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package Views;

import Models.DanhMuc;
import Models.KichCo;
import Models.MauSac;
import Models.SanPham;
import Services.UserService;
import Services.SanPhamService;
import Validator.MyValidate;
import ViewModels.SanPhamViewModel;
import ViewModels.UserViewModel;
import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author X1
 */
public class QuanLySanPham extends javax.swing.JInternalFrame {
    SanPhamService spService = new SanPhamService();
    DecimalFormat formatter = new DecimalFormat("#,###");
    UserService uService = new UserService();
    
    /**
     * Creates new form QuanLySanPham
     */
    
    public QuanLySanPham() {
        initComponents();
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);
        load();
    }
    
    public static UserViewModel u;

    private String d;

    public String Data(String d) {
        this.d = d;
        return d;
    }
    
    void load() {
        loadTableSanPhamChiTiet(spService.getListSanPham());
        loadCboTenDM(spService.getCboDM());
        loadCboMau(spService.getCboMau());
        loadCboSz(spService.getCboSz());
        loadTableDanhMuc(spService.getList());
    }

    //<editor-fold defaultstate="collapsed" desc=" LOAD ">
    //LOAD
    void loadTableDanhMuc(ArrayList<DanhMuc> ls){
        DefaultTableModel model = (DefaultTableModel) tblDanhMuc.getModel();
        model.setRowCount(0);
        for (DanhMuc dm : ls) {
            model.addRow(new Object[]{
                dm.getMaDM(), dm.getTenDM(), dm.getTrangThai()
            });
        }
    }
    void loadTableByThuocTinhMauSac(ArrayList<MauSac> ls) {
        String tenTT = "Màu Sắc";
        DefaultTableModel model = (DefaultTableModel) tblThuocTinh.getModel();
        model.setRowCount(0);
        for (MauSac ms : ls) {
            model.addRow(new Object[]{
                ms.getMaMau(), tenTT, ms.getTenMau()
            });
        }
    }
    void loadTableByThuocTinhSz(ArrayList<KichCo> ls) {
        String tenTT = "Kích Cỡ";
        DefaultTableModel model = (DefaultTableModel) tblThuocTinh.getModel();
        model.setRowCount(0);
        for (KichCo sz : ls) {
            model.addRow(new Object[]{
                sz.getMaSize(), tenTT, sz.getTenSize()
            });
        }
    }
    void loadTableSanPhamChiTiet(ArrayList<SanPhamViewModel> ls) {
        DefaultTableModel model = (DefaultTableModel) tblSPCT.getModel();
        model.setRowCount(0);

        for (SanPhamViewModel sp : ls) {
            String formatgiaN = formatter.format(sp.getGiaNhap());
            String formatgiaB = formatter.format(sp.getGiaBan());

            model.addRow(new Object[]{
                sp.getMaSP(), sp.getTenSP(), sp.getTenDM(),
                sp.getMauSac(), sp.getKichCo(),
                formatgiaN, formatgiaB, sp.getTrangThai(), sp.getSoLuong()
            });
        }
    }
    void loadCboTenDM(ArrayList<DanhMuc> ls) {
        DefaultComboBoxModel cboDM1 = (DefaultComboBoxModel) cboDanhMuc.getModel();
        DefaultComboBoxModel cboDM2 = (DefaultComboBoxModel) cboLocDM.getModel();
        HashSet<String> danhMucSet = new HashSet<>();

        for (DanhMuc dm : ls) {
            String danhMuc = dm.getTenDM();
            // Kiểm tra xem phương thức đã tồn tại trong HashSet chưa
            if (!danhMucSet.contains(danhMuc)) {
                cboDM1.addElement(danhMuc);
                cboDM2.addElement(dm.getTenDM());
                // Thêm phương thức vào HashSet để kiểm tra trùng lặp
                danhMucSet.add(danhMuc);
            }
        }
    }
    void loadCboMau(ArrayList<MauSac> ls) {
        DefaultComboBoxModel cboMS = (DefaultComboBoxModel) cboMauSac.getModel();
        for (MauSac ms : ls) {
            cboMS.addElement(ms.getTenMau());
        }
    }
    void loadCboSz(ArrayList<KichCo> ls) {
        DefaultComboBoxModel cboKC = (DefaultComboBoxModel) cboKichCo.getModel();
        for (KichCo sz : ls) {
            cboKC.addElement(sz.getTenSize());
        }
    }
    //</editor-fold>
    
    void loadDataToFormSPCT() {
        int pos = tblSPCT.getSelectedRow();
        Integer id = (Integer) tblSPCT.getValueAt(pos, 0);
        
        SanPhamViewModel sp = spService.getListById(id);
        
        cboDanhMuc.setSelectedItem(sp.getTenDM());
        txtMSP.setText(sp.getMaSP().toString());
        txtTSPCT.setText(sp.getTenSP());
        txtSoLuong.setText(sp.getSoLuong().toString());
        txtGiaNhap.setText(sp.getGiaNhap().toString());
        txtGiaBan.setText(sp.getGiaBan().toString());
        if (sp.getTrangThai().equalsIgnoreCase("Còn hàng")) {
            rdoConHang.setSelected(true);
        }else{
            rdoHetHang.setSelected(true);
        }
        cboMauSac.setSelectedItem(sp.getMauSac());
        cboKichCo.setSelectedItem(sp.getKichCo());
    }
    //CLEARTEXT
    void clearText(){
        txtSearch.setText("");
    }
    void clearFormSPCT(){
        cboDanhMuc.setSelectedItem("Polo Nam");
        txtMSP.setText("");
        txtTSPCT.setText("");
        txtSoLuong.setText("");
        rdoConHang.isSelected();
        txtGiaNhap.setText("");
        txtGiaBan.setText("");
        cboMauSac.setSelectedItem("Đen");
        cboKichCo.setSelectedItem("XS");
    }
    void clearTableThuocTinh(){
        DefaultTableModel model = (DefaultTableModel) tblThuocTinh.getModel();
        model.setRowCount(0);
    }
    
    //GET MODEL
    public SanPham getModel(){
        String danhMuc = cboDanhMuc.getSelectedItem().toString();
        Integer maDM = spService.getIdByNameee(danhMuc).getMaDM();
        
        int pos = tblSPCT.getSelectedRow();
        Integer maSP = (Integer) tblSPCT.getValueAt(pos, 0);
        String tenSP = txtTSPCT.getText();
        
        String tenMau = cboMauSac.getSelectedItem().toString();
        Integer maMau = spService.getIdByName(tenMau).getMaMau();
        
        String tenSz = cboKichCo.getSelectedItem().toString();
        Integer maSz = spService.getIdByNamee(tenSz).getMaSize();
        
        Double giaNhap = Double.valueOf(txtGiaNhap.getText());
        Double giaBan = Double.valueOf(txtGiaBan.getText());
        String trangThai = rdoConHang.isSelected() ? "Còn hàng" : "Hết hàng";
        Integer soL = Integer.valueOf(txtSoLuong.getText());
        
        SanPham sp = new SanPham(maSP, tenSP, maDM, trangThai, giaNhap, giaBan, maSz, maMau, soL);
        return sp;
    }
    public SanPham getModelSP(){
        String danhMuc = cboDanhMuc.getSelectedItem().toString();
        Integer maDM = spService.getIdByNameee(danhMuc).getMaDM();
        
        Integer maSP = Integer.valueOf(spService.getListSP().get(spService.getListSP().size()-1).getMaSP());
        String tenSP = txtTSPCT.getText();
        
        String tenMau = cboMauSac.getSelectedItem().toString();
        Integer maMau = spService.getIdByName(tenMau).getMaMau();
        
        String tenSz = cboKichCo.getSelectedItem().toString();
        Integer maSz = spService.getIdByNamee(tenSz).getMaSize();
        
        Double giaNhap = Double.valueOf(txtGiaNhap.getText());
        Double giaBan = Double.valueOf(txtGiaBan.getText());
        String trangThai = rdoConHang.isSelected() ? "Còn hàng" : "Hết hàng";
        Integer soL = Integer.valueOf(txtSoLuong.getText());
        
        SanPham sp = new SanPham(maSP, tenSP, maDM, trangThai, giaNhap, giaBan, maSz, maMau, soL);
        return sp;
    }
    
    //GET MODEL
    public DanhMuc getModelDM(){        
        Integer maDM = spService.getList().get(spService.getList().size()-1).getMaDM();
        String tenDM = txtTenDanhMuc.getText();
        String trangThai = rdoDMConHang.isSelected() ? "Còn hàng" : "Hết hàng";
        
        DanhMuc dm = new DanhMuc(maDM, tenDM, trangThai);
        return dm;   
    }
    public MauSac getModelUpdateCl(){
        MauSac ms = new MauSac();
        
        ms.setMaMau(Integer.valueOf(txtMaTT.getText()));
        ms.setTenMau(txtTenTT.getText().trim());
        return ms;
    }
    public MauSac getModelAddColor(){
        MauSac ms = new MauSac();

        ms.setTenMau(txtTenTT.getText().trim());
        return ms;
    }
    public KichCo getModelUpdateSz(){
        KichCo sz = new KichCo();
        
        sz.setMaSize(Integer.valueOf(txtMaTT.getText()));
        sz.setTenSize(txtTenTT.getText().trim());
        return sz;
    }
    public KichCo getModelAddSz(){
        KichCo sz = new KichCo();

        sz.setTenSize(txtTenTT.getText().trim());
        return sz;
    }
    
    
    
    public MauSac getModelMs() {
        int pos = tblThuocTinh.getSelectedRow();
        Integer maM = (Integer) tblThuocTinh.getValueAt(pos, 0);
        String tenM = txtTenTT.getText();
        MauSac ms = new MauSac(maM, tenM);

//        Integer maMau = spService.getIdByName(tenM).getMaMau();
//        System.out.println(maMau);
//        ms.setMaMau(maMau);
//        ms.setTenMau(tenM);
        return ms;
    }
    public KichCo getModelSz(){
        KichCo sz = new KichCo();
        sz.setMaSize(Integer.valueOf(txtMaTT.getText()));
        sz.setTenSize(txtTenTT.getText());
        return sz;
    }
    
    //VALIDATE
    public Boolean validateSanPham(){
        StringBuilder stb = new  StringBuilder();
        MyValidate v = new MyValidate();
        
        v.isEmpty(txtTSPCT, stb, "Chưa nhập tên sản phẩm!");
        v.isEmpty(txtSoLuong, stb, "Số lượng sản phẩm bị trống!");
        v.NumberLimit(txtGiaNhap, stb, "Giá nhập phải là 1 số nguyên!",0,10000);
        v.NumberLimit(txtGiaBan, stb, "Giá bán phải là 1 số nguyên!",0,10000);
        if (stb.length()>0) {
            JOptionPane.showMessageDialog(this, stb);
            return false;
        }else{
            return true;
        }
    }
    public Boolean validateThuocTinh(){
        StringBuilder stb = new  StringBuilder();
        MyValidate v = new MyValidate();
        
        v.isEmpty(txtTenTT, stb, "Tên thuộc tính bị trống!");
        if (stb.length()>0) {
            JOptionPane.showMessageDialog(this, stb);
            return false;
        }else{
            return true;
        }
    }
    public Boolean validateDanhMuc(){
        StringBuilder stb = new  StringBuilder();
        MyValidate v = new MyValidate();
        
        v.isEmpty(txtTenDanhMuc, stb, "Tên danh mục bị trống!");
        if (stb.length()>0) {
            JOptionPane.showMessageDialog(this, stb);
            return false;
        }else{
            return true;
        }
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
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        buttonGroup4 = new javax.swing.ButtonGroup();
        PaneQLSP = new javax.swing.JTabbedPane();
        ChiTietSanPham = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtMSP = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtTSPCT = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        cboDanhMuc = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtGiaBan = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        btnSua = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        btnHide = new javax.swing.JButton();
        LoadHide = new javax.swing.JButton();
        btnHide1 = new javax.swing.JButton();
        btnImport = new javax.swing.JButton();
        btnIn = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        txtSoLuong = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtGiaNhap = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        rdoConHang = new javax.swing.JRadioButton();
        rdoHetHang = new javax.swing.JRadioButton();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        cboMauSac = new javax.swing.JComboBox<>();
        cboKichCo = new javax.swing.JComboBox<>();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblSPCT = new javax.swing.JTable();
        jLabel20 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        cboLocDM = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        btnSearch1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        txtMaDanhMuc = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        btnThemDM = new javax.swing.JButton();
        btnSuaDM = new javax.swing.JButton();
        btnClearDM = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        txtTenDanhMuc = new javax.swing.JTextField();
        btnAnDM = new javax.swing.JButton();
        jLabel28 = new javax.swing.JLabel();
        rdoDMConHang = new javax.swing.JRadioButton();
        rdoDMHethang = new javax.swing.JRadioButton();
        jPanel15 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblDanhMuc = new javax.swing.JTable();
        jLabel27 = new javax.swing.JLabel();
        txtSearchDM = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        ThuocTinh = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        txtMaTT = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        btnThemTT = new javax.swing.JButton();
        btnSuaTT = new javax.swing.JButton();
        btnClearTT = new javax.swing.JButton();
        jLabel24 = new javax.swing.JLabel();
        txtTenTT = new javax.swing.JTextField();
        btnHideTT = new javax.swing.JButton();
        rdoTTMS = new javax.swing.JRadioButton();
        rdoTTKC = new javax.swing.JRadioButton();
        jPanel13 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblThuocTinh = new javax.swing.JTable();
        jLabel21 = new javax.swing.JLabel();
        rdoMauSac = new javax.swing.JRadioButton();
        rdoKichCo = new javax.swing.JRadioButton();

        setMaximumSize(new java.awt.Dimension(990, 610));
        setMinimumSize(new java.awt.Dimension(990, 610));
        setName(""); // NOI18N
        setPreferredSize(new java.awt.Dimension(990, 610));

        PaneQLSP.setAutoscrolls(true);
        PaneQLSP.setPreferredSize(new java.awt.Dimension(990, 610));

        ChiTietSanPham.setPreferredSize(new java.awt.Dimension(990, 610));

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP)), "Thông tin Chi Tiết", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Segoe UI", 3, 14))); // NOI18N

        jLabel7.setText("Mã Sản Phẩm:");

        txtMSP.setEnabled(false);

        jLabel8.setText("Tên Sản Phẩm:");

        jLabel9.setText("Danh Mục:");

        cboDanhMuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboDanhMucActionPerformed(evt);
            }
        });

        jLabel10.setText("Trạng Thái:");

        jLabel11.setText("Giá Bán:");

        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        btnSua.setText("SỬA");
        btnSua.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSuaMouseClicked(evt);
            }
        });

        btnThem.setText("THÊM");
        btnThem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnThemMouseClicked(evt);
            }
        });

        btnClear.setText("MỚI");
        btnClear.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnClearMouseClicked(evt);
            }
        });

        btnHide.setText("ẨN");
        btnHide.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnHideMouseClicked(evt);
            }
        });

        LoadHide.setText("HIỂN THỊ SP ĐÃ ẨN");
        LoadHide.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LoadHideMouseClicked(evt);
            }
        });
        LoadHide.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LoadHideActionPerformed(evt);
            }
        });

        btnHide1.setText("BỎ ẨN");
        btnHide1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnHide1MouseClicked(evt);
            }
        });

        btnImport.setText("IMPORT");
        btnImport.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnImportMouseClicked(evt);
            }
        });

        btnIn.setText("EXPORT");
        btnIn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnInMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addComponent(btnHide1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(LoadHide)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnImport)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnIn)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnHide, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24))))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap(9, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem)
                    .addComponent(btnSua)
                    .addComponent(btnClear)
                    .addComponent(btnHide))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnHide1)
                    .addComponent(LoadHide)
                    .addComponent(btnImport)
                    .addComponent(btnIn))
                .addGap(12, 12, 12))
        );

        jLabel12.setText("Số Lượng Tồn:");

        jLabel13.setText("VND");

        jLabel14.setText("Giá Nhập:");

        jLabel15.setText("VND");

        buttonGroup1.add(rdoConHang);
        rdoConHang.setText("Còn hàng");

        buttonGroup1.add(rdoHetHang);
        rdoHetHang.setText("Hết hàng");

        jLabel16.setText("Màu Sắc:");

        jLabel17.setText("Kích cỡ:");

        cboMauSac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboMauSacActionPerformed(evt);
            }
        });

        cboKichCo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboKichCoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(64, 64, 64)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtTSPCT, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMSP, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboDanhMuc, javax.swing.GroupLayout.Alignment.LEADING, 0, 251, Short.MAX_VALUE)
                            .addComponent(txtSoLuong))
                        .addGap(58, 58, 58)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(cboMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(rdoConHang, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(28, 28, 28)
                                        .addComponent(rdoHetHang, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cboKichCo, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(123, 123, 123))))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtGiaNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtGiaBan, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(cboDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel9))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(rdoConHang)
                                .addComponent(jLabel10)
                                .addComponent(rdoHetHang)))
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel16)
                                    .addComponent(cboMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel17)
                                    .addComponent(cboKichCo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(27, 27, 27))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel8)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel12)
                                        .addGap(3, 3, 3))
                                    .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addComponent(txtMSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtTSPCT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(jLabel14)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel11)
                                .addGap(97, 97, 97))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtGiaNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel15))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtGiaBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel13))
                                .addGap(90, 90, 90))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(81, 81, 81))))))
        );

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP)), "Chi Tiết Sản Phẩm", javax.swing.border.TitledBorder.LEADING, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Segoe UI", 3, 17), new java.awt.Color(0, 0, 153))); // NOI18N

        tblSPCT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã SP", "Tên SP", "Danh Mục", "Màu Sắc", "Kích Cỡ", "Giá Nhập", "Giá Bán", "Trạng Thái", "Số Lượng"
            }
        ));
        tblSPCT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSPCTMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblSPCT);

        jLabel20.setText("Tìm kiếm theo tên:");

        btnSearch.setText("SEARCH");
        btnSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSearchMouseClicked(evt);
            }
        });

        jLabel1.setText("Lọc:");

        btnSearch1.setText("SEARCH");
        btnSearch1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSearch1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(cboLocDM, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSearch1, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel20)
                        .addGap(18, 18, 18)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 903, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearch)
                    .addComponent(jLabel20)
                    .addComponent(btnSearch)
                    .addComponent(cboLocDM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(btnSearch1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout ChiTietSanPhamLayout = new javax.swing.GroupLayout(ChiTietSanPham);
        ChiTietSanPham.setLayout(ChiTietSanPhamLayout);
        ChiTietSanPhamLayout.setHorizontalGroup(
            ChiTietSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ChiTietSanPhamLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ChiTietSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ChiTietSanPhamLayout.createSequentialGroup()
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 15, Short.MAX_VALUE))
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 948, Short.MAX_VALUE)))
        );
        ChiTietSanPhamLayout.setVerticalGroup(
            ChiTietSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ChiTietSanPhamLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        PaneQLSP.addTab("Quản Lý Sản Phẩm", ChiTietSanPham);

        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP)), "Thông tin Danh Mục", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Segoe UI", 3, 14))); // NOI18N

        txtMaDanhMuc.setEnabled(false);

        jLabel25.setText("Trạng Thái:");

        btnThemDM.setText("THÊM");
        btnThemDM.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnThemDMMouseClicked(evt);
            }
        });

        btnSuaDM.setText("SỬA");
        btnSuaDM.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSuaDMMouseClicked(evt);
            }
        });

        btnClearDM.setText("MỚI");
        btnClearDM.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnClearDMMouseClicked(evt);
            }
        });

        jLabel26.setText("Mã Danh Mục:");

        btnAnDM.setText("XÓA");
        btnAnDM.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAnDMMouseClicked(evt);
            }
        });
        btnAnDM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnDMActionPerformed(evt);
            }
        });

        jLabel28.setText("Tên Danh Mục:");

        buttonGroup4.add(rdoDMConHang);
        rdoDMConHang.setText("Còn hàng");
        rdoDMConHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoDMConHangActionPerformed(evt);
            }
        });

        buttonGroup4.add(rdoDMHethang);
        rdoDMHethang.setText("Hết hàng");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThemDM)
                    .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTenDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel14Layout.createSequentialGroup()
                                .addComponent(txtMaDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(162, 162, 162)
                                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(38, 38, 38)
                                .addComponent(rdoDMConHang)
                                .addGap(66, 66, 66)
                                .addComponent(rdoDMHethang))))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(btnSuaDM)
                        .addGap(49, 49, 49)
                        .addComponent(btnClearDM)
                        .addGap(54, 54, 54)
                        .addComponent(btnAnDM)))
                .addContainerGap(90, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel26)
                            .addComponent(txtMaDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTenDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel28))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnThemDM)
                            .addComponent(btnSuaDM)
                            .addComponent(btnClearDM)
                            .addComponent(btnAnDM)))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel25)
                            .addComponent(rdoDMConHang)
                            .addComponent(rdoDMHethang))))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP)), "Chi Tiết Danh Mục", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Segoe UI", 3, 14))); // NOI18N

        tblDanhMuc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Mã Danh Mục", "Tên Danh Mục", "Trạng Thái"
            }
        ));
        tblDanhMuc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDanhMucMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblDanhMuc);

        jLabel27.setText("Tìm kiếm danh mục:");

        jButton1.setText("SEARCH");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel27)
                        .addGap(18, 18, 18)
                        .addComponent(txtSearchDM, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel15Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 903, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtSearchDM, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton1))
                    .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        PaneQLSP.addTab("Danh Mục", jPanel1);

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP)), "Thông tin Thuộc Tính", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Segoe UI", 3, 14))); // NOI18N

        txtMaTT.setEnabled(false);

        jLabel23.setText("Tên Thuộc Tính:");

        btnThemTT.setText("THÊM");
        btnThemTT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnThemTTMouseClicked(evt);
            }
        });

        btnSuaTT.setText("SỬA");
        btnSuaTT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSuaTTMouseClicked(evt);
            }
        });
        btnSuaTT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaTTActionPerformed(evt);
            }
        });

        btnClearTT.setText("MỚI");
        btnClearTT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnClearTTMouseClicked(evt);
            }
        });

        jLabel24.setText("Mã Thuộc Tính:");

        btnHideTT.setText("XÓA");
        btnHideTT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnHideTTMouseClicked(evt);
            }
        });
        btnHideTT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHideTTActionPerformed(evt);
            }
        });

        buttonGroup3.add(rdoTTMS);
        rdoTTMS.setText("Màu Sắc");

        buttonGroup3.add(rdoTTKC);
        rdoTTKC.setText("Kich Cỡ");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThemTT))
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addComponent(txtTenTT, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(71, 71, 71)
                                .addComponent(rdoTTMS)
                                .addGap(62, 62, 62)
                                .addComponent(rdoTTKC))
                            .addComponent(txtMaTT, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(btnSuaTT)
                        .addGap(49, 49, 49)
                        .addComponent(btnClearTT)
                        .addGap(54, 54, 54)
                        .addComponent(btnHideTT)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(txtMaTT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(txtTenTT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rdoTTMS)
                    .addComponent(rdoTTKC))
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThemTT)
                    .addComponent(btnSuaTT)
                    .addComponent(btnClearTT)
                    .addComponent(btnHideTT))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP)), "Chi Tiết Thuộc Tính", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Segoe UI", 3, 14))); // NOI18N

        tblThuocTinh.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "STT", "Loại Thuộc Tính", "Tên Thuộc Tính"
            }
        ));
        tblThuocTinh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblThuocTinhMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblThuocTinh);

        jLabel21.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel21.setText("Lọc:");

        buttonGroup2.add(rdoMauSac);
        rdoMauSac.setText("Màu Sắc");
        rdoMauSac.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoMauSacMouseClicked(evt);
            }
        });

        buttonGroup2.add(rdoKichCo);
        rdoKichCo.setText("Kích Cỡ");
        rdoKichCo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoKichCoMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(rdoMauSac)
                        .addGap(81, 81, 81)
                        .addComponent(rdoKichCo)
                        .addGap(65, 65, 65))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 903, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(rdoMauSac)
                    .addComponent(rdoKichCo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
                .addGap(13, 13, 13))
        );

        javax.swing.GroupLayout ThuocTinhLayout = new javax.swing.GroupLayout(ThuocTinh);
        ThuocTinh.setLayout(ThuocTinhLayout);
        ThuocTinhLayout.setHorizontalGroup(
            ThuocTinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ThuocTinhLayout.createSequentialGroup()
                .addGroup(ThuocTinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        ThuocTinhLayout.setVerticalGroup(
            ThuocTinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ThuocTinhLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        PaneQLSP.addTab("Thuộc Tính", ThuocTinh);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PaneQLSP, javax.swing.GroupLayout.PREFERRED_SIZE, 954, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PaneQLSP, javax.swing.GroupLayout.PREFERRED_SIZE, 549, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSearchMouseClicked
        // SEARCH BY NAME
        String searchID = txtSearch.getText();
        ArrayList<SanPhamViewModel> ls = spService.getListBySearch(searchID);
        loadTableSanPhamChiTiet(ls);
        clearText();
    }//GEN-LAST:event_btnSearchMouseClicked

    private void tblSPCTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSPCTMouseClicked
        //CLICK
        loadDataToFormSPCT();
    }//GEN-LAST:event_tblSPCTMouseClicked

    private void cboKichCoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboKichCoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboKichCoActionPerformed

    private void cboMauSacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboMauSacActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboMauSacActionPerformed

    private void btnClearMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnClearMouseClicked
        // CLEAR
        clearFormSPCT();
        loadTableSanPhamChiTiet(spService.getListSanPham());
    }//GEN-LAST:event_btnClearMouseClicked

    private void btnThemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThemMouseClicked
        // ADD
        String checkName = txtTSPCT.getText().trim();
        int result = JOptionPane.showConfirmDialog(this, "Bạn muốn thêm sản phẩm?", "POLYPOLO xác nhận", JOptionPane.YES_NO_OPTION);       
        if (spService.checkName(checkName)) {
            JOptionPane.showMessageDialog(this, "Tên sản phẩm bị trùng, không thể thêm!", "POLY POLO thông báo", 0);
            clearFormSPCT();
        }else{
            if (validateSanPham() && result == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this, spService.addSP(getModelSP()));
            loadTableSanPhamChiTiet(spService.getListSanPham());
            clearFormSPCT();
        } else {
            JOptionPane.showMessageDialog(this, "Đã hủy thao tác thêm sản phẩm!", "POLYPOLO thông báo", 0);
        }
        }
    }//GEN-LAST:event_btnThemMouseClicked

    private void cboDanhMucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboDanhMucActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboDanhMucActionPerformed

    private void btnSuaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSuaMouseClicked
        // UPDATE
        int result = JOptionPane.showConfirmDialog(this, "Bạn muốn sửa sản phẩm này?", "POLYPOLO xác nhận", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            String resultNek = spService.updateSP(getModel());
            JOptionPane.showMessageDialog(this, resultNek);
            loadTableSanPhamChiTiet(spService.getListSanPham());
        } else {
            JOptionPane.showMessageDialog(this, "Đã hủy thao tác sửa sản phẩm!", "POLYPOLO thông báo", 0);
        }
    }//GEN-LAST:event_btnSuaMouseClicked

    private void btnHideMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHideMouseClicked
      //HIDE
        int result = JOptionPane.showConfirmDialog(this, "Bạn muốn ẩn sản phẩm không?", "POLYPOLO xác nhận", JOptionPane.YES_NO_OPTION);        
        if (result == JOptionPane.YES_OPTION) {
            spService.hideSP(getModel());
            JOptionPane.showMessageDialog(this, "Ẩn sản phẩm thành công!", "POLYPOLO thông báo", 0);
            loadTableSanPhamChiTiet(spService.getListSanPham());
            clearFormSPCT();
        } else {
            JOptionPane.showMessageDialog(this, "Đã hủy thao tác ẩn sản phẩm!", "POLYPOLO thông báo", 0);
        }
    }//GEN-LAST:event_btnHideMouseClicked

    private void tblThuocTinhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblThuocTinhMouseClicked
        // CLICK
        int selectedRow = tblThuocTinh.getSelectedRow();        
        if (selectedRow != -1) {
            Integer idMau = Integer.valueOf(tblThuocTinh.getValueAt(selectedRow, 0).toString());
            Integer idSz = Integer.valueOf(tblThuocTinh.getValueAt(selectedRow, 0).toString());
            
            String mauSac = tblThuocTinh.getValueAt(selectedRow,2).toString();
            String kichCo = tblThuocTinh.getValueAt(selectedRow, 2).toString();
            txtMaTT.setText(idMau+"");
            txtMaTT.setText(idSz+"");
            
            txtTenTT.setText(mauSac);
            txtTenTT.setText(kichCo);
        }
    }//GEN-LAST:event_tblThuocTinhMouseClicked

    private void btnThemTTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThemTTMouseClicked
        //ADD TT
        int result = JOptionPane.showConfirmDialog(this, "Bạn muốn thêm mới thuộc tính?", "POLYPOLO xác nhận", JOptionPane.YES_NO_OPTION);       
        if (result == JOptionPane.YES_OPTION && validateThuocTinh()) {
            if (rdoTTMS.isSelected()) {
                JOptionPane.showMessageDialog(this, spService.addColor(getModelAddColor()));
                loadTableByThuocTinhMauSac(spService.loadDataColor());
            }else if(rdoTTKC.isSelected()){
                JOptionPane.showMessageDialog(this, spService.addSz(getModelAddSz()));
                loadTableByThuocTinhSz(spService.loadDataSz());
            }
        }else{
            JOptionPane.showMessageDialog(this, "Đã hủy thao tác thêm mới thuộc tính!", "POLYPOLO thông báo", 0);
        }
    }//GEN-LAST:event_btnThemTTMouseClicked

    private void btnClearTTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnClearTTMouseClicked
        //CLEAR SEARCH
        txtMaTT.setText("");
        txtTenTT.setText("");
        txtTenTT.setBackground(Color.white);
        clearTableThuocTinh();
    }//GEN-LAST:event_btnClearTTMouseClicked

    private void LoadHideMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LoadHideMouseClicked
        //LOAD_UNHIDE
        loadTableSanPhamChiTiet(spService.getListHide());
        clearFormSPCT();
    }//GEN-LAST:event_LoadHideMouseClicked

    private void btnHide1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHide1MouseClicked
        //UNHIDE
        int result = JOptionPane.showConfirmDialog(this, "Bạn muốn bỏ ẩn sản phẩm không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            int selectedRow = tblSPCT.getSelectedRow();
            if (selectedRow != -1) {
                Integer maSP = Integer.valueOf(tblSPCT.getModel().getValueAt(selectedRow, 0).toString());
                SanPham sp = new SanPham();
                sp.setMaSP(maSP);
                if (spService.unhideSP(sp)) {
                    JOptionPane.showMessageDialog(this, "Bỏ ẩn sản phẩm thành công!", "POLYPOLO thông báo", 0);
                    loadTableSanPhamChiTiet(spService.getListSanPham());
                    clearFormSPCT(); 
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một sản phẩm từ bảng!", "POLYPOLO thông báo", 0);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Đã hủy thao tác bỏ ẩn sản phẩm!", "POLYPOLO thông báo", 0);
        }
    }//GEN-LAST:event_btnHide1MouseClicked

    private void btnHideTTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHideTTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnHideTTActionPerformed

    private void btnSuaTTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSuaTTMouseClicked
        //UPDATE TT
        int result = JOptionPane.showConfirmDialog(this, "Bạn muốn cập nhật thuộc tính?", "POLYPOLO xác nhận", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION && validateThuocTinh()) {
            if (rdoTTMS.isSelected()) {
                JOptionPane.showMessageDialog(this, spService.updateCl(getModelUpdateCl()));
                loadTableByThuocTinhMauSac(spService.loadDataColor());
            }else if(rdoTTKC.isSelected()){
                JOptionPane.showMessageDialog(this, spService.updateSz(getModelUpdateSz()));
                loadTableByThuocTinhSz(spService.loadDataSz());
            }
        }else{
            JOptionPane.showMessageDialog(this, "Đã hủy thao tác cập nhật thuộc tính!", "POLYPOLO thông báo", 0);
        }
    }//GEN-LAST:event_btnSuaTTMouseClicked

    private void btnHideTTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHideTTMouseClicked
        // HIDE TT
//        int result = JOptionPane.showConfirmDialog(this, "Bạn muốn ẩn thuộc tính không?", "POLYPOLO xác nhận", JOptionPane.YES_NO_OPTION);
//        if (result == JOptionPane.YES_OPTION) {
//            spService.hidetTTMS(getModelMs());
//            spService.hideTTSz(getModelSz());
//            JOptionPane.showMessageDialog(this, "Ẩn thuộc tính thành công!", "POLYPOLO thông báo", 0);
//            loadTableByThuocTinhMauSac(spService.getListMS());
//            loadTableByThuocTinhSz(spService.getListSz());
//            txtTenTT.setText("");
//        } else {
//            JOptionPane.showMessageDialog(this, "Đã hủy thao tác ẩn thuộc tính", "POLYPOLO thông báo", 0);
//        }
        if (!rdoTTMS.isSelected() && !rdoTTKC.isSelected()) {
            JOptionPane.showMessageDialog(this, "Hãy chọn 1 lựa chọn", "POLYPOLO thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int result = JOptionPane.showConfirmDialog(this, "Bạn muốn ẩn thuộc tính không?", "POLYPOLO xác nhận", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            if (rdoTTMS.isSelected()) {
                spService.hidetTTMS(getModelMs()); 
                loadTableByThuocTinhMauSac(spService.getListMS());
            } else if (rdoTTKC.isSelected()) {
                spService.hideTTSz(getModelSz());
                loadTableByThuocTinhSz(spService.getListSz());
            }
            JOptionPane.showMessageDialog(this, "Ẩn thuộc tính thành công!", "POLYPOLO thông báo", JOptionPane.INFORMATION_MESSAGE);
            txtTenTT.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Đã hủy thao tác ẩn thuộc tính", "POLYPOLO thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnHideTTMouseClicked

    private void rdoMauSacMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoMauSacMouseClicked
        // FILTER BY COLOR
        loadTableByThuocTinhMauSac(spService.loadDataColor());
    }//GEN-LAST:event_rdoMauSacMouseClicked

    private void rdoKichCoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoKichCoMouseClicked
        // FILTER BY SZ
        loadTableByThuocTinhSz(spService.loadDataSz());
    }//GEN-LAST:event_rdoKichCoMouseClicked

    private void btnThemDMMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThemDMMouseClicked
        //ADD DM
        int result = JOptionPane.showConfirmDialog(this, "Bạn muốn thêm mới danh mục?", "POLYPOLO xác nhận", JOptionPane.YES_NO_OPTION);       
        if (result == JOptionPane.YES_OPTION && validateDanhMuc()) {
                JOptionPane.showMessageDialog(this, spService.addDanhMuc(getModelDM()));
                loadTableDanhMuc(spService.getList());
        }else{
            JOptionPane.showMessageDialog(this, "Đã hủy thao tác thêm mới danh mục!", "POLYPOLO thông báo", 0);
        }
    }//GEN-LAST:event_btnThemDMMouseClicked

    private void btnSuaDMMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSuaDMMouseClicked
        // UPDATE
        int result = JOptionPane.showConfirmDialog(this, "Bạn muốn cập nhật danh mục?", "POLYPOLO xác nhận", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION && validateDanhMuc()) {
            JOptionPane.showMessageDialog(this, spService.updateDanhMuc(getModelDM()));
            loadTableDanhMuc(spService.getList());
        } else {
            JOptionPane.showMessageDialog(this, "Đã hủy thao tác cập nhật danh mục!", "POLYPOLO thông báo", 0);
        }
    }//GEN-LAST:event_btnSuaDMMouseClicked

    private void btnClearDMMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnClearDMMouseClicked
        // CLEAR
        txtMaDanhMuc.setText("");
        txtTenDanhMuc.setText("");
        txtTenDanhMuc.setBackground(Color.white);
    }//GEN-LAST:event_btnClearDMMouseClicked

    private void btnAnDMMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAnDMMouseClicked
        // HIDE
        JOptionPane.showMessageDialog(this, spService.anDanhMuc(getModelDM()));
        loadTableDanhMuc(spService.getList());
    }//GEN-LAST:event_btnAnDMMouseClicked

    private void btnAnDMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnDMActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAnDMActionPerformed

    private void tblDanhMucMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDanhMucMouseClicked
        // DANHMUC
        int pos = tblDanhMuc.getSelectedRow();
        Integer id = (Integer) tblDanhMuc.getValueAt(pos, 0);
        DanhMuc dm = spService.getListDMById(id);
        txtMaDanhMuc.setText(dm.getMaDM().toString());
        txtTenDanhMuc.setText(dm.getTenDM());
    }//GEN-LAST:event_tblDanhMucMouseClicked

    private void btnInMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnInMouseClicked
        // IN
        try {
            XSSFWorkbook workBook = new XSSFWorkbook();
            XSSFSheet sheet = workBook.createSheet("Danh Sách Sản Phẩm POLYPOLO");
            
            //STYLE TITLE
            XSSFRow titleRow = sheet.createRow(0);
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellValue("Danh Sách Sản Phẩm POLYPOLO"); 
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
            Cell cell = null;
            row = sheet.createRow(3);

            cell = row.createCell(0, org.apache.poi.ss.usermodel.CellType.STRING);
            cell.setCellValue("STT");

            cell = row.createCell(1, org.apache.poi.ss.usermodel.CellType.STRING);
            Sheet s = cell.getSheet();
            cell.setCellValue("Mã Sản Phẩm");
            s.autoSizeColumn(1);

            cell = row.createCell(2, org.apache.poi.ss.usermodel.CellType.STRING);
            cell.setCellValue("Tên Sản Phẩm");
            
            cell = row.createCell(3, org.apache.poi.ss.usermodel.CellType.STRING);
            cell.setCellValue("Danh Mục");

            cell = row.createCell(4, org.apache.poi.ss.usermodel.CellType.STRING);
            cell.setCellValue("Màu Sắc");

            cell = row.createCell(5, org.apache.poi.ss.usermodel.CellType.STRING);
            cell.setCellValue("Kích Cỡ");

            cell = row.createCell(6, org.apache.poi.ss.usermodel.CellType.STRING);
            cell.setCellValue("Giá Nhập");

            cell = row.createCell(7, org.apache.poi.ss.usermodel.CellType.STRING);
            cell.setCellValue("Giá Bán");

            cell = row.createCell(8, org.apache.poi.ss.usermodel.CellType.STRING);
            cell.setCellValue("Trạng Thái");

            cell = row.createCell(9, org.apache.poi.ss.usermodel.CellType.STRING);
            cell.setCellValue("Tồn Kho");

            for (int i = 0; i < 10; i++) {
                cell = row.getCell(i);
                cell.setCellStyle(headerStyle);
            }

            ArrayList<SanPhamViewModel> ls = spService.getListSanPham();
            for (int i = 0; i < ls.size(); i++) {
                row = sheet.createRow(4 + i);

                //STYLE FONT
                CellStyle docuStyle = workBook.createCellStyle();
                docuStyle.setAlignment(HorizontalAlignment.CENTER);
                
                CellStyle cellStyleFormatNumber = null;

                if (cellStyleFormatNumber == null) {
                    short format = (short) BuiltinFormats.getBuiltinFormat("#,##0");
                    Workbook workbook = row.getSheet().getWorkbook();
                    cellStyleFormatNumber = workbook.createCellStyle();
                    cellStyleFormatNumber.setDataFormat(format);
                }

                cell = row.createCell(0, org.apache.poi.ss.usermodel.CellType.NUMERIC);
                cell.setCellValue(i + 1);
                cell.setCellStyle(docuStyle);

                cell = row.createCell(1, org.apache.poi.ss.usermodel.CellType.NUMERIC);
                cell.setCellValue("SP" + ls.get(i).getMaSPCT());
                cell.setCellStyle(docuStyle);

                cell = row.createCell(2, org.apache.poi.ss.usermodel.CellType.STRING);
                cell.setCellValue(ls.get(i).getTenSP());
                s.autoSizeColumn(2);

                cell = row.createCell(3, org.apache.poi.ss.usermodel.CellType.STRING);
                cell.setCellValue(ls.get(i).getTenDM());
                s.autoSizeColumn(3);

                cell = row.createCell(4, org.apache.poi.ss.usermodel.CellType.STRING);
                cell.setCellValue(ls.get(i).getMauSac());
                cell.setCellStyle(docuStyle);

                cell = row.createCell(5, org.apache.poi.ss.usermodel.CellType.STRING);
                cell.setCellValue(ls.get(i).getKichCo());
                cell.setCellStyle(docuStyle);

                cell = row.createCell(6, org.apache.poi.ss.usermodel.CellType.STRING);
                cell.setCellValue(ls.get(i).getGiaNhap());
                s.autoSizeColumn(6);
                cell.setCellStyle(cellStyleFormatNumber);

                cell = row.createCell(7, org.apache.poi.ss.usermodel.CellType.STRING);
                cell.setCellValue(ls.get(i).getGiaBan());
                s.autoSizeColumn(7);

                cell = row.createCell(8, org.apache.poi.ss.usermodel.CellType.STRING);
                cell.setCellValue(ls.get(i).getTrangThai());
                s.autoSizeColumn(8);
                cell.setCellStyle(docuStyle);

                cell = row.createCell(9, org.apache.poi.ss.usermodel.CellType.NUMERIC);
                cell.setCellValue(ls.get(i).getSoLuong());
                cell.setCellStyle(docuStyle);
            }
            
            //STYLE
            CellStyle footerStyle = workBook.createCellStyle();
            footerStyle.setFont(headerFont);
            footerStyle.setAlignment(HorizontalAlignment.LEFT);
            
            //ROW CUỐI
            int rowS = 5 + ls.size();

            XSSFRow tongSPRow = sheet.createRow(rowS);
            org.apache.poi.ss.usermodel.Cell tongSPCell = tongSPRow.createCell(1);

            tongSPCell.setCellValue("Tổng Sản Phẩm:");
            tongSPCell.setCellStyle(footerStyle);
            org.apache.poi.ss.usermodel.Cell tongSPCelll = tongSPRow.createCell(2);
            tongSPCelll.setCellValue(ls.size());
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);

            org.apache.poi.ss.usermodel.Cell ngX = tongSPRow.createCell(7);
            ngX.setCellValue("Người Xuất:");
            ngX.setCellStyle(footerStyle);
            org.apache.poi.ss.usermodel.Cell thongT = tongSPRow.createCell(8);
            thongT.setCellValue(uService.getName(Login.dataStatic));
            sheet.autoSizeColumn(7);
            sheet.autoSizeColumn(8);
            
        
            File file = new File("C:\\Users\\X1\\OneDrive\\Documents\\Custom Office Templates\\DS_demo.xlsx");
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
        JOptionPane.showInternalMessageDialog(this, "Đã in danh sách thành công!", "POLYPOLO thông báo", 0);
    }//GEN-LAST:event_btnInMouseClicked

    private void LoadHideActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LoadHideActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_LoadHideActionPerformed

    private void btnSearch1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSearch1MouseClicked
        // SEARCH
        String danhMuc = cboLocDM.getSelectedItem().toString();
        ArrayList<SanPhamViewModel> ls = spService.getListByDanhMuc(danhMuc);
        loadTableSanPhamChiTiet(ls);
    }//GEN-LAST:event_btnSearch1MouseClicked

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        // SEARCH DM
        String name = txtSearchDM.getText();
        txtSearchDM.setText("");
        ArrayList<DanhMuc> ls = spService.getListDMByName(name);
        loadTableDanhMuc(ls);
    }//GEN-LAST:event_jButton1MouseClicked

    private void rdoDMConHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoDMConHangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoDMConHangActionPerformed

    private void btnSuaTTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaTTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSuaTTActionPerformed

    private void btnImportMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnImportMouseClicked
        //import
        Import im = new Import();
        im.setVisible(true);
    }//GEN-LAST:event_btnImportMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ChiTietSanPham;
    private javax.swing.JButton LoadHide;
    private javax.swing.JTabbedPane PaneQLSP;
    private javax.swing.JPanel ThuocTinh;
    private javax.swing.JButton btnAnDM;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnClearDM;
    private javax.swing.JButton btnClearTT;
    private javax.swing.JButton btnHide;
    private javax.swing.JButton btnHide1;
    private javax.swing.JButton btnHideTT;
    private javax.swing.JButton btnImport;
    private javax.swing.JButton btnIn;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnSearch1;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnSuaDM;
    private javax.swing.JButton btnSuaTT;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnThemDM;
    private javax.swing.JButton btnThemTT;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    private javax.swing.JComboBox<String> cboDanhMuc;
    private javax.swing.JComboBox<String> cboKichCo;
    private javax.swing.JComboBox<String> cboLocDM;
    private javax.swing.JComboBox<String> cboMauSac;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JRadioButton rdoConHang;
    private javax.swing.JRadioButton rdoDMConHang;
    private javax.swing.JRadioButton rdoDMHethang;
    private javax.swing.JRadioButton rdoHetHang;
    private javax.swing.JRadioButton rdoKichCo;
    private javax.swing.JRadioButton rdoMauSac;
    private javax.swing.JRadioButton rdoTTKC;
    private javax.swing.JRadioButton rdoTTMS;
    private javax.swing.JTable tblDanhMuc;
    private javax.swing.JTable tblSPCT;
    private javax.swing.JTable tblThuocTinh;
    private javax.swing.JTextField txtGiaBan;
    private javax.swing.JTextField txtGiaNhap;
    private javax.swing.JTextField txtMSP;
    private javax.swing.JTextField txtMaDanhMuc;
    private javax.swing.JTextField txtMaTT;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtSearchDM;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTSPCT;
    private javax.swing.JTextField txtTenDanhMuc;
    private javax.swing.JTextField txtTenTT;
    // End of variables declaration//GEN-END:variables
}
