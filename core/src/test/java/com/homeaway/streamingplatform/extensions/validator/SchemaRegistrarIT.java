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
package com.homeaway.streamingplatform.extensions.validator;

import java.util.Map;

import com.homeaway.streamingplatform.exceptions.SchemaRegistrationException;
import com.homeaway.streamingplatform.extensions.validation.SchemaRegistrar;
import com.homeaway.streamingplatform.model.Stream;

public class SchemaRegistrarIT {

    public static class ValidSchemaRegistrar implements SchemaRegistrar {

        @Override
        public boolean isSchemaValid(Stream stream) {
            return true;
        }

        @Override
        public Stream registerSchema(Stream stream) throws SchemaRegistrationException {
            return stream;
        }

        @Override
        public String getValidationAssertion() {
            return "mock validation assertion";
        }

        @Override
        public void configure(Map<String, ?> configs) {

        }
    }
}
