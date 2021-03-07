package idv.lingerkptor.GoodsManager.core.Listener;

import idv.lingerkptor.GoodsManager.core.DataAccess.LocationManager;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class LocationManagerInit implements ServletContextListener {


    /**
     * 載入設定檔
     *
     * @param sce
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LocationManager manager = LocationManager.getLocationManager();
        manager.init();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        LocationManager manager = LocationManager.getLocationManager();
        manager.close();
    }
}
