package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import service.LoaiNhanVienService;
import service.LoaiTaiKhoanService;
import service.NhanVienService;
import service.SanPhamService;
import service.TaiKhoanService;
import service.ThongKeDoanhThuTheoNgayService;
import service.ThongKeDoanhThuTheoThangService;
import model.HienThiToanBoNhanVienModel;
import model.LoaiNhanVienModel;
import model.LoaiSanPhamModel;
import model.LoaiTaiKhoanModel;
import model.NhanVienModel;
import model.SanPhamModel;
import model.TaiKhoanModel;
import model.ThongKeDoanhThuTheoNgayModel;
import model.ThongKeDoanhThuTheoThangModel;

public class BanQuanLyUI extends JFrame{
	JMenuBar mnuBar;//nơi để đựng (chứa) các JMenu
	JMenu mnuHeThong;
	JMenuItem mnuThongTin;
	JMenuItem mnuDangXuat;
	JMenuItem mnuThoat;
	JToolBar tooBar;
	JButton btnThongTin,btnDangXuat, btnThoat, btnTroVe;
	JPanel pnTabNV, pnTabThongKe;
	JTabbedPane tab;

	//controls TabNV
	JTextField txtTimKiemNhanVien, txtTenNV, txtNamSinh, txtDiaChi, txtSDT, txtEmail, txtNgayVaoLamViec;
	JButton btnTimKiemNhanVien, btnThemTaiKhoan, btnThemNV, btnSuaNV, btnXoaNV, btnReload;
	JComboBox<LoaiNhanVienModel>cboLoaiNhanVien;
	JComboBox<TaiKhoanModel>cboTenTaiKhoan;
	DefaultTableModel dtmNhanVien;
	Vector<HienThiToanBoNhanVienModel>dsNhanVien;
	JTable tblNhanVien;
	JCheckBox chkHienThiToanBoNhanVien;

