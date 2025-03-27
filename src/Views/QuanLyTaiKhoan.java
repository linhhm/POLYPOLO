/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package Views;

import Models.User;
import Repositories.Huong_TaiKhoanRepository;
import Repositories.UserRepository;
import Services.NhanSuService;
import Services.UserService;
import Validator.MyValidate;
import ViewModels.UserViewModel;
import java.awt.event.ActionListener;
import static java.awt.image.ImageObserver.HEIGHT;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

/**
 *
 * @author X1
 */
public class QuanLyTaiKhoan extends javax.swing.JInternalFrame {
    UserService uService = new UserService();
    Huong_TaiKhoanRepository taiKhoanRepository = new Huong_TaiKhoanRepository();
    UserRepository userRepository = new UserRepository();
    
    /**
     * Creates new form QuanLyTaiKhoan
     */
    
    public QuanLyTaiKhoan() {
        initComponents();
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);
        loadTableTaiKhoan(uService.getList());
        getMyInfoData();
        loadTableTaiKhoan1(userRepository.getListUser());
        
    }

    //<editor-fold defaultstate="collapsed" desc=" LOAD MY INFO">
    //LOAD MY INFO
    public static UserViewModel u;

    private String d;

    public String Data(String d) {
        this.d = d;
        return d;
    }

    public void getMyInfoData() {
        lblTenDN.setText(taiKhoanRepository.getTenDN(Data(Login.dataStatic)));
        //HUONG - SAI TÊN HỘP - TXTMATKHAU - TXTPASSCODE
        txtPassCode.setText(taiKhoanRepository.getMK(Data(Login.dataStatic)));
        lblHoTen.setText(taiKhoanRepository.getName(Data(Login.dataStatic)));
        lblGioiTinh.setText(taiKhoanRepository.getGioiTinh(Data(Login.dataStatic)));
        lblVaiTro.setText(taiKhoanRepository.getVaiTro(Data(Login.dataStatic)));
        lblSDT.setText(taiKhoanRepository.getSDT(Data(Login.dataStatic)));
        lblDiaChi.setText(taiKhoanRepository.getDiaChi(Data(Login.dataStatic)));
    }
    //</editor-fold>

    void loadTableTaiKhoan(ArrayList<UserViewModel> ls) {
        DefaultTableModel model = (DefaultTableModel) tblTaiKhoan.getModel();
        model.setRowCount(0);
        for (UserViewModel u : ls) {
            model.addRow(new Object[]{
                u.getMaNV(),u.getId(), u.getTenNV(), u.getTenDN(), u.getMatKhau(),u.getVaiTro(),
                 u.getGioiT(),u.getSoDT(),u.getDiaC()
            });
        }
    }

    void loadTableTaiKhoan1(ArrayList<User> ls) {
        DefaultTableModel model = (DefaultTableModel) tblTaiKhoan1.getModel();
        model.setRowCount(0);
        for (User u : ls) {
            System.out.println(u.getUserID());
            model.addRow(new Object[]{
                u.getUserID(), u.getUserName(), u.getPassCode(), u.getRole()
            });
        }
    }

    public User getModelSua() {
        Integer userID = Integer.parseInt(txtMaNguoiDung1.getText());
        String userName = txtTenDangNhap1.getText();
        String passCode = txtMatKhau1.getText();
        String role = rdoVaiTroQuanLy1.isSelected() ? "Admin" : "Staff";
        User u = new User(userID, userName, passCode, role);
        return u;
    }

    public User getModel() {
        String userName = txtTenDangNhap1.getText();
        String passCode = txtMatKhau1.getText();
        String role = rdoVaiTroQuanLy1.isSelected() ? "Admin" : "Staff";
        User u = new User(userName, passCode, role);
        return u;
    }
    
    public void createUserWithQRCode() {
        try {
            String username = txtTenDangNhap1.getText();
            String password = txtMatKhau1.getText();
            String fileName = txtFileName.getText();

            if (fileName.isEmpty()) {
                fileName = username + "_" + password;
            }

            String loginInfo = username + ":" + password;
            ByteArrayOutputStream out = QRCode.from(loginInfo)
                    .to(ImageType.PNG).stream();

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chọn đường dẫn lưu");
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            int userSelection = fileChooser.showSaveDialog(this);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File directoryToSave = fileChooser.getSelectedFile();
                String path = directoryToSave.getAbsolutePath();

                FileOutputStream fout = new FileOutputStream(new File(path, fileName + ".PNG"));
                fout.write(out.toByteArray());
                fout.flush();
                fout.close();

                JOptionPane.showMessageDialog(this, "QR Code đã được thêm thành công tại: " + path);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Có lỗi xảy ra trong quá trình tạo QR Code: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    //VALIDATE
    public boolean validateTK() {
        StringBuilder stb = new StringBuilder();
        MyValidate v = new MyValidate();
        
        v.isEmpty(txtTenDangNhap1, stb, "Tên đăng nhập bị trống!");
        v.isEmpty(txtMatKhau1, stb, "Mật khẩu bị trống!");
        v.validPassCode(txtMatKhau1, "Mật khẩu phải là 3 số nguyên và không chứa ký tự đặc biệt!", stb);
        v.isRadioButtonSelected(buttonGroup1, stb, "Vui lòng chọn vai trò của bạn!");

        if (stb.length() > 0) {
            JOptionPane.showMessageDialog(this, stb);
            return false;
        } else {
            return true;
        }
    }
    
    //CLEAR FORM
    void clearFormTaiKhoan() {
        txtMaNguoiDung1.setText(null);
        txtTenDangNhap1.setText(null);
        txtMatKhau1.setText(null);
        rdoVaiTroNhanVien1.setSelected(false);
        rdoVaiTroQuanLy1.setSelected(false);
        chkShowPass1.setSelected(false);
        txtSearchTK1.setText(null);
        txtSearchTK1.setText(null);
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
        THONGTINTK = new javax.swing.JTabbedPane();
        QUANLYTAIKHOAN = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        txtTenDangNhap1 = new javax.swing.JTextField();
        txtMaNguoiDung1 = new javax.swing.JTextField();
        txtMatKhau1 = new javax.swing.JPasswordField();
        jLabel5 = new javax.swing.JLabel();
        rdoVaiTroNhanVien1 = new javax.swing.JRadioButton();
        rdoVaiTroQuanLy1 = new javax.swing.JRadioButton();
        jLabel31 = new javax.swing.JLabel();
        chkShowPass1 = new javax.swing.JCheckBox();
        jPanel4 = new javax.swing.JPanel();
        btnThem1 = new javax.swing.JButton();
        btnSua1 = new javax.swing.JButton();
        btnClear1 = new javax.swing.JButton();
        btnDelete1 = new javax.swing.JButton();
        txtFileName = new javax.swing.JTextField();
        btnFilePath = new javax.swing.JButton();
        btnCreateQR = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblTaiKhoan1 = new javax.swing.JTable();
        jLabel33 = new javax.swing.JLabel();
        btnSearchByName1 = new javax.swing.JButton();
        txtSearchTK1 = new javax.swing.JTextField();
        rdoFilter_QL1 = new javax.swing.JRadioButton();
        rdoFilter_NV1 = new javax.swing.JRadioButton();
        jLabel34 = new javax.swing.JLabel();
        QLTK_m = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblTaiKhoan = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        rdoFilter_QL = new javax.swing.JRadioButton();
        rdoFilter_NV = new javax.swing.JRadioButton();
        rdoNu = new javax.swing.JRadioButton();
        rdoNam = new javax.swing.JRadioButton();
        jLabel30 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        txtSearchTen = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        txtSearchTK = new javax.swing.JTextField();
        btnSearchByName = new javax.swing.JButton();
        btnLoad = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        MyInfo = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtPassCode = new javax.swing.JPasswordField();
        btnChangPass = new javax.swing.JButton();
        lblTenDN = new javax.swing.JLabel();
        lblHoTen = new javax.swing.JLabel();
        lblGioiTinh = new javax.swing.JLabel();
        lblVaiTro = new javax.swing.JLabel();
        lblSDT = new javax.swing.JLabel();
        lblDiaChi = new javax.swing.JLabel();
        chkShowHide = new javax.swing.JCheckBox();

        setMaximumSize(new java.awt.Dimension(990, 610));
        setMinimumSize(new java.awt.Dimension(990, 610));

        THONGTINTK.setAutoscrolls(true);
        THONGTINTK.setPreferredSize(new java.awt.Dimension(990, 610));

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP)), "Thông tin Tài Khoản", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Segoe UI", 3, 14))); // NOI18N

        jLabel28.setText("Mật Khẩu:");

        jLabel29.setText("Mã Người Dùng:");

        txtMaNguoiDung1.setEnabled(false);

        jLabel5.setText("Tên Đăng Nhập:");

        buttonGroup1.add(rdoVaiTroNhanVien1);
        rdoVaiTroNhanVien1.setText("Nhân Viên");

        buttonGroup1.add(rdoVaiTroQuanLy1);
        rdoVaiTroQuanLy1.setText("Quản Lý");

        jLabel31.setText("Vai Trò:");

        chkShowPass1.setText("Hiện mật khẩu");
        chkShowPass1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                chkShowPass1MouseClicked(evt);
            }
        });

        jPanel4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        btnThem1.setText("THÊM");
        btnThem1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnThem1MouseClicked(evt);
            }
        });

        btnSua1.setText("SỬA");
        btnSua1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSua1MouseClicked(evt);
            }
        });

        btnClear1.setText("MỚI");
        btnClear1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnClear1MouseClicked(evt);
            }
        });

        btnDelete1.setText("XÓA");
        btnDelete1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDelete1MouseClicked(evt);
            }
        });
        btnDelete1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelete1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addComponent(btnThem1)
                .addGap(18, 18, 18)
                .addComponent(btnSua1)
                .addGap(18, 18, 18)
                .addComponent(btnClear1)
                .addGap(18, 18, 18)
                .addComponent(btnDelete1)
                .addGap(23, 23, 23))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem1)
                    .addComponent(btnSua1)
                    .addComponent(btnClear1)
                    .addComponent(btnDelete1))
                .addGap(16, 16, 16))
        );

        txtFileName.setEnabled(false);

        btnFilePath.setText("Choose File");

        btnCreateQR.setText("TẠO QR");
        btnCreateQR.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCreateQRMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel5))
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(13, 13, 13))
                            .addComponent(btnCreateQR))
                        .addGap(28, 28, 28)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addComponent(txtMatKhau1, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(chkShowPass1))
                            .addComponent(txtTenDangNhap1, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMaNguoiDung1, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addComponent(rdoVaiTroQuanLy1)
                                .addGap(92, 92, 92)
                                .addComponent(rdoVaiTroNhanVien1))
                            .addComponent(txtFileName, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnFilePath)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(txtMaNguoiDung1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtTenDangNhap1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel28)
                        .addComponent(txtMatKhau1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(chkShowPass1))
                .addGap(18, 18, 18)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoVaiTroQuanLy1)
                    .addComponent(rdoVaiTroNhanVien1)
                    .addComponent(jLabel31))
                .addGap(18, 18, 18)
                .addComponent(txtFileName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnFilePath)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 101, Short.MAX_VALUE)
                .addComponent(btnCreateQR)
                .addGap(47, 47, 47)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Chi Tiết Tài Khoản", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 18))); // NOI18N

        tblTaiKhoan1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Mã Tài Khoản", "Tên Tài Khoản", "Mật Khẩu", "Vai Trò"
            }
        ));
        tblTaiKhoan1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblTaiKhoan1MouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblTaiKhoan1);

        jLabel33.setText("Tìm kiếm:");

        btnSearchByName1.setText("SEARCH");
        btnSearchByName1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSearchByName1MouseClicked(evt);
            }
        });
        btnSearchByName1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchByName1ActionPerformed(evt);
            }
        });

        buttonGroup3.add(rdoFilter_QL1);
        rdoFilter_QL1.setText("Quản Lý");
        rdoFilter_QL1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoFilter_QL1MouseClicked(evt);
            }
        });

        buttonGroup3.add(rdoFilter_NV1);
        rdoFilter_NV1.setText("Nhân Viên");
        rdoFilter_NV1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoFilter_NV1MouseClicked(evt);
            }
        });

        jLabel34.setText("Lọc:");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel34)
                        .addGap(18, 18, 18)
                        .addComponent(rdoFilter_QL1)
                        .addGap(18, 18, 18)
                        .addComponent(rdoFilter_NV1))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel33)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtSearchTK1, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnSearchByName1))
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 403, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(txtSearchTK1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearchByName1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rdoFilter_NV1)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rdoFilter_QL1)
                        .addComponent(jLabel34)))
                .addGap(16, 16, 16))
        );

        javax.swing.GroupLayout QUANLYTAIKHOANLayout = new javax.swing.GroupLayout(QUANLYTAIKHOAN);
        QUANLYTAIKHOAN.setLayout(QUANLYTAIKHOANLayout);
        QUANLYTAIKHOANLayout.setHorizontalGroup(
            QUANLYTAIKHOANLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(QUANLYTAIKHOANLayout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        QUANLYTAIKHOANLayout.setVerticalGroup(
            QUANLYTAIKHOANLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(QUANLYTAIKHOANLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(QUANLYTAIKHOANLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        THONGTINTK.addTab("Quản Lý Tài Khoản", QUANLYTAIKHOAN);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Chi Tiết Tài Khoản", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 18))); // NOI18N

        tblTaiKhoan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã Nhân Viên", "Mã Người Dùng", "Tên Nhân Viên", "Tên Tài Khoản", "Mật Khẩu", "Vai Trò", "Giới Tính", "Số Điện Thoại", "Địa Chỉ"
            }
        ));
        tblTaiKhoan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblTaiKhoanMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblTaiKhoan);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Bộ Lọc", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        jLabel22.setText("Lọc vai trò:");

        buttonGroup3.add(rdoFilter_QL);
        rdoFilter_QL.setText("Quản Lý");
        rdoFilter_QL.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoFilter_QLMouseClicked(evt);
            }
        });

        buttonGroup3.add(rdoFilter_NV);
        rdoFilter_NV.setText("Nhân Viên");
        rdoFilter_NV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoFilter_NVMouseClicked(evt);
            }
        });

        buttonGroup4.add(rdoNu);
        rdoNu.setText("Nữ");
        rdoNu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoNuMouseClicked(evt);
            }
        });

        buttonGroup4.add(rdoNam);
        rdoNam.setText("Nam");
        rdoNam.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoNamMouseClicked(evt);
            }
        });

        jLabel30.setText("Lọc giới tính:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(rdoNam)
                        .addGap(18, 18, 18)
                        .addComponent(rdoNu))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(rdoFilter_QL)
                        .addGap(18, 18, 18)
                        .addComponent(rdoFilter_NV)))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rdoFilter_NV)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rdoFilter_QL)
                        .addComponent(jLabel22)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rdoNu)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rdoNam)
                        .addComponent(jLabel30)))
                .addGap(30, 30, 30))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tìm Kiếm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        jLabel23.setText("Theo tên nhân viên:");

        jLabel24.setText("Theo tên tài khoản:");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtSearchTen, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtSearchTK, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(92, 92, 92))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(txtSearchTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(txtSearchTK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28))
        );

        btnSearchByName.setText("SEARCH");
        btnSearchByName.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSearchByNameMouseClicked(evt);
            }
        });
        btnSearchByName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchByNameActionPerformed(evt);
            }
        });

        btnLoad.setText("LOAD");
        btnLoad.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLoadMouseClicked(evt);
            }
        });
        btnLoad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoadActionPerformed(evt);
            }
        });

        btnReset.setText("RESET");
        btnReset.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnResetMouseClicked(evt);
            }
        });
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 893, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(19, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 406, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnSearchByName, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnLoad, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnReset, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(37, 37, 37))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(btnSearchByName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLoad)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnReset)))
                .addContainerGap())
        );

        javax.swing.GroupLayout QLTK_mLayout = new javax.swing.GroupLayout(QLTK_m);
        QLTK_m.setLayout(QLTK_mLayout);
        QLTK_mLayout.setHorizontalGroup(
            QLTK_mLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, QLTK_mLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        QLTK_mLayout.setVerticalGroup(
            QLTK_mLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(QLTK_mLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        THONGTINTK.addTab("Thông Tin Tài Khoản", QLTK_m);

        MyInfo.setPreferredSize(new java.awt.Dimension(990, 610));

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP)), "Thông tin Chi Tiết", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Segoe UI", 3, 14))); // NOI18N

        jLabel7.setText("Họ Tên:");

        jLabel8.setText("Vai Trò:");

        jLabel10.setText("Tên Đăng Nhập:");

        jLabel11.setText("Giới Tính:");

        jLabel12.setText("Số Điện Thoại:");

        jLabel14.setText("Địa Chỉ:");

        jLabel18.setText("Mật Khẩu:");

        txtPassCode.setEnabled(false);

        btnChangPass.setText("ĐỔI MẬT KHẨU");
        btnChangPass.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnChangPassMouseClicked(evt);
            }
        });

        lblTenDN.setText("----------");

        lblHoTen.setText("----------");

        lblGioiTinh.setText("----------");

        lblVaiTro.setText("----------");

        lblSDT.setText("----------");

        lblDiaChi.setText("----------");

        chkShowHide.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        chkShowHide.setText("Hiển thị mật khẩu");
        chkShowHide.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                chkShowHideMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(64, 64, 64)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTenDN, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblHoTen)
                            .addComponent(lblGioiTinh)
                            .addComponent(lblVaiTro)
                            .addComponent(lblSDT)
                            .addComponent(lblDiaChi)
                            .addComponent(chkShowHide, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPassCode, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(344, 344, 344)
                        .addComponent(btnChangPass, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(459, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(lblTenDN))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txtPassCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblHoTen)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(lblGioiTinh))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(lblVaiTro))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(lblSDT))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(lblDiaChi))
                .addGap(18, 18, 18)
                .addComponent(chkShowHide)
                .addGap(18, 18, 18)
                .addComponent(btnChangPass)
                .addContainerGap(34, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout MyInfoLayout = new javax.swing.GroupLayout(MyInfo);
        MyInfo.setLayout(MyInfoLayout);
        MyInfoLayout.setHorizontalGroup(
            MyInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MyInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );
        MyInfoLayout.setVerticalGroup(
            MyInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MyInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(113, Short.MAX_VALUE))
        );

        THONGTINTK.addTab("My Info", MyInfo);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(THONGTINTK, javax.swing.GroupLayout.PREFERRED_SIZE, 954, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(THONGTINTK, javax.swing.GroupLayout.PREFERRED_SIZE, 549, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSearchByNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchByNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSearchByNameActionPerformed

    private void btnSearchByNameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSearchByNameMouseClicked
        // SEARCH BY NAME
        String name = txtSearchTen.getText();
        String tendn =txtSearchTK.getText();
        ArrayList<UserViewModel> ls = uService.getListByTen(name,tendn);
        loadTableTaiKhoan(ls);
        txtSearchTen.setText("");
        txtSearchTK.setText("");
    }//GEN-LAST:event_btnSearchByNameMouseClicked

    private void tblTaiKhoanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblTaiKhoanMouseClicked
        // CLICK
    }//GEN-LAST:event_tblTaiKhoanMouseClicked

    private void chkShowHideMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chkShowHideMouseClicked
        // SHOW PASS
        if (chkShowHide.isSelected()) {
            txtPassCode.setEchoChar((char) 0);
        } else {
            txtPassCode.setEchoChar('*');
        }
    }//GEN-LAST:event_chkShowHideMouseClicked

    private void btnChangPassMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnChangPassMouseClicked
        //
        UserRepository userRepository = new UserRepository();
        JPanel panel = new JPanel();
        String input = JOptionPane.showInputDialog(this, "Nhập mật khẩu mới:", "Đổi mật khẩu", HEIGHT);
        System.out.println(input);
        if (input != null) {
            User u = new User(userRepository.getTenDN(Data(Login.dataStatic)), input);
            JOptionPane.showMessageDialog(this, uService.updateMatKhau(u));
            txtPassCode.setText(u.getPassCode());
        } else {
            // Người dùng đã chọn "Hủy" hoặc đóng cửa sổ nhập liệu
            // Ví dụ: Xử lý trường hợp không có dữ liệu nhập
        }
    }//GEN-LAST:event_btnChangPassMouseClicked

    private void tblTaiKhoan1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblTaiKhoan1MouseClicked
        // TODO add your handling code here:
        int pos = tblTaiKhoan1.getSelectedRow();
        User u = userRepository.getListUser().get(pos); 

        txtMaNguoiDung1.setText(String.valueOf(u.getUserID()));
        txtTenDangNhap1.setText(u.getUserName());
        txtMatKhau1.setText(u.getPassCode());
        
        if (u.getRole().equalsIgnoreCase("admin")) {
            rdoVaiTroQuanLy1.setSelected(true);
        } else {
            rdoVaiTroNhanVien1.setSelected(true);
        }
    }//GEN-LAST:event_tblTaiKhoan1MouseClicked

    private void btnSearchByName1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSearchByName1MouseClicked
        // TODO add your handling code here:
        String name = txtSearchTK1.getText();
        ArrayList<User> list = userRepository.getListByTenDN(name);
        loadTableTaiKhoan1(list);
        txtSearchTK.setText("");
    }//GEN-LAST:event_btnSearchByName1MouseClicked

    private void btnSearchByName1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchByName1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSearchByName1ActionPerformed

    private void btnDelete1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelete1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDelete1ActionPerformed

    private void btnDelete1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDelete1MouseClicked
        // TODO add your handling code here:
        try {
            int result = JOptionPane.showConfirmDialog(this, "Bạn muốn xóa tài khoản này?", "POLYPOLO xác nhận", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                String maND = txtMaNguoiDung1.getText();
                String kq = uService.delete(maND);
                JOptionPane.showMessageDialog(this, kq);
                loadTableTaiKhoan1(uService.getListUser());
                clearFormTaiKhoan();
            } else {
                JOptionPane.showMessageDialog(this, "Đã hủy thao tác xóa tài khoản!", "POLYPOLO thông báo", 0);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Tài khoản đang liên kết với người dùng!", "POLYPOLO thông báo", 0);
        }

    }//GEN-LAST:event_btnDelete1MouseClicked

    private void btnClear1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnClear1MouseClicked
        // TODO add your handling code here:
        txtMaNguoiDung1.setText(null);
        txtTenDangNhap1.setText(null);
        txtMatKhau1.setText(null);
        rdoVaiTroNhanVien1.setSelected(false);
        rdoVaiTroQuanLy1.setSelected(true);
        chkShowPass1.setSelected(false);
        loadTableTaiKhoan1(uService.getListUser());
    }//GEN-LAST:event_btnClear1MouseClicked

    private void btnSua1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSua1MouseClicked
        // TODO add your handling code here:
        int result = JOptionPane.showConfirmDialog(this, "Bạn muốn sửa tài khoản này?", "POLYPOLO xác nhận", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            String resultNek = uService.update(getModelSua());
            JOptionPane.showMessageDialog(this, resultNek);
            loadTableTaiKhoan1(uService.getListUser());
            clearFormTaiKhoan();
        } else {
            JOptionPane.showMessageDialog(this, "Đã hủy thao tác sửa tài khoản!", "POLYPOLO thông báo", 0);
        }
    }//GEN-LAST:event_btnSua1MouseClicked

    private void btnThem1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThem1MouseClicked
        // TODO add your handling code here:
        int result = JOptionPane.showConfirmDialog(this, "Bạn muốn tạo tài khoản?", "POLYPOLO xác nhận", JOptionPane.YES_NO_OPTION);
        String name = txtTenDangNhap1.getText().trim();
        if (uService.checkName(name)) {
            JOptionPane.showMessageDialog(this, "Tên người dùng đã tồn tại, không thể thêm!");
            loadTableTaiKhoan1(uService.getListUser());
        } else {
            if (validateTK() && result == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(this, uService.addAccount(getModel()));
                loadTableTaiKhoan1(uService.getListUser());
                clearFormTaiKhoan();
            } else {
                JOptionPane.showMessageDialog(this, "Đã hủy thao tác thêm tài khoản!", "POLYPOLO thông báo", 0);
            }
        }

    }//GEN-LAST:event_btnThem1MouseClicked

    private void chkShowPass1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chkShowPass1MouseClicked
        // SHOW/HIDE PASS
        if (chkShowPass1.isSelected()) {
            txtMatKhau1.setEchoChar((char) 0);
        } else {
            txtMatKhau1.setEchoChar('*');
        }
    }//GEN-LAST:event_chkShowPass1MouseClicked

    private void rdoFilter_QL1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoFilter_QL1MouseClicked
        //
        if (rdoFilter_QL1.isSelected()) {
            loadTableTaiKhoan1(userRepository.getListByVaiTro("admin"));
        } 
    }//GEN-LAST:event_rdoFilter_QL1MouseClicked

    private void rdoFilter_NV1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoFilter_NV1MouseClicked
        // 
        if (rdoFilter_NV1.isSelected()) {
            loadTableTaiKhoan1(userRepository.getListByVaiTro("staff"));
        } 
    }//GEN-LAST:event_rdoFilter_NV1MouseClicked

    private void rdoFilter_QLMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoFilter_QLMouseClicked
        // FILTER
        if (rdoFilter_QL.isSelected()) {
            loadTableTaiKhoan(userRepository.getListByVaiTroTTTK("Admin"));
        }else{
            if (rdoFilter_QL.isSelected()&&rdoNam.isSelected()) {
                loadTableTaiKhoan(uService.filterByRoleAndGender("Admin", "Nam"));
                rdoNu.setVisible(false);
            }else{
                loadTableTaiKhoan(uService.filterByRoleAndGender("Admin", "Nữ"));
                rdoNam.setVisible(false);
            }
        }
    }//GEN-LAST:event_rdoFilter_QLMouseClicked

    private void btnLoadMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLoadMouseClicked
        // LOAD
        rdoNam.setSelected(true);
        rdoFilter_QL.setSelected(true);
        loadTableTaiKhoan(uService.getList());
    }//GEN-LAST:event_btnLoadMouseClicked

    private void btnLoadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnLoadActionPerformed

    private void rdoFilter_NVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoFilter_NVMouseClicked
        //FILTER
        if (rdoFilter_NV.isSelected()) {
            loadTableTaiKhoan(userRepository.getListByVaiTroTTTK("Staff"));
        }else{
            if (rdoFilter_NV.isSelected()&&rdoNam.isSelected()) {
                loadTableTaiKhoan(uService.filterByRoleAndGender("Staff", "Nam"));
                rdoNu.setVisible(false);
                rdoNu.addActionListener((ActionListener) this);
            }else{
                loadTableTaiKhoan(uService.filterByRoleAndGender("Staff", "Nữ"));
                rdoNam.setVisible(false);
                rdoNam.addActionListener((ActionListener) this);
            }
        }
    }//GEN-LAST:event_rdoFilter_NVMouseClicked

    private void rdoNamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoNamMouseClicked
        // FILTER
        if (rdoNam.isSelected()) {
            loadTableTaiKhoan(userRepository.getListByGioiTinh("Nam"));
        }
    }//GEN-LAST:event_rdoNamMouseClicked

    private void rdoNuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoNuMouseClicked
        //FILTER
        if (rdoNu.isSelected()) {
            loadTableTaiKhoan(userRepository.getListByGioiTinh("Nữ"));
        }
    }//GEN-LAST:event_rdoNuMouseClicked

    private void btnResetMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnResetMouseClicked
        // RESET
        rdoFilter_QL.setSelected(true);
        rdoNam.setSelected(true);
        loadTableTaiKhoan(uService.getList());
    }//GEN-LAST:event_btnResetMouseClicked

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnCreateQRMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCreateQRMouseClicked
        // CREATE QR
        createUserWithQRCode();
    }//GEN-LAST:event_btnCreateQRMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel MyInfo;
    private javax.swing.JPanel QLTK_m;
    private javax.swing.JPanel QUANLYTAIKHOAN;
    private javax.swing.JTabbedPane THONGTINTK;
    private javax.swing.JButton btnChangPass;
    private javax.swing.JButton btnClear1;
    private javax.swing.JButton btnCreateQR;
    private javax.swing.JButton btnDelete1;
    private javax.swing.JButton btnFilePath;
    private javax.swing.JButton btnLoad;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSearchByName;
    private javax.swing.JButton btnSearchByName1;
    private javax.swing.JButton btnSua1;
    private javax.swing.JButton btnThem1;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    private javax.swing.JCheckBox chkShowHide;
    private javax.swing.JCheckBox chkShowPass1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lblDiaChi;
    private javax.swing.JLabel lblGioiTinh;
    private javax.swing.JLabel lblHoTen;
    private javax.swing.JLabel lblSDT;
    private javax.swing.JLabel lblTenDN;
    private javax.swing.JLabel lblVaiTro;
    private javax.swing.JRadioButton rdoFilter_NV;
    private javax.swing.JRadioButton rdoFilter_NV1;
    private javax.swing.JRadioButton rdoFilter_QL;
    private javax.swing.JRadioButton rdoFilter_QL1;
    private javax.swing.JRadioButton rdoNam;
    private javax.swing.JRadioButton rdoNu;
    private javax.swing.JRadioButton rdoVaiTroNhanVien1;
    private javax.swing.JRadioButton rdoVaiTroQuanLy1;
    private javax.swing.JTable tblTaiKhoan;
    private javax.swing.JTable tblTaiKhoan1;
    private javax.swing.JTextField txtFileName;
    private javax.swing.JTextField txtMaNguoiDung1;
    private javax.swing.JPasswordField txtMatKhau1;
    private javax.swing.JPasswordField txtPassCode;
    private javax.swing.JTextField txtSearchTK;
    private javax.swing.JTextField txtSearchTK1;
    private javax.swing.JTextField txtSearchTen;
    private javax.swing.JTextField txtTenDangNhap1;
    // End of variables declaration//GEN-END:variables
}
/*
Bỏ:(số dòng file cũ)
- loadDataToFormTaiKhoan (66)
- getModelTaiKhoan (103)
- clear validator(178)
- getModelTK (131)
- validateTaiKhoan(142)

Sửa: (số dòng file mới)
- form Quản lý tài khoản, QLTK
- loadTableTaiKhoan (Quản lý tài khoản, 78) + thêm loadTableTaiKhoan1 (của QLTK,90 )
- getModel (120 file cũ) + thêm getModelSua (102)
- clearFormTaiKhoan (141) + thêm  validateTK(122)
- Copy lại các button
 + Button btnChangPassMouseClicked chỉ cần thêm dòng (903)
- QuanLyTaiKhoan(40): xóa 2 dòng cuối trang cũ - thêm 3 dòng cuối trang mới
Giữ:
- chkShowHideMouseClicked (884)
- trừ LoadMyInfo (52)

*/