package org.uic.reservation.api.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.uic.reservation.api.services.ShowsService;
import org.uic.reservation.api.services.dataobjects.ApiError;
import org.uic.reservation.api.services.dataobjects.IndividualShows;
import org.uic.reservation.api.services.dataobjects.ShowSeats;
import org.uic.reservation.api.services.dataobjects.Shows;
import org.uic.reservation.api.services.dataobjects.ShowsAvailable;

@Path("/shows")
public class ShowsResource {

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response showsDetails() {
		ShowsService showsService = new ShowsService();
		ApiError apiError = new ApiError();
		List<Shows> shows = showsService.getAllShows();
		if (shows != null) {
			ShowsAvailable showsAvailable = new ShowsAvailable(shows);
			return Response.status(Response.Status.OK).entity(showsAvailable).build();
		} else {
			apiError.setErrorCode(9000);
			apiError.setErrorMessage("Something went wrong with the server. Please try again later!");
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(apiError).build();
		}
	}

	@GET
	@Path("/{showId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response individualShowInformaiton(@PathParam("showId") int showId) {

		/*
		 * List<Screens> screens = new ArrayList<Screens>(); List<ShowTimings>
		 * showTimings1 = new ArrayList<ShowTimings>(); showTimings1.add(new
		 * ShowTimings("8.00 A.M.", 41)); showTimings1.add(new
		 * ShowTimings("10.00 A.M.", 45)); screens.add(new Screens(34, "Plush",
		 * showTimings1)); List<ShowTimings> showTimings2 = new
		 * ArrayList<ShowTimings>(); showTimings2.add(new
		 * ShowTimings("6.00 A.M.", 33)); showTimings2.add(new
		 * ShowTimings("11.00 A.M.", 66)); screens.add(new Screens(27, "Evam",
		 * showTimings2)); List<ShowDetails> showDetails = new
		 * ArrayList<ShowDetails>(); showDetails.add(new
		 * ShowDetails("11/Feb/2017", screens)); IndividualShows individualShows
		 * = new IndividualShows(); individualShows.setShowId(123);
		 * individualShows.setShowName("InfoInfraPerformance");
		 * individualShows.setShowDescription("This is magic");
		 * individualShows.setShowDetails(showDetails);
		 */

		ShowsService showsService = new ShowsService();
		ApiError apiError = new ApiError();
		IndividualShows individualShows = showsService.getAllInformationOfSelectedShowId(showId);
		if (individualShows != null) {
			return Response.status(Response.Status.OK).entity(individualShows).build();
		} else {
			apiError.setErrorCode(9000);
			apiError.setErrorMessage("Something went wrong with the server. Please try again later!");
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(apiError).build();
		}

	}

	@GET
	@Path("/timings/{showTimingId}/seats")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response availableSeatNumbers(@PathParam("showTimingId") int showTimingId) {

		ShowsService showsService = new ShowsService();
		ApiError apiError = new ApiError();
		ShowSeats showSeats = showsService.getSeatsInfoByShowTimingId(showTimingId);
		if (showSeats != null) {
			return Response.status(Response.Status.OK).entity(showSeats).build();
		} else {
			apiError.setErrorCode(9000);
			apiError.setErrorMessage("Something went wrong with the server. Please try again later!");
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(apiError).build();
		}

	}
}
