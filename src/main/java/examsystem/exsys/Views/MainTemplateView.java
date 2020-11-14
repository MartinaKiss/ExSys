package examsystem.exsys.Views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The main view is a top-level placeholder for other views.
 */
@Route(value="template")
@JsModule("./styles/shared-styles.js")
@Theme(value = Lumo.class, variant = Lumo.DARK)
public class MainTemplateView extends AppLayout {

    private static final long serialVersionUID = 1L;
    private Label secure;
    private Label currentUser;
    private Button otherSecure;
    private Button logout = new Button("Kijelentkezés");
    public static final String NAME = "Secure";

    private final Tabs menu;

    public MainTemplateView() {
        HorizontalLayout imageContainer = new HorizontalLayout();
        Image logo = new Image("frontend/logoDarkMode.png", "ExSys Logo");
        logo.setWidth("90%");
        imageContainer.add(logo);
        imageContainer.setVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        imageContainer.setHeight("150%");
        setPrimarySection(Section.DRAWER);
        DrawerToggle drawerToggleButton = new DrawerToggle();
        Icon drawerIcon = new Icon(VaadinIcon.MENU);
        drawerIcon.setSize("200%");
        drawerToggleButton.setIcon(drawerIcon);
        addToNavbar(true, drawerToggleButton, imageContainer);
        createButtonLayout();
        logout.addThemeVariants(ButtonVariant.LUMO_LARGE);
        logout.addClickListener(e -> {
            UI.getCurrent().navigate(MainView.class);
        });

        menu = createMenuTabs();
        addToDrawer(menu);
    }

    private static Tabs createMenuTabs() {
        final Tabs tabs = new Tabs();
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        tabs.addThemeVariants(TabsVariant.LUMO_MINIMAL);
        tabs.setId("tabs");
        tabs.add(getAvailableTabs());
        return tabs;
    }

    private static Tab[] getAvailableTabs() {
        final List<Tab> tabs = new ArrayList<>();
        tabs.add(createTab("Főoldal", MainView.class));
        tabs.add(createTab("Bejelentkezés", LoginView.class));
        tabs.add(createTab("Regisztráció", RegisterView.class));
        tabs.add(createTab("Vizsgáim", MyExamsView.class));
        tabs.add(createTab("Kapcsolat", ContactView.class));
        return tabs.toArray(new Tab[0]);
    }

    private static Tab createTab(String title, Class<? extends Component> viewClass) {
        return createTab(populateLink(new RouterLink(null, viewClass), title));
    }

    private static Tab createTab(Component content) {
        final Tab tab = new Tab();
        tab.add(content);
        return tab;
    }

    private static <T extends HasComponents> T populateLink(T a, String title) {
        a.add(title);
        return a;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        selectTab();
    }

    private void selectTab() {
        String target = RouteConfiguration.forSessionScope().getUrl(getContent().getClass());
        Optional<Component> tabToSelect = menu.getChildren().filter(tab -> {
            Component child = null;
            if(tab.getChildren().findFirst().isPresent()) {
                child = tab.getChildren().findFirst().get();
            }
            return child instanceof RouterLink && ((RouterLink) child).getHref().equals(target);
        }).findFirst();
        tabToSelect.ifPresent(tab -> menu.setSelectedTab((Tab) tab));
    }

    private void createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        buttonLayout.setWidth("100%");
        buttonLayout
                .setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        logout.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        buttonLayout.add(logout);
        buttonLayout.setPadding(true);
        addToNavbar(buttonLayout);
    }
}
