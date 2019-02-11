package graduation.user.dto;

/**Auto Generated By Hap Code Generator**/
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import org.hibernate.validator.constraints.Length;
import javax.persistence.Table;
import com.hand.hap.system.dto.BaseDTO;
import org.hibernate.validator.constraints.NotEmpty;
@ExtensionAttribute(disable=true)
@Table(name = "login_user")
public class User2 {

     public static final String FIELD_USER_ID = "userId";
     public static final String FIELD_USER_NAME = "userName";
     public static final String FIELD_REAL_NAME = "realName";
     public static final String FIELD_PASSWORD = "password";
     public static final String FIELD_PHONE = "phone";

     @Id
     @GeneratedValue
     private Long userId;

     @NotEmpty
     @Length(max = 40)
     private String userName; //账号

     @Length(max = 40)
     private String realName; //姓名

     @NotEmpty
     @Length(max = 80)
     private String password; //密码

     @NotEmpty
     @Length(max = 40)
     private String phone; //电话号码


     public void setUserId(Long userId){
         this.userId = userId;
     }

     public Long getUserId(){
         return userId;
     }

     public void setUserName(String userName){
         this.userName = userName;
     }

     public String getUserName(){
         return userName;
     }

     public void setRealName(String realName){
         this.realName = realName;
     }

     public String getRealName(){
         return realName;
     }

     public void setPassword(String password){
         this.password = password;
     }

     public String getPassword(){
         return password;
     }

     public void setPhone(String phone){
         this.phone = phone;
     }

     public String getPhone(){
         return phone;
     }

     }
