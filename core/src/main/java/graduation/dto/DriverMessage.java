package graduation.dto;

/**Auto Generated By Hap Code Generator**/
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.hand.hap.core.annotation.Children;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import org.hibernate.validator.constraints.Length;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.hand.hap.system.dto.BaseDTO;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

@ExtensionAttribute(disable=true)
@Table(name = "driver_message")
public class DriverMessage extends BaseDTO {

     public static final String FIELD_MESSAGE_ID = "messageId";
    public static final String FIELD_REAL_NAME = "realName";
     public static final String FIELD_GENDER = "gender";
     public static final String FIELD_NATION = "nation";
     public static final String FIELD_ADDRESS = "address";
     public static final String FIELD_BIRTHDAY = "birthday";
     public static final String FIELD_ID_CARD = "idCard";
     public static final String FIELD_CONTACT_NAME = "contactName";
     public static final String FIELD_CONTACT_PHONE = "contactPhone";
    public static final String PHONE = "phone";
     public static final String FIELD_RELATION = "relation";
     public static final String FIELD_DRIVER_ID = "driverId";
     public static final String FIELD_MESSAGE_STATUS = "messageStatus";


     @Id
     @GeneratedValue
     private Long messageId;

     @NotEmpty
     @Length(max = 5)
     private String gender; //性别

     @NotEmpty
     @Length(max = 10)
     private String nation; //民族

     @NotEmpty
     @Length(max = 150)
     private String address; //家庭住址

     @NotEmpty
     @Length(max = 20)
     private String birthday; //出生年月日

     @NotEmpty
     @Length(max = 20)
     private String idCard; //身份证

    @NotEmpty
    @Length(max = 20)
    private String realName; //身份证

     @NotEmpty
     @Length(max = 10)
     private String contactName; //联系人姓名

     @NotEmpty
     @Length(max = 12)
     private String contactPhone; //联系人电话

    @NotEmpty
    @Length(max = 12)
    private String phone; //电话

     @NotEmpty
     @Length(max = 10)
     private String relation; //与司机的关系

     private Long driverId; //信息所属的司机

     private Long messageStatus; //是否审核通过

    @Children
    @Transient
    private List<DriverCar> carList;

    public void setMessageId(Long messageId){
         this.messageId = messageId;
     }

     public Long getMessageId(){
         return messageId;
     }

     public void setGender(String gender){
         this.gender = gender;
     }

     public String getGender(){
         return gender;
     }

     public void setNation(String nation){
         this.nation = nation;
     }

     public String getNation(){
         return nation;
     }

     public void setAddress(String address){
         this.address = address;
     }

     public String getAddress(){
         return address;
     }

     public void setBirthday(String birthday){
         this.birthday = birthday;
     }

     public String getBirthday(){
         return birthday;
     }

     public void setIdCard(String idCard){
         this.idCard = idCard;
     }

     public String getIdCard(){
         return idCard;
     }

     public void setContactName(String contactName){
         this.contactName = contactName;
     }

     public String getContactName(){
         return contactName;
     }

     public void setContactPhone(String contactPhone){
         this.contactPhone = contactPhone;
     }

     public String getContactPhone(){
         return contactPhone;
     }

     public void setRelation(String relation){
         this.relation = relation;
     }

     public String getRelation(){
         return relation;
     }

     public void setDriverId(Long driverId){
         this.driverId = driverId;
     }

     public Long getDriverId(){
         return driverId;
     }

     public void setMessageStatus(Long messageStatus){
         this.messageStatus = messageStatus;
     }

     public Long getMessageStatus(){
         return messageStatus;
     }

    public String getRealName() { return realName; }

    public void setRealName(String realName) { this.realName = realName; }

    public List<DriverCar> getCarList() {
        return carList;
    }

    public void setCarList(List<DriverCar> carList) {
        this.carList = carList;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}