package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.workbench.mapper.ContactsMapper;
import com.bjpowernode.crm.workbench.model.Contacts;
import com.bjpowernode.crm.workbench.service.ContactsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Date 2022/9/19 10:56
 */
@Service
public class ContactsServiceImpl implements ContactsService {
    @Resource
    private ContactsMapper contactsMapper;

    @Override
    public List<Contacts> queryAllContacts(String contactsName) {
        return contactsMapper.selectAllContacts(contactsName);
    }
}
