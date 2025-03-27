CREATE DATABASE POLYPOLO_UPDATE;
DROP DATABASE POLYPOLO_UPDATE;

USE POLYPOLO_UPDATE;

--CREATE TABLE
CREATE TABLE DanhMuc(
	MaDanhMuc INT IDENTITY(1,1) PRIMARY KEY,
	TenDanhMuc NVARCHAR(250) NOT NULL,
	TrangThai NVARCHAR(30) NOT NULL,
	Deleted INT
);

CREATE TABLE SanPham (
    MaSanPham INT PRIMARY KEY IDENTITY(1,1),
    MaDanhMuc INT,
    TrangThai NVARCHAR(30) NOT NULL,
    GiaNhap DECIMAL(13,3) NOT NULL,
    GiaBan DECIMAL(13,3) NOT NULL,
	KhuVucID INT NOT NULL,
	Deleted INT
    FOREIGN KEY (MaDanhMuc) REFERENCES DanhMuc(MaDanhMuc)
);

CREATE TABLE KhuVucKho (
    KhuVucID INT PRIMARY KEY IDENTITY(1,1),
    TenKhuVuc NVARCHAR(255) NOT NULL,
	Deleted INT
);

ALTER TABLE SanPham
ADD CONSTRAINT FK_SanPham_KhuVucKho FOREIGN KEY (KhuVucID) REFERENCES KhuVucKho(KhuVucID);

CREATE TABLE Size (
    MaSize INT PRIMARY KEY IDENTITY(1,1),
    TenSize NVARCHAR(10) NOT NULL,
	Deleted INT
);

CREATE TABLE MauSac (
    MaMau INT PRIMARY KEY IDENTITY(1,1),
    TenMau NVARCHAR(50) NOT NULL,
	Deleted INT
);

CREATE TABLE ChatLieu (
    MaChatLieu INT PRIMARY KEY IDENTITY(1,1),
    TenChatLieu NVARCHAR(50) NOT NULL,
	Deleted INT
);

CREATE TABLE SanPhamChiTiet (
    MaSanPhamChiTiet INT PRIMARY KEY IDENTITY(1,1),
    MaSanPham INT,
    TenSanPhamChiTiet NVARCHAR(250) NOT NULL,
	HinhAnh VARCHAR(255),
    MaSize INT,
    MaMau INT,
	MaChatLieu INT,
    TrangThai NVARCHAR(30) NOT NULL,
    SoLuongTon INT NOT NULL,
	NgayNhapKho DATE,
	Deleted INT,
    FOREIGN KEY (MaSanPham) REFERENCES SanPham(MaSanPham),
    FOREIGN KEY (MaSize) REFERENCES Size(MaSize),
    FOREIGN KEY (MaMau) REFERENCES MauSac(MaMau),
	FOREIGN KEY (MaChatLieu) REFERENCES ChatLieu(MaChatLieu)
);
--ALTER TABLE SanPhamChiTiet ADD Barcode NVARCHAR(255); 
--ALTER TABLE SanPhamChiTiet ADD GiaKhuyenMai DECIMAL(13,3);

CREATE TABLE NguoiDung(
    MaNguoiDung INT PRIMARY KEY IDENTITY(1,1),
    TenDangNhap VARCHAR(50) UNIQUE NOT NULL,
    MatKhau VARCHAR(10) NOT NULL, 
    VaiTro NVARCHAR(50) NOT NULL,
	Deleted INT
);

CREATE TABLE NhanVien (
    MaNhanVien INT PRIMARY KEY IDENTITY(1,1),
    MaNguoiDung INT,
    TenNhanVien NVARCHAR(250) NOT NULL,
    GioiTinh NVARCHAR(5),
    NgaySinh DATE,
    SoDienThoai VARCHAR(15) NOT NULL,
    DiaChi NVARCHAR(250),
	Deleted INT,
    FOREIGN KEY (MaNguoiDung) REFERENCES NguoiDung(MaNguoiDung)
);

