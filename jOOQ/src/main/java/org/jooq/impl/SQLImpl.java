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

import static org.jooq.Clause.TEMPLATE;
import static org.jooq.impl.DSL.list;
import static org.jooq.impl.Tools.renderAndBind;
import static org.jooq.impl.Tools.stringLiteral;

import java.util.List;

import org.jooq.Clause;
import org.jooq.Context;
import org.jooq.QueryPart;
import org.jooq.SQL;
import org.jooq.impl.QOM.UEmpty;

final class SQLImpl extends AbstractQueryPart implements SQL, UEmpty {

    private static final Clause[] CLAUSES = { TEMPLATE };
    private final String          sql;
    private final List<QueryPart> substitutes;

    SQLImpl(String sql, Object... input) {
        this.sql = sql;
        this.substitutes = Tools.queryParts(input);
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {










            default:
                renderAndBind(ctx, sql, substitutes);
                break;
        }
    }

    @Override
    public final Clause[] clauses(Context<?> ctx) {
        return CLAUSES;
    }

    @Override
    public String toString() {
        return sql;
    }
}
