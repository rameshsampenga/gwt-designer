/*******************************************************************************
 * Copyright 2011 Google Inc. All Rights Reserved.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.google.gwt.dev.resource.impl;

/**
 * Represents the abstract path prefix that goes between the
 * {@link ClassPathEntry} and the rest of resource's abstract path. This concept
 * allows us to specify subsets of path hierarchies orthogonally from path
 * roots. For example, a path root might be <code>/home/gwt/src/</code> and an
 * abstract path prefix might be <code>module/client/</code>. Importantly,
 * you can apply the same abstract path prefix to multiple path roots and find
 * more than one set of resources residing in disjoint locations yet occupying
 * the same logical hierarchy. Sorry this explanation is so abstract; it's how
 * we model things like the GWT module's client source path, public path, and
 * super source path.
 */
public final class PathPrefix {

  public static final PathPrefix ALL = new PathPrefix("", null);

  private final ResourceFilter filter;
  private final String prefix;
  private int priority = -1;
  private final boolean shouldReroot;

  /**
   * Construct a non-rerooting prefix.
   * 
   * @param prefix a string prefix that (1) is the empty string or (2) begins
   *          with something other than a slash and ends with a slash
   * @param filter the resource filter to use, or <code>null</code> for no
   *          filter; note that the filter must always return the same answer
   *          for the same candidate path (doing otherwise will produce
   *          inconsistent behavior in identifying available resources)
   */
  public PathPrefix(String prefix, ResourceFilter filter) {
    this(prefix, filter, false);
  }

  /**
   * Construct a prefix.
   * 
   * @param prefix a string prefix that (1) is the empty string or (2) begins
   *          with something other than a slash and ends with a slash
   * @param filter the resource filter to use, or <code>null</code> for no
   *          filter; note that the filter must always return the same answer
   *          for the same candidate path (doing otherwise will produce
   *          inconsistent behavior in identifying available resources)
   * @param shouldReroot if <code>true</code>, any matching {@link Resource}
   *          for this prefix will be rerooted to not include the initial prefix
   *          path; if <code>false</code>, the prefix will be included in a
   *          matching resource's path.
   * 
   */
  public PathPrefix(String prefix, ResourceFilter filter, boolean shouldReroot) {
    assertValidPrefix(prefix);
    this.prefix = prefix;
    this.filter = filter;
    this.shouldReroot = shouldReroot;
  }

  /**
   * Determines whether or not a given path is allowed by this path prefix by
   * checking both the prefix string and the filter.
   * 
   * @param path
   * @return
   */
  public boolean allows(String path) {
    if (!path.startsWith(prefix)) {
      return false;
    }
    if (filter == null) {
      return true;
    }
    if (shouldReroot) {
      path = getRerootedPath(path);
    }
    return filter.allows(path);
  }

  /**
   * Equality is based on prefixes representing the same string. Importantly,
   * the filter does not affect equality.
   */
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof PathPrefix) {
      if (prefix.equals(((PathPrefix) obj).prefix)) {
        return true;
      }
    }
    return false;
  }

  /**
   * The prefix.
   * 
   * @return the result is guaranteed to be non-<code>null</code>, and
   *         either be the empty string or it will not begin with a slash and
   *         will end with a slash; these guarantees are very useful when
   *         concatenating paths that incorporate prefixes
   */
  public String getPrefix() {
    return prefix;
  }

  public String getRerootedPath(String path) {
    assert (path.startsWith(prefix));
    if (shouldReroot) {
      return path.substring(prefix.length());
    } else {
      return path;
    }
  }

  @Override
  public int hashCode() {
    return prefix.hashCode();
  }

  public boolean shouldReroot() {
    return shouldReroot;
  }

  @Override
  public String toString() {
    return prefix + (shouldReroot ? "**" : "*") + (filter == null ? "" : "?");
  }

  int getPriority() {
    return priority;
  }

  void setPriority(int priority) {
    assert (this.priority == -1);
    this.priority = priority;
  }

  private void assertValidPrefix(String prefix) {
    assert (prefix != null);
    assert ("".equals(prefix) || (!prefix.startsWith("/") && prefix.endsWith("/"))) : "malformed prefix";
  }
}
