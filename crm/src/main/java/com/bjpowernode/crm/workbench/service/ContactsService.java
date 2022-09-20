package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.model.Contacts;

import java.util.List;

/**
 * @Date 2022/9/19 10:55
 */
public interface ContactsService {
    List<Contacts> queryAllContacts(String contactsName);
}
