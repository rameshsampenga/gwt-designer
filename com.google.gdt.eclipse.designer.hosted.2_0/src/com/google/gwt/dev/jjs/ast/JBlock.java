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
package com.google.gwt.dev.jjs.ast;

import com.google.gwt.dev.jjs.SourceInfo;
import com.google.gwt.dev.util.collect.Lists;

import java.util.Collections;
import java.util.List;

/**
 * A list of <code>JStatements</code>.
 */
public class JBlock extends JStatement {

  private List<JStatement> statements = Collections.emptyList();

  public JBlock(SourceInfo info) {
    super(info);
  }

  /**
   * Insert a statement into this block.
   */
  public void addStmt(int index, JStatement toAdd) {
    statements = Lists.add(statements, index, toAdd);
  }

  /**
   * Add a statement to the end of this block.
   */
  public void addStmt(JStatement toAdd) {
    statements = Lists.add(statements, toAdd);
  }

  /**
   * Insert a statements into this block.
   */
  public void addStmts(int index, List<JStatement> toAdd) {
    statements = Lists.addAll(statements, index, toAdd);
  }

  /**
   * Add statements to the end of this block.
   */
  public void addStmts(List<JStatement> toAdd) {
    statements = Lists.addAll(statements, toAdd);
  }

  public void clear() {
    statements = Collections.emptyList();
  }

  /**
   * Return the statements in this block.
   */
  public List<JStatement> getStatements() {
    return statements;
  }

  /**
   * Removes the statement from this block at the specified index.
   */
  public void removeStmt(int index) {
    statements = Lists.remove(statements, index);
  }

  public void traverse(JVisitor visitor, Context ctx) {
    if (visitor.visit(this, ctx)) {
      statements = visitor.acceptWithInsertRemoveImmutable(statements);
    }
    visitor.endVisit(this, ctx);
  }

  @Override
  public boolean unconditionalControlBreak() {
    for (JStatement stmt : statements) {
      if (stmt.unconditionalControlBreak()) {
        return true;
      }
    }
    return false;
  }
}
