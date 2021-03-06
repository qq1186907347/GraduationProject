package graduation.dto;

/**Auto Generated By Hap Code Generator**/
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import org.hibernate.validator.constraints.Length;
import javax.persistence.Table;
import com.hand.hap.system.dto.BaseDTO;
import java.util.Date;
import org.hibernate.validator.constraints.NotEmpty;
@ExtensionAttribute(disable=true)
@Table(name = "refuse_result")
public class RefuseResult extends BaseDTO {

     public static final String FIELD_RESULT_ID = "reId";
     public static final String FIELD_REFUSE_CONTENT = "refuseContent";
     public static final String FIELD_MESSAGE_ID = "messageId";
     public static final String FIELD_CREATE_TIME = "createTime";
     public static final String FIELD_REFUSE_STATUS = "refuseStatus";


     @Id
     @GeneratedValue
     /**审核不通过理由id*/
     private Long reId;

     @NotEmpty
     @Length(max = 151)
     private String refuseContent;

     private Long messageId; //认证信息id

     private Date createTime;

     private Long refuseStatus;

    public Long getReId() {
        return reId;
    }

    public void setReId(Long reId) {
        this.reId = reId;
    }

    public void setRefuseContent(String refuseContent){
         this.refuseContent = refuseContent;
     }

     public String getRefuseContent(){
         return refuseContent;
     }

     public void setMessageId(Long messageId){
         this.messageId = messageId;
     }

     public Long getMessageId(){
         return messageId;
     }

     public void setCreateTime(Date createTime){
         this.createTime = createTime;
     }

     public Date getCreateTime(){
         return createTime;
     }

     public void setRefuseStatus(Long refuseStatus){
         this.refuseStatus = refuseStatus;
     }

     public Long getRefuseStatus(){
         return refuseStatus;
     }

     }
