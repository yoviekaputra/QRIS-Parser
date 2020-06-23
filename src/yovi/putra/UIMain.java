package yovi.putra;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;

public class UIMain implements ActionListener, QRISCoreLogListener {

	private JFrame frmQrisParser;
	private JTextArea textAreaOutput;
	private JTextArea textAreaInput;
	
	private QRISCore qr = new QRISCore(this);
	private List<QRISSegment> qris = new ArrayList<QRISSegment>();
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIMain window = new UIMain();
					window.frmQrisParser.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public UIMain() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmQrisParser = new JFrame();
		frmQrisParser.setTitle("QRIS Parser (https://github.com/yovi-ep)");
		frmQrisParser.setBounds(100, 100, 566, 422);
		frmQrisParser.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton btnProcess = new JButton("Process");
		btnProcess.addActionListener(this);
		
		JLabel lblInputQrdata = new JLabel("Input QRData");
		
		JLabel lblOutput = new JLabel("Output");
		lblOutput.setHorizontalAlignment(SwingConstants.LEFT);
		
		JScrollPane scrollPane = new JScrollPane();
		
		JScrollPane scrollPane_1 = new JScrollPane();
		GroupLayout groupLayout = new GroupLayout(frmQrisParser.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(16)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblOutput, GroupLayout.DEFAULT_SIZE, 536, Short.MAX_VALUE)
							.addGap(6)
							.addComponent(btnProcess, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE))
						.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE)
						.addComponent(lblInputQrdata, GroupLayout.DEFAULT_SIZE, 640, Short.MAX_VALUE))
					.addGap(16))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(14)
					.addComponent(lblInputQrdata, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(32)
							.addComponent(lblOutput, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnProcess)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)
					.addGap(16))
		);
		
		textAreaInput = new JTextArea();
		scrollPane_1.setViewportView(textAreaInput);
		textAreaInput.setLineWrap(true);
		
		textAreaOutput = new JTextArea();
		textAreaOutput.setWrapStyleWord(true);
		scrollPane.setViewportView(textAreaOutput);
		textAreaOutput.setLineWrap(true);
		frmQrisParser.getContentPane().setLayout(groupLayout);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		process(textAreaInput.getText());
	}
	
	private void process(String qrData) {
		qris = qr.parsing(qrData);
		qr.print(qris);
	}

	@Override
	public void logging(String log) {
		textAreaOutput.append(log + "\n");
	}
}
