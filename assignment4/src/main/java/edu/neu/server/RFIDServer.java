package edu.neu.server;

import java.sql.SQLException;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import edu.neu.server.dao.SkierDataDao;
import edu.neu.server.model.SkierData;
import edu.neu.server.model.SkierMetrics;

/**
 * Root resource (exposed at "/" path)
 */
@Path("myresource")
public class RFIDServer {
    private static final SkierDataDao skierDataDao = SkierDataDao.getInstance();
    private static final int[] VERTICAL_VALUES = {200,300,400,500};
    private static CopyOnWriteArrayList<SkierMetrics> postMetrics = new CopyOnWriteArrayList<>();
    private static CopyOnWriteArrayList<SkierMetrics> getMetrics = new CopyOnWriteArrayList<>();

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "APPLICATION_JSON" media type.
     *
     * @return SkierData object that will be returned as a APPLICATION_JSON response.
     */
    @Path("myvert/{skierId}/{dayNum}")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
    public String getData(@PathParam("skierId") int skierID, @PathParam("dayNum") int dayNum) throws SQLException {
        long startTime = System.currentTimeMillis();
        String output = skierDataDao.getDataBySkierIdAndDay(skierID, dayNum);
        long endTime = System.currentTimeMillis();
        getMetrics.add(new SkierMetrics(startTime, endTime - startTime));
        return output;
    }
    @Path("load")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void postData(SkierData data) throws SQLException {
        long startTime = System.currentTimeMillis();
        int verticalMetres = calculateVertical(data.getLiftID());
        skierDataDao.insert(data, verticalMetres);
        long endTime = System.currentTimeMillis();
        postMetrics.add(new SkierMetrics(startTime, endTime - startTime));
    }

    private int calculateVertical(int liftID) {
        int verticalIndex = (liftID - 1) / 10;
        return VERTICAL_VALUES[verticalIndex];
    }

    @Path("/test")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String test() {
        return "Working";
    }

    @Path("metrics/{requestType}")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
    public String getMetrics(@PathParam("requestType") String requestType) {
        if(requestType.equals("POST")) {
            String result = postMetrics.toString();
            postMetrics.clear();
            return result;
        } else {
            String result = getMetrics.toString();
            getMetrics.clear();
            return result;
        }
    }
}