CREATE TABLE HoaDon (
    MaHoaDon INT PRIMARY KEY IDENTITY(1,1),
    MaNhanVien INT,
    TenKhachHang NVARCHAR(250) NOT NULL,
    TenNhanVien NVARCHAR(250) NOT NULL,
    TongTien DECIMAL(13,3) NOT NULL,
    NgayLap DATE,
    TrangThai NVARCHAR(25) NOT NULL,
	PhuongThucThanhToan NVARCHAR(50) NOT NULL,
	LoaiKhachHang NVARCHAR(250) NOT NULL,
	Deleted INT,
    FOREIGN KEY (MaNhanVien) REFERENCES NhanVien(MaNhanVien)
);

CREATE TABLE HoaDonChiTiet (
	MaHoaDonChiTiet INT PRIMARY KEY IDENTITY(1,1),
    MaHoaDon INT,
    MaSanPhamChiTiet INT,
    SoLuong INT NOT NULL,
    DonGia DECIMAL(13,3) NOT NULL,
	Deleted INT,
    FOREIGN KEY (MaHoaDon) REFERENCES HoaDon(MaHoaDon),
    FOREIGN KEY (MaSanPhamChiTiet) REFERENCES SanPhamChiTiet(MaSanPhamChiTiet)
);

CREATE TABLE KhachHang(
	MaKhachHang INT PRIMARY KEY IDENTITY(1,1),
	MaHoaDon INT,
	TenKhachHang NVARCHAR(250) NOT NULL,
	GioiTinh NVARCHAR(5),
	SoDienThoai VARCHAR(15) NOT NULL,
	DiaChi NVARCHAR(250) NOT NULL,
	LoaiKhachHang NVARCHAR(250) NOT NULL,
	Deleted INT
	FOREIGN KEY (MaHoaDon) REFERENCES HoaDon(MaHoaDon),
);

CREATE TABLE NhanHang (
    NhanHangID INT PRIMARY KEY IDENTITY(1,1),
    TenNhanHang NVARCHAR(255) NOT NULL,
	Deleted INT
);

CREATE TABLE NhaCungCap (
    NhaCungCapID INT PRIMARY KEY IDENTITY(1,1),
    TenNhaCungCap NVARCHAR(255) NOT NULL,
	Deleted INT
);

CREATE TABLE PhieuNhapKho (
    PhieuNhapID INT PRIMARY KEY IDENTITY(1,1),
    NhaCungCapID INT NOT NULL,
    NhanVienID INT NOT NULL,
    ThoiGianNhap DATE NOT NULL,
    TongDon DECIMAL(13, 3) NOT NULL,
	Deleted INT,
    FOREIGN KEY (NhaCungCapID) REFERENCES NhaCungCap(NhaCungCapID),
    FOREIGN KEY (NhanVienID) REFERENCES NhanVien(MaNhanVien)
);

CREATE TABLE PhieuXuatKho (
    PhieuXuatID INT PRIMARY KEY IDENTITY(1,1),
    TenKhachHang NVARCHAR(255) NOT NULL,
    NhanVienID INT NOT NULL,
    ThoiGianXuat DATE,
    TongDon DECIMAL(13, 3) NOT NULL,
	Deleted INT,
    FOREIGN KEY (NhanVienID) REFERENCES NhanVien(MaNhanVien)
);

CREATE TABLE ChiTietPhieuNhap (
    ChiTietID INT PRIMARY KEY IDENTITY(1,1),
    PhieuNhapID INT NOT NULL,
    NhanHangID INT NOT NULL,
    SoLuong INT NOT NULL,
    DonGia DECIMAL(13, 3) NOT NULL,
	Deleted INT,
    FOREIGN KEY (PhieuNhapID) REFERENCES PhieuNhapKho(PhieuNhapID),
    FOREIGN KEY (NhanHangID) REFERENCES NhanHang(NhanHangID)
);

