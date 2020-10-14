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

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.expediagroup.streamplatform.streamregistry.accesscontrol.AccessControlAuthoriser;
import com.expediagroup.streamplatform.streamregistry.accesscontrol.domain.AccessControlledResource;
import com.expediagroup.streamplatform.streamregistry.accesscontrol.domain.AccessType;

@Component
public class DefaultAccessControlAuthoriser implements AccessControlAuthoriser {

  @Override
  public boolean hasAccess(AccessControlledResource accessControlledResource, AccessType accessType, Authentication authentication) {
    return true;
  }
}
