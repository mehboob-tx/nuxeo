/*
 * (C) Copyright 2014 Nuxeo SA (http://nuxeo.com/) and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 *     Benoit Delbosc
 */

package org.nuxeo.ecm.platform.query.api;

/**
 * @since 6.0
 */
public interface AggregateRangeDateDefinition extends AggregateRangeDefinition {

    String getFromAsString();

    String getToAsString();

    void setFrom(String from);

    void setTo(String to);

    @Override
    AggregateRangeDateDefinition clone();
}
