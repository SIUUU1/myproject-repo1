package project.performance;
import java.io.Serializable;
import java.util.Objects;
//프로젝트명 : Ticket World
//클레스 역할 : 공연과 관련된 정보를 관리하는 기능을 처리하는 클래스
//제작자 : 안시우, 제작일 : 24년 4월 16일
public class Performance implements Comparable<Performance>, Serializable {
	// memberVariable
	public static final int ROW_NUM = 5; // 행
	public static final int COLUMN_NUM = 20; // 열
	private String performanceID;// 공연ID
	private String name; // 공연명
	private String genre; // 장르(뮤지컬, 연극, 콘서트)
	private String dayOfPerformance; // 공연일
	private String venue; // 장소
	private int limitAge; // 관람제한연령
	private int[][] seatArr; // 공연장 좌석
	private int totalSeats; // 총좌석수
	private int soldSeats; // 판매된좌석수
	private int ticketPrice; // 티켓가격,compare

	// constructor
	public Performance() {
		super();
		InitPerformance();
	}

	public Performance(String performanceID, String name, String genre, String dayOfPerformance, String venue,
			int limitAge, int totalSeats, int ticketPrice) {
		super();
		this.performanceID = performanceID;
		this.name = name;
		this.genre = genre;
		this.dayOfPerformance = dayOfPerformance;
		this.venue = venue;
		this.limitAge = limitAge;
		this.totalSeats = totalSeats;
		this.ticketPrice = ticketPrice;
		InitPerformance();
	}

	private void InitPerformance() {
		this.soldSeats = 0;
		seatArr = new int[ROW_NUM][COLUMN_NUM];
		for (int i = 0; i < seatArr.length; i++) {
			for (int j = 0; j < seatArr[i].length; j++) {
				seatArr[i][j] = 0;
			}
		}
	}

	// memberFunction
	public String getPerformanceID() {
		return performanceID;
	}

	public void setPerformanceID(String performanceID) {
		this.performanceID = performanceID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getDayOfPerformance() {
		return dayOfPerformance;
	}

	public void setDayOfPerformance(String dayOfPerformance) {
		this.dayOfPerformance = dayOfPerformance;
	}

	public String getVenue() {
		return venue;
	}

	public void setVenue(String venue) {
		this.venue = venue;
	}

	public int getLimitAge() {
		return limitAge;
	}

	public void setLimitAge(int limitAge) {
		this.limitAge = limitAge;
	}

	public int[][] getSeatArr() {
		return seatArr;
	}

	public void setSeatArr(int[][] seatArr) {
		this.seatArr = seatArr;
	}

	public int getTotalSeats() {
		return totalSeats;
	}

	public void setTotalSeats(int totalSeats) {
		this.totalSeats = totalSeats;
	}

	public int getSoldSeats() {
		return soldSeats;
	}

	public void setSoldSeats(int soldSeats) {
		this.soldSeats = soldSeats;
	}

	public int getTicketPrice() {
		return ticketPrice;
	}

	public void setTicketPrice(int ticketPrice) {
		this.ticketPrice = ticketPrice;
	}

	// 남은좌석계산함수
	public int calcRemainingSeat() {
		return totalSeats - soldSeats;
	}

	// Overriding
	@Override
	public int compareTo(Performance o) {
		return ticketPrice - o.ticketPrice;
	}

	@Override
	public int hashCode() {
		return Objects.hash(performanceID);
	}

	@Override
	public boolean equals(Object obj) {
		Performance performance = null;
		if (obj instanceof Performance) {
			performance = (Performance) obj;
			return performanceID.equals(performance.performanceID);
		}
		return false;
	}

	@Override
	public String toString() {
		return " " + performanceID + "|" + name + "|" + genre + "|" + dayOfPerformance + "|" + venue + "|" + limitAge
				+ "|" + (totalSeats - soldSeats) + "/" + totalSeats + "|" + ticketPrice + "";
	}

}
