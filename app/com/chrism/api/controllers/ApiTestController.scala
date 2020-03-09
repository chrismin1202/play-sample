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

import com.chrism.api.log.LoggingLike
import com.chrism.api.models.{ErrorResponse, GenericResponse, PostRequest}
import com.chrism.commons.json.json4s.Json4sFormatsLike
import io.swagger.annotations.{
  Api,
  ApiImplicitParam,
  ApiImplicitParams,
  ApiOperation,
  ApiParam,
  ApiResponse,
  ApiResponses
}
import javax.inject.{Inject, Singleton}
import play.api.http.{HeaderNames, MimeTypes}
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents, Request, Result}

@Api
@Singleton
final class ApiTestController @Inject() (cc: ControllerComponents)
    extends AbstractController(cc)
    with Json4sFormatsLike
    with LoggingLike {

  @ApiOperation(value = "ACKs the received request.", httpMethod = "GET", consumes = "application/json")
  @ApiResponses(
    Array(
      new ApiResponse(
        code = 200,
        message = "The request has been received successfully.",
        response = classOf[GenericResponse]),
      new ApiResponse(code = 406, message = "The request is not acceptable.", response = classOf[ErrorResponse]),
    ))
  def testGet(
    @ApiParam(value = "Any value (2048 characters or less)")
    any: String
  ): Action[AnyContent] =
    Action { implicit request: Request[AnyContent] =>
      checkContentType(request) match {
        case Some(err) => err
        case _         => Ok(GenericResponse(s"ACK: $any").toJsValue)
      }
    }

  @ApiOperation(
    value = "Processes the request body if in the correct format.",
    httpMethod = "POST",
    consumes = "application/json")
  @ApiImplicitParams(
    Array(
      new ApiImplicitParam(
        value = "The request to POST",
        required = true,
        dataType = "com.chrism.api.models.PostRequest",
        paramType = "body"),
    ))
  @ApiResponses(
    Array(
      new ApiResponse(code = 200, message = "The request has been received successfully."),
      new ApiResponse(code = 406, message = "The request is not acceptable.", response = classOf[ErrorResponse]),
    ))
  def testPost(): Action[AnyContent] =
    Action { implicit request: Request[AnyContent] =>
      checkContentType(request) match {
        case Some(err) => err
        case _ =>
          request.body.asJson
            .map(PostRequest.fromSnakeCasedJsValue)
            .map { r =>
              logger.info(s"Received request: $r")
              Ok
            }
            .getOrElse(NotAcceptable(ErrorResponse(s"The body ${request.body} is not parsable!").toJsValue))
      }
    }

  // TODO: add an example for multipart/form-data

  private def checkContentType(request: Request[AnyContent]): Option[Result] =
    request.contentType match {
      case Some(ct) =>
        if (ct.contains(MimeTypes.JSON)) {
          logger.info(s"The ${HeaderNames.CONTENT_TYPE} is $ct")
          None
        } else {
          logger.error(
            "Expected " + MimeTypes.JSON + " as the " + HeaderNames.CONTENT_TYPE +
              ", but " + ct + " is specified as the " + HeaderNames.CONTENT_TYPE)
          Some(NotAcceptable(ErrorResponse.unsupportedContentType(ct).toJsValue))
        }
      case _ =>
        logger.error(s"The request is missing ${HeaderNames.CONTENT_TYPE}!")
        Some(NotAcceptable(ErrorResponse.MissingContentType.toJsValue))
    }
}
