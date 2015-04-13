/**
 * (c) Copyright 2013 WibiData, Inc.
 *
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kiji.schema.util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import com.google.common.base.Preconditions;
import org.apache.commons.io.IOUtils;

import org.kiji.annotations.ApiAudience;
import org.kiji.schema.KijiIOException;
import org.kiji.schema.RuntimeInterruptedException;

/** Utility class to generate unique client IDs. */
@ApiAudience.Private
public final class JvmId {
  private static final String JVM_ID = generateJvmId();

  /**
   * Returns the unique ID for this JVM.
   *
   * <p> The ID is formatted as "hostname;unix-pid;timestamp". </p>
   *
   * @return the unique ID for this JVM.
   */
  public static String get() {
    return JVM_ID;
  }

  /**
   * Generates a unique ID for this JVM.
   *
   * <p> The ID is formatted as "hostname;unix-pid;timestamp". </p>
   *
   * @return a unique ID for this JVM.
   */
  private static String generateJvmId() {
	  final String pid = getPid();
	  final long timestamp = System.currentTimeMillis();
	  return String.format("%s@%d", pid, timestamp);
  }

  /**
   * Returns the Unix process ID of this JVM.
   *
   * @return the Unix process ID of this JVM.
   */
  public static String getPid() {
	  return java.lang.management.ManagementFactory.getRuntimeMXBean().getName();
  }

  /** Utility class may not be instantiated. */
  private JvmId() {
  }
}
