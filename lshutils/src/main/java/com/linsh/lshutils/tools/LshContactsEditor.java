package com.linsh.lshutils.tools;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.linsh.lshutils.utils.Basic.LshIOUtils;
import com.linsh.lshutils.utils.LshContextUtils;
import com.linsh.lshutils.utils.LshOSUtils;

/**
 * Created by Senh Linsh on 17/8/15.
 */

public class LshContactsEditor {

    private ContentResolver mResolver;
    private ContentValues mValues;
    private ContactBuilder mBuilder;

    public LshContactsEditor(ContentResolver resolver) {
        mResolver = resolver;
        mValues = new ContentValues();
    }

    public int getNewContactId() {
        int contactId = -1;
        ContentResolver resolver = LshContextUtils.get().getContentResolver();
        Cursor cursor = resolver.query(ContactsContract.RawContacts.CONTENT_URI,
                new String[]{ContactsContract.Contacts.Data._ID}, null, null, null);
        if (cursor != null) {
            cursor.moveToLast();
            contactId = cursor.getInt(0) + 1;
            putContactId(contactId);
            LshIOUtils.close(cursor);
        }
        return contactId;
    }

    private void putContactId(int contactId) {
        mValues.put(ContactsContract.Contacts.Data.RAW_CONTACT_ID, contactId);
        mResolver.insert(ContactsContract.RawContacts.CONTENT_URI, mValues);
    }

    public ContactBuilder buildContact() {
        if (mBuilder == null) {
            mBuilder = new ContactBuilder();
        }
        return mBuilder;
    }

    public ContactBuilder buildContact(int contactId) {
        if (mBuilder == null) {
            mBuilder = new ContactBuilder(contactId);
        }
        return mBuilder;
    }

    public class ContactBuilder {

        private int mContactId;

        private ContactBuilder() {
            this(getNewContactId());
        }

        private ContactBuilder(int contactId) {
            mContactId = contactId;
        }

        public ContactBuilder putDisplayName(String displayName) {
            mValues.clear();
            mValues.put(ContactsContract.Contacts.Data.RAW_CONTACT_ID, mContactId);
            mValues.put(ContactsContract.Contacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
            mValues.put(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, displayName);
            mResolver.insert(ContactsContract.Data.CONTENT_URI, mValues);
            return this;
        }

        public ContactBuilder putPhoneNumber(String phoneNumber) {
            mValues.clear();
            mValues.put(ContactsContract.Contacts.Data.RAW_CONTACT_ID, mContactId);
            mValues.put(ContactsContract.Contacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
            mValues.put(ContactsContract.CommonDataKinds.Phone.NUMBER, phoneNumber);
            mResolver.insert(ContactsContract.Data.CONTENT_URI, mValues);
            return this;
        }

        public ContactBuilder putBirthday(String birthday) {
            mValues.clear();
            mValues.put(ContactsContract.Contacts.Data.RAW_CONTACT_ID, mContactId);
            mValues.put(ContactsContract.Contacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE);
            mValues.put(ContactsContract.CommonDataKinds.Event.START_DATE, birthday);
            mValues.put(ContactsContract.CommonDataKinds.Event.TYPE, 3);
            mResolver.insert(ContactsContract.Data.CONTENT_URI, mValues);
            return this;
        }

        public ContactBuilder putLunarBirthday(String lunarBirthday) {
            mValues.clear();
            mValues.put(ContactsContract.Contacts.Data.RAW_CONTACT_ID, mContactId);
            // TODO: 17/8/15 完善定制系统的农历添加功能
            switch (LshOSUtils.getRomType()) {
                case MIUI:
                    mValues.put(ContactsContract.Contacts.Data.MIMETYPE, "vnd.com.miui.cursor.item/lunarBirthday");
                    mValues.put(ContactsContract.Contacts.Data.DATA1, lunarBirthday);
                    mResolver.insert(ContactsContract.Data.CONTENT_URI, mValues);
                    break;
                default:
                    break;
            }
            return this;
        }

        public ContactBuilder putNote(String note) {
            mValues.clear();
            mValues.put(ContactsContract.Contacts.Data.RAW_CONTACT_ID, mContactId);
            mValues.put(ContactsContract.Contacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE);
            mValues.put(ContactsContract.CommonDataKinds.Note.NOTE, note);
            mResolver.insert(ContactsContract.Data.CONTENT_URI, mValues);
            return this;
        }

        public ContactBuilder putPhoto(byte[] photo) {
            mValues.clear();
            mValues.put(ContactsContract.Contacts.Data.RAW_CONTACT_ID, mContactId);
            mValues.put(ContactsContract.Contacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE);
            mValues.put(ContactsContract.CommonDataKinds.Photo.PHOTO, photo);
            mResolver.insert(ContactsContract.Data.CONTENT_URI, mValues);
            return this;
        }
    }
}
