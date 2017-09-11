package org.uic.reservation.api.services.dataobjects;

public class Seats {
	private int seatId;
	private String rowNumber;
	private int columnNumber;
	private int status;
	
	public Seats(){
		
	}
	
	public Seats(int seatId, String rowNumber, int columnNumber, int status) {
		super();
		this.seatId = seatId;
		this.rowNumber = rowNumber;
		this.columnNumber = columnNumber;
		this.status = status;
	}

	public int getSeatId() {
		return seatId;
	}

	public void setSeatId(int seatId) {
		this.seatId = seatId;
	}

	public String getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(String rowNumber) {
		this.rowNumber = rowNumber;
	}

	public int getColumnNumber() {
		return columnNumber;
	}

	public void setColumnNumber(int columnNumber) {
		this.columnNumber = columnNumber;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	

}
