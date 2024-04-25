package project.opentalk;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
//프로젝트명 : Ticket World
//클레스 역할 : 공연별 오픈톡방 고객 서버를 관리하는 클래스
//제작자 : 안시우, 제작일 : 24년 4월 24일
public class OpenTalkClient {
	// memberVariable
	private String serverIP = "192.168.20.1";
	private int portNum; // 포트번호
	private JTextArea textArea;
	private JTextField textField;
	private Socket ss;
	private DataInputStream dis;
	private DataOutputStream dos;
	private String performanceName;

	// constructor
	public OpenTalkClient(String performanceName) {
		this.performanceName = performanceName;
		this.portNum = OpenTalkServer.sslist[0];
		MyFrame myframe = new MyFrame();

		try {
			ss = new Socket(serverIP, portNum);
			dis = new DataInputStream(ss.getInputStream());
			dos = new DataOutputStream(ss.getOutputStream());

			// 서버와 데이터를 주고 받을 Thread
			Thread thread = new Thread(new Runnable() {

				@Override
				public void run() {
					boolean flag = false;
					while (!flag) {
						try {
							String data = null;
							data = dis.readUTF();
							textArea.append("RECEIVE:" + new String(data) + "\n");
						} catch (IOException e) {
							e.printStackTrace();
						}
					} // end of while
				}// end of run
			});

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				dos.close();
				dis.close();
				ss.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	class MyFrame extends JFrame implements ActionListener {
		MyFrame() {
			// 창이름
			super(performanceName);
			// 창의 최대,최소,닫기 기능
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			//스크롤 기능, 
			JScrollPane jspane = new JScrollPane(textArea);
			textArea = new JTextArea(20, 30);
			textArea.setEditable(false);

			textField = new JTextField(30);
			textField.addActionListener(this);

			add(textField, BorderLayout.PAGE_END);
			add(jspane, BorderLayout.CENTER);
			pack();
			setVisible(true);
		}

		// 이벤트 발생 : 텍스트 필드에서 입력하고 엔터를 입력할 때 이벤트 발생
		@Override
		public void actionPerformed(ActionEvent e) {
			String message = textField.getText();

			try {
				dos.writeUTF(message);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			textArea.append(message);
			textField.selectAll();

			textArea.setCaretPosition(textArea.getDocument().getLength());

		}

	}

	public static void main(String[] args) {
		// server.txt
		try {
			OpenTalkServer.loadServer();
		} catch (Exception e) {
			System.out.println("파일을 읽어올 수 없습니다.");
		}
		OpenTalkClient client = new OpenTalkClient("영웅");
	}
}
