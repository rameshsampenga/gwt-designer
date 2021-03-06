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
package com.google.gdt.eclipse.designer.gxt.gefTree.part;

import com.google.gdt.eclipse.designer.gefTree.part.UIObjectEditPart;
import com.google.gdt.eclipse.designer.gxt.gefTree.DropLayoutEditPolicy;
import com.google.gdt.eclipse.designer.gxt.model.layout.LayoutInfo;
import com.google.gdt.eclipse.designer.gxt.model.widgets.LayoutContainerInfo;

import org.eclipse.wb.core.gefTree.policy.layout.LayoutPolicyUtils;
import org.eclipse.wb.gef.core.EditPart;
import org.eclipse.wb.gef.core.policies.EditPolicy;
import org.eclipse.wb.gef.tree.policies.LayoutEditPolicy;

/**
 * {@link EditPart} for {@link LayoutContainerInfo}.
 * 
 * @author scheglov_ke
 * @coverage ExtGWT.gefTree.part
 */
public class LayoutContainerEditPart extends UIObjectEditPart {
  protected final LayoutContainerInfo m_container;

  ////////////////////////////////////////////////////////////////////////////
  //
  // Constructor
  //
  ////////////////////////////////////////////////////////////////////////////
  public LayoutContainerEditPart(LayoutContainerInfo container) {
    super(container);
    m_container = container;
  }

  ////////////////////////////////////////////////////////////////////////////
  //
  // Policies
  //
  ////////////////////////////////////////////////////////////////////////////
  private LayoutInfo m_currentLayout;

  @Override
  protected void createEditPolicies() {
    super.createEditPolicies();
    // support for dropping Layout_Info's
    if (m_container.hasLayout()) {
      installEditPolicy(new DropLayoutEditPolicy(m_container));
    }
  }

  @Override
  protected void refreshEditPolicies() {
    super.refreshEditPolicies();
    // support for dropping components
    if (m_container.hasLayout()) {
      LayoutInfo layout = m_container.getLayout();
      if (m_currentLayout != layout) {
        LayoutEditPolicy policy = LayoutPolicyUtils.createLayoutEditPolicy(this, layout);
        if (policy != null) {
          m_currentLayout = layout;
          installEditPolicy(EditPolicy.LAYOUT_ROLE, policy);
        } else {
          installEditPolicy(EditPolicy.LAYOUT_ROLE, null);
        }
      }
    }
  }
}
