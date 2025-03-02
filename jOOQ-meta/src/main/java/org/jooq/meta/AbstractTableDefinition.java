/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Other licenses:
 * -----------------------------------------------------------------------------
 * Commercial licenses for this work are available. These replace the above
 * ASL 2.0 and offer limited warranties, support, maintenance, and commercial
 * database integrations.
 *
 * For more information, please visit: https://www.jooq.org/legal/licensing
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package org.jooq.meta;

import static org.jooq.impl.DSL.table;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableOptions.TableType;

/**
 * A base implementation for table definitions.
 *
 * @author Lukas Eder
 */
public abstract class AbstractTableDefinition
extends AbstractElementContainerDefinition<ColumnDefinition>
implements TableDefinition {

    private List<ParameterDefinition>   parameters;
    private TableDefinition             parentTable;
    private final List<TableDefinition> childTables;
    private final TableType             tableType;

    public AbstractTableDefinition(SchemaDefinition schema, String name, String comment) {
        this(schema, name, comment, TableType.TABLE, null);
    }

    public AbstractTableDefinition(SchemaDefinition schema, String name, String comment, TableType tableType, String source) {
        super(schema, null, name, comment, source);

        this.parentTable = null;
        this.childTables = new ArrayList<>();
        this.tableType = tableType;
    }

    @Override
    public /* non-final */ boolean isSynthetic() {
        return false;
    }

    @Override
    public final List<EmbeddableDefinition> getEmbeddables() {
        return getDatabase().getEmbeddables(this);
    }

    @Override
    public final List<EmbeddableDefinition> getReferencedEmbeddables() {
        return getDatabase().getEmbeddablesByReferencingTable(this);
    }

    @Override
    public final UniqueKeyDefinition getPrimaryKey() {
        for (ColumnDefinition column : getColumns())
            if (column.getPrimaryKey() != null)
                return column.getPrimaryKey();

        return null;
    }

    @Override
    public final List<IndexDefinition> getIndexes() {
        return getDatabase().getIndexes(this);
    }

    @Override
    public final List<UniqueKeyDefinition> getUniqueKeys() {
        return getDatabase().getRelations().getUniqueKeys(this);
    }

    @Override
    public final UniqueKeyDefinition getUniqueKey(String name) {
        for (UniqueKeyDefinition uk : getUniqueKeys())
            if (uk.getInputName().equals(name))
                return uk;

        return null;
    }

    @Override
    public final List<UniqueKeyDefinition> getKeys() {
        return getDatabase().getRelations().getKeys(this);
    }

    @Override
    public final UniqueKeyDefinition getKey(String name) {
        for (UniqueKeyDefinition uk : getKeys())
            if (uk.getInputName().equals(name))
                return uk;

        return null;
    }

    @Override
    public final List<ForeignKeyDefinition> getForeignKeys() {
        return getDatabase().getRelations().getForeignKeys(this);
    }

    @Override
    public final List<ForeignKeyDefinition> getForeignKeys(TableDefinition referenced) {
        List<ForeignKeyDefinition> result = new ArrayList<>();

        for (ForeignKeyDefinition key : getForeignKeys())
            if (referenced.equals(key.getReferencedTable()))
                result.add(key);

        return result;
    }

    @Override
    public final List<CheckConstraintDefinition> getCheckConstraints() {
        return getDatabase().getRelations().getCheckConstraints(this);
    }

    @Override
    public final IdentityDefinition getIdentity() {
        for (ColumnDefinition column : getColumns())
            if (column.isIdentity())
                return new DefaultIdentityDefinition(column);

        return null;
    }

    public final void setParentTable(TableDefinition parentTable) {
        this.parentTable = parentTable;
    }

    @Override
    public final TableDefinition getParentTable() {
        return parentTable;
    }

    @Override
    public final List<TableDefinition> getChildTables() {
        return childTables;
    }

    @Override
    public final Table<Record> getTable() {
        return table(getQualifiedName());
    }

    @Override
    public final List<ColumnDefinition> getColumns() {
        return getElements();
    }

    @Override
    public final ColumnDefinition getColumn(String columnName) {
        return getElement(columnName);
    }

    @Override
    public final ColumnDefinition getColumn(String columnName, boolean ignoreCase) {
        return getElement(columnName, ignoreCase);
    }

    @Override
    public final ColumnDefinition getColumn(int columnIndex) {
        return getElement(columnIndex);
    }

    @Override
    public final List<ParameterDefinition> getParameters() {
        if (parameters == null)
            parameters = getParameters0();

        return parameters;
    }

    @Override
    public final boolean isTemporary() {
        return tableType == TableType.TEMPORARY;
    }

    @Override
    public final boolean isView() {
        return tableType == TableType.VIEW;
    }

    @Override
    public final boolean isMaterializedView() {
        return tableType == TableType.MATERIALIZED_VIEW;
    }

    @Override
    public final boolean isTableValuedFunction() {
        return tableType == TableType.FUNCTION;
    }

    @Override
    protected List<ColumnDefinition> getElements0() throws SQLException {
        return null;
    }

    protected List<ParameterDefinition> getParameters0() {
        return Collections.emptyList();
    }
}
