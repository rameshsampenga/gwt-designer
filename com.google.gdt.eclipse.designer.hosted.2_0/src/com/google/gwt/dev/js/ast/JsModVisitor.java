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
package com.google.gwt.dev.js.ast;

import com.google.gwt.dev.jjs.InternalCompilerException;

import java.util.List;

/**
 * A visitor for iterating through and modifying an AST.
 */
public class JsModVisitor extends JsVisitor {

  private class ListContext<T extends JsVisitable<T>> implements JsContext<T> {
    private List<T> collection;
    private int index;
    private boolean removed;
    private boolean replaced;

    public boolean canInsert() {
      return true;
    }

    public boolean canRemove() {
      return true;
    }

    public void insertAfter(T node) {
      checkRemoved();
      collection.add(index + 1, node);
      didChange = true;
    }

    public void insertBefore(T node) {
      checkRemoved();
      collection.add(index++, node);
      didChange = true;
    }

    public boolean isLvalue() {
      return false;
    }

    public void removeMe() {
      checkState();
      collection.remove(index--);
      didChange = removed = true;
    }

    public void replaceMe(T node) {
      checkState();
      checkReplacement(collection.get(index), node);
      collection.set(index, node);
      didChange = replaced = true;
    }

    protected void traverse(List<T> collection) {
      this.collection = collection;
      for (index = 0; index < collection.size(); ++index) {
        removed = replaced = false;
        doTraverse(collection.get(index), this);
      }
    }

    private void checkRemoved() {
      if (removed) {
        throw new InternalCompilerException("Node was already removed");
      }
    }

    private void checkState() {
      checkRemoved();
      if (replaced) {
        throw new InternalCompilerException("Node was already replaced");
      }
    }
  }

  private class LvalueContext extends NodeContext<JsExpression> {
    @Override
    public boolean isLvalue() {
      return true;
    }
  }

  private class NodeContext<T extends JsVisitable<T>> implements JsContext<T> {
    private T node;
    private boolean replaced;

    public boolean canInsert() {
      return false;
    }

    public boolean canRemove() {
      return false;
    }

    public void insertAfter(T node) {
      throw new UnsupportedOperationException();
    }

    public void insertBefore(T node) {
      throw new UnsupportedOperationException();
    }

    public boolean isLvalue() {
      return false;
    }

    public void removeMe() {
      throw new UnsupportedOperationException();
    }

    public void replaceMe(T node) {
      if (replaced) {
        throw new InternalCompilerException("Node was already replaced");
      }
      checkReplacement(this.node, node);
      this.node = node;
      didChange = replaced = true;
    }

    protected T traverse(T node) {
      this.node = node;
      replaced = false;
      doTraverse(node, this);
      return this.node;
    }
  }

  protected static <T extends JsVisitable<T>> void checkReplacement(T origNode,
      T newNode) {
    if (newNode == null) {
      throw new InternalCompilerException("Cannot replace with null");
    }
    if (newNode == origNode) {
      throw new InternalCompilerException(
          "The replacement is the same as the original");
    }
  }

  protected boolean didChange = false;

  @Override
  public boolean didChange() {
    return didChange;
  }

  @Override
  protected <T extends JsVisitable<T>> T doAccept(T node) {
    return new NodeContext<T>().traverse(node);
  }

  @Override
  protected <T extends JsVisitable<T>> void doAcceptList(List<T> collection) {
    NodeContext<T> ctx = new NodeContext<T>();
    for (int i = 0, c = collection.size(); i < c; ++i) {
      ctx.traverse(collection.get(i));
      if (ctx.replaced) {
        collection.set(i, ctx.node);
      }
    }
  }

  protected JsExpression doAcceptLvalue(JsExpression expr) {
    return new LvalueContext().traverse(expr);
  }

  @Override
  protected <T extends JsVisitable<T>> void doAcceptWithInsertRemove(
      List<T> collection) {
    new ListContext<T>().traverse(collection);
  }

}
