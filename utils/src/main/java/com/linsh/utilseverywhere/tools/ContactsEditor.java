package com.linsh.utilseverywhere.tools;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.linsh.utilseverywhere.ContextUtils;
import com.linsh.utilseverywhere.OSUtils;
import com.linsh.utilseverywhere.module.SimpleDate;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2017/11/14
 *    desc   : 对系统联系人 (Contacts) 进行增删改的帮助类
 * </pre>
 */
public class ContactsEditor {

    private ContentResolver mResolver;
    private ContentValues mValues;
    private ContactBuilder mBuilder;

    public ContactsEditor(ContentResolver resolver) {
        mResolver = resolver;
        mValues = new ContentValues();
    }

    /**
     * 获取新的联系人 Id
     *
     * @return 新的联系人 Id
     */
    public long getNewContactId() {
        long contactId = -1;
        ContentResolver resolver = ContextUtils.get().getContentResolver();
        Cursor cursor = resolver.query(ContactsContract.RawContacts.CONTENT_URI,
                new String[]{ContactsContract.Contacts.Data._ID}, null, null, null);
        if (cursor != null) {
            cursor.moveToLast();
            contactId = cursor.getInt(0) + 1;
            putContactId(contactId);
            cursor.close();
        }
        return contactId;
    }

    /**
     * 保存 contactId
     */
    private void putContactId(long contactId) {
        mValues.clear();
        mValues.put(ContactsContract.RawContacts.CONTACT_ID, contactId);
        mResolver.insert(ContactsContract.RawContacts.CONTENT_URI, mValues);
    }

    /**
     * 创建一个 ContactBuilder 类进行增删改
     * <p>如果不指定 contactId, 默认新建一个联系人</p>
     */
    public ContactBuilder buildContact() {
        if (mBuilder == null) {
            mBuilder = new ContactBuilder();
        } else {
            mBuilder.mContactId = getNewContactId();
        }
        return mBuilder;
    }

