package project.customerservice;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
//프로젝트명 : Ticket World
//클레스 역할 : 고객센터 서버 기능을 관리하는 클래스
//제작자 : 안시우, 제작일 : 24년 4월 17일
public class CustomerServiceServer {
	public static void main(String[] args) {
		ServerSocket ss = null;
		Socket cilentServer = null;
		BufferedReader br = null;
		PrintWriter pw = null;
		try {
			ss = new ServerSocket(10000);
			cilentServer = new Socket();
			System.out.println("고갟센터와 연결 기다리는 중-----------");
			cilentServer = ss.accept();
			br = new BufferedReader(new InputStreamReader(cilentServer.getInputStream())); 
			boolean exit = false;
			while(!exit) {
				br.readLine();
			}
		} catch (IOException e) {
			System.out.println(e);
		}finally {
			try {
				ss.close();
			} catch (IOException e) {
				System.out.println(e);
			}
		}
		
	}

}
