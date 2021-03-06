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
package com.google.gdt.eclipse.designer.uibinder.model.widgets;

import com.google.gdt.eclipse.designer.model.widgets.support.GwtState;

import org.eclipse.wb.core.model.broadcast.EditorActivatedListener;
import org.eclipse.wb.core.model.broadcast.EditorActivatedRequest;
import org.eclipse.wb.internal.core.xml.model.IRootProcessor;
import org.eclipse.wb.internal.core.xml.model.XmlObjectInfo;

/**
 * Support for {@link UIObjectInfo} features.
 * 
 * @author scheglov_ke
 * @coverage GWT.UiBinder.model
 */
public final class UIObjectRootProcessor implements IRootProcessor {
  ////////////////////////////////////////////////////////////////////////////
  //
  // Instance
  //
  ////////////////////////////////////////////////////////////////////////////
  public static final IRootProcessor INSTANCE = new UIObjectRootProcessor();

  private UIObjectRootProcessor() {
  }

  ////////////////////////////////////////////////////////////////////////////
  //
  // IRootProcessor
  //
  ////////////////////////////////////////////////////////////////////////////
  public void process(XmlObjectInfo root) throws Exception {
    if (root instanceof UIObjectInfo) {
      UIObjectInfo object = (UIObjectInfo) root;
      reparseOnStateModification(object);
    }
  }

  private void reparseOnStateModification(UIObjectInfo object) {
    final GwtState state = object.getState();
    object.addBroadcastListener(new EditorActivatedListener() {
      public void invoke(EditorActivatedRequest request) throws Exception {
        if (state.isModified()) {
          request.requestRefresh();
        }
        state.activate();
      }
    });
  }
}
