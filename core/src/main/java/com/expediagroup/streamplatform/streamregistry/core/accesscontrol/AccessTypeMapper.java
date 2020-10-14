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
package com.expediagroup.streamplatform.streamregistry.core.accesscontrol;

import org.springframework.stereotype.Component;

import com.expediagroup.streamplatform.streamregistry.core.accesscontrol.domain.AccessType;

@Component
public class AccessTypeMapper {

  public AccessType map(Object permission) {
    String permissionString = (String) permission;

    switch (permissionString) {
      case "create":
        return AccessType.CREATE;
      case "update":
        return AccessType.UPDATE;
      case "delete":
        return AccessType.DELETE;
      default:
        throw new IllegalArgumentException("Unsupported AccessType");
    }
  }
}