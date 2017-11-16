package com.sencha.gxt.explorer.client.layout;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.IconProvider;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.examples.resources.client.TestData;
import com.sencha.gxt.examples.resources.client.images.ExampleImages;
import com.sencha.gxt.examples.resources.client.model.NameImageModel;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.ContentPanel.ContentPanelAppearance;
import com.sencha.gxt.widget.core.client.container.AccordionLayoutContainer;
import com.sencha.gxt.widget.core.client.tree.Tree;

@Detail(
  name = "Accordion Layout (UiBinder)",
  category = "Layouts",
  icon = "accordionlayoutuibinder",
  files = "AccordionUiBinderExample.ui.xml",
  minHeight = AccordionUiBinderExample.MIN_HEIGHT,
  minWidth = AccordionUiBinderExample.MIN_WIDTH,
  classes = { NameImageModel.class, TestData.class })
public class AccordionUiBinderExample implements IsWidget, EntryPoint {

  protected static final int MIN_HEIGHT = 320;
  protected static final int MIN_WIDTH = 200;

  private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

  interface MyUiBinder extends UiBinder<Widget, AccordionUiBinderExample> {
  }

  @UiField(provided = true)
  String dummyTextShort = TestData.DUMMY_TEXT_SHORT;
  @UiField
  ContentPanel panel;
  @UiField
  AccordionLayoutContainer con;
  @UiField
  Tree<NameImageModel, String> tree;

  @Override
  public Widget asWidget() {
    if (panel == null) {
      uiBinder.createAndBindUi(this);

      con.setActiveWidget((ContentPanel) con.getWidget(0));

      Scheduler.get().scheduleDeferred(new Command() {
        public void execute() {
          tree.expandAll();
        }
      });
    }

    return panel;
  }

  @UiFactory
  public ContentPanel createContentPanel(ContentPanelAppearance appearance) {
    return new ContentPanel(appearance);
  }

  @UiFactory
  public IconProvider<NameImageModel> createIconProvider() {
    return new IconProvider<NameImageModel>() {
      public ImageResource getIcon(NameImageModel model) {
        if (null == model.getImage()) {
          return null;
        } else if ("user-girl" == model.getImage()) {
          return ExampleImages.INSTANCE.userFemale();
        } else if ("user-kid" == model.getImage()) {
          return ExampleImages.INSTANCE.userKid();
        } else {
          return ExampleImages.INSTANCE.user();
        }
      }
    };
  }

  @UiFactory
  public TreeStore<NameImageModel> createTreeStore() {
    TreeStore<NameImageModel> store = new TreeStore<NameImageModel>(NameImageModel.KP);

    NameImageModel modelFamily = newItem("Family", null);
    store.add(modelFamily);
    store.add(modelFamily, newItem("John", "user"));
    store.add(modelFamily, newItem("Olivia", "user-girl"));
    store.add(modelFamily, newItem("Noah", "user-kid"));
    store.add(modelFamily, newItem("Emma", "user-kid"));
    store.add(modelFamily, newItem("Liam", "user-kid"));

    NameImageModel modelFriends = newItem("Friends", null);
    store.add(modelFriends);
    store.add(modelFriends, newItem("Mason", "user"));
    store.add(modelFriends, newItem("Sophia", "user-girl"));
    store.add(modelFriends, newItem("Isabella", "user-girl"));
    store.add(modelFriends, newItem("Jacob", "user"));

    return store;
  }

  @UiFactory
  public ValueProvider<NameImageModel, String> createValueProvider() {
    return new ValueProvider<NameImageModel, String>() {
      @Override
      public String getValue(NameImageModel object) {
        return object.getName();
      }

      @Override
      public void setValue(NameImageModel object, String value) {
      }

      @Override
      public String getPath() {
        return "name";
      }
    };
  }

  private NameImageModel newItem(String text, String iconStyle) {
    return new NameImageModel(text, iconStyle);
  }

  @Override
  public void onModuleLoad() {
    new ExampleContainer(this).setMinHeight(MIN_HEIGHT).setMinWidth(MIN_WIDTH).doStandalone();
  }

}
