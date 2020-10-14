/**
 * Copyright (C) 2018-2020 Expedia, Inc.
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
package com.expediagroup.streamplatform.streamregistry.core.accesscontrol.resourcemapper;

import static com.expediagroup.streamplatform.streamregistry.core.accesscontrol.domain.AcessControlledResourceFields.CONSUMER_NAME;
import static com.expediagroup.streamplatform.streamregistry.core.accesscontrol.domain.AcessControlledResourceFields.DOMAIN_NAME;
import static com.expediagroup.streamplatform.streamregistry.core.accesscontrol.domain.AcessControlledResourceFields.INFRASTRUCTURE_NAME;
import static com.expediagroup.streamplatform.streamregistry.core.accesscontrol.domain.AcessControlledResourceFields.SPECIFICATION_TYPE;
import static com.expediagroup.streamplatform.streamregistry.core.accesscontrol.domain.AcessControlledResourceFields.STREAM_NAME;
import static com.expediagroup.streamplatform.streamregistry.core.accesscontrol.domain.AcessControlledResourceFields.STREAM_VERSION;
import static com.expediagroup.streamplatform.streamregistry.core.accesscontrol.domain.AcessControlledResourceFields.ZONE_NAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import com.expediagroup.streamplatform.streamregistry.core.accesscontrol.domain.AccessControlledResource;
import com.expediagroup.streamplatform.streamregistry.model.ConsumerBinding;
import com.expediagroup.streamplatform.streamregistry.model.Specification;
import com.expediagroup.streamplatform.streamregistry.model.Status;
import com.expediagroup.streamplatform.streamregistry.model.keys.ConsumerBindingKey;

public class ConsumerBindingAccessControlledResourceMapperTest {

  private ConsumerBindingAccessControlledResourceMapper underTest = new ConsumerBindingAccessControlledResourceMapper();

  @Test
  public void canMap() {
    ConsumerBinding consumerBinding = createTestConsumerBinding();
    assertEquals(true, underTest.canMap(consumerBinding));
  }

  @Test
  public void map() {
    ConsumerBinding consumerBinding = createTestConsumerBinding();
    Specification specification = consumerBinding.getSpecification();
    String specificationType = "spType";
    when(specification.getType()).thenReturn(specificationType);
    AccessControlledResource accessControlledResource = underTest.map(consumerBinding);

    assertEquals("domain", accessControlledResource.getKeys().get(DOMAIN_NAME));
    assertEquals("streamName", accessControlledResource.getKeys().get(STREAM_NAME));
    assertEquals("1", accessControlledResource.getKeys().get(STREAM_VERSION));
    assertEquals("zone", accessControlledResource.getKeys().get(ZONE_NAME));
    assertEquals("infrastructure", accessControlledResource.getKeys().get(INFRASTRUCTURE_NAME));
    assertEquals("consumer", accessControlledResource.getKeys().get(CONSUMER_NAME));

    assertEquals(specificationType, accessControlledResource.getAdditionalInfo().get(SPECIFICATION_TYPE));
  }

  private ConsumerBinding createTestConsumerBinding() {
    ConsumerBindingKey consumerBindingKey = new ConsumerBindingKey("domain", "streamName", 1, "zone", "infrastructure", "consumer");
    Specification specification = mock(Specification.class);
    Status status = mock(Status.class);
    return new ConsumerBinding(consumerBindingKey, specification, status);
  }
}