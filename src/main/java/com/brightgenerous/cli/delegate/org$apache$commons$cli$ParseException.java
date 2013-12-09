package com.brightgenerous.cli.delegate;

//@formatter:off
/*
 * origin file
 *   groupId: commons-cli
 *   artifactId: commons-cli
 *   version: 1.2
 *     org.apache.commons.cli.ParseException
 * 
 * edit
 *   - package org.apache.commons.cli;
 *   + //package org.apache.commons.cli;
 * 
 *   - public class ParseException extends Exception
 *   + //public class ParseException extends Exception
 *   + class ParseException extends Exception
 */


/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
//package org.apache.commons.cli;

/**
 * Base for Exceptions thrown during parsing of a command-line.
 *
 * @author bob mcwhirter (bob @ werken.com)
 * @version $Revision: 680644 $, $Date: 2008-07-29 01:13:48 -0700 (Tue, 29 Jul 2008) $
 */
//public class ParseException extends Exception
class ParseException extends Exception
{
    /**
     * 
     */
    private static final long serialVersionUID = 7841341020193200556L;

    /**
     * Construct a new <code>ParseException</code>
     * with the specified detail message.
     *
     * @param message the detail message
     */
    public ParseException(String message)
    {
        super(message);
    }
}
