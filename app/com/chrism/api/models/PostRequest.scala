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
package com.chrism.api.models

import com.chrism.commons.json.{JsonWritableCompanionLike, SnakeCasedJsonWritable}
import io.swagger.annotations.{ApiModel, ApiModelProperty}

@ApiModel(value = "PostRequest", description = "The request body for the POST example")
final case class PostRequest(
  @ApiModelProperty(name = "first_name", required = true)
  firstName: String,
  @ApiModelProperty(name = "last_name", required = true)
  lastName: String,
  @ApiModelProperty(name = "middle_name")
  middleName: Option[String] = None)
    extends SnakeCasedJsonWritable[PostRequest] {

  override def toString: String = middleName.map(m => s"$firstName $m $lastName").getOrElse(s"$firstName $lastName")
}

object PostRequest extends JsonWritableCompanionLike[PostRequest]
