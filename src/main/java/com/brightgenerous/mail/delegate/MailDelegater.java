package com.brightgenerous.mail.delegate;

import java.util.Properties;

import com.brightgenerous.mail.MailException;
import com.brightgenerous.mail.MailInfo;

interface MailDelegater {

    void send(Properties prop, MailInfo info) throws MailException;
}
