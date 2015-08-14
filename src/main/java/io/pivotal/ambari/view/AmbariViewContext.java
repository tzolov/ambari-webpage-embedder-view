/**
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license
 * agreements. See the NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The ASF licenses this file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License. You may obtain a
 * copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package io.pivotal.ambari.view;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.ambari.view.ViewContext;

/**
 * REST service that returns the view properties set during the instantiation of the View.
 */
public class AmbariViewContext {

  /**
   * This view has single property backend.server.url used to point to the Web application being
   * wrapped.
   */
  private static final String BACKEND_REST_SERVER_URL = "backend.server.url";

  /**
   * The Ambari View context.
   */
  @Inject
  ViewContext context;

  @GET
  @Path("/")
  @Produces({"text/plain", "application/json"})
  public Response getRestServerUrl(@Context HttpHeaders headers, @Context UriInfo ui) {

    String backendServerUrl = context.getProperties().get(BACKEND_REST_SERVER_URL);

    String jsonResponse = String.format("{ \"backendServerUrl\" : \"%s\" }", backendServerUrl);

    return Response.ok(jsonResponse).build();
  }
}