CREATE TABLE ChiTietPhieuXuat (
    ChiTietID INT PRIMARY KEY IDENTITY(1,1),
    PhieuXuatID INT NOT NULL,
    NhanHangID INT NOT NULL,
    SoLuong INT NOT NULL,
    DonGia DECIMAL(13, 3) NOT NULL,
	Deleted INT,
    FOREIGN KEY (PhieuXuatID) REFERENCES PhieuXuatKho(PhieuXuatID),
    FOREIGN KEY (NhanHangID) REFERENCES NhanHang(NhanHangID)
);

--INSERT DATA
INSERT INTO NguoiDung(TenDangNhap,MatKhau,VaiTro,Deleted) 
						VALUES('admin','111', 'Admin',0)
						      , ('staff', '222', 'Staff',0)
							  , ('hoadk','444','Staff',0)
							  , ('linhhm','333', 'Admin',0)
							  , ('huongdm', '555', 'Staff',0)
							  ;
---------------
INSERT INTO NhanVien(MaNguoiDung,TenNhanVien,GioiTinh,NgaySinh,SoDienThoai,DiaChi,Deleted)
			VALUES('1', 'Loi Choi', 'Nam', '1997-09-01', '0123456789', N'Hà Nội',0)
					, ('2', N'Ly Lê', N'Nữ', '1998-07-28', '0234567891', N'TP.HCM',0)
					, ('3', N'Hòa Đinh', N'Nam', '2000-06-26', '0234156781', N'Quảng Ninh',0)
					, ('4', N'Linh Hoàng', N'Nữ', '2001-08-28', '0634156781', N'Hải Phòng',0)
					, ('5', N'Hương Đào', N'Nữ', '1996-01-08', '0321451671', N'Ninh Bình',0)
					;
-----------------
INSERT INTO DanhMuc(TenDanhMuc, TrangThai,Deleted) VALUES
												  (N'Polo Unisex', N'Hoạt động',0)
												, (N'Polo Đặc Biệt', N'Hoạt động',0)
												, (N'Polo Gen-Z', N'Ngừng hoạt động',0)
												, (N'Polo Limited-Edition', N'Hoạt động',0)
												, (N'Polo Special Tết 2024', N'Hoạt động',0)
												, (N'Polo Nam', N'Hoạt động',0)
												, (N'Polo Nữ', N'Hoạt động',0)
												, (N'Polo Trẻ Em', N'Hoạt động',0)
												;
---------------------
INSERT INTO KhuVucKho (TenKhuVuc, Deleted) VALUES (N'Kho A', 0), 
												(N'Kho B', 0), 
												(N'Kho C', 0), 
												(N'Kho D', 0), 
												(N'Kho E', 0), 
												(N'Kho G', 0), 
												(N'Kho H', 0), 
												(N'Kho JK', 0);
------------------
INSERT INTO SanPham(MaDanhMuc, TrangThai, GiaNhap, GiaBan, KhuVucID, Deleted) 
VALUES (1, N'Còn hàng', 450000, 550000, 1,0), 
       (2, N'Còn hàng', 620000, 780000, 2,0),
       (2, N'Còn hàng', 320000, 560000, 3,0),
       (3, N'Còn hàng', 120000, 240000, 4, 0),
       (4, N'Còn hàng', 230000, 780000,5, 0),
       (5, N'Còn hàng', 120000, 580000,2,0),
       (6, N'Còn hàng', 350000, 760000,3,0),
       (7, N'Còn hàng', 1350000, 2560000,5,0),
       (8, N'Còn hàng', 201200, 760000,5,0),
       (1, N'Còn hàng', 550000, 600000,4,0),
       (1, N'Còn hàng', 500000, 650000,3, 0),
       (2, N'Còn hàng', 700000, 900000,4, 0),
       (3, N'Còn hàng', 200000, 400000,2, 0),
       (4, N'Còn hàng', 300000, 850000,5, 0),
       (5, N'Còn hàng', 150000, 650000,3, 0),
       (6, N'Còn hàng', 450000, 850000,1, 0),
       (7, N'Còn hàng', 1600000, 3000000,1, 0),
       (8, N'Còn hàng', 250000, 800000,7, 0),
       (1, N'Còn hàng', 600000, 700000,8, 0),
       (2, N'Còn hàng', 800000, 1000000,8, 0),
       (3, N'Còn hàng', 220000, 420000,7, 0),
       (4, N'Còn hàng', 310000, 860000,6, 0),
       (5, N'Còn hàng', 160000, 660000,6, 0),
       (6, N'Còn hàng', 460000, 860000,5, 0),
       (7, N'Còn hàng', 1650000, 3050000,2, 0),
       (8, N'Còn hàng', 255000, 810000,3, 0),
       (1, N'Còn hàng', 610000, 710000,4, 0),
       (2, N'Còn hàng', 810000, 1010000,7, 0),
       (3, N'Còn hàng', 225000, 425000,8, 0),
       (4, N'Còn hàng', 315000, 865000,5, 0);

