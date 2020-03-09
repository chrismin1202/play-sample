<!---
 Licensed to the Apache Software Foundation (ASF) under one or more
 contributor license agreements.  See the NOTICE file distributed with
 this work for additional information regarding copyright ownership.
 The ASF licenses this file to You under the Apache License, Version 2.0
 (the "License"); you may not use this file except in compliance with
 the License.  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
-->

play-sample
===========

`play-sample` contains a simple Play Framework example.

More details to come.

How to Run
----------

First, compile
```
sbt compile
```
You can optionally, `clean`:
```
sbt clean compile
```

Run locally
```
sbt run
```
or to run after (re)compiling
```
sbt clean compile run
```

<br />

Go to [Swagger](http://localhost:9000/docs/api) and play with the endpoints.Alternatively, you can use `curl` or Postman.

<br />

Run tests
```
sbt test
```
or to run tests after (re)compiling
```
sbt clean compile test
```

License
-------
This code is under the [Apache Licence v2](https://www.apache.org/licenses/LICENSE-2.0).
