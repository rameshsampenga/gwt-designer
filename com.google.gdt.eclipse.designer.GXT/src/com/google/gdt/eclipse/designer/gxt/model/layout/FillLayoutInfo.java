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
package com.google.gdt.eclipse.designer.gxt.model.layout;

import com.google.gdt.eclipse.designer.gxt.model.layout.assistant.FillLayoutAssistant;
import com.google.gdt.eclipse.designer.model.widgets.WidgetInfo;

import org.eclipse.wb.internal.core.model.creation.CreationSupport;
import org.eclipse.wb.internal.core.model.description.ComponentDescription;
import org.eclipse.wb.internal.core.utils.ast.AstEditor;

/**
 * Model for <code>FillLayout</code>.
 * 
 * @author scheglov_ke
 * @coverage ExtGWT.model.layout
 */
public final class FillLayoutInfo extends RowLayoutInfo {
  ////////////////////////////////////////////////////////////////////////////
  //
  // Constructor
  //
  ////////////////////////////////////////////////////////////////////////////
  public FillLayoutInfo(AstEditor editor,
      ComponentDescription description,
      CreationSupport creationSupport) throws Exception {
    super(editor, description, creationSupport);
  }

  ////////////////////////////////////////////////////////////////////////////
  //
  // Initialize
  //
  ////////////////////////////////////////////////////////////////////////////
  @Override
  protected void initializeLayoutAssistant() {
    new FillLayoutAssistant(this);
  }

  ////////////////////////////////////////////////////////////////////////////
  //
  // LayoutData
  //
  ////////////////////////////////////////////////////////////////////////////
  /**
   * @return {@link FillDataInfo} associated with given {@link WidgetInfo}.
   */
  public static FillDataInfo getFillData(WidgetInfo widget) {
    return (FillDataInfo) getLayoutData(widget);
  }
}
