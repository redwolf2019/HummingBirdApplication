/*
 * This file is generated by jOOQ.
 */
package com.wqh.hummingbird.server.generator.tables.records;


import com.wqh.hummingbird.server.generator.tables.DeviceType;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;
import org.jooq.types.UShort;


/**
 * 设备类型表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.11"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class DeviceTypeRecord extends UpdatableRecordImpl<DeviceTypeRecord> implements Record3<UShort, String, String> {

    private static final long serialVersionUID = 412590664;

    /**
     * Setter for <code>hummingbird_db.device_type.id</code>. 设备类型ID
     */
    public void setId(UShort value) {
        set(0, value);
    }

    /**
     * Getter for <code>hummingbird_db.device_type.id</code>. 设备类型ID
     */
    public UShort getId() {
        return (UShort) get(0);
    }

    /**
     * Setter for <code>hummingbird_db.device_type.name</code>. 设备类型名称
     */
    public void setName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>hummingbird_db.device_type.name</code>. 设备类型名称
     */
    public String getName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>hummingbird_db.device_type.description</code>. 描述
     */
    public void setDescription(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>hummingbird_db.device_type.description</code>. 描述
     */
    public String getDescription() {
        return (String) get(2);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<UShort> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record3 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row3<UShort, String, String> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row3<UShort, String, String> valuesRow() {
        return (Row3) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<UShort> field1() {
        return DeviceType.DEVICE_TYPE.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return DeviceType.DEVICE_TYPE.NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return DeviceType.DEVICE_TYPE.DESCRIPTION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UShort component1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component2() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getDescription();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UShort value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getDescription();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DeviceTypeRecord value1(UShort value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DeviceTypeRecord value2(String value) {
        setName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DeviceTypeRecord value3(String value) {
        setDescription(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DeviceTypeRecord values(UShort value1, String value2, String value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached DeviceTypeRecord
     */
    public DeviceTypeRecord() {
        super(DeviceType.DEVICE_TYPE);
    }

    /**
     * Create a detached, initialised DeviceTypeRecord
     */
    public DeviceTypeRecord(UShort id, String name, String description) {
        super(DeviceType.DEVICE_TYPE);

        set(0, id);
        set(1, name);
        set(2, description);
    }
}