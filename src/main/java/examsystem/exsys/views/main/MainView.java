package examsystem.exsys.views.main;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.charts.model.Navigator;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

import examsystem.exsys.views.addotherdata.AddOtherDataView;
import examsystem.exsys.views.addquestions.AddQuestionsView;
import examsystem.exsys.views.contact.ContactView;
import examsystem.exsys.views.createexam.CreateExamView;
import examsystem.exsys.views.home.HomeView;
import examsystem.exsys.views.login.LoginView;
import examsystem.exsys.views.myexams.MyExamsView;
import examsystem.exsys.views.register.RegisterView;
import examsystem.exsys.views.result.ResultView;
import examsystem.exsys.views.takeexam.TakeExamView;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The main view is a top-level placeholder for other views.
 */
@Route(value="")
@JsModule("./styles/shared-styles.js")
@PWA(name = "ExSys", shortName = "ExSys")
@Theme(value = Lumo.class, variant = Lumo.DARK)
public class MainView extends AppLayout {

    private static final long serialVersionUID = 1L;
    private Label secure;
    private Label currentUser;
    private Button otherSecure;
    private Button logout = new Button("Kijelentkezés");
    public static final String NAME = "Secure";

    private final Tabs menu;

    public MainView() {
        HorizontalLayout imageContainer = new HorizontalLayout();
        Image logo = new Image("frontend/logo.png", "ExSys Logo");
        logo.setWidth("60%");
        imageContainer.add(logo);
        imageContainer.setVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        imageContainer.setHeight(null);
        setPrimarySection(Section.DRAWER);

        addToNavbar(true, new DrawerToggle(), imageContainer);
        createButtonLayout();

        logout.addClickListener(e -> {
            Notification.show("Not implemented");
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
        tabs.add(createTab("Főoldal", HomeView.class));
        tabs.add(createTab("Bejelentkezés", LoginView.class));
        tabs.add(createTab("Regisztráció", RegisterView.class));
        tabs.add(createTab("Vizsgáim", MyExamsView.class));
        tabs.add(createTab("Vizsga létrehozása", CreateExamView.class));
        tabs.add(createTab("Kérdések hozzáadása", AddQuestionsView.class));
        tabs.add(createTab("Egyéb adatok megadása", AddOtherDataView.class));
        tabs.add(createTab("Vizsga", TakeExamView.class));
        tabs.add(createTab("Eredmény", ResultView.class));
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
        addToNavbar(buttonLayout);
    }
}
