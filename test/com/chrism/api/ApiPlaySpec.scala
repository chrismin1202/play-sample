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
package com.chrism.api

import java.nio.{file => jnf}
import java.util.UUID
import java.util.concurrent.ThreadLocalRandom

import com.chrism.commons.io.FileUtils
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.libs.Files
import play.api.libs.Files.SingletonTemporaryFileCreator
import play.api.mvc.MultipartFormData
import play.api.mvc.MultipartFormData.{BadPart, FilePart}
import play.api.test.Injecting

abstract class ApiPlaySpec extends PlaySpec with GuiceOneAppPerTest with Injecting {

  protected final def withMultipartFormDataOfPath(
    key: String,
    path: jnf.Path,
    filename: Option[String] = None,
    dataParts: Map[String, Seq[String]] = Map.empty,
    badParts: Seq[BadPart] = Seq.empty
  )(
    f: MultipartFormData[Files.TemporaryFile] => Any
  ): Unit =
    FileUtils.withTempDirectory(prefix = Some(ApiPlaySpec.randomPrefix())) { dir =>
      val name = filename.getOrElse(path.getFileName.toString)
      val file = FileUtils.writeByteArrayToFile(FileUtils.readPathAsByteArray(path), FileUtils.newFile(dir, name))
      f(
        MultipartFormData[Files.TemporaryFile](
          dataParts,
          Seq(FilePart(key, name, None, SingletonTemporaryFileCreator.create(file.toPath))),
          badParts
        )
      )
    }

  protected final def generateRandomId(): String = s"TEST-${UUID.randomUUID()}"
}

private[this] object ApiPlaySpec {

  private def randomPrefix(): String = ThreadLocalRandom.current().nextLong(1L, Long.MaxValue).toString
}
