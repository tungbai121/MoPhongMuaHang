import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

@SuppressWarnings("serial")
public class MuaHangGUI extends JFrame {

	//Attributes
	private JTable tblMatHang;
	private JTable tblGioHang;
	private JButton btnThem;
	private JButton btnXoa;
	private JLabel lblTongTien;
	private JTextField txtTongTien;
	private SanPhamDB listSanPham;
	private int tongTien = 0;
	
	//Constructors
	public MuaHangGUI() {
		setTitle("Ứng dụng mô phỏng mua hàng");
		setBounds(200, 20, 800, 700);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//Database sản phẩm
		listSanPham = new SanPhamDB();
		
		//Container
		Container container = getContentPane();
		container.setLayout(new GridLayout(0, 1, 0, 0));
		
		//Render ô "Ảnh minh hoạ"
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
		
		//Panel mặt hàng chứa table mặt hàng + button thêm sản phẩm vào giỏ hàng
		JPanel panelMatHang = new JPanel();
		panelMatHang.setBorder(new TitledBorder("Mặt hàng"));
		panelMatHang.setLayout(new BorderLayout());
		container.add(panelMatHang);
		
		//Table mặt hàng
		class LabelRender implements TableCellRenderer {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				return (Component) value;
			}
		}
		
		tblMatHang = new JTable() {
			@Override
			public boolean isCellEditable(int rowIndex, int colIndex) {
				return false;
			}
		};
		
		String[] titles_tblMatHang = new String[] {"Mã sản phẩm", "Tên sản phẩm", "Đơn giá", "Ảnh minh hoạ"};
		DefaultTableModel model_tblMatHang = new DefaultTableModel();
		model_tblMatHang.setColumnIdentifiers(titles_tblMatHang);
		
		for (SanPham sp : listSanPham.getSanPhams()) {
			String maSP = sp.getMaSP();
			String tenSP = sp.getTenSP();
			int donGia = sp.getDonGia();
			ImageIcon image = sp.getImage();
			
			JLabel label = new JLabel(image);
			
			Object[] data = {maSP, tenSP, donGia, label};
			model_tblMatHang.addRow(data);
		}
		
		tblMatHang.setModel(model_tblMatHang);
		tblMatHang.setRowHeight(100);
		tblMatHang.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblMatHang.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
		tblMatHang.getColumnModel().getColumn(3).setPreferredWidth(0);
		tblMatHang.getColumn("Ảnh minh hoạ").setCellRenderer(new LabelRender());
		
		JScrollPane scp_tblMatHang = new JScrollPane(tblMatHang);
		panelMatHang.add(scp_tblMatHang, BorderLayout.CENTER);
		
		//Button thêm
		JPanel subPanel1 = new JPanel(new FlowLayout());
		panelMatHang.add(subPanel1, BorderLayout.SOUTH);
		btnThem = new JButton("Thêm vào giỏ hàng");
		subPanel1.add(btnThem);
		
		//Panel giỏ hàng chứa table giỏ hàng + button xoá + tổng tiền
		JPanel panelGioHang = new JPanel();
		panelGioHang.setBorder(new TitledBorder("Giỏ hàng"));
		panelGioHang.setLayout(new BorderLayout());
		container.add(panelGioHang);
		
		//Table giỏ hàng
		tblGioHang = new JTable() {
			@Override
			public boolean isCellEditable(int rowIndex, int colIndex) {
				return false;
			}
		};
		
		String[] titles_tblGioHang = new String[] {"Mã sản phẩm", "Tên sản phẩm", "Đơn giá", "Số lượng", "Thành tiền"};
		DefaultTableModel model_tblGioHang = new DefaultTableModel();
		model_tblGioHang.setColumnIdentifiers(titles_tblGioHang);
		
		tblGioHang.setModel(model_tblGioHang);
		tblGioHang.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblGioHang.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
		tblGioHang.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
		tblGioHang.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
		
		JScrollPane scp_tblGioHang = new JScrollPane(tblGioHang);
		panelGioHang.add(scp_tblGioHang, BorderLayout.CENTER);
		
		//Button xoá + tổng tiền
		JPanel subPanel2 = new JPanel(new FlowLayout());
		panelGioHang.add(subPanel2, BorderLayout.SOUTH);
		lblTongTien = new JLabel("Tổng tiền:");
		txtTongTien = new JTextField(10);
		txtTongTien.setHorizontalAlignment(SwingConstants.RIGHT);
		txtTongTien.setEditable(false);
		btnXoa = new JButton("Xoá");
		
		subPanel2.add(lblTongTien);
		subPanel2.add(txtTongTien);
		subPanel2.add(btnXoa);
		
		//Xử lý sự kiện
		//Thêm sản phẩm vào giỏ hàng
		btnThem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRowIndex = tblMatHang.getSelectedRow();
				if (selectedRowIndex != -1) {
					String value = JOptionPane.showInputDialog("Nhập số lượng");
					if (value != null) {
						try {
							int soLuong = Integer.parseInt(value);
							if (soLuong > 0) {
								String maSP = (String) model_tblMatHang.getValueAt(selectedRowIndex, 0);
								String tenSP = (String) model_tblMatHang.getValueAt(selectedRowIndex, 1);
								int donGia = (int) model_tblMatHang.getValueAt(selectedRowIndex, 2);
								int thanhTien = soLuong*donGia;
								
								Object[] data = {maSP, tenSP, donGia, soLuong, thanhTien};
								model_tblGioHang.addRow(data);
								
								tongTien += thanhTien;
								txtTongTien.setText(Integer.toString(tongTien));
							} else JOptionPane.showMessageDialog(null, "Số lượng không hợp lệ!");
						} catch (NumberFormatException ex) {
							JOptionPane.showMessageDialog(null, "Số lượng không hợp lệ!");
						}
					}
				} else JOptionPane.showMessageDialog(null, "Bạn chưa chọn sản phẩm!");
			}
		});
		
		//Xoá sản phẩm khỏi giỏ hàng
		btnXoa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRowIndex = tblGioHang.getSelectedRow();
				if (selectedRowIndex != -1) {
					int thanhTien = (int) model_tblGioHang.getValueAt(selectedRowIndex, 4);
					
					model_tblGioHang.removeRow(selectedRowIndex);
					
					tongTien -= thanhTien;
					txtTongTien.setText(Integer.toString(tongTien));
				} else JOptionPane.showMessageDialog(null, "Bạn chưa chọn sản phẩm!");
			}
		});
	}

}
