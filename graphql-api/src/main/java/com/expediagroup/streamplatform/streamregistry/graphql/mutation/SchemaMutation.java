/**
 * Copyright (C) 2018-2019 Expedia, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.expediagroup.streamplatform.streamregistry.graphql.mutation;

import static com.expediagroup.streamplatform.streamregistry.graphql.StateHelper.maintainState;

import com.expediagroup.streamplatform.streamregistry.core.services.Services;
import com.expediagroup.streamplatform.streamregistry.graphql.inputs.SchemaKeyInput;
import com.expediagroup.streamplatform.streamregistry.graphql.inputs.SpecificationInput;
import com.expediagroup.streamplatform.streamregistry.graphql.inputs.StatusInput;
import com.expediagroup.streamplatform.streamregistry.model.Schema;

public class SchemaMutation {

  private Services services;

  public SchemaMutation(Services services) {
    this.services = services;
  }

  public Schema insert(SchemaKeyInput key, SpecificationInput specification) {
    return services.getSchemaService().create(asSchema(key, specification)).get();
  }

  public Schema update(SchemaKeyInput key, SpecificationInput specification) {
    return services.getSchemaService().update(asSchema(key, specification)).get();
  }

  public Schema upsert(SchemaKeyInput key, SpecificationInput specification) {
    return services.getSchemaService().upsert(asSchema(key, specification)).get();
  }

  public Boolean delete(SchemaKeyInput key) {
    throw new UnsupportedOperationException("delete");
  }

  public Schema updateStatus(SchemaKeyInput key, StatusInput status) {
    Schema schema = services.getSchemaService().read(key.asSchemaKey()).get();
    schema.setStatus(status.asStatus());
    return services.getSchemaService().update(schema).get();
  }

  private Schema asSchema(SchemaKeyInput key, SpecificationInput specification) {
    Schema schema = new Schema();
    schema.setKey(key.asSchemaKey());
    schema.setSpecification(specification.asSpecification());
    maintainState(schema, services.getSchemaService().read(schema.getKey()));
    return schema;
  }

}