INSERT INTO Size (TenSize,Deleted) VALUES ('XS',0), ('S',0), ('M',0), ('L',0);

INSERT INTO MauSac (TenMau,Deleted) VALUES (N'Xanh',0), (N'Đỏ',0), (N'Trắng',0), (N'Đen',0);

INSERT INTO ChatLieu (TenChatLieu, Deleted) VALUES (N'Len',0), (N'Cotton',0), (N'Nhung',0);

INSERT INTO SanPhamChiTiet (MaSanPham, TenSanPhamChiTiet, HinhAnh, MaSize, MaMau, MaChatLieu, TrangThai, SoLuongTon, NgayNhapKho, Deleted) 
VALUES (1, N'Polo Nam Tay Dài Xanh Navy', '"C:\\Users\\X1\OneDrive\\Documents\\NetBeansProjects\\POLYPOLO\\src\\Images\\M12_157_V2_BASELINE_FLATSQUARE.jpg"', 3, 1, 1, N'Còn hàng', 100, '2024-01-01', 0),
       (2, N'Polo Nữ Slim Fit Đen', NULL, 3, 4, 1, N'Còn hàng', 50, '2024-01-01', 0),
       (3, N'Polo Nữ Đỏ Đô (Special Tết 2024)', NULL, 1, 2, 1, N'Còn hàng', 7, '2024-01-01', 0),
       (4, N'Polo Trẻ Em Essential Trắng', NULL, 2, 3, 1, N'Còn hàng', 27, '2024-01-01', 0),
       (5, N'Áo Polo Nam Đen S', NULL, 1, 1, 1, N'Còn hàng', 100, '2024-01-01', 0),
       (6, N'Áo Polo Nữ Trắng M', NULL, 2, 2, 1, N'Còn hàng', 100, '2024-01-01', 0),
       (7, N'Áo Polo Nữ Trắng XS', NULL, 2, 2, 1, N'Còn hàng', 100, '2024-01-01', 0),
       (8, N'Polo Nam Tay Ngắn Trắng', NULL, 2, 2, 1, N'Còn hàng', 100, '2024-01-01', 0),
       (9, N'Áo Polo Thư Giãn', NULL, 1, 3, 1, N'Còn hàng', 160, '2024-01-01', 0),
       (10, N'Áo Polo Khám Phá', NULL, 2, 3, 1, N'Còn hàng', 170, '2024-01-01', 0),
       (11, N'Áo Polo Hải Đăng', NULL, 1, 4, 1, N'Còn hàng', 180, '2024-01-01', 0),
       (12, N'Áo Polo Đường Chân Trời', NULL, 3, 1, 1, N'Còn hàng', 190, '2024-01-01', 0),
       (13, N'Áo Polo Cầu Vồng', NULL, 2, 2, 1, N'Còn hàng', 200, '2024-01-01', 0),
       (14, N'Áo Polo Bình Minh', NULL, 3, 3, 1, N'Còn hàng', 210, '2024-01-01', 0),
       (15, N'Áo Polo Hoàng Hôn', NULL, 1, 4, 1, N'Còn hàng', 220, '2024-01-01', 0),
       (16, N'Áo Polo Bầu Trời Đêm', NULL, 2, 1, 1, N'Còn hàng', 230, '2024-01-01', 0),
       (17, N'Áo Polo Mặt Trăng', NULL, 3, 2, 1, N'Còn hàng', 240, '2024-01-01', 0),
       (18, N'Áo Polo Sao Băng', NULL, 1, 3, 1, N'Còn hàng', 250, '2024-01-01', 0),
       (19, N'Áo Polo Hành Tinh', NULL, 2, 4, 1, N'Còn hàng', 260, '2024-01-01', 0),
       (20, N'Áo Polo Ngân Hà', NULL, 3, 1, 1, N'Còn hàng', 270, '2024-01-01', 0),
	   (21, N'Áo Polo Huyền Bí', NULL, 1, 4, 2, N'Còn hàng', 75, '2024-01-02', 0),
       (22, N'Áo Polo Thần Thoại', NULL, 2, 1, 2, N'Còn hàng', 85, '2024-01-03', 0),
       (23, N'Áo Polo Cổ Điển', NULL, 3, 2, 2, N'Còn hàng', 95, '2024-01-04', 0),
       (24, N'Áo Polo Hiện Đại', NULL, 1, 3, 2, N'Còn hàng', 105, '2024-01-05', 0),
       (25, N'Áo Polo Tương Lai', NULL, 2, 4, 2, N'Còn hàng', 115, '2024-01-06', 0),
       (26, N'Áo Polo Khám Phá Mới', NULL, 3, 1, 2, N'Còn hàng', 125, '2024-01-07', 0),
       (27, N'Áo Polo Phiêu Lưu', NULL, 1, 2, 2, N'Còn hàng', 135, '2024-01-08', 0),
       (28, N'Áo Polo Mạo Hiểm', NULL, 2, 3, 2, N'Còn hàng', 145, '2024-01-09', 0),
       (29, N'Áo Polo Kỳ Thú', NULL, 3, 4, 2, N'Còn hàng', 155, '2024-01-10', 0),
       (30, N'Áo Polo Huyền Ảo', NULL, 1, 1, 2, N'Còn hàng', 165, '2024-01-11', 0);
