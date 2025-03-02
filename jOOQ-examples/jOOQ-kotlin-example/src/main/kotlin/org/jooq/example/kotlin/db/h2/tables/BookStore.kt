/*
 * This file is generated by jOOQ.
 */
package org.jooq.example.kotlin.db.h2.tables


import java.util.function.Function

import org.jooq.Field
import org.jooq.ForeignKey
import org.jooq.Name
import org.jooq.Record
import org.jooq.Records
import org.jooq.Row1
import org.jooq.Schema
import org.jooq.SelectField
import org.jooq.Table
import org.jooq.TableField
import org.jooq.TableOptions
import org.jooq.UniqueKey
import org.jooq.example.kotlin.db.h2.Public
import org.jooq.example.kotlin.db.h2.keys.UK_T_BOOK_STORE_NAME
import org.jooq.example.kotlin.db.h2.tables.records.BookStoreRecord
import org.jooq.impl.DSL
import org.jooq.impl.Internal
import org.jooq.impl.SQLDataType
import org.jooq.impl.TableImpl


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
open class BookStore(
    alias: Name,
    child: Table<out Record>?,
    path: ForeignKey<out Record, BookStoreRecord>?,
    aliased: Table<BookStoreRecord>?,
    parameters: Array<Field<*>?>?
): TableImpl<BookStoreRecord>(
    alias,
    Public.PUBLIC,
    child,
    path,
    aliased,
    parameters,
    DSL.comment(""),
    TableOptions.table()
) {
    companion object {

        /**
         * The reference instance of <code>PUBLIC.BOOK_STORE</code>
         */
        val BOOK_STORE: BookStore = BookStore()
    }

    /**
     * The class holding records for this type
     */
    override fun getRecordType(): Class<BookStoreRecord> = BookStoreRecord::class.java

    /**
     * The column <code>PUBLIC.BOOK_STORE.NAME</code>.
     */
    val NAME: TableField<BookStoreRecord, String?> = createField(DSL.name("NAME"), SQLDataType.VARCHAR(400).nullable(false), this, "")

    private constructor(alias: Name, aliased: Table<BookStoreRecord>?): this(alias, null, null, aliased, null)
    private constructor(alias: Name, aliased: Table<BookStoreRecord>?, parameters: Array<Field<*>?>?): this(alias, null, null, aliased, parameters)

    /**
     * Create an aliased <code>PUBLIC.BOOK_STORE</code> table reference
     */
    constructor(alias: String): this(DSL.name(alias))

    /**
     * Create an aliased <code>PUBLIC.BOOK_STORE</code> table reference
     */
    constructor(alias: Name): this(alias, null)

    /**
     * Create a <code>PUBLIC.BOOK_STORE</code> table reference
     */
    constructor(): this(DSL.name("BOOK_STORE"), null)

    constructor(child: Table<out Record>, key: ForeignKey<out Record, BookStoreRecord>): this(Internal.createPathAlias(child, key), child, key, BOOK_STORE, null)
    override fun getSchema(): Schema? = if (aliased()) null else Public.PUBLIC
    override fun getPrimaryKey(): UniqueKey<BookStoreRecord> = UK_T_BOOK_STORE_NAME
    override fun `as`(alias: String): BookStore = BookStore(DSL.name(alias), this)
    override fun `as`(alias: Name): BookStore = BookStore(alias, this)
    override fun `as`(alias: Table<*>): BookStore = BookStore(alias.getQualifiedName(), this)

    /**
     * Rename this table
     */
    override fun rename(name: String): BookStore = BookStore(DSL.name(name), null)

    /**
     * Rename this table
     */
    override fun rename(name: Name): BookStore = BookStore(name, null)

    /**
     * Rename this table
     */
    override fun rename(name: Table<*>): BookStore = BookStore(name.getQualifiedName(), null)

    // -------------------------------------------------------------------------
    // Row1 type methods
    // -------------------------------------------------------------------------
    override fun fieldsRow(): Row1<String?> = super.fieldsRow() as Row1<String?>

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    fun <U> mapping(from: (String?) -> U): SelectField<U> = convertFrom(Records.mapping(from))

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    fun <U> mapping(toType: Class<U>, from: (String?) -> U): SelectField<U> = convertFrom(toType, Records.mapping(from))
}
