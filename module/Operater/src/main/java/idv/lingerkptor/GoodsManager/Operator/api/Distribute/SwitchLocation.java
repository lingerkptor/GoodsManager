package idv.lingerkptor.GoodsManager.Operator.api.Distribute;

import idv.lingerkptor.GoodsManager.Operator.api.Distribute.request.SwitchLocationRequest;
import idv.lingerkptor.GoodsManager.Operator.api.Distribute.response.SwitchLocationResponse;
import idv.lingerkptor.GoodsManager.core.DataAccess.LocationManager;
import idv.lingerkptor.GoodsManager.core.Distribute.DistributeException;
import idv.lingerkptor.GoodsManager.core.Distribute.Location;
import idv.lingerkptor.GoodsManager.core.annotation.ContentType;
import idv.lingerkptor.GoodsManager.core.api.Service;
import idv.lingerkptor.GoodsManager.core.api.request.Request;
import idv.lingerkptor.GoodsManager.core.api.response.Response;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/Distribute/switchLocation")
public class SwitchLocation extends Service {
    @Override
    @ContentType(reqType = ContentType.RequestType.Json, respType = ContentType.ResponseType.Json)
    public Response process(Request request) {
        SwitchLocationRequest switchLocationRequest = (SwitchLocationRequest) request;
        LocationManager location =  LocationManager.getLocationManager();
        try {
            boolean switchState = location.switchLocation(switchLocationRequest.getLocationName(),
                    switchLocationRequest.isSwitchON());
            return SwitchLocationResponse.success(switchLocationRequest.getLocationName(), switchState);
        } catch (DistributeException e) {
//            e.printStackTrace();
            System.out.println("有被捕捉到....");
            return SwitchLocationResponse.failure(switchLocationRequest.getLocationName(),
                    e.getMessage());
        }

    }

    @Override
    protected void configRequestClass() {
        this.requestClass = SwitchLocationRequest.class;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.operater(req, resp);
    }
}