----------------------------------
INSERT INTO HoaDon (MaNhanVien, TenKhachHang, TenNhanVien, TongTien, NgayLap, TrangThai, PhuongThucThanhToan, LoaiKhachHang, Deleted)
				VALUES (1, N'Lê Vũ', N'Loi Choi', 1200000, '2024-01-15', N'Đã thanh toán', N'Chuyển khoản', N'Thành Viên', 0)
					 , (1, N'Đào Phạm', N'Loi Choi', 780000, '2024-01-04', N'Đã thanh toán', N'Tiền mặt', N'Thành Viên', 0)
					 , (2, N'Mi Nguyễn', N'Ly Lê', 1680000, '2024-01-17', N'Đã thanh toán', N'Tiền mặt',N'Thành Viên', 0)
					 , (2, N'Ly Dương', N'Ly Lê', 480000, '2024-01-22', N'Đã thanh toán', N'Chuyển khoản',N'Khách Lẻ', 0)
					 , (1, N'Phan Văn I', N'Loi Choi', 3000000, '2024-02-05', N'Đã thanh toán', N'Tiền mặt',N'Khách Lẻ', 0)
					 , (2, N'Vũ Văn K', N'Ly Lê', 3500000, '2024-02-06', N'Đã thanh toán', N'Chuyển khoản',N'Khách Lẻ', 0)
					 , (1, N'Đặng Thị M', N'Loi Choi', 4000000, '2024-02-07', N'Đã thanh toán', N'Tiền mặt',N'Khách Lẻ', 0)
					 , (2, N'Bùi Văn O', N'Ly Lê', 4500000, '2024-02-08',N'Đã thanh toán', N'Chuyển khoản',N'Khách Lẻ', 0)
					 , (3, N'Nguyễn Văn A', N'Hòa Đinh', 2000000, '2024-02-15', N'Đã thanh toán', N'Tiền mặt',N'Khách Lẻ', 0)
				   	 , (3, N'Trần Thị B', N'Hòa Đinh', 3000000, '2024-02-20', N'Đã thanh toán', N'Chuyển khoản',N'Khách Lẻ', 0)
					 , (4, N'Lê Văn C', N'Linh Hoàng', 2500000, '2024-02-25', N'Đã thanh toán', N'Tiền mặt',N'Khách Lẻ', 0)
					 , (4, N'Phạm Thị D', N'Linh Hoàng', 1200000, '2024-03-01', N'Đã thanh toán', N'Chuyển khoản',N'Khách Lẻ', 0)
					 , (5, N'Hoàng Văn E', N'Hương Đào', 1800000, '2024-03-05', N'Đã thanh toán', N'Tiền mặt',N'Khách Lẻ', 0)
					 , (5, N'Nguyễn Thị F', N'Hương Đào', 2200000, '2024-03-10', N'Đã thanh toán', N'Chuyển khoản',N'Khách Lẻ', 0)
					 , (3, N'Vũ Văn G', N'Hòa Đinh', 1950000, '2024-03-15', N'Đã thanh toán', N'Tiền mặt',N'Khách Lẻ', 0),
					 (1, N'Nguyễn Văn A', N'Phạm Thị B', 100000, '2024-03-20',N'Đã thanh toán', N'Tiền mặt',N'Khách Lẻ', 0),
					(2, N'Lê Thị C', N'Tran Van D', 150000, '2024-03-21', N'Đã thanh toán', N'Chuyển khoản',N'Khách Lẻ', 0),
					(1, N'Phan Văn E', N'Nguyễn Thị F', 200000, '2024-03-22', N'Đã thanh toán', N'Chuyển khoản',N'Khách Lẻ', 0),
					(3, N'Tran Hồng G', N'Phạm Văn H', 250000, '2020-03-23', N'Đã thanh toán', N'Tiền mặt',N'Khách Lẻ', 0),
					(2, N'Nguyễn Minh I', N'Lê Thị J', 300000, '2021-03-24', N'Đã thanh toán', N'Chuyển khoản',N'Khách Lẻ', 0),
					(1, N'Phạm Khôi K', N'Tran Văn L', 350000, '2022-03-25', N'Đã thanh toán', N'Chuyển khoản',N'Khách Lẻ', 0),
					(3, N'Nguyễn Văn M', N'Phạm Thị N', 400000, '2020-03-26', N'Đã thanh toán', N'Tiền mặt',N'Khách Lẻ', 0),
					(2, N'Lê Thị O', N'Tran Van P', 450000, '2023-03-27', N'Đã thanh toán', N'Chuyển khoản',N'Khách Lẻ', 0),
					(1, N'Phan Văn Q', N'Nguyễn Thị R', 500000, '2021-03-28', N'Đã thanh toán', N'Tiền mặt',N'Khách Lẻ', 0),
					(3, N'Tran Hồng S', N'Phạm Văn T', 550000, '2020-03-29', N'Đã thanh toán', N'Tiền mặt',N'Khách Lẻ', 0),
					(2, N'Nguyễn Minh U', N'Lê Thị V', 600000, '2021-03-30',N'Đã thanh toán', N'Chuyển khoản',N'Khách Lẻ', 0),
					(1, N'Phạm Khôi W', N'Tran Văn X', 650000, '2020-04-01', N'Đã thanh toán', N'Tiền mặt',N'Khách Lẻ', 0),
					(3, N'Nguyễn Văn Y', N'Phạm Thị Z', 700000, '2022-04-02',N'Đã thanh toán', N'Tiền mặt',N'Khách Lẻ', 0),
					(2, N'Lê Thị AA', N'Tran Van BB', 750000, '2022-04-03',N'Đã thanh toán', N'Chuyển khoản',N'Khách Lẻ', 0),
					(1, N'Phan Văn CC', N'Nguyễn Thị DD', 800000, '2022-04-04', N'Đã thanh toán', N'Chuyển khoản',N'Khách Lẻ', 0);
