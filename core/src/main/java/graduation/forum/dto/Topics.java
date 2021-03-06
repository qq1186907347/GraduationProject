package graduation.forum.dto;

/**Auto Generated By Hap Code Generator**/
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import org.hibernate.validator.constraints.Length;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.hand.hap.system.dto.BaseDTO;
import java.util.Date;
import org.hibernate.validator.constraints.NotEmpty;
@ExtensionAttribute(disable=true)
@Table(name = "topics")
public class Topics extends BaseDTO {

     public static final String FIELD_TOPIC_ID = "topicId";
     public static final String FIELD_USER_ID = "userId";
     public static final String FIELD_TOPIC_TITLE = "topicTitle";
     public static final String FIELD_TOPIC_CONTENT = "topicContent";
     public static final String FIELD_TOPIC_STATUS = "topicStatus";
     public static final String FIELD_CREATION_TIME = "creationTime";
     public static final String FIELD_LAST_UPDATE_TIME = "lastUpdateTime";


     @Id
     @GeneratedValue
     private Long topicId;

     private Long userId; //谁创的帖子

     @NotEmpty
     @Length(max = 100)
     private String topicTitle; //帖子主题

     @NotEmpty
     @Length(max = 151)
     private String topicContent; //帖子内容

     private Long topicStatus; //帖子状态

     private Date creationTime;

     private Date lastUpdateTime;

    @Transient
     private  String userCall;


     public void setTopicId(Long topicId){
         this.topicId = topicId;
     }

     public Long getTopicId(){
         return topicId;
     }

     public void setUserId(Long userId){
         this.userId = userId;
     }

     public Long getUserId(){
         return userId;
     }

     public void setTopicTitle(String topicTitle){
         this.topicTitle = topicTitle;
     }

     public String getTopicTitle(){
         return topicTitle;
     }

     public void setTopicContent(String topicContent){
         this.topicContent = topicContent;
     }

     public String getTopicContent(){
         return topicContent;
     }

     public void setTopicStatus(Long topicStatus){
         this.topicStatus = topicStatus;
     }

     public Long getTopicStatus(){
         return topicStatus;
     }

     public void setCreationTime(Date creationTime){
         this.creationTime = creationTime;
     }

     public Date getCreationTime(){
         return creationTime;
     }

     public void setLastUpdateTime(Date lastUpdateTime){
         this.lastUpdateTime = lastUpdateTime;
     }

     public Date getLastUpdateTime(){
         return lastUpdateTime;
     }

    public String getUserCall() {
        return userCall;
    }

    public void setUserCall(String userCall) {
        this.userCall = userCall;
    }
}
