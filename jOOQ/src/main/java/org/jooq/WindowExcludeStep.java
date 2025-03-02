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
package org.jooq;

// ...
// ...
// ...
// ...
// ...
import static org.jooq.SQLDialect.H2;
// ...
import static org.jooq.SQLDialect.MARIADB;
import static org.jooq.SQLDialect.MYSQL;
// ...
// ...
import static org.jooq.SQLDialect.POSTGRES;
// ...
// ...
// ...
// ...
import static org.jooq.SQLDialect.SQLITE;
// ...
// ...
// ...
// ...
// ...
import static org.jooq.SQLDialect.YUGABYTEDB;

import org.jetbrains.annotations.NotNull;

/**
 * This type is used for the window function DSL API.
 * <p>
 * Example: <pre><code>
 * field.firstValue()
 *      .ignoreNulls()
 *      .over()
 *      .partitionBy(AUTHOR_ID)
 *      .orderBy(PUBLISHED_IN.asc())
 *      .rowsBetweenUnboundedPreceding()
 *      .andUnboundedFollowing()
 * </code></pre>
 *
 * @param <T> The function return type
 * @author Lukas Eder
 */
public interface WindowExcludeStep<T> extends WindowFinalStep<T> {

    /**
     * Add an <code>EXCLUDE CURRENT ROW</code> clause.
     */
    @NotNull
    @Support({ H2, POSTGRES, SQLITE, YUGABYTEDB })
    WindowFinalStep<T> excludeCurrentRow();

    /**
     * Add an <code>EXCLUDE GROUP</code> clause.
     */
    @NotNull
    @Support({ H2, POSTGRES, SQLITE, YUGABYTEDB })
    WindowFinalStep<T> excludeGroup();

    /**
     * Add an <code>EXCLUDE TIES</code> clause.
     */
    @NotNull
    @Support({ H2, POSTGRES, SQLITE, YUGABYTEDB })
    WindowFinalStep<T> excludeTies();

    /**
     * Add an <code>EXCLUDE NO OTHERS</code> clause.
     */
    @NotNull
    @Support({ H2, MARIADB, MYSQL, POSTGRES, SQLITE, YUGABYTEDB })
    WindowFinalStep<T> excludeNoOthers();
}
