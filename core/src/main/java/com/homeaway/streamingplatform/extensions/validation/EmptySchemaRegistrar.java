/* Copyright (c) 2018 Expedia Group.
 * All rights reserved.  http://www.homeaway.com

 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at

 *      http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.homeaway.streamingplatform.extensions.validation;

import java.util.Map;

import com.homeaway.streamingplatform.model.Stream;
import lombok.extern.slf4j.Slf4j;

import com.homeaway.digitalplatform.streamregistry.SchemaCompatibility;
import com.homeaway.streamingplatform.exceptions.SchemaRegistrationException;
import com.homeaway.streamingplatform.model.Schema;

@SuppressWarnings("unused")
@Slf4j
public class EmptySchemaRegistrar implements SchemaRegistrar {

    @Override
    public boolean isSchemaValid(Stream stream) {
        return false;
    }

    @Override
    public Stream registerSchema(Stream stream) throws SchemaRegistrationException {
        return null;
    }

    @Override
    public String getValidationAssertion() {
        return null;
    }

    @Override
    public void configure(Map<String, ?> configs) {
    }
}
