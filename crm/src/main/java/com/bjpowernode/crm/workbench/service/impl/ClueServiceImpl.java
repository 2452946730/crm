package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.commons.contants.Contants;
import com.bjpowernode.crm.commons.utils.DateUtils;
import com.bjpowernode.crm.commons.utils.UUIDUtils;
import com.bjpowernode.crm.settings.model.User;
import com.bjpowernode.crm.workbench.mapper.*;
import com.bjpowernode.crm.workbench.model.*;
import com.bjpowernode.crm.workbench.service.ClueService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Date 2022/8/30 21:07
 */
@Service
public class ClueServiceImpl implements ClueService {
    private ClueMapper clueMapper;
    private ClueRemarkMapper clueRemarkMapper;
    private ClueActivityRelationMapper clueActivityRelationMapper;
    private CustomerMapper customerMapper;
    private CustomerRemarkMapper customerRemarkMapper;
    private ContactsMapper contactsMapper;
    private ContactsActivityRelationMapper contactsActivityRelationMapper;
    private ContactsRemarkMapper contactsRemarkMapper;
    private TranMapper tranMapper;
    private TranRemarkMapper tranRemarkMapper;

    public ClueServiceImpl(ClueMapper clueMapper, ClueRemarkMapper clueRemarkMapper,
                           ClueActivityRelationMapper clueActivityRelationMapper, CustomerMapper customerMapper,
                           CustomerRemarkMapper customerRemarkMapper, ContactsMapper contactsMapper,
                           ContactsActivityRelationMapper contactsActivityRelationMapper, ContactsRemarkMapper contactsRemarkMapper,
                           TranMapper tranMapper, TranRemarkMapper tranRemarkMapper) {
        this.clueMapper = clueMapper;
        this.clueRemarkMapper = clueRemarkMapper;
        this.clueActivityRelationMapper = clueActivityRelationMapper;
        this.customerMapper = customerMapper;
        this.customerRemarkMapper = customerRemarkMapper;
        this.contactsMapper = contactsMapper;
        this.contactsActivityRelationMapper = contactsActivityRelationMapper;
        this.contactsRemarkMapper = contactsRemarkMapper;
        this.tranMapper = tranMapper;
        this.tranRemarkMapper = tranRemarkMapper;
    }

    @Override
    public int saveCreateClue(Clue clue) {
        return clueMapper.insertClue(clue);
    }

    @Override
    public List<Clue> queryClueByConditionForPage(Map<String,Object> map) {
        return clueMapper.selectClueByConditionForPage(map);
    }

    @Override
    public int queryTotalRows(Map<String,Object> map) {
        return clueMapper.selectTotalRows(map);
    }

    @Override
    public void deleteClueByIds(String[] ids) {
        clueMapper.deleteClueByIds(ids);
        clueRemarkMapper.deleteClueRemarkByClueIds(ids);
        clueActivityRelationMapper.deleteClueActivityRelationByClueIds(ids);
    }

    @Override
    public Clue queryClueById(String id) {
        return clueMapper.selectClueById(id);
    }

    @Override
    public int saveEditClue(Clue clue) {
        return clueMapper.updateClue(clue);
    }

