package idv.lingerkptor.GoodsManager.Operator.api.Distribute;

import idv.lingerkptor.GoodsManager.Operator.api.Distribute.request.GetLocationListRequest;
import idv.lingerkptor.GoodsManager.Operator.api.Distribute.response.GetLocationListResponse;
import idv.lingerkptor.GoodsManager.core.DataAccess.LocationManager;
import idv.lingerkptor.GoodsManager.core.annotation.ContentType;
import idv.lingerkptor.GoodsManager.core.api.Service;
import idv.lingerkptor.GoodsManager.core.api.request.Request;
import idv.lingerkptor.GoodsManager.core.api.response.Response;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@WebServlet("/api/Distribute/getLocationList")
public class GetLocationList extends Service {
    @Override
    @ContentType(reqType = ContentType.RequestType.Json, respType = ContentType.ResponseType.Json)
    public Response process(Request request) {
        Set<String> locationNames = LocationManager.getLocationManager().getLocationNames();
        Set<Object> locationObjSet = new HashSet<>();

        for (String locationName : locationNames) { //[{"locationName":String,"locationState":boolean}]
            locationObjSet.add(GetLocationListResponse.
                    getLocationResponseObj(locationName, LocationManager.getLocationManager().getLocationState(locationName)));
        }
//        locationObjSet.add(GetLocationListResponse.getLocationResponseObj("locationName", true)); // test
//        locationObjSet.add(GetLocationListResponse.getLocationResponseObj("locationName1", false)); // test

        return GetLocationListResponse.success(locationObjSet.toArray());
    }

    @Override
    protected void configRequestClass() {
        requestClass = GetLocationListRequest.class;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        this.operater(req, resp);
    }
}
