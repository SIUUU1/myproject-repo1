package project.opentalk;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
//프로젝트명 : Ticket World
//클레스 역할 : 공연별 오픈톡방 서버를 관리하는 클래스
//제작자 : 안시우, 제작일 : 24년 4월 22일
public class OpenTalkServer {
	// memberVariable
	public static int[] sslist = new int[1]; // server.txt 로딩 값 저장
	public static ArrayList<GuestSocketThread> list = new ArrayList<>();

	String performanceName;

	// constructor----수정??
	public OpenTalkServer(String performanceName) {
		this.performanceName = performanceName;
	}

	// memberFunction
	public static void main(String[] args) {
		int totalCount = 0; // 오픈톡방 서버에 접속한 총 게스트 수
		boolean exit = false; // 무한 반복
		ServerSocket ss = null; // 서버 소켓
		// server.txt 로딩
		try {
			loadServer();
		} catch (Exception e) {
			System.out.println("파일을 읽어올 수 없습니다.");
		}
		try {
			ss = new ServerSocket(sslist[0]);
			while (!exit) {
				System.out.println("공연 오픈톡과 연결 기다리는 중---------");
				Socket guestSocket = ss.accept();
				totalCount++;
				GuestSocketThread cst = new GuestSocketThread(guestSocket);
				list.add(cst);
				cst.start();
				System.out.println("******공연 오픈톡******");
				// 서버에 접속한 게스트 수가 50이상일 때 더이상 받지 않는다.
				if (totalCount >= 50) {
					System.out.println("서버과부하");
					exit = true;
				}
			}
		} catch (NumberFormatException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		} finally {
			try {
				ss.close();
			} catch (IOException e) {
				System.out.println(e);
			}
		}
	}// end of main

	// GuestSocketThread 클래스
	static class GuestSocketThread extends Thread {
		// memberVariable
		static int count = 0;
		Socket cs;
		DataInputStream dis;
		DataOutputStream dos;
		String guestName;
		boolean isActive;

		// constructor
		GuestSocketThread(Socket cs) {
			count++;
			this.cs = new Socket();
			this.isActive = true;
			this.guestName = "guest" + count;
			try {
				dis = new DataInputStream(cs.getInputStream());
				dos = new DataOutputStream(cs.getOutputStream());
			} catch (IOException e) {
				System.out.println(e);
			}
		}

		// memberFunction
		// override
		@Override
		public void run() {
			String message = null;
			while (isActive) {
				try {
					message = dis.readUTF();
					if (message.equals("exit")) {
						isActive = false;
					}
					for (GuestSocketThread data : list) {
						if (isActive == false) {
							data.dos.writeUTF(guestName + " : 나감");
							System.out.println(guestName + " : 나감");
						}
						data.dos.writeUTF(message);
					}
				} catch (IOException e) {
					System.out.println(e);
				}
			} // end of while
			try {
				dis.close();
				dos.close();
				cs.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// server.txt 로딩
	public static void loadServer() {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream("server.txt")));
			sslist[0] = br.read();
		} catch (FileNotFoundException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println("server.txt를 읽어올 수 없습니다.");
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				System.out.println(e);
			}
		}
	}
}