---------------------
INSERT INTO HoaDonChiTiet (MaHoaDon, MaSanPhamChiTiet, SoLuong, DonGia, Deleted) 
															VALUES 
																(1, 1, 2, 600000, 0),
																(2, 2, 2, 390000, 0),
																(3, 3, 4, 420000, 0),
																(4, 4, 2, 240000, 0),
																(5, 5, 10, 300000, 0),
																(6, 6, 10, 350000, 0),
																(7, 7, 10, 400000, 0),
																(8, 8, 10, 450000, 0),
																(9, 9, 4, 500000, 0),
																(10, 10, 6, 500000, 0),
																(11, 11, 5, 500000, 0),
																(12, 12, 2, 600000, 0),
																(13, 13, 3, 600000, 0),
																(14, 14, 4, 550000, 0),
																(15, 15, 3, 650000, 0),
																(16, 16, 1, 100000, 0),
																(17, 17, 1, 150000, 0),
																(18, 18, 1, 200000, 0),
																(19, 19, 1, 250000, 0),
																(20, 20, 1, 300000, 0),
																(21, 21, 1, 350000, 0),
																(22, 22, 1, 400000, 0),
																(23, 23, 1, 450000, 0),
																(24, 24, 1, 500000, 0),
																(25, 25, 1, 550000, 0),
																(26, 26, 1, 600000, 0),
																(27, 27, 1, 650000, 0),
																(28, 28, 1, 700000, 0),
																(29, 29, 1, 750000, 0),
																(30, 30, 1, 800000, 0);
