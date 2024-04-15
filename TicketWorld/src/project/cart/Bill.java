package project.cart;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Bill implements Serializable {
	// memberVariable
	private String billID; // 영수증 ID
	private String name; // 배송받을 고객 이름
	private String phone; // 배송받을 고객 연락처
	private String address; // 배송받을 고객 주소
	private String dateOfReservation; // 예매날짜
	private int totalPaymentAmount; // 총 결제금액
	private ArrayList<CartItem> paymentItemList = new ArrayList<>();

	// constructor
	public Bill(String name, String phone, String address, int totalPaymentAmount,
			ArrayList<CartItem> paymentItemList) {
		super();
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.totalPaymentAmount = totalPaymentAmount;
		this.paymentItemList.addAll(paymentItemList);
		updateDateOfReservation();
	}

	// memberFunction
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDateOfReservation() {
		return dateOfReservation;
	}

	public int getTotalPaymentAmount() {
		return totalPaymentAmount;
	}

	public void setTotalPaymentAmount(int totalPaymentAmount) {
		this.totalPaymentAmount = totalPaymentAmount;
	}

	public ArrayList<CartItem> getPaymentItemList() {
		return paymentItemList;
	}

	public void setPaymentItemList(ArrayList<CartItem> paymentItemList) {
		this.paymentItemList = paymentItemList;
	}

	// 예매날짜지정함수
	public void updateDateOfReservation() {
		Date date = new Date();
		SimpleDateFormat form = new SimpleDateFormat("yyyy/MM/dd");
		this.dateOfReservation = form.format(date);
		SimpleDateFormat formattorId = new SimpleDateFormat("yyMMddhhmmss");
		this.billID = formattorId.format(date);
	}

	// 영수증 출력함수
	public void printBill() {
		System.out.println("==========================  결제  내역  ==========================");
		System.out.println(" 고객명 : " + name + "   \t\t연락처 : " + phone);
		System.out.println(" 배송지 : " + address + "\t예매날짜 : " + dateOfReservation);
		System.out.println("----------------------------------------------------------------");
		for (CartItem c : paymentItemList) {
			System.out.println(" 공연ID : " + c.getPerformanceID() + "\t공연명 : " + c.getItem().getName());
			System.out.println(" 예매좌석 :" + c.getSeatNum() + "\t총예매수 : " + c.getQuantity());
			System.out.println("----------------------------------------------------------------");
		}
		System.out.println("\t\t   총 결제금액 : " + totalPaymentAmount);
		System.out.println("================================================================");
		System.out.println();
	}

	// Overriding
	@Override
	public int hashCode() {
		return Objects.hash(billID);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bill other = (Bill) obj;
		return Objects.equals(billID, other.billID);
	}

}
