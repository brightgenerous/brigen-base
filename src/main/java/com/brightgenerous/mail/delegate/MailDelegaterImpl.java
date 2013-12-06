package com.brightgenerous.mail.delegate;

import static com.brightgenerous.commons.ObjectUtils.*;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.brightgenerous.lang.Args;
import com.brightgenerous.mail.MailException;
import com.brightgenerous.mail.MailInfo;

class MailDelegaterImpl implements MailDelegater {

    {
        check();
    }

    private static void check() {
        try {
            Class.forName(Message.class.getName());
            Class.forName(MessagingException.class.getName());
            Class.forName(Session.class.getName());
            Class.forName(Transport.class.getName());
            Class.forName(AddressException.class.getName());
            Class.forName(InternetAddress.class.getName());
            Class.forName(MimeMessage.class.getName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void send(Properties prop, MailInfo info) throws MailException {
        Args.notNull(prop, "prop");
        Args.notNull(info, "info");

        MimeMessage message = new MimeMessage(Session.getDefaultInstance(prop));

        try {
            {
                InternetAddress[] addrs = getInternetAddress(info.getTos());
                if (isNotNoSize(addrs)) {
                    message.setRecipients(Message.RecipientType.TO, addrs);
                }
            }
            {
                InternetAddress[] addrs = getInternetAddress(info.getCcs());
                if (isNotNoSize(addrs)) {
                    message.setRecipients(Message.RecipientType.CC, addrs);
                }
            }
            {
                InternetAddress[] addrs = getInternetAddress(info.getBccs());
                if (isNotNoSize(addrs)) {
                    message.setRecipients(Message.RecipientType.BCC, addrs);
                }
            }
            {
                InternetAddress[] addrs = getInternetAddress(info.getReplyTos());
                if (isNotNoSize(addrs)) {
                    message.setReplyTo(addrs);
                }
            }
            {
                Date sentDate = info.getSentDate();
                if (sentDate != null) {
                    message.setSentDate(sentDate);
                }
            }
            {
                String fromAddress = info.getFromAddress();
                if (fromAddress != null) {
                    String fromPersonal = info.getFromPersonal();
                    if (fromPersonal != null) {
                        message.setFrom(new InternetAddress(fromAddress, fromPersonal));
                    } else {
                        message.setFrom(new InternetAddress(fromAddress));
                    }
                }
            }
            {
                String senderAddress = info.getSenderAddress();
                if (senderAddress != null) {
                    String senderPersonal = info.getSenderPersonal();
                    if (senderPersonal != null) {
                        message.setSender(new InternetAddress(senderAddress, senderPersonal));
                    } else {
                        message.setSender(new InternetAddress(senderAddress));
                    }
                }
            }
            {
                String subjectText = info.getSubjectText();
                if (subjectText != null) {
                    String subjectEncode = info.getSubjectEncode();
                    if (subjectEncode != null) {
                        message.setSubject(subjectText, subjectEncode);
                    } else {
                        message.setSubject(subjectText);
                    }
                }
            }
            {
                String bodyText = info.getBodyText();
                if (bodyText != null) {
                    String bodyEncode = info.getBodyEncode();
                    if (bodyEncode != null) {
                        message.setText(bodyText, bodyEncode);
                    } else {
                        message.setText(bodyText);
                    }
                }
            }

            Transport.send(message);

        } catch (UnsupportedEncodingException | MessagingException e) {
            throw new MailException(e);
        }
    }

    private static final InternetAddress[] EMPTY_ADDRESSES = new InternetAddress[0];

    private static InternetAddress[] getInternetAddress(Set<String> addrs) throws AddressException {
        if (addrs == null) {
            return null;
        }
        if (addrs.isEmpty()) {
            return EMPTY_ADDRESSES;
        }
        List<InternetAddress> ret = new ArrayList<>();
        for (String addr : addrs) {
            if (addr != null) {
                ret.add(new InternetAddress(addr));
            }
        }
        if (ret.isEmpty()) {
            return EMPTY_ADDRESSES;
        }
        return ret.toArray(new InternetAddress[ret.size()]);
    }
}
