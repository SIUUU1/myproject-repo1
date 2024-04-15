package project.cart;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

import project.member.Admin;
import project.performance.Performance;

public class CartItem implements Serializable {
	// memberVariable
	private Performance item;
	private String performanceID; // 공연ID
	private int[][] seatArr; // 좌석
	// private int soldSeats; // 판매된 좌석수
	private ArrayList<String> seatNum = new ArrayList<String>(); // 좌석 번호
	private int quantity; // 예매 인원수
	private int totalPrice; // 총구매금액

	// constructor
	public CartItem(Performance item, int quantity, ArrayList<String> seatNum) {
		super();
		this.item = item;
		performanceID = item.getPerformanceID();
		seatArr = item.getSeatArr();
		// soldSeats = item.getSoldSeats();
		this.seatNum = seatNum;
		this.quantity = quantity;
		item.setSoldSeats(item.getSoldSeats() + quantity);
		updateTotalPrice();
	}

	// memberFunction
	public Performance getItem() {
		return item;
	}

	public void setItem(Performance item) {
		this.item = item;
	}

	public String getPerformanceID() {
		return performanceID;
	}

	public int[][] getSeatArr() {
		return seatArr;
	}

	public void setSeatArr(int[][] seatArr) {
		this.seatArr = seatArr;
	}

	public ArrayList<String> getSeatNum() {
		return seatNum;
	}

	public void setSeatNum(ArrayList<String> seatNum) {
		this.seatNum = seatNum;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	// 총가격계산 함수
	public void updateTotalPrice() {
		this.totalPrice = item.getTicketPrice() * quantity;
	}

	// 공연좌석갯수 감소 함수
	public void diminishQuantity() {
		item.setSoldSeats(item.getSoldSeats() - quantity);
	}

	// Overriding
	@Override
	public int hashCode() {
		return Objects.hash(performanceID);
	}

	@Override
	public boolean equals(Object obj) {
		CartItem cartItem = null;
		if (obj instanceof CartItem) {
			cartItem = (CartItem) obj;
			return performanceID.equals(cartItem.performanceID);
		}
		return false;
	}

	@Override
	public String toString() {
		return " " + performanceID + " | " + item.getName() + " | " + item.getDayOfPerformance() + " | " + seatNum
				+ " | " + quantity + "";
	}

}
