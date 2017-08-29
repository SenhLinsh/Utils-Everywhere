package com.linsh.lshutils.tools;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.linsh.lshutils.utils.Basic.LshIOUtils;
import com.linsh.lshutils.utils.LshArrayUtils;
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

    public long getNewContactId() {
        long contactId = -1;
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

    private void putContactId(long contactId) {
        mValues.clear();
        mValues.put(ContactsContract.RawContacts.CONTACT_ID, contactId);
        mResolver.insert(ContactsContract.RawContacts.CONTENT_URI, mValues);
    }

    public ContactBuilder buildContact() {
        if (mBuilder == null) {
            mBuilder = new ContactBuilder();
        }
        return mBuilder;
    }

    public ContactBuilder buildContact(long contactId) {
        if (mBuilder == null) {
            mBuilder = new ContactBuilder(contactId);
        }
        return mBuilder;
    }

    public class ContactBuilder {

        private long mContactId;

        private ContactBuilder() {
            this(getNewContactId());
        }

        private ContactBuilder(long contactId) {
            mContactId = contactId;
        }

        public long getContactId() {
            return mContactId;
        }

        public ContactBuilder insert(String mimeType, String column, String columnValue) {
            mValues.clear();
            mValues.put(ContactsContract.Contacts.Data.RAW_CONTACT_ID, mContactId);
            mValues.put(ContactsContract.Contacts.Data.MIMETYPE, mimeType);
            mValues.put(column, columnValue);
            mResolver.insert(ContactsContract.Data.CONTENT_URI, mValues);
            return this;
        }

        public ContactBuilder insert(String mimeType, String[] columns, String[] columnValues) {
            mValues.clear();
            mValues.put(ContactsContract.Contacts.Data.RAW_CONTACT_ID, mContactId);
            mValues.put(ContactsContract.Contacts.Data.MIMETYPE, mimeType);
            if (!LshArrayUtils.isEmpty(columns) && !LshArrayUtils.isEmpty(columnValues)) {
                for (int i = 0; i < columns.length; i++) {
                    if (i < columnValues.length) {
                        mValues.put(columns[i], columnValues[i]);
                    }
                }
            }
            mResolver.insert(ContactsContract.Data.CONTENT_URI, mValues);
            return this;
        }


        public ContactBuilder insert(String mimeType, ContentValues values) {
            mValues.clear();
            mValues.put(ContactsContract.Contacts.Data.RAW_CONTACT_ID, mContactId);
            mValues.put(ContactsContract.Contacts.Data.MIMETYPE, mimeType);
            mValues.putAll(values);
            mResolver.insert(ContactsContract.Data.CONTENT_URI, mValues);
            return this;
        }

        public ContactBuilder update(String mimeType, String column, String columnValue) {
            mValues.clear();
            mValues.put(column, columnValue);

            String where = new LshWhereBuilder().equalTo(ContactsContract.Contacts.Data.RAW_CONTACT_ID, mContactId)
                    .and().equalTo(ContactsContract.Contacts.Data.MIMETYPE, mimeType)
                    .toString();
            mResolver.update(ContactsContract.Data.CONTENT_URI, mValues, where, null);
            return this;
        }

        public ContactBuilder update(String mimeType, String[] whereColumns, String[] whereColumnValues, String[] updateColumns, String[] updateColumnValues) {
            mValues.clear();
            if (!LshArrayUtils.isEmpty(updateColumns) && !LshArrayUtils.isEmpty(updateColumnValues)) {
                for (int i = 0; i < updateColumns.length; i++) {
                    if (i < updateColumnValues.length) {
                        mValues.put(updateColumns[i], updateColumnValues[i]);
                    }
                }
            }
            LshWhereBuilder.Where where = new LshWhereBuilder().equalTo(ContactsContract.Contacts.Data.RAW_CONTACT_ID, mContactId)
                    .and().equalTo(ContactsContract.Contacts.Data.MIMETYPE, mimeType);
            if (!LshArrayUtils.isEmpty(whereColumns) && !LshArrayUtils.isEmpty(whereColumnValues)) {
                for (int i = 0; i < whereColumnValues.length; i++) {
                    if (i < whereColumnValues.length) {
                        where.and().equalTo(whereColumnValues[i], whereColumnValues[i]);
                    }
                }
            }
            mResolver.update(ContactsContract.Data.CONTENT_URI, mValues, where.toString(), null);
            return this;
        }

        public ContactBuilder update(String mimeType, ContentValues values) {
            mValues.clear();
            mValues.putAll(values);

            String where = new LshWhereBuilder().equalTo(ContactsContract.Contacts.Data.RAW_CONTACT_ID, mContactId)
                    .and().equalTo(ContactsContract.Contacts.Data.MIMETYPE, mimeType)
                    .toString();
            mResolver.update(ContactsContract.Data.CONTENT_URI, mValues, where, null);
            return this;
        }

        public ContactBuilder delete(String mimeType) {
            String where = new LshWhereBuilder().equalTo(ContactsContract.Contacts.Data.RAW_CONTACT_ID, mContactId)
                    .and().equalTo(ContactsContract.Contacts.Data.MIMETYPE, mimeType)
                    .toString();
            mResolver.delete(ContactsContract.Data.CONTENT_URI, where, null);
            return this;
        }

        public ContactBuilder delete(String mimeType, String[] whereColumns, String[] whereColumnValues) {
            LshWhereBuilder.Where where = new LshWhereBuilder().equalTo(ContactsContract.Contacts.Data.RAW_CONTACT_ID, mContactId)
                    .and().equalTo(ContactsContract.Contacts.Data.MIMETYPE, mimeType);
            if (!LshArrayUtils.isEmpty(whereColumns) && !LshArrayUtils.isEmpty(whereColumnValues)) {
                for (int i = 0; i < whereColumnValues.length; i++) {
                    if (i < whereColumnValues.length) {
                        where.and().equalTo(whereColumnValues[i], whereColumnValues[i]);
                    }
                }
            }
            mResolver.delete(ContactsContract.Data.CONTENT_URI, where.toString(), null);
            return this;
        }

        public ContactBuilder insertDisplayName(String displayName) {
            return insert(ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE,
                    ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, displayName);
        }

        public ContactBuilder updateDisplayName(String displayName) {
            return update(ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE,
                    ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, displayName);
        }

        public ContactBuilder deleteDisplayName() {
            return delete(ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
        }

        public ContactBuilder insertPhoneNumber(String phoneNumber) {
            return insert(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE,
                    ContactsContract.CommonDataKinds.Phone.NUMBER, phoneNumber);
        }

        public ContactBuilder updatePhoneNumber(String oldNumber, String newNumber) {
            return update(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE,
                    new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER}, new String[]{oldNumber},
                    new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER}, new String[]{newNumber});
        }

        public ContactBuilder deletePhoneNumber(String phoneNumber) {
            return delete(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE,
                    new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER}, new String[]{phoneNumber});
        }

        public ContactBuilder deleteAllPhoneNumbers() {
            return delete(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        }

        public ContactBuilder insertBirthday(String birthday) {
            return insert(ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE,
                    new String[]{ContactsContract.CommonDataKinds.Event.START_DATE, ContactsContract.CommonDataKinds.Event.TYPE},
                    new String[]{birthday, String.valueOf(3)});
        }

        public ContactBuilder updateBirthday(String birthday) {
            return update(ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE,
                    new String[]{ContactsContract.CommonDataKinds.Event.TYPE}, new String[]{String.valueOf(3)},
                    new String[]{ContactsContract.CommonDataKinds.Event.START_DATE}, new String[]{birthday});
        }

        public ContactBuilder deleteBirthday() {
            return delete(ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE,
                    new String[]{ContactsContract.CommonDataKinds.Event.TYPE}, new String[]{String.valueOf(3)});
        }

        public ContactBuilder insertLunarBirthday(String lunarBirthday) {
            // TODO: 17/8/15 完善定制系统的农历添加功能
            switch (LshOSUtils.getRomType()) {
                case MIUI:
                    insert("vnd.com.miui.cursor.item/lunarBirthday", "data1", lunarBirthday);
                    break;
                default:
                    break;
            }
            return this;
        }

        public ContactBuilder updateLunarBirthday(String lunarBirthday) {
            switch (LshOSUtils.getRomType()) {
                case MIUI:
                    update("vnd.com.miui.cursor.item/lunarBirthday", "data1", lunarBirthday);
                    break;
                default:
                    break;
            }
            return this;
        }

        public ContactBuilder deleteLunarBirthday() {
            switch (LshOSUtils.getRomType()) {
                case MIUI:
                    delete("vnd.com.miui.cursor.item/lunarBirthday");
                    break;
                default:
                    break;
            }
            return this;
        }

        public ContactBuilder insertNote(String note) {
            return insert(ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE,
                    ContactsContract.CommonDataKinds.Note.NOTE, note);
        }

        public ContactBuilder updateNote(String note) {
            return update(ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE,
                    ContactsContract.CommonDataKinds.Note.NOTE, note);
        }

        public ContactBuilder deleteNote() {
            return delete(ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE);
        }

        public ContactBuilder insertPhoto(byte[] photo) {
            ContentValues values = new ContentValues();
            values.put(ContactsContract.CommonDataKinds.Photo.PHOTO, photo);
            return insert(ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE, values);
        }

        public ContactBuilder updatePhoto(byte[] photo) {
            ContentValues values = new ContentValues();
            values.put(ContactsContract.CommonDataKinds.Photo.PHOTO, photo);
            return update(ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE, values);
        }

        public ContactBuilder deletePhoto() {
            return delete(ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE);
        }
    }
}
