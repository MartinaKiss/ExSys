package examsystem.exsys.Views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "contact", layout = MainTemplateView.class)
@PageTitle("Kapcsolat")
@CssImport("styles/views/kapcsolat/kapcsolat-view.css")
public class ContactView extends Div {

    private TextField fullName = new TextField();
    private TextField email = new TextField();
    private TextArea message = new TextArea();

    private Button send = new Button("Küldés");

    public ContactView() {
        setId("kapcsolat-view");
        VerticalLayout wrapper = createWrapper();
        VerticalLayout container = new VerticalLayout();

        container.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        container.setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        container.setAlignItems(FlexComponent.Alignment.CENTER);

        container.add(wrapper);

        createTitle(wrapper, "Kapcsolat");
        createLabel(wrapper, "Ha akármilyen problémája, kérdése merül fel, akkor érdeklődjön az alább megadott elérhetőségeken vagy küldjön nekünk üzenetet az adatlap kitöltésével.");
        createLabel(wrapper,"Email: info@exsys.hu");
        createLabel(wrapper,"Telefon: +36 30 556 8315");

        VerticalLayout formContainer = new VerticalLayout();
        formContainer.getElement().getStyle().set("padding-left", "0");
        createFormLayout(formContainer);
        VerticalLayout buttonContainer = new VerticalLayout();
        buttonContainer.getElement().getStyle().set("padding-left", "0");
        createButtonLayout(buttonContainer);
        wrapper.add(formContainer, buttonContainer);
        wrapper.setWidth("90%");
        wrapper.setPadding(false);
        add(container);
    }

    private void createTitle(VerticalLayout wrapper, String text) {
        H1 h1 = new H1(text);
        wrapper.add(h1);
    }

    private VerticalLayout createWrapper() {
        VerticalLayout wrapper = new VerticalLayout();
        wrapper.setId("wrapper");
        wrapper.setSpacing(true);
        wrapper.setPadding(true);
        return wrapper;
    }

    private void createLabel(VerticalLayout wrapper, String text){
        Label label = new Label(text);
        label.getElement().getStyle()
                .set("font-size", "18px");
        wrapper.add(label);
    }

    private void createFormLayout(VerticalLayout wrapper) {
        FormLayout formLayout = new FormLayout();
        FormLayout.FormItem nameFormItem = addFormItem(wrapper, formLayout,
                fullName, "Név");
        formLayout.setColspan(nameFormItem, 1);
        FormLayout.FormItem emailFormItem = addFormItem(wrapper, formLayout,
                email, "Email");
        formLayout.setColspan(emailFormItem, 1);
        FormLayout.FormItem messageFormItem = addFormItem(wrapper, formLayout,
                message, "Üzenet");
        formLayout.setColspan(messageFormItem, 2);
    }

    private void createButtonLayout(VerticalLayout wrapper) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        buttonLayout.setWidthFull();
        buttonLayout
                .setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        send.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(send);
        wrapper.add(buttonLayout);
    }

    private FormLayout.FormItem addFormItem(VerticalLayout wrapper,
            FormLayout formLayout, Component field, String fieldName) {
        FormLayout.FormItem formItem = formLayout.addFormItem(field, fieldName);
        field.getElement().getClassList().add("full-width");
        wrapper.add(formLayout);
        return formItem;
    }

}
