package examsystem.exsys.Views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

@Route(value="secondarytemplate")
@JsModule("./styles/shared-styles.js")
@Theme(value = Lumo.class, variant = Lumo.DARK)
public class SecondaryTemplateView extends AppLayout {

    private Button logout = new Button("Kijelentkezés");

    public SecondaryTemplateView() {

        HorizontalLayout imageContainer = new HorizontalLayout();
        VerticalLayout container = new VerticalLayout();
        Image logo = new Image("frontend/logoDarkMode.png", "ExSys Logo");
        logo.setWidth("90%");
        imageContainer.add(logo);
        imageContainer.setVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        imageContainer.setHeight("150%");
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