    @Override
    public void saveConvertClue(Map map) {
        User user = (User) map.get("user");
        Clue clue = clueMapper.selectByPrimaryKey((String) map.get("clueId"));
        //??????????????????????????????????????????????????????
        Customer customer = new Customer();
        customer.setAddress(clue.getAddress());
        customer.setContactSummary(clue.getContactSummary());
        customer.setCreateBy(user.getId());
        customer.setCreateTime(DateUtils.formateDateTime(new Date()));
        customer.setDescription(clue.getDescription());
        customer.setId(UUIDUtils.getUUID());
        customer.setName(clue.getCompany());
        customer.setNextContactTime(clue.getNextContactTime());
        customer.setOwner(user.getId());
        customer.setPhone(clue.getPhone());
        customer.setWebsite(clue.getWebsite());
        customerMapper.insert(customer);
        //?????????????????????????????????????????????????????????
        Contacts contacts = new Contacts();
        contacts.setAddress(clue.getAddress());
        contacts.setContactSummary(clue.getContactSummary());
        contacts.setCreateBy(clue.getCreateBy());
        contacts.setCreateTime(DateUtils.formateDateTime(new Date()));
        contacts.setDescription(clue.getDescription());
        contacts.setAppellation(clue.getAppellation());
        contacts.setCustomerId(customer.getId());
        contacts.setEmail(clue.getEmail());
        contacts.setFullname(clue.getFullname());
        contacts.setId(UUIDUtils.getUUID());
        contacts.setJob(clue.getJob());
        contacts.setMphone(clue.getMphone());
        contacts.setNextContactTime(clue.getNextContactTime());
        contacts.setOwner(user.getId());
        contacts.setSource(clue.getSource());
        contactsMapper.insert(contacts);
        //???????????????id?????????????????????????????????
        List<ClueRemark> crl = clueRemarkMapper.selectClueRemarkByClueId(clue.getId());
        if(crl != null && crl.size()>0){
            CustomerRemark cusRe = null;
            List cusReList = new ArrayList();
            ContactsRemark conRe = null;
            List conReList = new ArrayList();
            for(ClueRemark cr:crl){
                // ?????????????????????????????????????????????????????????
                cusRe = new CustomerRemark();
                cusRe.setCreateBy(cr.getCreateBy());
                cusRe.setCreateTime(DateUtils.formateDateTime(new Date()));
                cusRe.setCustomerId(customer.getId());
                cusRe.setId(UUIDUtils.getUUID());
                cusRe.setEditFlag(Contants.CLUE_REMARK_EDIT_FLAG_NO);
                cusRe.setNoteContent(cr.getNoteContent());
                cusReList.add(cusRe);


                // ????????????????????????????????????????????????????????????
                conRe = new ContactsRemark();
                conRe.setCreateBy(cr.getCreateBy());
                conRe.setCreateTime(DateUtils.formateDateTime(new Date()));
                conRe.setContactsId(contacts.getId());
                conRe.setId(UUIDUtils.getUUID());
                conRe.setEditFlag(Contants.CLUE_REMARK_EDIT_FLAG_NO);
                conRe.setNoteContent(cr.getNoteContent());
                conReList.add(conRe);

            }
            customerRemarkMapper.insertCustomerRemarkByList(cusReList);
            contactsRemarkMapper.insertContactsByList(conReList);
        }
        //???????????????id??????????????????????????????????????????
        List<ClueActivityRelation> carList = clueActivityRelationMapper.selectClueActivityRelationByClueId(clue.getId());
        if(carList != null && carList.size() > 0){
            ContactsActivityRelation conActRe = null;
            List<ContactsActivityRelation> conActReList = new ArrayList<>();
            for(ClueActivityRelation car:carList){
                conActRe = new ContactsActivityRelation();
                //?????????????????????????????????????????????????????????????????????????????????????????????
                conActRe.setContactsId(contacts.getId());
                conActRe.setId(UUIDUtils.getUUID());
                conActRe.setActivityId(car.getActivityId());
                conActReList.add(conActRe);

            }
            contactsActivityRelationMapper.insertContactsActivityrelationByList(conActReList);
        }
        if("true".equals(map.get("isCreate"))){
            //????????????????????????,???????????????????????????????????????
            Tran tran = new Tran();
            tran.setActivityId((String) map.get("activityId"));
            tran.setContactsId(contacts.getId());
            tran.setCreateBy(user.getId());
            tran.setCreateTime(DateUtils.formateDateTime(new Date()));
            tran.setId(UUIDUtils.getUUID());
            tran.setCustomerId(customer.getId());
            tran.setMoney((String) map.get("money"));
            tran.setName((String) map.get("name"));
            tran.setOwner(user.getId());
            tran.setSource((String) map.get("source"));
            tran.setStage((String) map.get("stage"));
            tranMapper.insert(tran);
            //????????????????????????,???????????????????????????????????????????????????????????????
            if(crl != null && crl.size() > 0){
                TranRemark tr = null;
                List<TranRemark> trList = new ArrayList<>();
                for(ClueRemark cr:crl){
                    tr.setCreateBy(clue.getCreateBy());
                    tr.setCreateTime(clue.getCreateTime());
                    tr.setId(UUIDUtils.getUUID());
                    tr.setEditFlag(Contants.REMARK_EDIT_FLAG_NO);
                    tr.setTranId(tran.getId());
                    tr.setNoteContent(cr.getNoteContent());
                    trList.add(tr);

                }
                tranRemarkMapper.insertTramRemarkByList(trList);
            }
        }
        // ?????????????????????
        clueRemarkMapper.deleteClueRemarkByClueId(clue.getId());
        //??????????????????????????????????????????
        clueActivityRelationMapper.deleteClueActivityRelationByClueId(clue.getId());
        //????????????
        clueMapper.deleteByPrimaryKey(clue.getId());
    }
}
