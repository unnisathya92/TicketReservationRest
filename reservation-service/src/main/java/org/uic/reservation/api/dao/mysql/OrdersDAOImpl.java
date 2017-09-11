package org.uic.reservation.api.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.uic.reservation.api.services.dataobjects.OrderSummary;
import org.uic.reservation.api.services.dataobjects.OrderUpdate;
import org.uic.reservation.api.services.dataobjects.OrdersRequest;

import com.mysql.cj.api.jdbc.Statement;

public class OrdersDAOImpl {

	Database db = new Database();
	Connection connection = null;
	PreparedStatement preparedStatementOrderTable = null;
	PreparedStatement preparedStatementOrderLineTable = null;
	PreparedStatement preparedStatement = null;
	PreparedStatement preparedStatementForSeats = null;

	public boolean validateIfOrderExists(int orderId) {
		try {
			connection = db.getConnection();
			String query = "Select OID from Orders where OID=" + orderId;
			preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			int orderIdFromQuery;
			while (resultSet.next()) {
				orderIdFromQuery = Integer.parseInt(resultSet.getString("OID"));
				if (orderId == orderIdFromQuery)
					return true;
				else
					return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public int confirmOrderStatus(int orderId) {
		try {
			connection = db.getConnection();
			connection.setAutoCommit(false);
			String query = "update PerformanceSeats, OrderLine, Orders set PerformanceSeats.BookingStatus=1,OrderLine.Status=\"BOOKED\",Orders.Status=\"BOOKED\" where OrderLine.PSID=PerformanceSeats.PSID AND Orders.OID=OrderLine.OID AND Orders.OID="
					+ orderId;
			preparedStatementForSeats = connection.prepareStatement(query);
			preparedStatementForSeats.executeUpdate();
			query = "select NumOfSeatsSelected from Orders where OID=" + orderId;
			preparedStatementOrderTable = connection.prepareStatement(query);
			ResultSet rs = preparedStatementOrderTable.executeQuery();
			int count = 0;
			while (rs.next()) {
				count = Integer.parseInt(rs.getString("NumOfSeatsSelected"));
			}
			query = "update PerformanceTime,Orders set PerformanceTime.TicketsAvailable=PerformanceTime.TicketsAvailable-"
					+ count + " where Orders.PTID=PerformanceTime.PTID AND Orders.OID=" + orderId;
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.execute();
			connection.commit();
			connection.setAutoCommit(true);
			return 200;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 9000;
	}

	public int cancelOrderStatus(int orderId) {
		try {
			connection = db.getConnection();
			String query = "update PerformanceSeats, OrderLine, Orders set PerformanceSeats.BookingStatus=0,OrderLine.Status=\"CANCELLED\",Orders.Status=\"CANCELLED\" where OrderLine.PSID=PerformanceSeats.PSID AND Orders.OID=OrderLine.OID AND Orders.OID="
					+ orderId;
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.execute();
			return 200;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 9000;
	}

	public OrderSummary getOrderSummary(int orderId) {
		try {
			connection = db.getConnection();
			OrderSummary orderSummary = new OrderSummary();
			int numberOfSeatsBooked = 0;
			String query = "select UsersDB1.FirstName,UsersDB1.LastName,Performance.PerformanceName,PerformanceTime.Date,PerformanceTime.Time,screen.ScreenName,Orders.NumOfSeatsSelected from Orders inner join UsersDB1 on Orders.UUID=UsersDB1.UUID inner join PerformanceTime on Orders.PTID=PerformanceTime.PTID inner join Performance on PerformanceTime.PID=Performance.PID inner join screen on PerformanceTime.SID=screen.SID where Orders.OID="
					+ orderId;
			preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				orderSummary.setFirstName(resultSet.getString("FirstName"));
				orderSummary.setLastName(resultSet.getString("LastName"));
				orderSummary.setShowName(resultSet.getString("PerformanceName"));
				orderSummary.setShowDate(resultSet.getString("Date"));
				orderSummary.setShowTime(resultSet.getString("Time"));
				orderSummary.setScreenName(resultSet.getString("ScreenName"));
				numberOfSeatsBooked = Integer.parseInt(resultSet.getString("NumOfSeatsSelected"));
				orderSummary.setNumberOfSeatsBooked(numberOfSeatsBooked);
			}
			query = "Select PerformanceSeats.RowNumber,PerformanceSeats.SeatNumber from PerformanceSeats inner join OrderLine on OrderLine.PSID=PerformanceSeats.PSID where OrderLine.OID="
					+ orderId;
			preparedStatementForSeats = connection.prepareStatement(query);
			ResultSet resultSetSeats = preparedStatementForSeats.executeQuery();
			int i = 0;
			String[] seatNumbers = new String[numberOfSeatsBooked];
			while (resultSetSeats.next() && (i < numberOfSeatsBooked)) {
				System.out.println("Inside the second result set ");
				seatNumbers[i] = resultSetSeats.getString("RowNumber") + resultSetSeats.getString("SeatNumber");
				i++;
			}
			orderSummary.setSeatNumbers(seatNumbers);
			return orderSummary;

		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean validateIfSeatsEmpty(OrdersRequest order) {
		try {
			connection = db.getConnection();
			int firstSeatId = order.getSeatIds()[0];
			StringBuilder builder = new StringBuilder();
			builder.append(firstSeatId);
			for (int i = 1; i < order.getSeatIds().length; i++) {
				builder.append("," + order.getSeatIds()[i]);
			}
			String seatIds = builder.toString();

			String query = "Select BookingStatus from PerformanceSeats where PTID=" + order.getShowTimingId()
					+ " AND PSID IN (" + seatIds + ")";
			preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			int bookingStatus = 0;
			while (resultSet.next()) {
				bookingStatus = Integer.parseInt(resultSet.getString("BookingStatus"));
				if (bookingStatus == 1) {
					return false;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public int updateExistingOrder(OrderUpdate orderUpdate, int orderId) {
		try {
			connection = db.getConnection();
			connection.setAutoCommit(false);
			String query = "Select OLID from OrderLine where OrderLine.OID=" + orderId;
			preparedStatementOrderLineTable = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatementOrderLineTable.executeQuery();
			int i = 0;
			int orderLineId = 0;
			while (resultSet.next() && (i < orderUpdate.getSeatIds().length)) {
				orderLineId = Integer.parseInt(resultSet.getString("OLID"));
				query = "Update OrderLine set OrderLine.PSID = " + orderUpdate.getSeatIds()[i]
						+ " where OrderLine.OLID=" + orderLineId;
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.executeUpdate();
				i++;
			}
			connection.commit();
			connection.setAutoCommit(true);
			return 200;

		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 9000;

	}

	public boolean validateIfSeatsEmptyToReOrder(OrderUpdate orderUpdate, int orderId) {
		try {
			connection = db.getConnection();
			int firstSeatId = orderUpdate.getSeatIds()[0];
			StringBuilder builder = new StringBuilder();
			builder.append(firstSeatId);
			for (int i = 1; i < orderUpdate.getSeatIds().length; i++) {
				builder.append("," + orderUpdate.getSeatIds()[i]);
			}
			String seatIds = builder.toString();

			String query = "Select PTID from Orders where Orders.OID=" + orderId;
			preparedStatementOrderTable = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatementOrderTable.executeQuery();
			int showTimingId = 0;
			while (resultSet.next()) {
				showTimingId = Integer.parseInt(resultSet.getString("PTID"));
			}

			query = "Select BookingStatus from PerformanceSeats where PTID=" + showTimingId + " AND PSID IN (" + seatIds
					+ ")";
			preparedStatement = connection.prepareStatement(query);
			ResultSet resultSetBookingStatus = preparedStatement.executeQuery();
			int bookingStatus = 0;
			while (resultSetBookingStatus.next()) {
				bookingStatus = Integer.parseInt(resultSetBookingStatus.getString("BookingStatus"));
				if (bookingStatus == 1) {
					return false;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public int addNewOrder(OrdersRequest order, int numberOfSeats) {
		int orderId = -1;
		try {
			connection = db.getConnection();
			connection.setAutoCommit(false);
			/*
			 * insert into Orders (OID,UUID,Status,NumOfSeatsSelected) values
			 * (NULL,'c7c72556-24a9-4ca6-8ae7-11e2897a94ac','INITIATED',3);
			 * SET @OID:= last_insert_id(); insert into
			 * OrderLine(OLID,OID,PSID,Status) values
			 * (NULL,@OID,9003,'INITIATED'),(NULL,@OID,9004,'INITIATED');
			 */

			String query = "INSERT INTO Orders (OID,PTID,UUID,Status,NumOfSeatsSelected) VALUES (NULL,?,?,?,?)";
			preparedStatementOrderTable = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			preparedStatementOrderTable.setInt(1, order.getShowTimingId());
			preparedStatementOrderTable.setString(2, order.getUuid());
			preparedStatementOrderTable.setString(3, "INITIATED");
			preparedStatementOrderTable.setInt(4, numberOfSeats);
			preparedStatementOrderTable.execute();
			ResultSet rs = preparedStatementOrderTable.getGeneratedKeys();
			if (rs.next()) {
				orderId = rs.getInt(1);
			}
			query = "INSERT INTO OrderLine (OLID,OID,PSID,Status) VALUES (NULL,?,?,?)";
			for (int seatId : order.getSeatIds()) {
				preparedStatementOrderLineTable = connection.prepareStatement(query);
				preparedStatementOrderLineTable.setInt(1, orderId);
				preparedStatementOrderLineTable.setInt(2, seatId);
				preparedStatementOrderLineTable.setString(3, "INITIATED");
				preparedStatementOrderLineTable.execute();
			}
			connection.commit();
			connection.setAutoCommit(true);
			return orderId;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderId;

	}

}
