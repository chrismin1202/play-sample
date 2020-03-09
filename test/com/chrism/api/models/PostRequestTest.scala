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

import com.chrism.commons.FunTestSuite
import com.chrism.commons.json.json4s.Json4sFormatsLike

final class PostRequestTest extends FunTestSuite with Json4sFormatsLike {

  import com.chrism.commons.json.JsonUtils.implicits._

  test("serializing request as JSON: without middle_name") {
    val request = PostRequest("Saul", "Goodman")
    val fields = request.toJsValue.asJsObject.value
    fields should have size 2
    fields.foreach(kv =>
      kv._1 match {
        case "first_name" => assert(kv._2.asJsString.value === "Saul")
        case "last_name"  => assert(kv._2.asJsString.value === "Goodman")
        case other        => fail(s"'$other' is not an expected field!")
      })
  }

  test("serializing request as JSON: with middle_name") {
    val request = PostRequest("Chandler", "Bing", middleName = Some("Muriel"))
    val fields = request.toJsValue.asJsObject.value
    fields should have size 3
    fields.foreach(kv =>
      kv._1 match {
        case "first_name"  => assert(kv._2.asJsString.value === "Chandler")
        case "middle_name" => assert(kv._2.asJsString.value === "Muriel")
        case "last_name"   => assert(kv._2.asJsString.value === "Bing")
        case other         => fail(s"'$other' is not an expected field!")
      })
  }
}