---------------------
INSERT INTO KhachHang (MaHoaDon, TenKhachHang, GioiTinh, SoDienThoai, DiaChi, LoaiKhachHang, Deleted)
				VALUES (1, N'Lê Vũ', 'Nam', '0912345688', N'Hà Nội',N'Thành Viên', 0)
					, (2, N'Đào Phạm', N'Nữ', '0955120820', N'Bến Tre',N'Thành Viên', 0)
					, (3, N'Lưu Quỳnh Phương', N'Nữ', '0912345689', 'TP.HCM',N'Thành Viên', 0)
					, (4, N'My Thị Hoàng Dương', N'Nữ', '0912345777', N'Hải Phòng',N'Thành Viên', 0)
					,(5, N'Sỹ Hiếu', 'Nữ', '098765400', N'Hà Nội',N'Thành Viên', 0)
					,(6, N'Phạm Đức Long', 'Nam', '0912345682', N'Hải Phòng',N'Thành Viên', 0)
					, (7, N'Vũ Văn Ki', 'Nam', '0912345683', N'Quảng Ninh',N'Thành Viên', 0)
					, (8, N'Đặng Thị Moe', 'Nữ', '0912345684', N'Bình Dương',N'Thành Viên', 0)
					;
-----------------------
INSERT INTO NhanHang (TenNhanHang, Deleted) VALUES (N'Ralph Laurent', 0)
													, (N'Fred Perry', 0)
													, (N'Gucci', 0)
													, (N'Alber & Crombie', 0)
													, (N'Canifa', 0)
													, (N'Uniqlo', 0)
													, (N'Rupaul Drag', 0)
													, (N'RKive Loves', 0);
-- NhaCungCap
INSERT INTO NhaCungCap (TenNhaCungCap, Deleted) VALUES (N'Công ty TNHH Xuất Nhập Khẩu Kafergh', 0)
													, (N'Công ty CPTN Vĩnh Sơn 5', 0)
													, (N'Hệ Thống Phân Phối Chính Hãng Malef', 0)
													, (N'Công ty CPTNHH Xuất Nhập Khẩu Moms Faves', 0)
													, (N'Công ty TNHH Hybe World', 0)
													, (N'Công ty CP Malibu', 0)
													, (N'Hệ Thống Phân Phối Chính Hãng Malef', 0)
													, (N'Công ty TNHH Xuất Nhập HETCUU', 0)
													;
