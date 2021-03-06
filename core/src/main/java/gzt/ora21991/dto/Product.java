package gzt.ora21991.dto;

/**Auto Generated By Hap Code Generator**/
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import org.hibernate.validator.constraints.Length;
import javax.persistence.Table;
import com.hand.hap.system.dto.BaseDTO;
import org.hibernate.validator.constraints.NotEmpty;
@ExtensionAttribute(disable=true)
@Table(name = "hcrm_product")
public class Product extends BaseDTO {

     public static final String FIELD_PRODUCT_ID = "productId";
     public static final String FIELD_CUSTOMER_ID = "customerId";
     public static final String FIELD_PRODUCT_TYPE = "productType";
     public static final String FIELD_LEVEL_FIRST = "levelFirst";
     public static final String FIELD_LEVEL_SECOND = "levelSecond";
     public static final String FIELD_MAN_DAY = "manDay";
     public static final String FIELD_PRICE = "price";
     public static final String FIELD_TOTAL_PRICE = "totalPrice";
     public static final String FIELD_PRODUCT_ADV_DESC = "productAdvDesc";
     public static final String FIELD_PRODUCT_OTHER_DESC = "productOtherDesc";


     @Id
     @GeneratedValue
     private Long productId;

     private Long customerId;

     @NotEmpty
     @Length(max = 30)
     private String productType;

     @Length(max = 100)
     private String levelFirst;

     @Length(max = 100)
     private String levelSecond;

     private Long manDay;

     private Long price;

     private Long totalPrice;

     @Length(max = 300)
     private String productAdvDesc;

     @Length(max = 300)
     private String productOtherDesc;


     public void setProductId(Long productId){
         this.productId = productId;
     }

     public Long getProductId(){
         return productId;
     }

     public void setCustomerId(Long customerId){
         this.customerId = customerId;
     }

     public Long getCustomerId(){
         return customerId;
     }

     public void setProductType(String productType){
         this.productType = productType;
     }

     public String getProductType(){
         return productType;
     }

     public void setLevelFirst(String levelFirst){
         this.levelFirst = levelFirst;
     }

     public String getLevelFirst(){
         return levelFirst;
     }

     public void setLevelSecond(String levelSecond){
         this.levelSecond = levelSecond;
     }

     public String getLevelSecond(){
         return levelSecond;
     }

     public void setManDay(Long manDay){
         this.manDay = manDay;
     }

     public Long getManDay(){
         return manDay;
     }

     public void setPrice(Long price){
         this.price = price;
     }

     public Long getPrice(){
         return price;
     }

     public void setTotalPrice(Long totalPrice){
         this.totalPrice = totalPrice;
     }

     public Long getTotalPrice(){
         return totalPrice;
     }

     public void setProductAdvDesc(String productAdvDesc){
         this.productAdvDesc = productAdvDesc;
     }

     public String getProductAdvDesc(){
         return productAdvDesc;
     }

     public void setProductOtherDesc(String productOtherDesc){
         this.productOtherDesc = productOtherDesc;
     }

     public String getProductOtherDesc(){
         return productOtherDesc;
     }

     }
