/*
 * This file is generated by jOOQ.
 */
package com.wqh.hummingbird.server.generator.tables;


import com.wqh.hummingbird.server.generator.HummingbirdDb;
import com.wqh.hummingbird.server.generator.Indexes;
import com.wqh.hummingbird.server.generator.Keys;
import com.wqh.hummingbird.server.generator.tables.records.DeviceRecord;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;
import org.jooq.types.UShort;


/**
 * 设备表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.11"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Device extends TableImpl<DeviceRecord> {

    private static final long serialVersionUID = 35696547;

    /**
     * The reference instance of <code>hummingbird_db.device</code>
     */
    public static final Device DEVICE = new Device();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<DeviceRecord> getRecordType() {
        return DeviceRecord.class;
    }

    /**
     * The column <code>hummingbird_db.device.id</code>.
     */
    public final TableField<DeviceRecord, Long> ID = createField("id", org.jooq.impl.SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>hummingbird_db.device.type_id</code>. 设备类型
     */
    public final TableField<DeviceRecord, UShort> TYPE_ID = createField("type_id", org.jooq.impl.SQLDataType.SMALLINTUNSIGNED.nullable(false), this, "设备类型");

    /**
     * The column <code>hummingbird_db.device.code</code>. 设备编码
     */
    public final TableField<DeviceRecord, String> CODE = createField("code", org.jooq.impl.SQLDataType.VARCHAR(20).nullable(false), this, "设备编码");

    /**
     * The column <code>hummingbird_db.device.name</code>. 设备名称
     */
    public final TableField<DeviceRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR(100).nullable(false), this, "设备名称");

    /**
     * The column <code>hummingbird_db.device.online</code>. 是否在线
     */
    public final TableField<DeviceRecord, Boolean> ONLINE = createField("online", org.jooq.impl.SQLDataType.BOOLEAN.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.BOOLEAN)), this, "是否在线");

    /**
     * Create a <code>hummingbird_db.device</code> table reference
     */
    public Device() {
        this(DSL.name("device"), null);
    }

    /**
     * Create an aliased <code>hummingbird_db.device</code> table reference
     */
    public Device(String alias) {
        this(DSL.name(alias), DEVICE);
    }

    /**
     * Create an aliased <code>hummingbird_db.device</code> table reference
     */
    public Device(Name alias) {
        this(alias, DEVICE);
    }

    private Device(Name alias, Table<DeviceRecord> aliased) {
        this(alias, aliased, null);
    }

    private Device(Name alias, Table<DeviceRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment("设备表"));
    }

    public <O extends Record> Device(Table<O> child, ForeignKey<O, DeviceRecord> key) {
        super(child, key, DEVICE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return HummingbirdDb.HUMMINGBIRD_DB;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.DEVICE_DEVICE_CODE_UINDEX, Indexes.DEVICE_DEVICE_NAME_UINDEX, Indexes.DEVICE_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<DeviceRecord, Long> getIdentity() {
        return Keys.IDENTITY_DEVICE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<DeviceRecord> getPrimaryKey() {
        return Keys.KEY_DEVICE_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<DeviceRecord>> getKeys() {
        return Arrays.<UniqueKey<DeviceRecord>>asList(Keys.KEY_DEVICE_PRIMARY, Keys.KEY_DEVICE_DEVICE_CODE_UINDEX, Keys.KEY_DEVICE_DEVICE_NAME_UINDEX);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Device as(String alias) {
        return new Device(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Device as(Name alias) {
        return new Device(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Device rename(String name) {
        return new Device(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Device rename(Name name) {
        return new Device(name, null);
    }
}