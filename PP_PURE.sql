CREATE DATABASE POLYPOLO_PURE;
DROP DATABASE POLYPOLO_PURE;

USE  POLYPOLO_PURE;

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
    GiaNhap FLOAT NOT NULL,
    GiaBan FLOAT NOT NULL,
	Deleted INT
    FOREIGN KEY (MaDanhMuc) REFERENCES DanhMuc(MaDanhMuc)
);

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

CREATE TABLE SanPhamChiTiet (
    MaSanPhamChiTiet INT PRIMARY KEY IDENTITY(1,1),
    MaSanPham INT,
    TenSanPhamChiTiet NVARCHAR(250) NOT NULL,
    MaSize INT,
    MaMau INT,
    TrangThai NVARCHAR(30) NOT NULL,
    SoLuongTon INT NOT NULL,
	Deleted INT,
    FOREIGN KEY (MaSanPham) REFERENCES SanPham(MaSanPham),
    FOREIGN KEY (MaSize) REFERENCES Size(MaSize),
    FOREIGN KEY (MaMau) REFERENCES MauSac(MaMau)
);

CREATE TABLE NguoiDung(
    MaNguoiDung INT PRIMARY KEY IDENTITY(1,1),
    TenDangNhap VARCHAR(50) NOT NULL,
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
    TongTien FLOAT NOT NULL,
    NgayLap DATE,
    TrangThai NVARCHAR(25) NOT NULL,
	PhuongThucThanhToan NVARCHAR(50) NOT NULL,
	Deleted INT,
    FOREIGN KEY (MaNhanVien) REFERENCES NhanVien(MaNhanVien)
);

