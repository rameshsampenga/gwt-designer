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
package com.google.gdt.eclipse.designer.gxt;

import com.google.gdt.eclipse.designer.gxt.databinding.BindingTests;
import com.google.gdt.eclipse.designer.gxt.model.layout.LayoutTests;
import com.google.gdt.eclipse.designer.gxt.model.property.PropertyTests;
import com.google.gdt.eclipse.designer.gxt.model.widgets.WidgetsTests;

import org.eclipse.wb.tests.designer.Expectations;
import org.eclipse.wb.tests.designer.Expectations.StrValue;
import org.eclipse.wb.tests.designer.core.DesignerSuiteTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * All Ext-GWT tests.
 * 
 * @author scheglov_ke
 */
public class ExtGwtTests extends DesignerSuiteTests {
  public static final String GXT_FILE = "gxt-2.2.5-gwt22.jar";
  public static final String GXT_LOCATION = Expectations.get(
      "C:/Work/GWT/gxt-2.2.5",
      new StrValue[]{
          new StrValue("mitin-aa-mac", "/Users/mitin_aa/gwt/gxt-2.2.3"),
          new StrValue("scheglov-macpro", "/Users/scheglov/GWT/gxt-2.2.3"),
          new StrValue("flanker-desktop", "/home/flanker/Work/GWT/gxt-2.2.3")});
  public static final String GXT_LOCATION_OLD = Expectations.get(
      "C:/Work/GWT/gxt-1.2.5",
      new StrValue[]{new StrValue("mitin-aa-mac", "/Users/mitin_aa/gwt/gxt-1.2.4")});

  public static Test suite() {
    TestSuite suite = new TestSuite("gwt.GXT");
    suite.addTestSuite(ConfigureExtGwtOperationTest.class);
    suite.addTestSuite(GxtPropertyTesterTest.class);
    suite.addTest(WidgetsTests.suite());
    suite.addTest(PropertyTests.suite());
    suite.addTest(LayoutTests.suite());
    suite.addTest(BindingTests.suite());
    return suite;
  }
}
