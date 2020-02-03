import java.util.ArrayList;

public class SanPhamDB {

	//Attributes
	private ArrayList<SanPham> sanPhams;
	
	//Constructors
	public SanPhamDB() {
		sanPhams = new ArrayList<SanPham>();
		sanPhams.add(new SanPham("VCBLTN", "Vỏ case Black Technology", 890000, "Images/bltn.png"));
		sanPhams.add(new SanPham("MHDU2417", "Màn hình Dell", 5200000, "Images/du2417.png"));
		sanPhams.add(new SanPham("LE1380", "Loa Edifier", 1250000, "Images/e1380.png"));
		sanPhams.add(new SanPham("BPESH", "Bàn phím Ensoho", 200000, "Images/esh.png"));
		sanPhams.add(new SanPham("CFL", "Chuột Fuhlen", 120000, "Images/fl.png"));
	}
	
	//Methods
	public ArrayList<SanPham> getSanPhams() {
		return sanPhams;
	}

	public void setSanPhams(ArrayList<SanPham> sanPhams) {
		this.sanPhams = sanPhams;
	}

}
