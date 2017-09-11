package org.uic.reservation.api.services;

import java.util.List;

import org.uic.reservation.api.dao.mysql.ShowsDAOImpl;
import org.uic.reservation.api.services.dataobjects.IndividualShows;
import org.uic.reservation.api.services.dataobjects.ShowSeats;
import org.uic.reservation.api.services.dataobjects.Shows;

public class ShowsService {

	public List<Shows> getAllShows() {
		ShowsDAOImpl showsDAOImpl = new ShowsDAOImpl();
		List<Shows> shows = showsDAOImpl.getAllShowDetails();
		return shows;

	}

	public IndividualShows getAllInformationOfSelectedShowId(int showId) {
		ShowsDAOImpl showsDAOImpl = new ShowsDAOImpl();
		IndividualShows individualShows = showsDAOImpl.getAllDetailsOfShow(showId);
		return individualShows;
	}

	public ShowSeats getSeatsInfoByShowTimingId(int showTimingId) {
		ShowSeats showSeats = new ShowSeats();
		ShowsDAOImpl showsDAOImpl = new ShowsDAOImpl();
		showSeats = showsDAOImpl.getShowSeatsByPTID(showTimingId);
		return showSeats;

	}

}
