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
package org.jooq.impl;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;
import static java.util.Collections.singleton;
import static java.util.Collections.singletonList;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.jooq.DiagnosticsContext;
import org.jooq.QueryPart;
import org.jooq.tools.JooqLogger;

/**
 * @author Lukas Eder
 */
final class DefaultDiagnosticsContext implements DiagnosticsContext {

    private static final JooqLogger log = JooqLogger.getLogger(DefaultDiagnosticsContext.class);

    final QueryPart                 part;
    final String                    message;
    ResultSet                       resultSet;
    DiagnosticsResultSet            resultSetWrapper;
    boolean                         resultSetClosing;
    int                             resultSetFetchedColumnCount;
    int                             resultSetConsumedColumnCount;
    int                             resultSetFetchedRows;
    int                             resultSetConsumedRows;
    final String                    actualStatement;
    final String                    normalisedStatement;
    final Set<String>               duplicateStatements;
    final List<String>              repeatedStatements;
    boolean                         resultSetUnnecessaryWasNullCall;
    boolean                         resultSetMissingWasNullCall;
    int                             resultSetColumnIndex;
    final Throwable                 exception;

    DefaultDiagnosticsContext(String message, String actualStatement) {
        this(message, actualStatement, null);
    }

    DefaultDiagnosticsContext(String message, String actualStatement, Throwable exception) {
        this(
            message,
            actualStatement,
            actualStatement,
            singleton(actualStatement),
            singletonList(actualStatement),
            null,
            exception
        );
    }

    DefaultDiagnosticsContext(
        String message,
        String actualStatement,
        String normalisedStatement,
        Set<String> duplicateStatements,
        List<String> repeatedStatements,
        QueryPart part,
        Throwable exception
    ) {
        this.message = message;
        this.actualStatement = actualStatement;
        this.normalisedStatement = normalisedStatement;
        this.duplicateStatements = duplicateStatements == null ? emptySet() : duplicateStatements;
        this.repeatedStatements = repeatedStatements == null ? emptyList() : repeatedStatements;
        this.part = part;
        this.exception = exception;
    }

    @Override
    public final QueryPart part() {
        return part;
    }

    @Override
    public final String message() {
        return message;
    }

    @Override
    public final ResultSet resultSet() {
        return resultSet;
    }

    @Override
    public final int resultSetConsumedRows() {
        return resultSet == null ? -1 : resultSetConsumedRows;
    }

    @Override
    public final int resultSetFetchedRows() {
        if (resultSet == null)
            return -1;

        try {
            if (resultSetClosing || resultSet.getType() != ResultSet.TYPE_FORWARD_ONLY) {
                while (resultSet.next())
                    resultSetFetchedRows++;

                resultSet.absolute(resultSetConsumedRows);
            }
        }
        catch (SQLException ignore) {}

        return resultSetFetchedRows;
    }

    @Override
    public final int resultSetConsumedColumnCount() {
        return resultSet == null ? -1 : resultSetConsumedColumnCount;
    }

    @Override
    public final int resultSetFetchedColumnCount() {
        return resultSet == null ? -1 : resultSetFetchedColumnCount;
    }

    @Override
    public final List<String> resultSetConsumedColumnNames() {
        return resultSetColumnNames(false);
    }

    @Override
    public final List<String> resultSetFetchedColumnNames() {
        return resultSetColumnNames(true);
    }

    private final List<String> resultSetColumnNames(boolean fetched) {
        List<String> result = new ArrayList<>();

        if (resultSet != null) {
            try {
                ResultSetMetaData meta = resultSet.getMetaData();

                for (int i = 1; i <= meta.getColumnCount(); i++)
                    if (fetched || resultSetWrapper.read.get(i - 1))
                        result.add(meta.getColumnLabel(i));
            }
            catch (SQLException e) {
                log.info(e);
            }
        }

        return Collections.unmodifiableList(result);
    }

    @Override
    public final boolean resultSetUnnecessaryWasNullCall() {
        return resultSet == null ? false : resultSetUnnecessaryWasNullCall;
    }

    @Override
    public final boolean resultSetMissingWasNullCall() {
        return resultSet == null ? false : resultSetMissingWasNullCall;
    }

    @Override
    public final int resultSetColumnIndex() {
        return resultSet == null ? 0 : resultSetColumnIndex;
    }

    @Override
    public final String actualStatement() {
        return actualStatement;
    }

    @Override
    public final String normalisedStatement() {
        return normalisedStatement;
    }

    @Override
    public final Set<String> duplicateStatements() {
        return Collections.unmodifiableSet(duplicateStatements);
    }

    @Override
    public final List<String> repeatedStatements() {
        return Collections.unmodifiableList(repeatedStatements);
    }

    @Override
    public final Throwable exception() {
        return exception;
    }
}