    /**
     * 创建一个 ContactBuilder 类进行增删改
     * <p>如果指定 contactId, 默认操作该联系人</p>
     *
     * @param contactId 指定的联系人 id
     */
    public ContactBuilder buildContact(long contactId) {
        if (mBuilder == null) {
            mBuilder = new ContactBuilder(contactId);
        } else {
            mBuilder.mContactId = contactId;
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

        /**
         * 获取当前 ContactBuilder 所操作的联系人的 ContactId
         *
         * @return ContactId
         */
        public long getContactId() {
            return mContactId;
        }

        /**
         * 为该联系人插入一行数据
         *
         * @param mimeType    Mime 类型
         * @param column      列名
         * @param columnValue 列的值
         */
        public ContactBuilder insert(String mimeType, String column, String columnValue) {
            mValues.clear();
            mValues.put(ContactsContract.Contacts.Data.RAW_CONTACT_ID, mContactId);
            mValues.put(ContactsContract.Contacts.Data.MIMETYPE, mimeType);
            mValues.put(column, columnValue);
            mResolver.insert(ContactsContract.Data.CONTENT_URI, mValues);
            return this;
        }

        /**
         * 为该联系人插入一行数据
         *
         * @param mimeType     Mime 类型
         * @param columns      多个列名
         * @param columnValues 多个列的值
         * @return
         */
        public ContactBuilder insert(String mimeType, String[] columns, String[] columnValues) {
            mValues.clear();
            mValues.put(ContactsContract.Contacts.Data.RAW_CONTACT_ID, mContactId);
            mValues.put(ContactsContract.Contacts.Data.MIMETYPE, mimeType);
            if (columns != null && columns.length > 0 && columnValues != null && columnValues.length > 0) {
                for (int i = 0; i < columns.length; i++) {
                    if (i < columnValues.length) {
                        mValues.put(columns[i], columnValues[i]);
                    }
                }
            }
            mResolver.insert(ContactsContract.Data.CONTENT_URI, mValues);
            return this;
        }

        /**
         * 为该联系人插入一行数据
         *
         * @param mimeType Mime 类型
         * @param values   使用 ContentValues 封装的列与值的信息
         */
        public ContactBuilder insert(String mimeType, ContentValues values) {
            mValues.clear();
            mValues.put(ContactsContract.Contacts.Data.RAW_CONTACT_ID, mContactId);
            mValues.put(ContactsContract.Contacts.Data.MIMETYPE, mimeType);
            mValues.putAll(values);
            mResolver.insert(ContactsContract.Data.CONTENT_URI, mValues);
            return this;
        }

        /**
         * 更新数据, 将该联系人指定的列更新为指定的值
         * <P>注意: 该操作将改变该联系人下所有的行中指定列的值</P>
         *
         * @param mimeType    Mime 类型
         * @param column      列名
         * @param columnValue 列的值
         */
        public ContactBuilder update(String mimeType, String column, String columnValue) {
            mValues.clear();
            mValues.put(column, columnValue);

            String where = new WhereBuilder().equalTo(ContactsContract.Contacts.Data.RAW_CONTACT_ID, mContactId)
                    .and().equalTo(ContactsContract.Contacts.Data.MIMETYPE, mimeType)
                    .toString();
            mResolver.update(ContactsContract.Data.CONTENT_URI, mValues, where, null);
            return this;
        }

        /**
         * 更新数据
         *
         * @param mimeType           Mime 类型
         * @param whereColumns       筛选条件指定的多个列
         * @param whereColumnValues  筛选条件指定的多个列的值
         * @param updateColumns      需要更新的列
         * @param updateColumnValues 需要更新的列的值
         */
        public ContactBuilder update(String mimeType, String[] whereColumns, String[] whereColumnValues, String[] updateColumns, String[] updateColumnValues) {
            mValues.clear();
            if (whereColumns != null && whereColumns.length > 0
                    && updateColumnValues != null && updateColumnValues.length > 0) {
                for (int i = 0; i < updateColumns.length; i++) {
                    if (i < updateColumnValues.length) {
                        mValues.put(updateColumns[i], updateColumnValues[i]);
                    }
                }
            }
            WhereBuilder.Where where = new WhereBuilder().equalTo(ContactsContract.Contacts.Data.RAW_CONTACT_ID, mContactId)
                    .and().equalTo(ContactsContract.Contacts.Data.MIMETYPE, mimeType);
            if (whereColumns != null && whereColumns.length > 0
                    && whereColumnValues != null && whereColumnValues.length > 0) {
                for (int i = 0; i < whereColumnValues.length; i++) {
                    if (i < whereColumnValues.length) {
                        where.and().equalTo(whereColumns[i], whereColumnValues[i]);
                    }
                }
            }
            mResolver.update(ContactsContract.Data.CONTENT_URI, mValues, where.toString(), null);
            return this;
        }

        /**
         * 更新数据
         *
         * @param mimeType Mime 类型
         * @param values   使用 ContentValues 封装需要更新的列与值的信息
         */
        public ContactBuilder update(String mimeType, ContentValues values) {
            mValues.clear();
            mValues.putAll(values);

            String where = new WhereBuilder().equalTo(ContactsContract.Contacts.Data.RAW_CONTACT_ID, mContactId)
                    .and().equalTo(ContactsContract.Contacts.Data.MIMETYPE, mimeType)
                    .toString();
            mResolver.update(ContactsContract.Data.CONTENT_URI, mValues, where, null);
            return this;
        }

        /**
         * 删除数据, 删除该联系人指定 Mime 类型的所有数据
         *
         * @param mimeType Mime 类型
         */
        public ContactBuilder delete(String mimeType) {
            String where = new WhereBuilder().equalTo(ContactsContract.Contacts.Data.RAW_CONTACT_ID, mContactId)
                    .and().equalTo(ContactsContract.Contacts.Data.MIMETYPE, mimeType)
                    .toString();
            mResolver.delete(ContactsContract.Data.CONTENT_URI, where, null);
            return this;
        }

        /**
         * 删除数据
         *
         * @param mimeType          Mime 类型
         * @param whereColumns      筛选条件指定的多个列
         * @param whereColumnValues 筛选条件指定的多个列的值
         */
        public ContactBuilder delete(String mimeType, String[] whereColumns, String[] whereColumnValues) {
            WhereBuilder.Where where = new WhereBuilder().equalTo(ContactsContract.Contacts.Data.RAW_CONTACT_ID, mContactId)
                    .and().equalTo(ContactsContract.Contacts.Data.MIMETYPE, mimeType);
            if (whereColumns != null && whereColumns.length > 0
                    && whereColumnValues != null && whereColumnValues.length > 0) {
                for (int i = 0; i < whereColumnValues.length; i++) {
                    if (i < whereColumnValues.length) {
                        where.and().equalTo(whereColumns[i], whereColumnValues[i]);
                    }
                }
            }
            mResolver.delete(ContactsContract.Data.CONTENT_URI, where.toString(), null);
            return this;
        }

        /**
         * 插入联系人名字
         *
         * @param displayName 联系人名字
         */
        public ContactBuilder insertDisplayName(String displayName) {
            return insert(ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE,
                    ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, displayName);
        }

        /**
         * 更新联系人名字
         *
         * @param displayName 联系人名字
         */
        public ContactBuilder updateDisplayName(String displayName) {
            return update(ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE,
                    ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, displayName);
        }

        /**
         * 删除联系人名字
         */
        public ContactBuilder deleteDisplayName() {
            return delete(ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
        }

        /**
         * 插入电话号码
         *
         * @param phoneNumber 电话号码
         */
        public ContactBuilder insertPhoneNumber(String phoneNumber) {
            return insert(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE,
                    ContactsContract.CommonDataKinds.Phone.NUMBER, phoneNumber);
        }

        /**
         * 更新电话号码
         *
         * @param oldNumber 指定的旧号码
         * @param newNumber 新号码
         */
        public ContactBuilder updatePhoneNumber(String oldNumber, String newNumber) {
            return update(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE,
                    new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER}, new String[]{oldNumber},
                    new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER}, new String[]{newNumber});
        }

        /**
         * 删除指定的电话号码
         *
         * @param phoneNumber 电话号码
         */
        public ContactBuilder deletePhoneNumber(String phoneNumber) {
            return delete(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE,
                    new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER}, new String[]{phoneNumber});
        }