CREATE TABLE HoaDonChiTiet (
	MaHoaDonChiTiet INT PRIMARY KEY IDENTITY(1,1),
    MaHoaDon INT,
    MaSanPhamChiTiet INT,
    SoLuong INT NOT NULL,
    DonGia FLOAT NOT NULL,
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
	Deleted INT
	FOREIGN KEY (MaHoaDon) REFERENCES HoaDon(MaHoaDon),
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
												  (N'Polo Unisex', N'Còn hàng',0)
												, (N'Polo Đặc Biệt', N'Còn hàng',0)
												, (N'Polo Gen-Z', N'Còn hàng',0)
												, (N'Polo Limited-Edition', N'Còn hàng',0)
												, (N'Polo Special Tết 2024', N'Còn hàng',0)
												, (N'Polo Nam', N'Còn hàng',0)
												, (N'Polo Nữ', N'Còn hàng',0)
												, (N'Polo Trẻ Em', N'Còn hàng',0)
												;
------------------
INSERT INTO SanPham(MaDanhMuc, TrangThai, GiaNhap, GiaBan, Deleted) 									
							 VALUES	(1, N'Còn hàng', 450000, 550000,0)								 
									 , (2, N'Còn hàng', 620000, 780000,0)
									 , (2, N'Còn hàng', 320000, 560000,0)
									 , (3, N'Còn hàng', 120000, 240000,0)
									 , (4, N'Còn hàng', 230000, 780000,0)
									 , (5, N'Còn hàng', 120000, 580000,0)
									 , (6, N'Còn hàng', 350000, 760000,0)
									 , (7, N'Còn hàng', 1350000, 2560000,0)
									 , (8, N'Còn hàng', 201200, 760000,0)
									 , (1, N'Còn hàng', 550000, 600000,0)
									 , (1, N'Còn hàng', 500000, 650000, 0)
									 , (2, N'Còn hàng', 700000, 900000, 0)
									 , (3, N'Còn hàng', 200000, 400000, 0)
									 , (4, N'Còn hàng', 300000, 850000, 0)
								     , (5, N'Còn hàng', 150000, 650000, 0)
									 , (6, N'Còn hàng', 450000, 850000, 0)
									 , (7, N'Còn hàng', 1600000, 3000000, 0)
									 , (8, N'Còn hàng', 250000, 800000, 0)
									 , (1, N'Còn hàng', 600000, 700000, 0)
									 , (2, N'Còn hàng', 800000, 1000000, 0)
									 , (3, N'Còn hàng', 220000, 420000, 0)
									 , (4, N'Còn hàng', 310000, 860000, 0)
									 , (5, N'Còn hàng', 160000, 660000, 0)
									 , (6, N'Còn hàng', 460000, 860000, 0)
									 , (7, N'Còn hàng', 1650000, 3050000, 0)
									 , (8, N'Còn hàng', 255000, 810000, 0)
									 , (1, N'Còn hàng', 610000, 710000, 0)
									 , (2, N'Còn hàng', 810000, 1010000, 0)
									 , (3, N'Còn hàng', 225000, 425000, 0)
									 , (4, N'Còn hàng', 315000, 865000, 0)
									;
--------------------
INSERT INTO Size (TenSize,Deleted) VALUES ('XS',0), ('S',0), ('M',0), ('L',0);
INSERT INTO MauSac (TenMau,Deleted) VALUES (N'Xanh',0), (N'Đỏ',0), (N'Trắng',0), (N'Đen',0);
--------------------
INSERT INTO SanPhamChiTiet (MaSanPham, TenSanPhamChiTiet, MaSize, MaMau, TrangThai, SoLuongTon, Deleted) 
					  VALUES  (1, N'Polo Nam Tay Dài Xanh Navy', 3, 1, N'Còn hàng', 100, 0)
							, (2, N'Polo Nữ Slim Fit Đen', 3, 4, N'Còn hàng', 50, 0)
							, (3, N'Polo Nữ Đỏ Đô (Special Tết 2024)', 1, 2, N'Còn hàng', 7, 0)
							, (4, N'Polo Trẻ Em Essential Trắng', 2, 3, N'Còn hàng', 27, 0)
							, (5, N'Áo Polo Nam Đen S', 1, 1, N'Còn hàng', 100, 0)
							, (6, N'Áo Polo Nữ Trắng M', 2, 2, N'Còn hàng', 100, 0)
							, (7, N'Áo Polo Nữ Trắng XS', 2, 2, N'Còn hàng', 100, 0)
							, (8, N'Polo Nam Tay Ngắn Trắng', 2, 2, N'Còn hàng', 100, 0)
							;
----------------------
INSERT INTO HoaDon (MaNhanVien, TenKhachHang, TenNhanVien, TongTien, NgayLap, TrangThai, PhuongThucThanhToan, Deleted)
				VALUES (1, N'Lê Vũ', N'Loi Choi', 1200000, '2024-01-15', N'Đã thanh toán', N'Chuyển khoản', 0)
					 , (1, N'Đào Phạm', N'Loi Choi', 780000, '2024-01-04', N'Đã thanh toán', N'Tiền mặt', 0)
					 , (2, N'Mi Nguyễn', N'Ly Lê', 1680000, '2024-01-17', N'Chưa thanh toán', N'Tiền mặt', 0)
					 , (2, N'Ly Dương', N'Ly Lê', 480000, '2024-01-22', N'Đã thanh toán', N'Chuyển khoản', 0)
					 , (1, N'Phan Văn I', N'Loi Choi', 3000000, '2024-02-05', N'Đã thanh toán', N'Tiền mặt', 0)
					 , (2, N'Vũ Văn K', N'Ly Lê', 3500000, '2024-02-06', N'Chưa thanh toán', N'Chuyển khoản', 0)
					 , (1, N'Đặng Thị M', N'Loi Choi', 4000000, '2024-02-07', N'Đã thanh toán', N'Tiền mặt', 0)
					 , (2, N'Bùi Văn O', N'Ly Lê', 4500000, '2024-02-08', N'Chưa thanh toán', N'Chuyển khoản', 0)
					 , (3, N'Nguyễn Văn A', N'Hòa Đinh', 2000000, '2024-02-15', N'Đã thanh toán', N'Tiền mặt', 0)
				   	 , (3, N'Trần Thị B', N'Hòa Đinh', 3000000, '2024-02-20', N'Đã thanh toán', N'Chuyển khoản', 0)
					 , (4, N'Lê Văn C', N'Linh Hoàng', 2500000, '2024-02-25', N'Chưa thanh toán', N'Tiền mặt', 0)
					 , (4, N'Phạm Thị D', N'Linh Hoàng', 1200000, '2024-03-01', N'Đã thanh toán', N'Chuyển khoản', 0)
					 , (5, N'Hoàng Văn E', N'Hương Đào', 1800000, '2024-03-05', N'Đã thanh toán', N'Tiền mặt', 0)
					 , (5, N'Nguyễn Thị F', N'Hương Đào', 2200000, '2024-03-10', N'Chưa thanh toán', N'Chuyển khoản', 0)
					 , (3, N'Vũ Văn G', N'Hòa Đinh', 1950000, '2024-03-15', N'Đã thanh toán', N'Tiền mặt', 0)
					 ;
---------------------
INSERT INTO HoaDonChiTiet (MaHoaDon, MaSanPhamChiTiet, SoLuong, DonGia, Deleted) VALUES (1, 1, 2, 600000, 0), 
																						(2, 2, 1, 780000, 0), 
																						(3, 3, 3, 560000, 0),
																						(4, 4, 2, 240000, 0),
																						(3, 1, 4, 250000, 0),
																						(3, 2, 1, 1000000, 0),
																						(4, 1, 5, 200000, 0),
																						(4, 2, 2, 1250000, 0),
																						(5,1,2, 600000, 0),
																						(9, 9, 3, 650000, 0),
																						(9, 10, 2, 900000, 0),
																						(10, 11, 1, 400000, 0),
																						(10, 12, 1, 850000, 0),
																						(11, 13, 2, 650000, 0),
																						(11, 14, 2, 850000, 0),
																						(12, 15, 1, 3000000, 0),
																						(12, 16, 1, 800000, 0),
																						(13, 17, 3, 700000, 0),
																						(13, 18, 2, 1000000, 0),
																						(14, 19, 1, 420000, 0),
																						(14, 20, 1, 860000, 0),
																						(15, 21, 2, 660000, 0),
																						(15, 22, 2, 860000, 0);
---------------------
INSERT INTO KhachHang (MaHoaDon, TenKhachHang, GioiTinh, SoDienThoai, DiaChi, Deleted)
				VALUES (1, N'Lê Vũ', 'Nam', '0912345688', N'Hà Nội', 0)
					, (2, N'Đào Phạm', N'Nữ', '0955120820', N'Bến Tre', 0)
					, (3, N'Lưu Quỳnh Phương', N'Nữ', '0912345689', 'TP.HCM', 0)
					, (4, N'My Thị Hoàng Dương', N'Nữ', '0912345777', N'Hải Phòng', 0)
					,(5, N'Sỹ Hiếu', 'Nữ', '098765400', N'Hà Nội', 0)
					,(6, N'Phạm Đức Long', 'Nam', '0912345682', N'Hải Phòng', 0)
					, (7, N'Vũ Văn Ki', 'Nam', '0912345683', N'Quảng Ninh', 0)
					, (8, N'Đặng Thị Moe', 'Nữ', '0912345684', N'Bình Dương', 0)
					;