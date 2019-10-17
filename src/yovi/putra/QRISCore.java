package yovi.putra;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/***
 * @author yoviekaputra
 */
public class QRISCore {
	/* Configure Root ID/ChildID */
    static final String PAYLOAD_FMT_INDICATOR		= "00";
    static final String POINT_OF_INIT_METHOD		= "01";
    static final String MERCHANT_ACC_VISA			= "02";
    static final String MERCHANT_ACC_MASTER			= "04";
    
    static final String MERCHANT_ACC_INFO1			= "26";
    static final String MERCHANT_ACC_INFO1_GLOBAL_UID = "00";
    static final String MERCHANT_ACC_INFO1_MPAN		= "01";
    static final String MERCHANT_ACC_INFO1_MID		= "02";
    static final String MERCHANT_ACC_INFO1_MCRITERIA= "03";
    
    static final String MERCHANT_ACC_INFO2			= "27";
    static final String MERCHANT_ACC_INFO2_GLOBAL_UID= "00";
    static final String MERCHANT_ACC_INFO2_MPAN		= "01";
    static final String MERCHANT_ACC_INFO2_MID		= "02";
    
    static final String MERCHANT_ACC_INFO3			= "51";
    static final String MERCHANT_ACC_INFO3_GLOBAL_UID = "00";
    static final String MERCHANT_ACC_INFO3_MID		= "02";
    static final String MERCHANT_ACC_INFO3_MCRITERIA= "03";
    
    static final String MCC							= "52";
    static final String TRX_CURRENCY				= "53";
    static final String COUNTRY_CODE				= "58";
    static final String MERCHANT_NAME				= "59";
    static final String MERCHANT_CITY				= "60";
    static final String POSTAL_CODE					= "61";
    static final String CRC							= "63";

    private Map<String, QRISSegment> qrisData;
    
    public static void main(String []args) {
        String payload = "00020101021102154076620099999990415508999888888888265400020001189360000901234567890215ABCD123456789010303UMI27660021ID.CO.PERMATABANK.WWW01189360001329876543210215BDFHF123456789051380008ID.GPNQR0215ABCDE12345678900303UMI5204581253033605802ID5909BASO JONO6007JAKARTA61051031063045187";

        try {
        	/* Sample 1 */
            QRISCore qris = new QRISCore();
        	qris.parsing(qris.getQrisData(), payload);
			System.out.println(qris.getQrisData().get(QRISCore.MERCHANT_ACC_INFO1_MID));
			
        	/* Sample 2 */
			qris = new QRISCore(payload);
			QRISSegment merchantInfo1 = qris.getQrisData().get(QRISCore.MERCHANT_ACC_INFO1);
			System.out.println(merchantInfo1);
			
			/* Get child from segment */
			if (merchantInfo1.isHasChild()) {
				System.out.println(merchantInfo1.getChilds().get(MERCHANT_ACC_INFO1_GLOBAL_UID).getData());
			}
		} catch (Throwable e) {
			System.err.println(e.getMessage());
		}
    }

    public QRISCore() {
    	configure();
    }
    public QRISCore(String payload) throws Throwable {
    	configure();
    	parsing(qrisData, payload);
    }
    