        /**
         * 删除该联系人所有的电话号码
         */
        public ContactBuilder deleteAllPhoneNumbers() {
            return delete(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        }

        /**
         * 插入生日
         *
         * @param birthday 生日, 注意: 国内不同的 ROM 可能再格式上有所不同, 需要特别注意
         */
        public ContactBuilder insertBirthday(String birthday) {
            return insert(ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE,
                    new String[]{ContactsContract.CommonDataKinds.Event.START_DATE, ContactsContract.CommonDataKinds.Event.TYPE},
                    new String[]{getSavedBirthdayStr(birthday), String.valueOf(3)});
        }

        /**
         * 更新生日
         *
         * @param birthday 生日
         */
        public ContactBuilder updateBirthday(String birthday) {
            return update(ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE,
                    new String[]{ContactsContract.CommonDataKinds.Event.TYPE}, new String[]{String.valueOf(3)},
                    new String[]{ContactsContract.CommonDataKinds.Event.START_DATE}, new String[]{getSavedBirthdayStr(birthday)});
        }

        /**
         * 删除生日
         */
        public ContactBuilder deleteBirthday() {
            return delete(ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE,
                    new String[]{ContactsContract.CommonDataKinds.Event.TYPE}, new String[]{String.valueOf(3)});
        }

        /**
         * 插入农历生日
         * <p>注意: 国内大部分 ROM 都会自己新增农历生日的支持, 但各自农历生日的所对应的 Mime 类型和农历生日的格式各有不同, 需要进行适配</p>
         *
         * @param lunarBirthday 农历生日
         */
        public ContactBuilder insertLunarBirthday(String lunarBirthday) {
            // TODO: 17/8/15 完善定制系统的农历添加功能
            switch (OSUtils.getRomType()) {
                case MIUI:
                    insert("vnd.com.miui.cursor.item/lunarBirthday", "data1", lunarBirthday);
                    break;
                default:
                    break;
            }
            return this;
        }

        /**
         * 更新农历生日
         * <p>注意: 国内大部分 ROM 都会自己新增农历生日的支持, 但各自农历生日的所对应的 Mime 类型和农历生日的格式各有不同, 需要进行适配</p>
         *
         * @param lunarBirthday 农历生日
         */
        public ContactBuilder updateLunarBirthday(String lunarBirthday) {
            switch (OSUtils.getRomType()) {
                case MIUI:
                    update("vnd.com.miui.cursor.item/lunarBirthday", "data1", lunarBirthday);
                    break;
                default:
                    break;
            }
            return this;
        }

        /**
         * 删除农历生日
         * <p>注意: 国内大部分 ROM 都会自己新增农历生日的支持, 但各自农历生日的所对应的 Mime 类型和农历生日的格式各有不同, 需要进行适配</p>
         */
        public ContactBuilder deleteLunarBirthday() {
            switch (OSUtils.getRomType()) {
                case MIUI:
                    delete("vnd.com.miui.cursor.item/lunarBirthday");
                    break;
                default:
                    break;
            }
            return this;
        }

        /**
         * 插入备注
         *
         * @param note 备注
         */
        public ContactBuilder insertNote(String note) {
            return insert(ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE,
                    ContactsContract.CommonDataKinds.Note.NOTE, note);
        }

        /**
         * 更新备注
         *
         * @param note 备注
         */
        public ContactBuilder updateNote(String note) {
            return update(ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE,
                    ContactsContract.CommonDataKinds.Note.NOTE, note);
        }

        /**
         * 删除备注
         */
        public ContactBuilder deleteNote() {
            return delete(ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE);
        }

        /**
         * 插入头像
         *
         * @param photo 头像图片的 Byte 数组
         */
        public ContactBuilder insertPhoto(byte[] photo) {
            ContentValues values = new ContentValues();
            values.put(ContactsContract.CommonDataKinds.Photo.PHOTO, photo);
            return insert(ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE, values);
        }

        /**
         * 更新头像
         *
         * @param photo 头像图片的 Byte 数组
         */
        public ContactBuilder updatePhoto(byte[] photo) {
            ContentValues values = new ContentValues();
            values.put(ContactsContract.CommonDataKinds.Photo.PHOTO, photo);
            return update(ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE, values);
        }

        /**
         * 删除头像
         */
        public ContactBuilder deletePhoto() {
            return delete(ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE);
        }
    }

    private String getSavedBirthdayStr(String birthday) {
        SimpleDate simpleDate = SimpleDate.parseDateString(birthday);
        if (simpleDate != null) {
            birthday = simpleDate.getNormalizedString();
            if (OSUtils.getRomType() == OSUtils.ROM.MIUI) {
                if (birthday.matches("\\d{1,2}-\\d{1,2}")) {
                    birthday = "--" + birthday;
                }
            }
        }
        return birthday;
    }
}
