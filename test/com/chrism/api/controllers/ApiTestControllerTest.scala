/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *      http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.chrism.api.controllers

import com.chrism.api.ApiPlaySpec
import com.chrism.api.models.{ErrorResponse, GenericResponse, PostRequest}
import com.chrism.commons.json.json4s.Json4sFormatsLike
import play.api.http.{HeaderNames, MimeTypes}
import play.api.mvc.{AnyContentAsEmpty, AnyContentAsJson, Headers}
import play.api.test.FakeRequest

final class ApiTestControllerTest extends ApiPlaySpec with Json4sFormatsLike {

  "ApiTestController GET" should {

    import play.api.test.Helpers._

    "return 200 with message for application/json Content-Type" in {
      val controller = new ApiTestController(stubControllerComponents())
      val response = controller.testGet("your mom")(
        FakeRequest(
          GET,
          "/api/test/v0/testGet/",
          Headers(HeaderNames.CONTENT_TYPE -> MimeTypes.JSON),
          AnyContentAsEmpty
        )
      )

      status(response) mustBe OK
      contentType(response) mustBe Some(MimeTypes.JSON)
      GenericResponse.fromSnakeCasedJsValue(contentAsJson(response)) mustBe GenericResponse("ACK: your mom")
    }

    "return 406 for application/xml Content-Type" in {
      val controller = new ApiTestController(stubControllerComponents())
      val response = controller.testGet("your mom")(
        FakeRequest(
          GET,
          "/api/test/v0/testGet/",
          Headers(HeaderNames.CONTENT_TYPE -> MimeTypes.XML),
          AnyContentAsEmpty
        )
      )

      status(response) mustBe NOT_ACCEPTABLE
      ErrorResponse.fromSnakeCasedJsValue(contentAsJson(response)) mustBe ErrorResponse.unsupportedContentType(
        MimeTypes.XML
      )
    }
  }

  "ApiTestController POST" should {

    import play.api.test.Helpers._

    "return 200 for correctly formatted request" in {
      val controller = new ApiTestController(stubControllerComponents())
      val response = controller.testPost()(
        FakeRequest(
          POST,
          "/api/test/v0/testPost/",
          Headers(HeaderNames.CONTENT_TYPE -> MimeTypes.JSON),
          AnyContentAsJson(PostRequest("Stewart", "Griffin", middleName = Some("Gilligan")).toJsValue)
        )
      )

      status(response) mustBe OK
    }

    "return 406 for application/xml Content-Type" in {
      val controller = new ApiTestController(stubControllerComponents())
      val response = controller.testPost()(
        FakeRequest(
          POST,
          "/api/test/v0/testPost/",
          Headers(HeaderNames.CONTENT_TYPE -> MimeTypes.XML),
          AnyContentAsJson(PostRequest("Jules", "Winnfield").toJsValue)
        )
      )

      status(response) mustBe NOT_ACCEPTABLE
      ErrorResponse.fromSnakeCasedJsValue(contentAsJson(response)) mustBe ErrorResponse.unsupportedContentType(
        MimeTypes.XML
      )
    }

    "return 406 for missing body" in {
      val controller = new ApiTestController(stubControllerComponents())
      val response = controller.testPost()(
        FakeRequest(
          POST,
          "/api/test/v0/testPost/",
          Headers(HeaderNames.CONTENT_TYPE -> MimeTypes.XML),
          AnyContentAsEmpty
        )
      )

      status(response) mustBe NOT_ACCEPTABLE
    }
  }
}
