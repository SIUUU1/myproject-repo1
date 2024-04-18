package project.customerservice;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
//프로젝트명 : Ticket World
//클레스 역할 : 고객센터 서버 기능을 관리하는 클래스
//제작자 : 안시우, 제작일 : 24년 4월 17일
public class CustomerServiceServer {
	public static String[] sslist = new String[2];
	public static ArrayList<CilentSocketThread> list = new ArrayList<>();

	public static void main(String[] args) {
		int totalCount = 0;
		boolean exit = false;
		// server.txt 로딩
		try {
			loadServer();
		} catch (Exception e) {
			System.out.println("파일을 읽어올 수 없습니다.");
		}
		// 서버 소켓
		ServerSocket ss = null;
		try {
			ss = new ServerSocket(Integer.parseInt(sslist[1]));
			while (!exit) {
				System.out.println("고객센터와 연결 기다리는 중-----------");
				Socket cilentSocket = ss.accept();
				totalCount++;
				CilentSocketThread cst = new CilentSocketThread();
				list.add(cst);
				System.out.println("******Ticket World Customer Service Center******");
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

	// CilentSocketThread 클래스
	static class CilentSocketThread extends Thread {
		Socket cs = new Socket();
	}

	// server.txt 로딩
	public static void loadServer() {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream("server.txt")));
			sslist[0] = br.readLine();
			sslist[1] = br.readLine();
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
