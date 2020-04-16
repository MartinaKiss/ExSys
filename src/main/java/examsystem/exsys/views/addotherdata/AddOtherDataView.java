package examsystem.exsys.views.addotherdata;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import examsystem.exsys.views.main.MainView;

@Route(value = "addocqs", layout = MainView.class)
@PageTitle("EV kérdések hozzáadása")
@CssImport("styles/views/evkérdésekhozzáadása/e-vkérdésekhozzáadása-view.css")
public class AddOtherDataView extends Div {

    private static final long serialVersionUID = 1L;
    public static final String NAME = "Secure";

    private Checkbox isWrongAnswerMinusPoint  = new Checkbox();
    private NumberField amountOfMinusPoint = new NumberField();
    private NumberField markFiveLimit = new NumberField();
    private NumberField markFourLimit = new NumberField();
    private NumberField markThreeLimit = new NumberField();
    private NumberField markTwoLimit = new NumberField();
    private TextArea description = new TextArea();

    private Button save = new Button("Mentés");

    public AddOtherDataView() {
        setId("addocqs-view");
        VerticalLayout wrapper = createWrapper();
        VerticalLayout container = new VerticalLayout();

        createTitle(wrapper, "Egyéb adatok megadása");
        Paragraph description = new Paragraph("Itt tudja megadni a vizsga ponthatárait százalékban, azt, hogy a " +
                "helytelen válaszok pontlevonással járnak-e és a levont pontok számát. A Tájékozató mezőbe írhatja bele " +
                "a diákjainak szóló tájékoztatót a vizsgával kapcsolatban (fontos tudnivalók, mire érdemes figyelni, stb.). " +
                "A Mentés gombra kattintva a vizsga mentésre kerül (a vizsgákat bármikor szerkesztheti), a Vissza gombra " +
                "kattintva vissza léphet az előző oldalra és szerkesztheti az ott megadott adatokat.");
        wrapper.add(description);
        createFormLayout(wrapper);
        createButtonLayout(wrapper);
        wrapper.setWidth("90%");

        container.add(wrapper);
        container.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);

        save.addClickListener(e -> {
            Notification.show("Not implemented");
        });

        add(container);
    }

    private void createTitle(VerticalLayout wrapper, String text) {
        H1 h1 = new H1(text);
        wrapper.add(h1);
    }

    private VerticalLayout createWrapper() {
        VerticalLayout wrapper = new VerticalLayout();
        wrapper.setId("wrapper");
        wrapper.setSpacing(false);
        return wrapper;
    }

    private void createFormLayout(VerticalLayout wrapper) {
        FormLayout formLayout = new FormLayout();
        FormLayout.FormItem isWrongAnswerMinusPointFormItem = addFormItem(wrapper, formLayout,
                isWrongAnswerMinusPoint, "A rossz válaszért kerüljön pont levonásra?");
        formLayout.setColspan(isWrongAnswerMinusPointFormItem, 1);
        FormLayout.FormItem amountOfMinusPointFormItem = addFormItem(wrapper, formLayout,
                amountOfMinusPoint, "Levonásra kerülő pontok száma");
        formLayout.setColspan(amountOfMinusPointFormItem, 1);

        FormLayout.FormItem markFiveLimitFormItem = addFormItem(wrapper, formLayout,
                markFiveLimit, "5-ös ponthatár");
        formLayout.setColspan(markFiveLimitFormItem, 1);
        FormLayout.FormItem markFourLimitFormItem = addFormItem(wrapper, formLayout,
                markFourLimit, "4-es ponthatár");
        formLayout.setColspan(markFourLimitFormItem, 1);
        FormLayout.FormItem markThreeLimitFormItem = addFormItem(wrapper, formLayout,
                markThreeLimit, "3-as ponthatár");
        formLayout.setColspan(markThreeLimitFormItem, 1);
        FormLayout.FormItem markTwoLimitFormItem = addFormItem(wrapper, formLayout,
                markTwoLimit, "2-es ponthatár");
        formLayout.setColspan(markTwoLimitFormItem, 1);

        FormLayout.FormItem descriptionFormItem = addFormItem(wrapper, formLayout,
                description, "Leírás");
        formLayout.setColspan(descriptionFormItem, 2);

        wrapper.add(formLayout);
    }

    private void createButtonLayout(VerticalLayout wrapper) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        buttonLayout.setWidthFull();
        buttonLayout
                .setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save);
        wrapper.add(buttonLayout);
    }

    private FormLayout.FormItem addFormItem(VerticalLayout wrapper,
            FormLayout formLayout, Component field, String fieldName) {
        FormLayout.FormItem formItem = formLayout.addFormItem(field, fieldName);
        wrapper.add(formLayout);
        field.getElement().getClassList().add("full-width");
        return formItem;
    }

}
