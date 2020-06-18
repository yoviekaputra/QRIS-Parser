package yovi.putra;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/***
 * @author yoviekaputra
 */
public class QRISCore {
    Properties qrisField = null;
    Properties qrisFieldAdditionalData = null;
    		
    public QRISCore() {
    	qrisField = PropertiesUtils.getProperties(getClass(), "qrisfield.prop");
    	qrisFieldAdditionalData = PropertiesUtils.getProperties(getClass(), "qrisadditionaldata.prop");
    }

	public List<QRISSegment> parsing(String payload) {
		if (isQRISValid(payload)) {
			List<QRISSegment> segment = parsingRootId(payload, qrisField);
			parsingMerchantInfo(segment);
			parsingAddionalData(segment);
			return segment;			
		} else {
			return new ArrayList<>();
		}
		
	}
	
	public void print(List<QRISSegment> qris) {
		for (QRISSegment q : qris) {
			System.out.println(q);
		}
	}
	
	private List<QRISSegment> parsingRootId(String payload, Properties qrisField) {
		List<QRISSegment> segment = new ArrayList<>();
		QRISSegment seg;
		for (int tag = 0; tag < 100; tag++) {
			String rootId = String.format("%02d", tag);
			
			try {
				if (payload.startsWith(rootId)) {
					// clean root id
					payload = payload.substring(2);
					// get data length 
					int len = Integer.parseInt(payload.substring(0,2));
					//remove data length
					payload = payload.substring(2);
					//get data
					String data = payload.substring(0, len);
					/* get field name */
					String field = getFieldName(qrisField, rootId);
					
					seg = new QRISSegment(rootId, field, len, data);
					segment.add(seg);
					
					
					//update payload data to next parsing
					payload = payload.substring(len);
				}
			} catch (Exception e) {
				System.err.println("Error: " + rootId);
				System.err.println(payload);
			}
		}
		return segment;
	}
	
	private String getFieldName(Properties prop, String key) {
		if (prop != null) {
			return prop.getProperty(key);
		}
		return null;
	}
	
	private void parsingMerchantInfo(List<QRISSegment> segments) {
		for (QRISSegment seg: segments) {
			String sRootId = seg.getRootId();
			int rootId = Integer.parseInt(sRootId);
			
			if (rootId >= 2 && rootId <=51) {
				String payload = (String) seg.getData();
				seg.setData(getMerchantInfo(payload));
			}
		}
	}
	
	public QRISMerchantInfo getMerchantInfo(String payload) {
		QRISMerchantInfo mInfo = new QRISMerchantInfo();
		
		for (int tag = 0; tag <= 3; tag++) {
			String rootId = String.format("%02d", tag);
			if (payload.startsWith(rootId)) {
				payload = payload.substring(2);
				int len = Integer.parseInt(payload.substring(0,2));
				payload = payload.substring(2);
				String data = payload.substring(0, len);
				payload = payload.substring(len);
				
				if (tag == 0) {
					mInfo.setGlobalId(data);
				} else if (tag == 1) {
					mInfo.setmPAN(data);
				} else if (tag == 2) {
					mInfo.setmId(data);
				} else if (tag == 3) {
					mInfo.setmCriteria(data);
				}
			}
		}
		
		return mInfo;
	}
	
	private void parsingAddionalData(List<QRISSegment> segment) {
		for (QRISSegment seg: segment) {
			if ("62".equals(seg.getRootId())) {
				String payload = (String) seg.getData();
				seg.setData(parsingRootId(payload, qrisFieldAdditionalData));
			}
		}
	}
	
	public boolean isQRISValid(String qrdata) {
		//checsum 4 digit ascii
		if (qrdata != null && qrdata.length() > 4) {
			String qrDataNonCRC = qrdata.substring(0, qrdata.length() - 4);
			String qrCRC = qrdata.substring(qrdata.length()-4).toUpperCase();
			String checkCRC = checkCRC(qrDataNonCRC.getBytes()).toUpperCase();
			
			System.err.println("QR CRC: " + qrCRC + ", System CRC: " + checkCRC + "\n");			
			if (qrDataNonCRC.startsWith("00") && qrCRC.equalsIgnoreCase(checkCRC)) {
				System.out.println("QRIS payload valid");
				return true;
			}
		}
		System.err.println("QRIS payload invalid");
		return false;
	}
	
	private String checkCRC(byte []bytes) {
		int crc = 0xFFFF;
        int polynomial = 0x1021;
        String sCRC = "";
        
        for (byte b : bytes) {
            for (int i = 0; i < 8; i++) {
                boolean bit = ((b   >> (7-i) & 1) == 1);
                boolean c15 = ((crc >> 15    & 1) == 1);
                crc <<= 1;
                if (c15 ^ bit) crc ^= polynomial;
            }
        }
        crc &= 0xffff;
        
        sCRC = String.format("%04x", crc);
        return sCRC;
	}
}