	//controls Tab Thống kê
	JCheckBox chkThongKeTheoNgay, chkThongKeTheoThang;
	DefaultTableModel dtmThongKeTheoNgay, dtmThongKeTheoThang;
	JTable tblThongKeTheoNgay, tblThongKeTheoThang;
	JComboBox<Integer> cboNam;
	Vector<ThongKeDoanhThuTheoNgayModel> dsThongKeTheoNgay;
	Vector<ThongKeDoanhThuTheoThangModel> dsThongKeTheoThang;
	public BanQuanLyUI(String title){
		super(title);
		addControls();
		addEvents();
		hienThiToanBoTaiKhoanTenCombobox();
		hienThiToanBoLoaiNhanVienTrenCombobox();
		hienThiToanBoNhanVien();
	}
	public int layMaNhanVienTheoTen(int index){
		return Integer.parseInt(tblNhanVien.getValueAt(index, 0)+"");
	}
	private void hienThiToanBoNhanVien() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		NhanVienService nvService = new NhanVienService();
		dsNhanVien = nvService.docToanBoNhanVien();
		dtmNhanVien.setRowCount(0);
		for(HienThiToanBoNhanVienModel nv : dsNhanVien)
		{
			Vector<Object>vec = new Vector<Object>();
			vec.add(nv.getMaNV());
			vec.add(nv.getTenNV());
			vec.add(nv.getNamSinh());
			vec.add(nv.getDiaChi());
			vec.add(nv.getSDT());
			vec.add(nv.getEmail());
			vec.add(nv.getNgayVaoLamViec());
			vec.add(nv.getTenBoPhan());
			vec.add(nv.getTaiKhoan());
			dtmNhanVien.addRow(vec);
		}
	}
	private void hienThiToanBoLoaiNhanVienTrenCombobox() {
		LoaiNhanVienService lnvService = new LoaiNhanVienService();
		Vector<LoaiNhanVienModel>vec = lnvService.docToanBoLoaiNhanVien();
		cboLoaiNhanVien.removeAllItems();
		for(LoaiNhanVienModel lnv : vec)
		{
			cboLoaiNhanVien.addItem(lnv);
		}

	}
	private void hienThiToanBoTaiKhoanTenCombobox() {
		TaiKhoanService tkService = new TaiKhoanService();
		Vector<TaiKhoanModel>vec = tkService.docToanBoTaiKhoan();
		cboTenTaiKhoan.removeAllItems();
		for(TaiKhoanModel tk : vec)
		{
			cboTenTaiKhoan.addItem(tk);
		}
	}
	public void addControls(){
		mnuBar = new JMenuBar();
		setJMenuBar(mnuBar);
		mnuHeThong = new JMenu("Hệ Thống");
		mnuBar.add(mnuHeThong);
		mnuThongTin = new JMenuItem("Thông Tin");
		mnuThongTin.setIcon(new ImageIcon("images/info3.png"));
		mnuHeThong.add(mnuThongTin);
		mnuHeThong.addSeparator();
		mnuDangXuat = new JMenuItem("Đăng Xuất");
		mnuDangXuat.setIcon(new ImageIcon("images/logout4.png"));
		mnuHeThong.add(mnuDangXuat);
		mnuHeThong.addSeparator();
		mnuThoat = new JMenuItem("Thoát");
		mnuThoat.setIcon(new ImageIcon("images/exit6.png"));
		mnuHeThong.add(mnuThoat);
		Container con = getContentPane();
		con.setLayout(new BorderLayout());
		tooBar = new JToolBar();
		tooBar.setBackground(Color.LIGHT_GRAY);
		btnThongTin = new JButton("Thông Tin");
		btnThongTin.setBackground(Color.WHITE);
		btnThongTin.setIcon(new ImageIcon("images/info4.png"));
		btnThongTin.setFont(new Font("", Font.BOLD, 12));
		btnDangXuat = new JButton("Đăng Xuất");
		btnDangXuat.setBackground(Color.WHITE);
		btnDangXuat.setIcon(new ImageIcon("images/logout5.png"));
		btnDangXuat.setFont(new Font("", Font.BOLD, 12));
		btnThoat = new JButton("Thoát");
		btnThoat.setBackground(Color.WHITE);
		btnThoat.setIcon(new ImageIcon("images/exit7.png"));
		btnThoat.setFont(new Font("", Font.BOLD, 12));
		btnTroVe = new JButton("Trở Về");
		btnTroVe.setFont(new Font("", Font.BOLD, 12));
		btnTroVe.setBackground(Color.WHITE);
		btnTroVe.setIcon(new ImageIcon("images/return2.png"));
		tooBar.add(btnTroVe);
		tooBar.add(btnThongTin);
		tooBar.add(btnDangXuat);
		tooBar.add(btnThoat);
		con.add(tooBar,BorderLayout.NORTH);
		tab = new JTabbedPane();
		con.add(tab);
		pnTabNV = new JPanel();
		tab.add(pnTabNV,"Quản lý nhân viên");
		pnTabThongKe = new JPanel();
		tab.add(pnTabThongKe,"Thống kê doanh thu");

		//giao diện TabNV
		pnTabNV.setLayout(new BorderLayout());
		JPanel pnTop = new JPanel();
		pnTop.setLayout(new BoxLayout(pnTop, BoxLayout.Y_AXIS));
		pnTabNV.add(pnTop, BorderLayout.NORTH);

		JPanel pnTimKiemNhanVien = new JPanel();
		pnTimKiemNhanVien.setLayout(new FlowLayout(FlowLayout.CENTER));
		JLabel lblTimKiemNhanVien = new JLabel("Nhập dữ liệu tìm kiếm: ");
		txtTimKiemNhanVien = new JTextField(30);
		btnTimKiemNhanVien = new JButton("Tìm kiếm");
		btnTimKiemNhanVien.setIcon(new ImageIcon("images/search9.png"));
		pnTimKiemNhanVien.add(lblTimKiemNhanVien);
		pnTimKiemNhanVien.add(txtTimKiemNhanVien);
		pnTimKiemNhanVien.add(btnTimKiemNhanVien);
		pnTop.add(pnTimKiemNhanVien);

		JPanel pnComboLoaiNV = new JPanel();
		pnComboLoaiNV.setLayout(new FlowLayout(FlowLayout.CENTER));
		JLabel lblComboLoaiNV = new JLabel("Bộ phận: ");
		cboLoaiNhanVien = new JComboBox<LoaiNhanVienModel>();
		cboLoaiNhanVien.setPreferredSize(new Dimension(445, 20));
		pnComboLoaiNV.add(lblComboLoaiNV);
		pnComboLoaiNV.add(cboLoaiNhanVien);
		pnTop.add(pnComboLoaiNV);

		JPanel pnTenNV = new JPanel();
		pnTenNV.setLayout(new FlowLayout(FlowLayout.CENTER));
		JLabel lblTenNV = new JLabel("Tên nhân viên: ");
		txtTenNV = new JTextField(40);
		pnTenNV.add(lblTenNV);
		pnTenNV.add(txtTenNV);
		pnTop.add(pnTenNV);

		JPanel pnNamSinh = new JPanel();
		pnNamSinh.setLayout(new FlowLayout(FlowLayout.CENTER));
		JLabel lblNamSinh = new JLabel("Năm sinh: ");
		txtNamSinh = new JTextField(40);
		pnNamSinh.add(lblNamSinh);
		pnNamSinh.add(txtNamSinh);
		pnTop.add(pnNamSinh);

		JPanel pnDiaChi = new JPanel();
		pnDiaChi.setLayout(new FlowLayout(FlowLayout.CENTER));
		JLabel lblDiaChi = new JLabel("Địa chỉ: ");
		txtDiaChi = new JTextField(40);
		pnDiaChi.add(lblDiaChi);
		pnDiaChi.add(txtDiaChi);
		pnTop.add(pnDiaChi);

		JPanel pnSDT = new JPanel();
		pnSDT.setLayout(new FlowLayout(FlowLayout.CENTER));
		JLabel lblSDT = new JLabel("Số điện thoại: ");
		txtSDT = new JTextField(40);
		pnSDT.add(lblSDT);
		pnSDT.add(txtSDT);
		pnTop.add(pnSDT);

		JPanel pnEmail = new JPanel();
		pnEmail.setLayout(new FlowLayout(FlowLayout.CENTER));
		JLabel lblEmail = new JLabel("Email: ");
		txtEmail = new JTextField(40);
		pnEmail.add(lblEmail);
		pnEmail.add(txtEmail);
		pnTop.add(pnEmail);

		JPanel pnNgayVaoLamViec = new JPanel();
		pnNgayVaoLamViec.setLayout(new FlowLayout(FlowLayout.CENTER));
		JLabel lblNgayVaoLamViec = new JLabel("Ngày vào làm việc: ");
		txtNgayVaoLamViec = new JTextField(40);
		pnNgayVaoLamViec.add(lblNgayVaoLamViec);
		pnNgayVaoLamViec.add(txtNgayVaoLamViec);
		pnTop.add(pnNgayVaoLamViec);

		JPanel pnComboTenTK = new JPanel();
		pnComboTenTK.setLayout(new FlowLayout(FlowLayout.CENTER));
		JLabel lblComboTenTK = new JLabel("Tên tài khoản: ");
		cboTenTaiKhoan = new JComboBox<TaiKhoanModel>();
		cboTenTaiKhoan.setPreferredSize(new Dimension(230, 20));
		btnThemTaiKhoan = new JButton("Thêm tài khoản");
		btnThemTaiKhoan.setIcon(new ImageIcon("images/them1.png"));
		btnReload = new JButton();
		btnReload.setIcon(new ImageIcon("images/reload2.png"));
		btnReload.setBackground(Color.WHITE);
		pnComboTenTK.add(lblComboTenTK);
		pnComboTenTK.add(cboTenTaiKhoan);
		pnComboTenTK.add(btnReload);
		pnComboTenTK.add(btnThemTaiKhoan);
		pnTop.add(pnComboTenTK);
		
		JPanel pnHienThiToanBoNhanVien = new JPanel();
		pnHienThiToanBoNhanVien.setLayout(new FlowLayout(FlowLayout.CENTER));
		chkHienThiToanBoNhanVien = new JCheckBox("Hiển thị toàn bộ nhân viên");
		chkHienThiToanBoNhanVien.setFont(new Font("", Font.ITALIC, 15));
		chkHienThiToanBoNhanVien.setForeground(Color.BLUE);
		pnHienThiToanBoNhanVien.add(chkHienThiToanBoNhanVien);
		pnTop.add(pnHienThiToanBoNhanVien);

		//canh chỉnh
		lblTenNV.setPreferredSize(lblTimKiemNhanVien.getPreferredSize());
		lblNamSinh.setPreferredSize(lblTimKiemNhanVien.getPreferredSize());
		lblDiaChi.setPreferredSize(lblTimKiemNhanVien.getPreferredSize());
		lblSDT.setPreferredSize(lblTimKiemNhanVien.getPreferredSize());
		lblEmail.setPreferredSize(lblTimKiemNhanVien.getPreferredSize());
		lblNgayVaoLamViec.setPreferredSize(lblTimKiemNhanVien.getPreferredSize());
		lblComboLoaiNV.setPreferredSize(lblTimKiemNhanVien.getPreferredSize());
		lblComboTenTK.setPreferredSize(lblTimKiemNhanVien.getPreferredSize());
		chkHienThiToanBoNhanVien.setPreferredSize(txtTimKiemNhanVien.getPreferredSize());

		JPanel pnControlsOfNV = new JPanel();
		pnControlsOfNV.setLayout(new FlowLayout(FlowLayout.CENTER));
		btnThemNV = new JButton("Thêm mới");
		btnThemNV.setIcon(new ImageIcon("images/new8.png"));
		btnSuaNV = new JButton("Sửa thông tin");
		btnSuaNV.setIcon(new ImageIcon("images/edit8.png"));
		btnXoaNV = new JButton("Xoá");
		btnXoaNV.setIcon(new ImageIcon("images/remove8.png"));
		pnControlsOfNV.add(btnThemNV);
		pnControlsOfNV.add(btnSuaNV);
		pnControlsOfNV.add(btnXoaNV);
		pnTop.add(pnControlsOfNV);


		JPanel pnBottom = new JPanel();
		pnBottom.setLayout(new BorderLayout());
		pnTabNV.add(pnBottom, BorderLayout.CENTER);

		TitledBorder borderNV = new TitledBorder(
				BorderFactory.createLineBorder(Color.RED),
				"Thông tin nhân viên");
		borderNV.setTitleColor(Color.BLUE);
		borderNV.setTitleFont(new Font("", Font.BOLD, 15));
		pnBottom.setBorder(borderNV);

		dtmNhanVien = new DefaultTableModel();
		dtmNhanVien.addColumn("Mã nhân viên");
		dtmNhanVien.addColumn("Tên nhân viên");
		dtmNhanVien.addColumn("Năm sinh");
		dtmNhanVien.addColumn("Địa chỉ");
		dtmNhanVien.addColumn("Số điện thoai");
		dtmNhanVien.addColumn("Email");
		dtmNhanVien.addColumn("Ngày vào làm việc");
		dtmNhanVien.addColumn("Bộ phân");
		dtmNhanVien.addColumn("Tài khoản");
		tblNhanVien = new JTable(dtmNhanVien);
		tblNhanVien.setFont(new Font("", Font.ITALIC, 15));
		JScrollPane scTableNV = new JScrollPane(tblNhanVien,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		pnBottom.add(scTableNV,BorderLayout.CENTER);


		//giao diện Tab thống kê
		//giao diện pnLeft
		pnTabThongKe.setLayout(new BorderLayout());
		JPanel pnLeft = new JPanel();
		pnLeft.setPreferredSize(new Dimension(700, 0));
		JPanel pnRight = new JPanel();
		JSplitPane sp = new JSplitPane(
				JSplitPane.HORIZONTAL_SPLIT, pnLeft,pnRight);
		sp.setOneTouchExpandable(true);
		pnTabThongKe.add(sp,BorderLayout.CENTER);

		pnLeft.setLayout(new BorderLayout());
		JPanel pnTopOfLeft = new JPanel();
		pnTopOfLeft.setLayout(new FlowLayout(FlowLayout.LEFT));
		chkThongKeTheoNgay = new JCheckBox("Thống kê doanh thu theo ngày");
		chkThongKeTheoNgay.setFont(new Font("", Font.ITALIC, 20));
		chkThongKeTheoNgay.setForeground(Color.BLUE);
		pnTopOfLeft.add(chkThongKeTheoNgay);
		pnLeft.add(pnTopOfLeft, BorderLayout.NORTH);

		JPanel pnBottomOfLeft = new JPanel();
		pnBottomOfLeft.setLayout(new BorderLayout());
		TitledBorder borderThongKeTheoNgay = new TitledBorder
				(BorderFactory.createLineBorder(Color.RED),"Thông tin chi tiết");
		borderThongKeTheoNgay.setTitleColor(Color.BLUE);
		borderThongKeTheoNgay.setTitleFont(new Font("", Font.BOLD, 15));
		pnBottomOfLeft.setBorder(borderThongKeTheoNgay);
		pnLeft.add(pnBottomOfLeft,BorderLayout.CENTER);
		//pnBottomOfLeft.setPreferredSize(new Dimension(0, 300));

		dtmThongKeTheoNgay = new DefaultTableModel();
		dtmThongKeTheoNgay.addColumn("Ngày");
		dtmThongKeTheoNgay.addColumn("Tổng số tiền thu được");
		tblThongKeTheoNgay = new JTable(dtmThongKeTheoNgay);
		tblThongKeTheoNgay.setFont(new Font("", Font.ITALIC, 15));
		JScrollPane scTableThongKeTheoNgay = new JScrollPane
				(tblThongKeTheoNgay,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		pnBottomOfLeft.add(scTableThongKeTheoNgay,BorderLayout.CENTER);

		//xử lý giao diện pnRight
		pnRight.setLayout(new BorderLayout());
		JPanel pnTopOfRight = new JPanel();
		pnTopOfRight.setLayout(new BoxLayout(pnTopOfRight, BoxLayout.Y_AXIS));
		pnRight.add(pnTopOfRight, BorderLayout.NORTH);

		JPanel pnNam = new JPanel();
		pnNam.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblNam = new JLabel("Chọn năm: ");
		lblNam.setForeground(Color.RED);
		lblNam.setFont(new Font("", Font.ITALIC, 15));
		cboNam = new JComboBox<Integer>();
		cboNam.setPreferredSize(new Dimension(200, 20));
		Vector<Integer>vec = new Vector<Integer>();
		cboNam.removeAllItems();
		for(int i=2022; i<2030; i++){
			vec.add(i);
		}
		for(Integer x : vec)
		{
			cboNam.addItem(x);
		}
		pnNam.add(lblNam);
		pnNam.add(cboNam);
		pnTopOfRight.add(pnNam);

		JPanel pnThongKeTheoThang = new JPanel();
		pnThongKeTheoThang.setLayout(new FlowLayout(FlowLayout.LEFT));
		chkThongKeTheoThang = new JCheckBox("Thống kê doanh thu theo tháng");
		chkThongKeTheoThang.setFont(new Font("", Font.ITALIC, 20));
		chkThongKeTheoThang.setForeground(Color.BLUE);
		pnThongKeTheoThang.add(chkThongKeTheoThang);
		pnTopOfRight.add(pnThongKeTheoThang);

		JPanel pnBottomOfRight = new JPanel();
		pnBottomOfRight.setLayout(new BorderLayout());
		TitledBorder borderThongKeTheoThang = new TitledBorder(
				BorderFactory.createLineBorder(Color.RED),
				"Thông tin chi tiết");
		borderThongKeTheoThang.setTitleColor(Color.BLUE);
		borderThongKeTheoThang.setTitleFont(new Font("", Font.BOLD, 15));
		pnBottomOfRight.setBorder(borderThongKeTheoThang);
		pnRight.add(pnBottomOfRight,BorderLayout.CENTER);
		dtmThongKeTheoThang = new DefaultTableModel();
		dtmThongKeTheoThang.addColumn("Tháng");
		dtmThongKeTheoThang.addColumn("Tổng số tiền thu được");
		tblThongKeTheoThang = new JTable(dtmThongKeTheoThang);
		tblThongKeTheoThang.setFont(new Font("", Font.ITALIC, 15));
		JScrollPane scTableThongKeTheoThang = new JScrollPane(
				tblThongKeTheoThang,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		pnBottomOfRight.add(scTableThongKeTheoThang,BorderLayout.CENTER);
	}
	public void addEvents(){
		mnuDangXuat.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				int ret = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn đăng xuất không?","Thông báo",JOptionPane.YES_NO_OPTION);
				if(ret == JOptionPane.NO_OPTION){
					return;
				}
				else{
					dispose();
					DangNhapUI ui = new DangNhapUI("Login");
					ui.showWindows();
				}
			}
		});
		mnuThoat.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				int ret = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn thoát không?","Thông báo",JOptionPane.YES_NO_OPTION);
				if(ret == JOptionPane.NO_OPTION){
					return;
				}
				else{
					System.exit(0);
				}
			}
		});
		btnTroVe.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				dispose();
				DanhSachChucNangUI ui = new DanhSachChucNangUI("Hệ thống cửa hàng điện thoại");
				ui.showWindows();
			}
		});
		btnDangXuat.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				int ret = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn đăng xuất không?","Thông báo",JOptionPane.YES_NO_OPTION);
				if(ret == JOptionPane.NO_OPTION){
					return;
				}
				else{
					dispose();
					DangNhapUI ui = new DangNhapUI("Login");
					ui.showWindows();
				}
			}
		});
		btnThoat.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				int ret = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn thoát không?","Thông báo",JOptionPane.YES_NO_OPTION);
				if(ret == JOptionPane.NO_OPTION){
					return;
				}
				else{
					System.exit(0);
				}
			}
		});
		btnThemTaiKhoan.addActionListener(new  ActionListener() {

			public void actionPerformed(ActionEvent e) {
				ThemTaiKhoanUI ui = new ThemTaiKhoanUI("Thêm tài khoản");
				ui.showWindows();
			}
		});
		btnThemNV.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				xuLyThemNhanVien();
			}
		});
		cboLoaiNhanVien.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if(cboLoaiNhanVien.getSelectedIndex() == -1){
					return;
				}
				else{
					NhanVienService nvService = new NhanVienService();
					dsNhanVien = nvService.docNhanVienTheoLoai(cboLoaiNhanVien.getSelectedIndex()==0?1:2);
					dtmNhanVien.setRowCount(0);
					for(HienThiToanBoNhanVienModel nv : dsNhanVien)
					{
						Vector<Object>vec = new Vector<Object>();
						vec.add(nv.getMaNV());
						vec.add(nv.getTenNV());
						vec.add(nv.getNamSinh());
						vec.add(nv.getDiaChi());
						vec.add(nv.getSDT());
						vec.add(nv.getEmail());
						vec.add(nv.getNgayVaoLamViec());
						vec.add(nv.getTenBoPhan());
						vec.add(nv.getTaiKhoan());
						dtmNhanVien.addRow(vec);
					}
				}	
			}
		});
		cboTenTaiKhoan.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if(cboTenTaiKhoan.getSelectedIndex() == -1){
					return;
				}
				else{
					NhanVienService nvService = new NhanVienService();
					dsNhanVien = nvService.docNhanVienTheoTenDangNhap(cboTenTaiKhoan.getSelectedItem()+"");
					dtmNhanVien.setRowCount(0);
					for(HienThiToanBoNhanVienModel nv : dsNhanVien)
					{
						Vector<Object>vec = new Vector<Object>();
						vec.add(nv.getMaNV());
						vec.add(nv.getTenNV());
						vec.add(nv.getNamSinh());
						vec.add(nv.getDiaChi());
						vec.add(nv.getSDT());
						vec.add(nv.getEmail());
						vec.add(nv.getNgayVaoLamViec());
						vec.add(nv.getTenBoPhan());
						vec.add(nv.getTaiKhoan());
						dtmNhanVien.addRow(vec);
					}
				}	
			}
		});
		tblNhanVien.addMouseListener(new MouseListener() {

			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mouseClicked(MouseEvent e) {

				int row = tblNhanVien.getSelectedRow();
				if(row == -1){
					return;
				}
				else{
					HienThiToanBoNhanVienModel nv = dsNhanVien.get(row);
					txtTenNV.setText(nv.getTenNV());
					txtNamSinh.setText(nv.getNamSinh());
					txtDiaChi.setText(nv.getDiaChi());
					txtSDT.setText(nv.getSDT());
					txtEmail.setText(nv.getEmail());
					txtNgayVaoLamViec.setText(nv.getNgayVaoLamViec());
				}
			}
		});
		btnSuaNV.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				xuLySuaNhanVien();
			}
		});
		btnXoaNV.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				xuLyXoaNhanVien();
			}
		});
		btnTimKiemNhanVien.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				xuLyTimKiemNhanVien();
			}
		});
		chkHienThiToanBoNhanVien.addItemListener(new ItemListener() {
			
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==1){
					NhanVienService nvService = new NhanVienService();
					dsNhanVien = nvService.docToanBoNhanVien();
					dtmNhanVien.setRowCount(0);
					for(HienThiToanBoNhanVienModel nv : dsNhanVien)
					{
						Vector<Object>vec = new Vector<Object>();
						vec.add(nv.getMaNV());
						vec.add(nv.getTenNV());
						vec.add(nv.getNamSinh());
						vec.add(nv.getDiaChi());
						vec.add(nv.getSDT());
						vec.add(nv.getEmail());
						vec.add(nv.getNgayVaoLamViec());
						vec.add(nv.getTenBoPhan());
						vec.add(nv.getTaiKhoan());
						dtmNhanVien.addRow(vec);
					}
				}
				else{
					dtmNhanVien.setRowCount(0);
				}
			}
		});
		btnReload.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				hienThiToanBoTaiKhoanTenCombobox();
			}
		});
		chkThongKeTheoNgay.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==1){
					ThongKeDoanhThuTheoNgayService tktnService = new ThongKeDoanhThuTheoNgayService();
					dsThongKeTheoNgay = tktnService.thongKeDoanhThuTheoNgay();
					dtmThongKeTheoNgay.setRowCount(0);
					for(ThongKeDoanhThuTheoNgayModel tktn : dsThongKeTheoNgay)
					{
						Vector<Object>vec = new Vector<Object>();
						vec.add(tktn.getNgay());
						vec.add(tktn.getTongTien());
						dtmThongKeTheoNgay.addRow(vec);
					}
				}
				else{
					dtmThongKeTheoNgay.setRowCount(0);
				}
			}
		});
		chkThongKeTheoThang.addItemListener(new ItemListener() {
			
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==1){
					ThongKeDoanhThuTheoThangService tkttService = new ThongKeDoanhThuTheoThangService();
					dsThongKeTheoThang = tkttService.thongKeDoanhThuTheoThang(Integer.parseInt(cboNam.getSelectedItem()+""));
					dtmThongKeTheoThang.setRowCount(0);
					for(ThongKeDoanhThuTheoThangModel tktt : dsThongKeTheoThang){
						Vector<Object>vec = new Vector<Object>();
						vec.add(tktt.getThang());
						vec.add(tktt.getTongTien());
						dtmThongKeTheoThang.addRow(vec);
					}
				}
				else{
					dtmThongKeTheoThang.setRowCount(0);
				}
			}
		});
	}

	protected void xuLyTimKiemNhanVien() {
		NhanVienService nvService = new NhanVienService();
		Vector<NhanVienModel>dsnv = nvService.timNhanVienTheoTen(txtTimKiemNhanVien.getText());
		dtmNhanVien.setRowCount(0);
		for(NhanVienModel nv : dsnv)
		{
			Vector<Object> vec = new Vector<Object>();
			vec.add(nv.getMaNV());
			vec.add(nv.getTenNV());
			vec.add(nv.getNamSinh());
			vec.add(nv.getDiaChi());
			vec.add(nv.getSDT());
			vec.add(nv.getEmail());
			vec.add(nv.getNgayVaoLamViec());
			vec.add(nv.getMaLoaiNV());
			vec.add(nv.getTenDangNhap());
			dtmNhanVien.addRow(vec);
		}
	}
	protected void xuLyXoaNhanVien() {
		int rowSelected = tblNhanVien.getSelectedRow();
		if(rowSelected == -1){
			JOptionPane.showMessageDialog(null, "Bạn chưa chọn nhân viên cần xoá!");
			return;
		}
		else{
			int nvSelected = Integer.parseInt(tblNhanVien.getValueAt(rowSelected, 0)+"");
			int ret = JOptionPane.showConfirmDialog(null, 
					"Bạn có chắc chắn xoá nhân viên "+txtTenNV.getText()+" không?",
					"Xác nhận xoá",JOptionPane.YES_NO_OPTION);
			if(ret == JOptionPane.NO_OPTION){
				return;
			}
			else{
				try{
					NhanVienService nvService = new NhanVienService();
					if(nvService.xoaNhanVien(nvSelected) > 0){
						JOptionPane.showMessageDialog(null, "Xoá nhân viên thành công!");
						txtTenNV.setText("");
						txtNamSinh.setText("");
						txtDiaChi.setText("");
						txtSDT.setText("");
						txtEmail.setText("");
						txtNgayVaoLamViec.setText("");
						txtTenNV.requestFocus();
						hienThiToanBoNhanVien();
					}
				}
				catch(Exception ex){
					ex.printStackTrace();
				}
			}
		}
	}
	protected void xuLySuaNhanVien() {
		int rowSelected = tblNhanVien.getSelectedRow();
		if(rowSelected == -1) {
			JOptionPane.showMessageDialog(null, "Bạn chưa chọn nhân viên cần sửa!");
			return;
		}
		else {
			int nvSelected = Integer.parseInt(tblNhanVien.getValueAt(rowSelected, 0)+"");
			try {
				NhanVienService nvService = new NhanVienService();
				if(nvService.suaNhanVien(nvSelected, txtTenNV.getText(), txtNamSinh.getText(), 
						txtDiaChi.getText(), txtSDT.getText(), txtEmail.getText(), 
						txtNgayVaoLamViec.getText()) > 0){
					JOptionPane.showMessageDialog(null, "Sửa thông tin nhân viên thành công!");
					txtTenNV.setText("");
					txtNamSinh.setText("");
					txtDiaChi.setText("");
					txtSDT.setText("");
					txtEmail.setText("");
					txtNgayVaoLamViec.setText("");
					txtTenNV.requestFocus();
					hienThiToanBoNhanVien();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	protected void xuLyThemNhanVien() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		NhanVienModel nv = new NhanVienModel();
		nv.setTenNV(txtTenNV.getText());
		nv.setNamSinh(txtNamSinh.getText()); //nhập theo format yyyy/MM/dd
		nv.setDiaChi(txtDiaChi.getText());
		nv.setSDT(txtSDT.getText());
		nv.setEmail(txtEmail.getText());
		nv.setNgayVaoLamViec(txtNgayVaoLamViec.getText()); //nhập theo format yyyy/MM/dd
		nv.setMaLoaiNV(cboLoaiNhanVien.getSelectedIndex()==0 ? 1 : 2); 
		nv.setTenDangNhap(cboTenTaiKhoan.getSelectedItem()+"");
		NhanVienService nvService = new NhanVienService();
		if(nvService.themNhanVien(nv) > 0)
		{
			JOptionPane.showMessageDialog(null, "Thêm nhân viên thành công!");
			txtTenNV.setText("");
			txtNamSinh.setText("");
			txtDiaChi.setText("");
			txtSDT.setText("");
			txtEmail.setText("");
			txtNgayVaoLamViec.setText("");
			txtTenNV.requestFocus();
			hienThiToanBoNhanVien();
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Thêm nhân viên thất bại!");
		}

	}
	public void showWindows(){
		this.setSize(1500, 900);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
}
