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
@Table(name = "hcrm_customer_contact")
public class CustomerContact extends BaseDTO {

     public static final String FIELD_CONTACT_ID = "contactId";
     public static final String FIELD_CUSTOMER_ID = "customerId";
     public static final String FIELD_CONTACT_TYPE = "contactType";
     public static final String FIELD_NAME = "name";
     public static final String FIELD_SEX = "sex";
     public static final String FIELD_DEPARTMENT = "department";
     public static final String FIELD_POSITION = "position";
     public static final String FIELD_PHONE = "phone";
     public static final String FIELD_TEL = "tel";
     public static final String FIELD_EMAIL = "email";
     public static final String FIELD_ADDRESS_COUNTRY = "addressCountry";
     public static final String FIELD_ADDRESS_PROVINCE = "addressProvince";
     public static final String FIELD_ADDRESS_CITY = "addressCity";
     public static final String FIELD_ADDRESS_ZONE = "addressZone";
     public static final String FIELD_ADDRESS_DETAILS = "addressDetails";
     public static final String FIELD_ADDRESS_ZIP_CODE = "addressZipCode";


     @Id
     @GeneratedValue
     private Long contactId; //表ID，主键，供其他表做外键

     private Long customerId; //对应客户ID

     @Length(max = 50)
     private String contactType; //联系人类型（LOOKUP表，类型代码：HCRM_CONTACTS_TYPE）

     @NotEmpty
     @Length(max = 30)
     private String name;

     @NotEmpty
     @Length(max = 50)
     private String sex;

     @Length(max = 200)
     private String department;

     @Length(max = 100)
     private String position;

     @NotEmpty
     @Length(max = 20)
     private String phone;

     @Length(max = 20)
     private String tel;

     @Length(max = 100)
     private String email;

     @Length(max = 50)
     private String addressCountry;

     @Length(max = 50)
     private String addressProvince;

     @Length(max = 50)
     private String addressCity;

     @Length(max = 50)
     private String addressZone;

     @Length(max = 1000)
     private String addressDetails;

     @Length(max = 30)
     private String addressZipCode;


     public void setContactId(Long contactId){
         this.contactId = contactId;
     }

     public Long getContactId(){
         return contactId;
     }

     public void setCustomerId(Long customerId){
         this.customerId = customerId;
     }

     public Long getCustomerId(){
         return customerId;
     }

     public void setContactType(String contactType){
         this.contactType = contactType;
     }

     public String getContactType(){
         return contactType;
     }

     public void setName(String name){
         this.name = name;
     }

     public String getName(){
         return name;
     }

     public void setSex(String sex){
         this.sex = sex;
     }

     public String getSex(){
         return sex;
     }

     public void setDepartment(String department){
         this.department = department;
     }

     public String getDepartment(){
         return department;
     }

     public void setPosition(String position){
         this.position = position;
     }

     public String getPosition(){
         return position;
     }

     public void setPhone(String phone){
         this.phone = phone;
     }

     public String getPhone(){
         return phone;
     }

     public void setTel(String tel){
         this.tel = tel;
     }

     public String getTel(){
         return tel;
     }

     public void setEmail(String email){
         this.email = email;
     }

     public String getEmail(){
         return email;
     }

     public void setAddressCountry(String addressCountry){
         this.addressCountry = addressCountry;
     }

     public String getAddressCountry(){
         return addressCountry;
     }

     public void setAddressProvince(String addressProvince){
         this.addressProvince = addressProvince;
     }

     public String getAddressProvince(){
         return addressProvince;
     }

     public void setAddressCity(String addressCity){
         this.addressCity = addressCity;
     }

     public String getAddressCity(){
         return addressCity;
     }

     public void setAddressZone(String addressZone){
         this.addressZone = addressZone;
     }

     public String getAddressZone(){
         return addressZone;
     }

     public void setAddressDetails(String addressDetails){
         this.addressDetails = addressDetails;
     }

     public String getAddressDetails(){
         return addressDetails;
     }

     public void setAddressZipCode(String addressZipCode){
         this.addressZipCode = addressZipCode;
     }

     public String getAddressZipCode(){
         return addressZipCode;
     }

     }
