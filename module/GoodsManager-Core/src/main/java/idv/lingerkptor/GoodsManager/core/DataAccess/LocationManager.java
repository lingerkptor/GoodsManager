package idv.lingerkptor.GoodsManager.core.DataAccess;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import idv.lingerkptor.GoodsManager.core.Distribute.DistributeException;
import idv.lingerkptor.GoodsManager.core.Distribute.Location;
import idv.lingerkptor.GoodsManager.core.Distribute.LocationConfig;

import javax.sql.rowset.serial.SQLOutputImpl;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

public class LocationManager {

    private static LocationManager manager = null;
    /**
     * 發佈地清單
     */
    private Map<String, Location> locationMap = new HashMap<String, Location>();
    /**
     * 發佈地設定清單
     */
    private List<LocationConfig> locationConfigList;
    /**
     * Location 清單檔
     */
    private File LocationListFile;
    private String locationLibPath;

    /**
     * Location Lib 路徑
     */
//    private File locationLibPath;
    public static LocationManager getLocationManager() {
        if (manager == null) {
            manager = new LocationManager();
            manager.LocationListFile = new File(ConfigReader.getConfigReader().getLocationListFileName());
            manager.locationLibPath = ConfigReader.getConfigReader().getLocationLibPath();
        }
        return manager;
    }

    /**
     * 初始化LocationListFile
     */
    public void init() {
        if (!this.LocationListFile.exists()) {
            createLocationListFile();
        }
        this.roadLocationConfigFile();

        for (LocationConfig config : this.locationConfigList) {
            if (!config.isStart())
                continue;
            Optional<Location> location = this.getLocationInstance(config);
            if (!location.isEmpty())
                this.registerLocation(location.get());
            else
                new DistributeException("Location　建立失敗.");
            
        }
    }


    /**
     * 建立空的LocationListFile
     * 檔案結構：LocationConfig 陣列 (Json方式儲存)
     *
     * @return 是否建立成功
     */
    private boolean createLocationListFile() {
        boolean result = false;
        FileWriter writer = null;
        try {
            this.LocationListFile.createNewFile();
            writer = new FileWriter(this.LocationListFile);
            writer.write("[]");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null)
                try {
                    writer.close();
                    result = true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return result;
    }

    /**
     * 載入LocationConfigFile
     *
     * @return 載入是否成功
     */
    private boolean roadLocationConfigFile() {
        boolean result = false;
        Gson gson = new Gson();
        FileReader reader = null;
        try {
            reader = new FileReader(this.LocationListFile);
            Type type = new TypeToken<ArrayList<LocationConfig>>() {
            }.getType();
            locationConfigList = gson.fromJson(reader, type);
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null)
                try {
                    reader.close();
                    result = true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return result;
    }

    /**
     * 依照設定建立一個Location實體物件 <br/>
     * 用途: Location Manager初始化時，將Locaiton List內的Location建立實體物件
     * @param config Location 設定
     * @return　Location 實體物件
     */
    private Optional<Location> getLocationInstance(LocationConfig config) {
        if (config != null)
            try {
                URL[] urls = new URL[]{new URL("jar:file:" + this.locationLibPath + "//" + config.getLocationLib() + " !/ ")};

                URLClassLoader classLoader = new URLClassLoader(urls, this.getClass().getClassLoader());
                try {
                    Location location = (Location) Class.forName(config.getLocationClassName(), true, classLoader)
                            .getConstructor(LocationConfig.class)
                            .newInstance(config);
                    return Optional.<Location>of(location);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        return Optional.<Location>empty();
    }

    /**
     * 註冊發佈地
     *
     * @param location location
     */
    private void registerLocation(Location location) {
        this.locationMap.put(location.getName(), location);
    }

    /**
     * Location 啟動或關閉
     *
     * @param locationName
     * @return
     */
    public boolean switchLocation(String locationName) {
        try {
            Optional<Location> location = this.getLocation(locationName);
            LocationConfig locationConfig = null;
            // 啟動或關閉Location，儲存設定
            for (LocationConfig config : this.locationConfigList) {
                if (config.getLocationName().equals(locationName)) {
                    locationConfig = config;
                    config.locationSwitch();
                    this.saveLocationConfig();
                }
            }

            if (location.isEmpty()) {
                if (locationConfig == null)
                    throw new DistributeException("locationConfig不存在無法建立");

                Optional<Location> newLocation = this.getLocationInstance(locationConfig);
                if (newLocation.isEmpty())
                    throw new DistributeException("location建立失敗");
                this.registerLocation(newLocation.get());

            } else {
                synchronized (locationMap) {
                    location.get().close();
                    this.locationMap.remove(locationName);
                }
            }
        } catch (DistributeException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 取得發佈地
     *
     * @param name 發佈地名稱
     * @return 發佈地
     */
    public Optional<Location> getLocation(String name) throws DistributeException {
        synchronized (locationMap) {
            try {
                Location location = locationMap.get(name);
                return Optional.<Location>of(location);
            } catch (NullPointerException e) {
                return Optional.<Location>empty();
            }
        }
    }

    /**
     * 取得發佈地名稱清單
     *
     * @return 發佈地名稱清單
     */
    public Set<String> getLocationNames() {
        return this.locationMap.keySet();
    }

    /**
     * 加入發佈地設定
     *
     * @param jsonString Json物件
     *                   {
     *                   locationName:String ,
     *                   locationLib:String ,
     *                   locationClassName:String ,
     *                   locationPara:Map<String,String>
     *                   }
     * @return 是否加入成功
     */
    public boolean addConfig(String jsonString) {
        boolean result = false;
        try {
            Gson gson = new Gson();
            LocationConfig config = gson.fromJson(jsonString, LocationConfig.class);
            this.locationConfigList.add(config);
            result = true;
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 儲存 Location 設定
     *
     * @return 儲存是否成功
     */
    private boolean saveLocationConfig() {
        boolean result = false;
        Gson gson = new Gson();
        FileWriter writer = null;
        try {
            writer = new FileWriter(this.LocationListFile);
            gson.toJson(this.locationConfigList, writer);
            writer.flush();
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
            result = false;
        } finally {
            if (writer != null)
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return result;
    }


    /**
     * 關閉LocationManager
     *
     * @return 關閉是否成功
     */
    public boolean close() {
        for (Location location : this.locationMap.values()) {
            location.close();
        }
        return this.saveLocationConfig();

    }
}
