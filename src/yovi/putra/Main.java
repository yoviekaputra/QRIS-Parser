package yovi.putra;

public class Main {
	public static void main(String []args) {
        String qrCodeDataFull = "00020101021126660017ID.CO.EXAMPLE.WWW01159360123401234560215MIDCONTOH1234560303UMI5204123453031115802ID5913NamaMerchant16009NamaKota16110123456789062070703K196304A8E0";
        //String qrCodeDataFull = "26490119123456789012345678902151234567890123450303UMI27490119123456789012345678902151234567890123450303UMI";
        
    	QRISCore qris = new QRISCore();
		if (qris.isQRISValid(qrCodeDataFull)) {
			for (QRISSegment s: qris.parsing(qrCodeDataFull)) {
				System.out.println(s);
			}
		} else {
			System.out.println("QRIS Invalid");
		}
    }
}
