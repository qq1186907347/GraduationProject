package gzt.ora21991.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.code.rule.exception.CodeRuleException;
import com.hand.hap.code.rule.service.ISysCodeRuleProcessService;
import com.hand.hap.core.IRequest;
import com.hand.hap.mail.ReceiverTypeEnum;
import com.hand.hap.mail.dto.MessageReceiver;
import com.hand.hap.mail.service.IMessageService;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import gzt.ora21991.dto.CustomerContact;
import gzt.ora21991.dto.Product;
import gzt.ora21991.mapper.CustomerContactMapper;
import gzt.ora21991.mapper.CustomerMapper;
import gzt.ora21991.mapper.ProductMapper;
import org.mybatis.spring.MyBatisSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import gzt.ora21991.dto.Customer;
import gzt.ora21991.service.ICustomerService;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
@Transactional(rollbackFor = Exception.class)
public class CustomerServiceImpl extends BaseServiceImpl<Customer> implements ICustomerService {
    @Autowired
    CustomerMapper customerMapper;
    @Autowired
    ProductMapper productMapper;
    @Autowired
    CustomerContactMapper customerContactMapper;
    @Autowired
    IMessageService iMessageService;
    private static final Logger logger = LoggerFactory.getLogger(Customer.class);
    /**
     * 定义锁对象
     */
    private Lock lock = new ReentrantLock();

    @Override
    public List<Customer> selectByCustomerId(Customer dto) {
        return customerMapper.selectByCustomerId(dto);
    }

    @Override
    public List<Customer> update(List<Customer> customers, IRequest iRequest) {
        for (Customer customer : customers) {
                if (customer.getCustomerId() == null) {
                    /**add customer*/
                    this.self().createCustomer(customer, iRequest);
                } else {
                    /**update customer*/
                    this.self().updateCustomer(customer, iRequest);
                }
            }
        return customers;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createCustomer(Customer customer, IRequest iRequest) {
        try {
            lock.lock();
            //哪个用户创建的创建的
            customer.setCreatedBy(iRequest.getUserId());
            customerMapper.insertSelective(customer);
            checkCustomerProduct(customer,iRequest);
            checkCustomerContact(customer, iRequest);
        } catch (Exception e) {
            throw e;
        } finally {
            lock.unlock();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCustomer(Customer customer, IRequest iRequest) {
        try {
            lock.lock();
            customer.setLastUpdatedBy(iRequest.getUserId());
            customerMapper.updateByPrimaryKey(customer);
            checkCustomerProduct(customer,iRequest);
            checkCustomerContact(customer, iRequest);
        } catch (Exception e) {
            throw e;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public List<Customer> selectALLCustomer(IRequest requestContext, Customer dto, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return customerMapper.selectALLCustomer(dto);
    }

    /**
     * 更新或者保存联系人
     */
    @Transactional(rollbackFor = Exception.class)
    void checkCustomerContact(Customer customer, IRequest iRequest) {
        if (customer.getCustomerId() != null) {
            //避免没有填联系人
            if (customer.getCustomerContacts() != null) {
                for (CustomerContact contact : customer.getCustomerContacts()) {
                    //add contact
                    if (contact.getContactId() == null) {
                        contact.setCustomerId(customer.getCustomerId());
                        customerContactMapper.insertSelective(contact);

                        //发送邮件通知信息已经维护-start
                        if (contact.getEmail() != null) {
                            try {
                                List<MessageReceiver> recipients = new ArrayList<>();
                                // 收件人
                                MessageReceiver messageReceiver = new MessageReceiver();
                                messageReceiver.setMessageAddress(contact.getEmail());
                                messageReceiver.setMessageType(ReceiverTypeEnum.NORMAL.getCode());
                                recipients.add(messageReceiver);
                                HashMap<String, Object> templateData = new HashMap<String, Object>();
                                templateData.put("concactName", contact.getName());
                                templateData.put("customerName", customer.getFullName());
                                //发送邮件
                                iMessageService.sendMessage(iRequest, "SEND_MAIL_CONCACT", templateData, recipients, (List<Long>) null);
                            } catch (Exception e) {
                                logger.debug("发送邮件出错！！" + e.getMessage());
                                e.printStackTrace();
                            }
                            //发送邮件通知信息已经维护-end
                        }
                    } else {
                        //update contact
                        contact.setLastUpdatedBy(iRequest.getUserId());
                        customerContactMapper.updateByPrimaryKey(contact);
                    }
                }
            }
        }

    }

    /**
     * 更新或者保存产品
     */
    @Transactional(rollbackFor = Exception.class)
    void checkCustomerProduct(Customer customer,IRequest iRequest) {
        if (customer.getCustomerId() != null) {
            if (customer.getProducts() != null) {
                for (Product product : customer.getProducts()) {
                    //add product
                    if (product.getProductId() == null) {
                        product.setCustomerId(customer.getCustomerId());
                        product.setTotalPrice(product.getPrice()*product.getManDay());
                        productMapper.insertSelective(product);
                    } else {
                        //update product
                        product.setLastUpdatedBy(iRequest.getUserId());
                        product.setTotalPrice(product.getPrice()*product.getManDay());
                        productMapper.updateByPrimaryKey(product);
                    }
                }
            }
        }
    }

}