/*
 **author:lyf
 */


package csuduc.platform.util.lyf;

import java.io.File;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import javax.measure.unit.SI;
import org.geotools.data.FeatureWriter;
import org.geotools.data.Transaction;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.shapefile.ShapefileDataStoreFactory;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.geojson.geom.GeometryJSON;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.geom.MultiPoint;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class lyfGis {
	 public static Map geojson2Shape(String GeoJsonstr, String shpPath){  
	        Map<String,String> map = new HashMap<String,String>();  
	        GeometryJSON gjson = new GeometryJSON();  
	        try{  
	            String strJson = GeoJsonstr;  
	            JSONObject json = JSONObject.fromObject(strJson);  
	            JSONArray features = (JSONArray) json.get("features");  
	            JSONObject feature0 = JSONObject.fromObject(features.get(0).toString());  
	            System.out.println(feature0.toString());  
	            String strType = ((JSONObject)feature0.get("geometry")).getString("type").toString();  
	              
	            Class<?> geoType = null;  
	            switch(strType){  
	                case "Point":  
	                    geoType = Point.class;  
	                case "MultiPoint":  
	                    geoType = MultiPoint.class;  
	                case "LineString":  
	                    geoType = LineString.class;  
	                case "MultiLineString":  
	                    geoType = MultiLineString.class;  
	                case "Polygon":  
	                    geoType = Polygon.class;  
	                case "MultiPolygon":  
	                    geoType = MultiPolygon.class;  
	            }  
	            //创建shape文件对象  
	            File file = new File(shpPath);  
	            Map<String, Serializable> params = new HashMap<String, Serializable>();  
	            params.put(ShapefileDataStoreFactory.URLP.key, file.toURI().toURL());  
	            ShapefileDataStore ds = (ShapefileDataStore) new ShapefileDataStoreFactory().createNewDataStore(params);  
	            //定义图形信息和属性信息  
	            SimpleFeatureTypeBuilder tb = new SimpleFeatureTypeBuilder();  
	            tb.setCRS(DefaultGeographicCRS.WGS84);  
	            tb.setName("shapefile");  
	            tb.add("the_geom", geoType);  
	            tb.add("POIID", Long.class);  
	            ds.createSchema(tb.buildFeatureType());  
	            //设置编码  
	            Charset charset = Charset.forName("GBK");  
	            ds.setCharset(charset);  
	            //设置Writer  
	            FeatureWriter<SimpleFeatureType, SimpleFeature> writer = ds.getFeatureWriter(ds.getTypeNames()[0], Transaction.AUTO_COMMIT);  
	              
	            for(int i=0,len=features.size();i<len;i++){  
	                String strFeature = features.get(i).toString();  
	                Reader reader = new StringReader(strFeature);  
	                SimpleFeature feature = writer.next();  
	                feature.setAttribute("the_geom",gjson.readMultiPolygon(reader));  
	                feature.setAttribute("POIID",i);  
	                writer.write();  
	            }  
	            writer.close();  
	            ds.dispose();  
	            map.put("status", "success");  
	            map.put("message", shpPath);  
	        }  
	        catch(Exception e){  
	            map.put("status", "failure");  
	            map.put("message", e.getMessage());  
	            e.printStackTrace();  
	        }  
	        return map;  
	    } 
}