    private void configure() {
    	Map<String, QRISSegment> childs = new LinkedHashMap<String, QRISSegment>();
    	qrisData = new HashMap<>();
    	
    	qrisData.put(PAYLOAD_FMT_INDICATOR, new QRISSegment(PAYLOAD_FMT_INDICATOR, false));
    	qrisData.put(POINT_OF_INIT_METHOD, new QRISSegment(POINT_OF_INIT_METHOD, false));
    	qrisData.put(MERCHANT_ACC_VISA, new QRISSegment(MERCHANT_ACC_VISA, false));
    	qrisData.put(MERCHANT_ACC_MASTER, new QRISSegment(MERCHANT_ACC_MASTER, false));

    	childs = new HashMap<>();
    	childs.put(MERCHANT_ACC_INFO1_GLOBAL_UID, new QRISSegment(MERCHANT_ACC_INFO1_GLOBAL_UID, false));
    	childs.put(MERCHANT_ACC_INFO1_MPAN, new QRISSegment(MERCHANT_ACC_INFO1_MPAN, false));
    	childs.put(MERCHANT_ACC_INFO1_MID, new QRISSegment(MERCHANT_ACC_INFO1_MID, false));
    	childs.put(MERCHANT_ACC_INFO1_MCRITERIA, new QRISSegment(MERCHANT_ACC_INFO1_MCRITERIA, false));
    	qrisData.put(MERCHANT_ACC_INFO1, new QRISSegment(MERCHANT_ACC_INFO1, true, childs));
    	
    	childs = new HashMap<>();
    	childs.put(MERCHANT_ACC_INFO2_GLOBAL_UID, new QRISSegment(MERCHANT_ACC_INFO2_GLOBAL_UID, false));
    	childs.put(MERCHANT_ACC_INFO2_MPAN, new QRISSegment(MERCHANT_ACC_INFO2_MPAN, false));
    	childs.put(MERCHANT_ACC_INFO2_MID, new QRISSegment(MERCHANT_ACC_INFO2_MID, false));
    	qrisData.put(MERCHANT_ACC_INFO2, new QRISSegment(MERCHANT_ACC_INFO2, true, childs));
    	
    	childs = new HashMap<>();
    	childs.put(MERCHANT_ACC_INFO3_GLOBAL_UID, new QRISSegment(MERCHANT_ACC_INFO3_GLOBAL_UID, false));
    	childs.put(MERCHANT_ACC_INFO3_MID, new QRISSegment(MERCHANT_ACC_INFO3_MID, false));
    	childs.put(MERCHANT_ACC_INFO3_MCRITERIA, new QRISSegment(MERCHANT_ACC_INFO3_MCRITERIA, false));
    	qrisData.put(MERCHANT_ACC_INFO3, new QRISSegment(MERCHANT_ACC_INFO3, true, childs));
    	
    	qrisData.put(MCC, new QRISSegment(MCC, false));
    	qrisData.put(TRX_CURRENCY, new QRISSegment(TRX_CURRENCY, false));
    	qrisData.put(COUNTRY_CODE, new QRISSegment(COUNTRY_CODE, false));
    	qrisData.put(MERCHANT_NAME, new QRISSegment(MERCHANT_NAME, false));
    	qrisData.put(MERCHANT_CITY, new QRISSegment(MERCHANT_CITY, false));
    	qrisData.put(POSTAL_CODE, new QRISSegment(POSTAL_CODE, false));
    	qrisData.put(CRC, new QRISSegment(CRC, false));
    }
    
    /***
     * @param data
     * @param payload
     * @throws Throwable
     */
    public void parsing(Map<String, QRISSegment> data, String payload) throws Throwable {
    	/* Sorting key Map */
    	Object keys[]= data.keySet().toArray();
    	Arrays.sort(keys);
    	
    	for (Object key : keys) {
    		QRISSegment seg = data.get(key);
    		if (payload.startsWith(seg.getRootId())) {
    			try {
    				/* get rootId, Length */
    				String id = payload.substring(0,4);
        			payload = payload.substring(4);
        			
        			/* get Length */
        			int length = Integer.parseInt(id.substring(2));
        			
        			/* update segment data */
        			seg.setLength(length);
        			seg.setData(payload.substring(0, length));
        			
        			/* cut payload data */
        			payload = payload.substring(length);
        			
        			/* parsing when segment has child */
        			if (seg.isHasChild()) {
        				parsing(seg.getChilds(), seg.getData());
        			}
    			} catch (Exception e) {
    				throw new Throwable("Error parsing data, detail segment: " + seg);
    			}
    			
    		} else {
    			throw new Throwable("Cannot find rootID: " + seg.getRootId() + ", detail segment: " + seg);
    		}
    	}
    }

	
    public Map<String, QRISSegment> getQrisData() {
		return qrisData;
	}
}
