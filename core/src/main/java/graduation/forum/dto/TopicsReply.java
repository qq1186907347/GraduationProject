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
@Table(name = "topics_reply")
public class TopicsReply extends BaseDTO {

     public static final String FIELD_REPLY_ID = "replyId";
     public static final String FIELD_TOPIC_ID = "topicId";
     public static final String FIELD_REPLY_CONTENT = "replyContent";
     public static final String FIELD_USER_ID = "userId";
     public static final String FIELD_REPLY_TIME = "replyTime";


     @Id
     @GeneratedValue
     private Long replyId;

     private Long topicId;

     @NotEmpty
     @Length(max = 151)
     private String replyContent;

     private Long userId;

     private Date replyTime;

     @Transient
    private String userCall;


     public void setReplyId(Long replyId){
         this.replyId = replyId;
     }

     public Long getReplyId(){
         return replyId;
     }

     public void setTopicId(Long topicId){
         this.topicId = topicId;
     }

     public Long getTopicId(){
         return topicId;
     }

     public void setReplyContent(String replyContent){
         this.replyContent = replyContent;
     }

     public String getReplyContent(){
         return replyContent;
     }

     public void setUserId(Long userId){
         this.userId = userId;
     }

     public Long getUserId(){
         return userId;
     }

     public void setReplyTime(Date replyTime){
         this.replyTime = replyTime;
     }

     public Date getReplyTime(){
         return replyTime;
     }

    public String getUserCall() {
        return userCall;
    }

    public void setUserCall(String userCall) {
        this.userCall = userCall;
    }
}
