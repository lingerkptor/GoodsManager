package idv.lingerkptor.GoodsManager.core.annotation;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;

import idv.lingerkptor.GoodsManager.core.Analyzable;
import idv.lingerkptor.GoodsManager.core.AnalyzeJson;
import idv.lingerkptor.GoodsManager.core.AnalyzeMultiPart;
import idv.lingerkptor.GoodsManager.core.SendJson;
import idv.lingerkptor.GoodsManager.core.Sendable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;

/**
 * 請求型態 ex: application/json; http/text
 *
 * @author lingerkptor
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface ContentType {
    public enum RequestType {
        // 在這裡註冊
        Json("application/json", AnalyzeJson.class),
    	MultiPart("multipart/form-data",AnalyzeMultiPart.class);

        /**
         * 請求的關鍵字
         */
        private String key = null;
        /**
         * 對應的分析法
         */
        private Class<? extends Analyzable> analyzeObj = null;

        RequestType(String key, Class<? extends Analyzable> analyzable) {
            this.key = key;
            this.analyzeObj = analyzable;
        }

        public String getKey() {
            return key;
        }

//        /**
//         * 因為java泛型是Type Erasure，
//         * 在編譯後都會變成Object．所以沒辦法在這裡使用
//         * 未來有機會看有沒有機會修正，讓他能夠更有彈性
//         *　<a href="https://docs.oracle.com/javase/tutorial/java/generics/erasure.html">Type Erasure</a>
//         * @return
//         */

        /**
         * 建立請求的分析方法
         *
         * @param <T> 請求的主體類別
         * @return 請求內容的分析法
         */
        public  Analyzable factory() {
            try {
                return analyzeObj.getConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException | NoSuchMethodException | SecurityException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    ;

    public enum ResponceType {
    	// 在這裡註冊
        Json("application/json", SendJson.class);


        private String key = null;

        private Class<? extends Sendable> sendObj = null;

        ResponceType(String key, Class<? extends Sendable> sendObj) {
            this.key = key;
            this.sendObj = sendObj;
        }

        public String getKey() {
            return key;
        }

        public  Sendable factory() {
            try {
                return sendObj.getConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException | NoSuchMethodException | SecurityException e) {
                e.printStackTrace();
            }
            return null;

        }
    }

    public RequestType reqType();

    public ResponceType respType();

}
