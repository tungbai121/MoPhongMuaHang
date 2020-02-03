import javax.swing.ImageIcon;

public class SanPham {

	//Attributes
	private String maSP;
	private String tenSP;
	private int donGia;
	private ImageIcon image;
	
	//Constructors
	public SanPham() {

	}
	
	public SanPham(String maSP, String tenSP, int donGia, String path) {
		this.maSP = maSP;
		this.tenSP = tenSP;
		this.donGia = donGia;
		image = new ImageIcon(path);
	}

	//Methods
	public String getMaSP() {
		return maSP;
	}

	public void setMaSP(String maSP) {
		this.maSP = maSP;
	}

	public String getTenSP() {
		return tenSP;
	}

	public void setTenSP(String tenSP) {
		this.tenSP = tenSP;
	}

	public int getDonGia() {
		return donGia;
	}

	public void setDonGia(int donGia) {
		this.donGia = donGia;
	}
	
	public ImageIcon getImage() {
		return image;
	}

	public void setImage(ImageIcon image) {
		this.image = image;
	}

}
