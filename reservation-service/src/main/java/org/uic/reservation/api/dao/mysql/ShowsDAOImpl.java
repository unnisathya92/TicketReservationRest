package org.uic.reservation.api.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.uic.reservation.api.services.dataobjects.IndividualShows;
import org.uic.reservation.api.services.dataobjects.Screens;
import org.uic.reservation.api.services.dataobjects.Seats;
import org.uic.reservation.api.services.dataobjects.ShowDetails;
import org.uic.reservation.api.services.dataobjects.ShowSeats;
import org.uic.reservation.api.services.dataobjects.ShowTimings;
import org.uic.reservation.api.services.dataobjects.Shows;

public class ShowsDAOImpl {

	Database db = new Database();
	Connection connection = null;
	PreparedStatement preparedStatement = null;

	public ShowSeats getShowSeatsByPTID(int showTimingId) {
		try {
			connection = db.getConnection();
			String query = "SELECT * FROM PerformanceSeats WHERE PTID like '" + showTimingId + "'";
			preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			int seatId, columnNumber, bookingStatus;
			String rowNumber;
			List<Seats> seats = new ArrayList<Seats>();
			while (resultSet.next()) {
				seatId = Integer.parseInt(resultSet.getString("PSID"));
				rowNumber = resultSet.getString("RowNumber");
				columnNumber = Integer.parseInt(resultSet.getString("SeatNumber"));

				bookingStatus = Integer.parseInt(resultSet.getString("BookingStatus"));
				seats.add(new Seats(seatId, rowNumber, columnNumber, bookingStatus));
			}
			ShowSeats showSeats = new ShowSeats(showTimingId, seats);
			return showSeats;
		} catch (SQLException sqlEx) {
			sqlEx.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public IndividualShows getAllDetailsOfShow(int showId) {

		try {
			connection = db.getConnection();
			String query = "Select PerformanceTime.PID,Performance.PerformanceName,Performance.PerformanceDescription,PerformanceTime.Date,PerformanceTime.SID,screen.ScreenName,PerformanceTime.PTID,PerformanceTime.Time,PerformanceTime.TicketsAvailable from PerformanceTime inner join screen on PerformanceTime.SID=screen.SID inner join Performance on PerformanceTime.PID=Performance.PID where PerformanceTime.PID="
					+ showId;
			preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			/*
			 * Set<Screens> screens = new HashSet<Screens>(); Set<ShowTimings>
			 * showTimings = new HashSet<ShowTimings>(); Set<ShowDetails>
			 * showDetails = new HashSet<ShowDetails>(); int showTimingId = 0;
			 * int ticketsAvailable; int screenId = 0; String performanceName =
			 * null; String performanceDescription = null;
			 */
			/*
			 * while (resultSet.next()) { showTimingId =
			 * Integer.parseInt(resultSet.getString("PTID")); ticketsAvailable =
			 * Integer.parseInt(resultSet.getString("TicketsAvailable"));
			 * showTimings.add(new ShowTimings(showTimingId,
			 * resultSet.getString("Time"), ticketsAvailable)); screenId =
			 * Integer.parseInt(resultSet.getString("SID")); screens.add(new
			 * Screens(screenId, resultSet.getString("ScreenName"), new
			 * ArrayList<ShowTimings>(showTimings))); showDetails.add(new
			 * ShowDetails(resultSet.getString("Date"), new
			 * ArrayList<Screens>(screens))); performanceName =
			 * resultSet.getString("PerformanceName"); performanceDescription =
			 * resultSet.getString("PerformanceDescription"); } //map of
			 * showTimings with key showTimingId - value is showTiming object
			 * //map of screenId and screen object
			 * 
			 * 
			 * IndividualShows individualShows = new IndividualShows(showId,
			 * performanceName, performanceDescription, new
			 * ArrayList<ShowDetails>(showDetails));
			 */

			/*
			 * Map<String, Map<Integer, Map<Integer, ShowTimings>>>
			 * showDateToShowDetailMap = new HashMap<String, Map<Integer,
			 * Map<Integer, ShowTimings>>>(); while (resultSet.next()) {
			 * showTimingId = Integer.parseInt(resultSet.getString("PTID"));
			 * ShowTimings showTimings1 = new
			 * ShowTimings(Integer.parseInt(resultSet.getString("PTID")),
			 * resultSet.getString("Time"),
			 * Integer.parseInt(resultSet.getString("TicketsAvailable")));
			 * screenId = Integer.parseInt(resultSet.getString("SID"));
			 * Map<Integer, Map<Integer, ShowTimings>> sreenIdToScreenMap =
			 * null; if
			 * (showDateToShowDetailMap.containsKey(resultSet.getString("Date"))
			 * ) { sreenIdToScreenMap =
			 * showDateToShowDetailMap.get(resultSet.getString("Date"));
			 * Map<Integer, ShowTimings> showTimeIdToshowTimeMap = null;
			 * 
			 * if (sreenIdToScreenMap.containsKey(screenId)) {
			 * showTimeIdToshowTimeMap = sreenIdToScreenMap.get(screenId); }
			 * else { sreenIdToScreenMap.put(screenId, new HashMap<Integer,
			 * ShowTimings>()); }
			 * sreenIdToScreenMap.get(screenId).put(showTimingId, showTimings1);
			 * } else { showDateToShowDetailMap.put(resultSet.getString("Date"),
			 * new HashMap<Integer, Map<Integer, ShowTimings>>());
			 * showDateToShowDetailMap.get(resultSet.getString("Date")).put(
			 * screenId, new HashMap<Integer, ShowTimings>());
			 * showDateToShowDetailMap.get(resultSet.getString("Date")).get(
			 * screenId).put(showTimingId, showTimings1); } }
			 * System.out.println(showDateToShowDetailMap); Gson gson = new
			 * Gson(); String indShows = gson.toJson(showDateToShowDetailMap);
			 * System.out.println(indShows);
			 * 
			 * IndividualShows individualShows = new IndividualShows(showId,
			 * performanceName, performanceDescription, new
			 * ArrayList<ShowDetails>(showDetails));
			 */
			IndividualShows individualShows = new IndividualShows();
			if (showId == 10) {
				List<Screens> screens = new ArrayList<Screens>();
				List<ShowTimings> showTimings1 = new ArrayList<ShowTimings>();
				showTimings1.add(new ShowTimings(753, "4:00:00", 29));
				showTimings1.add(new ShowTimings(754, "8:00:00", 40));
				screens.add(new Screens(1, "Plush", showTimings1));
				List<ShowDetails> showDetails = new ArrayList<ShowDetails>();
				showDetails.add(new ShowDetails("2017-02-12", screens));

				individualShows.setShowId(10);
				individualShows.setShowName("MadMax");
				individualShows.setShowDescription("This is MadMax movie");
				individualShows.setShowDetails(showDetails);
			} else {
				List<Screens> screens1 = new ArrayList<Screens>();
				List<Screens> screens2 = new ArrayList<Screens>();
				List<ShowTimings> showTimings1 = new ArrayList<ShowTimings>();
				showTimings1.add(new ShowTimings(755, "3:00:00", 30));
				screens1.add(new Screens(2, "Evam", showTimings1));
				List<ShowTimings> showTimings2 = new ArrayList<ShowTimings>();
				showTimings2.add(new ShowTimings(756, "3:00:00", 32));
				screens2.add(new Screens(1, "Plush", showTimings2));
				List<ShowDetails> showDetails = new ArrayList<ShowDetails>();
				showDetails.add(new ShowDetails("2017-02-12", screens1));
				showDetails.add(new ShowDetails("2017-02-13", screens2));

				individualShows.setShowId(11);
				individualShows.setShowName("Singam");
				individualShows.setShowDescription("This is Singam movie");
				individualShows.setShowDetails(showDetails);
			}

			return individualShows;
		} catch (SQLException sqlEx) {
			sqlEx.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public List<Shows> getAllShowDetails() {
		try {
			connection = db.getConnection();
			String query = "SELECT * FROM Performance";
			preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			int showId;
			String showName, showDescription;
			List<Shows> shows = new ArrayList<Shows>();
			while (resultSet.next()) {
				showId = Integer.parseInt(resultSet.getString("PID"));
				showName = resultSet.getString("PerformanceName");
				showDescription = resultSet.getString("PerformanceDescription");
				shows.add(new Shows(showId, showName, showDescription));
			}
			return shows;
		} catch (SQLException sqlEx) {
			sqlEx.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
