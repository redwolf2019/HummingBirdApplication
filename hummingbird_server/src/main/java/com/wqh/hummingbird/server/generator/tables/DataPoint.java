/*
 * This file is generated by jOOQ.
 */
package com.wqh.hummingbird.server.generator.tables;


import com.wqh.hummingbird.server.generator.HummingbirdDb;
import com.wqh.hummingbird.server.generator.Indexes;
import com.wqh.hummingbird.server.generator.Keys;
import com.wqh.hummingbird.server.generator.tables.records.DataPointRecord;

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
 * 数据点位信息
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.11"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class DataPoint extends TableImpl<DataPointRecord> {

    private static final long serialVersionUID = 533244597;

    /**
     * The reference instance of <code>hummingbird_db.data_point</code>
     */
    public static final DataPoint DATA_POINT = new DataPoint();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<DataPointRecord> getRecordType() {
        return DataPointRecord.class;
    }

    /**
     * The column <code>hummingbird_db.data_point.id</code>. 主键
     */
    public final TableField<DataPointRecord, Long> ID = createField("id", org.jooq.impl.SQLDataType.BIGINT.nullable(false).identity(true), this, "主键");

    /**
     * The column <code>hummingbird_db.data_point.device_type_id</code>. 设备类型ID
     */
    public final TableField<DataPointRecord, UShort> DEVICE_TYPE_ID = createField("device_type_id", org.jooq.impl.SQLDataType.SMALLINTUNSIGNED.nullable(false), this, "设备类型ID");

    /**
     * The column <code>hummingbird_db.data_point.data_point</code>.
     */
    public final TableField<DataPointRecord, UShort> DATA_POINT_ = createField("data_point", org.jooq.impl.SQLDataType.SMALLINTUNSIGNED.nullable(false), this, "");

    /**
     * The column <code>hummingbird_db.data_point.ability</code>. 0:R,1:W,2:RW
     */
    public final TableField<DataPointRecord, UShort> ABILITY = createField("ability", org.jooq.impl.SQLDataType.SMALLINTUNSIGNED.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.SMALLINTUNSIGNED)), this, "0:R,1:W,2:RW");

    /**
     * The column <code>hummingbird_db.data_point.data_type</code>. 数据类型
     */
    public final TableField<DataPointRecord, UShort> DATA_TYPE = createField("data_type", org.jooq.impl.SQLDataType.SMALLINTUNSIGNED.nullable(false), this, "数据类型");

    /**
     * The column <code>hummingbird_db.data_point.name</code>. 字段名称
     */
    public final TableField<DataPointRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR(50).nullable(false), this, "字段名称");

    /**
     * The column <code>hummingbird_db.data_point.description</code>. 描述
     */
    public final TableField<DataPointRecord, String> DESCRIPTION = createField("description", org.jooq.impl.SQLDataType.VARCHAR(255), this, "描述");

    /**
     * Create a <code>hummingbird_db.data_point</code> table reference
     */
    public DataPoint() {
        this(DSL.name("data_point"), null);
    }

    /**
     * Create an aliased <code>hummingbird_db.data_point</code> table reference
     */
    public DataPoint(String alias) {
        this(DSL.name(alias), DATA_POINT);
    }

    /**
     * Create an aliased <code>hummingbird_db.data_point</code> table reference
     */
    public DataPoint(Name alias) {
        this(alias, DATA_POINT);
    }

    private DataPoint(Name alias, Table<DataPointRecord> aliased) {
        this(alias, aliased, null);
    }

    private DataPoint(Name alias, Table<DataPointRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment("数据点位信息"));
    }

    public <O extends Record> DataPoint(Table<O> child, ForeignKey<O, DataPointRecord> key) {
        super(child, key, DATA_POINT);
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
        return Arrays.<Index>asList(Indexes.DATA_POINT_DATA_POINT_DEVICE_TYPE_ID_NAME_INDEX, Indexes.DATA_POINT_DATA_POINT_DEVICE_TYPE_ID_POINT_UINDEX, Indexes.DATA_POINT_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<DataPointRecord, Long> getIdentity() {
        return Keys.IDENTITY_DATA_POINT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<DataPointRecord> getPrimaryKey() {
        return Keys.KEY_DATA_POINT_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<DataPointRecord>> getKeys() {
        return Arrays.<UniqueKey<DataPointRecord>>asList(Keys.KEY_DATA_POINT_PRIMARY, Keys.KEY_DATA_POINT_DATA_POINT_DEVICE_TYPE_ID_POINT_UINDEX);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataPoint as(String alias) {
        return new DataPoint(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataPoint as(Name alias) {
        return new DataPoint(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public DataPoint rename(String name) {
        return new DataPoint(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public DataPoint rename(Name name) {
        return new DataPoint(name, null);
    }
}