-- PhieuNhapKho
INSERT INTO PhieuNhapKho (NhaCungCapID, NhanVienID, ThoiGianNhap, TongDon, Deleted) 
											VALUES (1, 1, '2023-01-01', 100000, 0)
												, (2, 2, '2023-02-01', 200000, 0)
												, (3, 3, '2023-02-01', 245000, 0)
												, (4, 4, '2023-02-01', 6780000, 0)
												, (4, 4, '2023-02-01', 900000, 0)
												, (2, 2, '2023-02-01', 132000000, 0)
												, (3, 3, '2023-02-01', 326000000, 0)
												, (4, 4, '2023-02-01', 452000, 0)
												;
-- PhieuXuatKho
INSERT INTO PhieuXuatKho (TenKhachHang, NhanVienID, ThoiGianXuat, TongDon, Deleted) 
											VALUES (N'Khách Hàng A', 1, '2023-03-01', 150000, 0)
													, (N'Khách Hàng B', 2, '2023-04-01', 250000, 0)
													, (N'Khách Hàng C', 3, '2023-04-01', 4500000, 0)
													, (N'Khách Hàng D', 4, '2023-04-01', 12000000, 0)
													, (N'Khách Hàng E', 4, '2023-04-01', 5603000, 0)
													, (N'Khách Hàng G', 2, '2023-04-01', 2570000, 0)
													, (N'Khách Hàng H', 3, '2023-04-01', 560000, 0)
													, (N'Khách Hàng I', 4, '2023-04-01', 780000, 0);

--BỔ SUNG BẢNG KM
CREATE TABLE KhuyenMai (
    MaKhuyenMai INT PRIMARY KEY IDENTITY(1,1),
    TenKhuyenMai NVARCHAR(255) NOT NULL,
    LoaiKhuyenMai NVARCHAR(50) NOT NULL, -- 'Voucher' hoặc 'DotGiamGia'
    MucGiamGia DECIMAL(13, 3), -- Có thể là số tiền giảm hoặc phần trăm giảm giá
    SoLuongVoucher INT, -- Sử dụng cho loại 'Voucher', NULL cho 'DotGiamGia'
    NgayBatDau DATE, -- NULL cho 'Voucher'
    NgayKetThuc DATE, -- NULL cho 'Voucher'
    MoTa NVARCHAR(250),
    TrangThai NVARCHAR(50) NOT NULL,
	Deleted INT -- 'DangDienRa', 'DaKetThuc', 'ChuaBatDau'
);

CREATE TABLE ApDungKhuyenMai (
    MaApDung INT PRIMARY KEY IDENTITY(1,1),
    MaKhuyenMai INT,
    MaHoaDon INT,
    GiaTriGiam INT,
	Deleted INT,
    FOREIGN KEY (MaKhuyenMai) REFERENCES KhuyenMai(MaKhuyenMai),
    FOREIGN KEY (MaHoaDon) REFERENCES HoaDon(MaHoaDon)
);

CREATE TABLE ChiTietKhuyenMai (
    MaChiTiet INT PRIMARY KEY IDENTITY(1,1),
    MaKhuyenMai INT,
    MaSanPham INT, -- NULL nếu áp dụng cho danh mục hoặc tất cả sản phẩm
    MaDanhMuc INT, -- NULL nếu áp dụng cho sản phẩm cụ thể
	Deleted INT,
    FOREIGN KEY (MaKhuyenMai) REFERENCES KhuyenMai(MaKhuyenMai),
    FOREIGN KEY (MaSanPham) REFERENCES SanPham(MaSanPham),
    FOREIGN KEY (MaDanhMuc) REFERENCES DanhMuc(MaDanhMuc)
);
